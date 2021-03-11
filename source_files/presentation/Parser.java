package presentation;

import businessLayer.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;
import dataAccessLayer.OrderItemDAO;
import model.*;

import java.util.*;
import java.io.*;
import java.util.List;

/**
 * Scopul clasei Parser este de a citi fisierul de intrare si de a converti sirurile de caractere continute, in operatii pe clasele BLL.
 */
public class Parser {
    /**
     * Citeste un fisier linie cu linie. Apeleaza metoda parse, care converteste fiecare linie.
     * @param fileIn fisierul cu comenzi
     * @throws FileNotFoundException
     */
    public void readAndParseFile(String fileIn) throws FileNotFoundException {
        File file = new File(fileIn);
        Scanner scan = new Scanner(file);
        int clientId = 0, orderID = 0, prodId = 0;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            clientId++;
            orderID++;
            prodId++;
            parse(line, clientId, prodId, orderID);
        }
    }

    /**
     * Fiecare linie primita ca parametru este parcursa si convertita in operatii pe clasele BLL, care mai apoi o vor transforma in statement SQL.
     * In cazul in care comanda citita este 'report', creeaza documente PDF cu continuturile fiecarul tabel din baza de date.
     * In cazul in care comanda citita este 'order', creeaza documente PDF cu cate o chitanta pentru fiecare order.
     * @param line linia curenta citita din fisier
     * @param clientId ID-ul pe care il dam unui client atunci cand este creat
     * @param productId ID-ul pe care il dam unui produs atunci cand este creat
     * @param orderId ID-ul pe care il dam unui order atunci cand este creat
     */
    public void parse(String line, int clientId, int productId, int orderId) {
        int i = line.indexOf(' ');
        int length = line.length();
        String statement = line.substring(0, i);
        if (statement.equals("Insert")) {
            int j = line.indexOf(':');
            String arg = line.substring(i, j);
            if (arg.equals(" client")) {
                int v = line.indexOf(',');
                ClientBLL.addClient(line.substring(j + 1, v), line.substring(v + 1, length), clientId);
            }
            if (arg.equals(" product")) {
                int v1 = line.indexOf(',');
                int v2 = line.indexOf(',', v1 + 1);
                ProductBLL.addProduct(line.substring(j + 1, v1), Integer.parseInt(line.substring(v1 + 1, v2).trim()), Float.parseFloat(line.substring(v2 + 1, length).trim()), productId);
            }
        }

        if (statement.equals("Delete")) {
            int j = line.indexOf(':');
            String arg = line.substring(i, j);
            if (arg.equals(" client")) {
                ClientBLL.delClient(line.substring(j + 1, length));
            }

            if (arg.equals(" product")) {
                ProductBLL.delProduct(line.substring(j + 1, length));
            }
        }

        if (statement.equals("Order:")) {
            int j = line.indexOf(':');
            int v1 = line.indexOf(',');
            int v2 = line.indexOf(',', v1 + 1);
            OrderBLL.addOrder(line.substring(j + 1, v1), line.substring(v1 + 1, v2), Integer.parseInt(line.substring(v2 + 1, length).trim()), orderId);
        }

        if (statement.equals("Report")) {
            String arg = line.substring(i, length);

            if (arg.equals(" client")) {
                List<Client> list = ClientBLL.report();
                ArrayList<String> columns = new ArrayList<String>();
                columns.add("Name");
                columns.add("Address");
                columns.add("ClientId");
                PdfPTable table = PDFTable.createTable(3, columns);
                ArrayList<String> row = new ArrayList<String>();
                for (Client c : list) {
                    row.add(c.getName());
                    row.add(c.getAddress());
                    row.add(c.clientIdToString());
                    PDFTable.addRows(row, table);
                    row.clear();
                }
                PDFTable.addTableToDoc("report-client.pdf", table);
            }
            if (arg.equals(" product")) {
                List<Product> list = ProductBLL.report();
                ArrayList<String> columns = new ArrayList<String>();
                columns.add("Name");
                columns.add("Quantity");
                columns.add("Price");
                columns.add("ProductId");
                PdfPTable table = PDFTable.createTable(4, columns);
                ArrayList<String> row = new ArrayList<String>();
                for (Product p : list) {
                    row.add(p.getName());
                    row.add(p.quantityToString());
                    row.add(p.priceToString());
                    row.add(p.prodIdToString());
                    PDFTable.addRows(row, table);
                    row.clear();
                }
                PDFTable.addTableToDoc("report-product.pdf", table);
            }
            if (arg.equals(" order")) {
                List<Order> list = OrderBLL.report();
                List<OrderItem> orderItemList = OrderItemDAO.reportOrderItem();
                ArrayList<String> columns = new ArrayList<String>();
                columns.add("Client");
                columns.add("Product");
                columns.add("ProdQty");
                columns.add("OrderId");
                PdfPTable table = PDFTable.createTable(4, columns);
                ArrayList<String> row = new ArrayList<String>();
                for (Order o : list) {
                    row.add(o.getClient());
                    row.add(o.getProduct());
                    row.add(o.prodQtyToString());
                    row.add(o.orderIdToString());
                    PDFTable.addRows(row, table);
                    row.clear();
                }
                PDFTable.addTableToDoc("report-order.pdf", table);

                String bill = new String();

                for (OrderItem oI : orderItemList) {
                    for (Order or : list) {
                        if (or.getOrderId() == oI.getOrderId()) {
                            bill += or.toString() + "\n";
                        }
                    }
                    bill += "\nTOTAL PRICE: " + oI.getTotalPrice();
                    Bill.CreateBill("order"+oI.getOrderId()+".pdf", bill);
                }

            }
        }
    }
}
