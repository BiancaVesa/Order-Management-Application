package businessLayer;

import dataAccessLayer.*;
import model.*;
import presentation.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderBLL este clasa care asigura logica de manipulare a obiectelor Order, ce trebuie inserate in baza de date a depozitului.
 * Lucrul efectiv cu operatii pe order se realizeaza in clasa OrderDAO.
 */
public class OrderBLL {
    /**
     * Daca numele clientului, al produsului, si cantitatea acestuia sunt valide, cauta comanda in baza de date dupa numele clientului.
     * Daca comanda nu exista se va crea una cu valorile date, precum si un OrderItem cu numele clientului, al produsului si pretul acestuia.
     * Daca comanda este deja in baza de date, se modifica pretul total al OrderItem-ului corespunzator.
     * In cazul in care clientul doreste sa comande o cantitate mai mare de produs decat exista deja in baza de date, se genereaza o chitanta in format PDF cu mesajul "We are out of stock for this product!".
     *
     * @param name     numele clientului
     * @param product  numele produsului
     * @param quantity cantitatea produsului
     * @param id       ID-ul comenzii
     */
    public static void addOrder(String name, String product, int quantity, int id) {
        if (DataValidation.isValidString(name) && DataValidation.isValidString(product) && DataValidation.isValidQuantity(quantity)) {
            Product prod = ProductDAO.getProduct(product);

            if (prod.getQuantity() - quantity >= 0) {
                ProductDAO.updateProduct(product, prod.getQuantity() - quantity);
                if (!OrderDAO.findOrder(name)) {
                    Client client = ClientDAO.getClient(name);
                    OrderDAO.addOrder(name, product, quantity, id);
                    OrderItemDAO.addOrderItem(id, client.getClientId(), (prod.getPrice() * quantity));
                } else {
                    int orderId = OrderDAO.getOrder(name).getOrderId();
                    OrderDAO.addOrder(name, product, quantity, orderId);
                    OrderItem orderItem = OrderItemDAO.getOrderItem(orderId);
                    OrderItemDAO.updateOrderItem(orderId, orderItem.getTotalPrice() + (prod.getPrice() * quantity));
                }
            } else
                Bill.CreateBill("order" + id + ".pdf", "We are out of stock for this product!");
        }
    }

    /**
     * Returneaza toate comenzile existente in baza de date a depozitului.
     */
    public static List<Order> report() {
        return OrderDAO.reportOrder();
    }
}
