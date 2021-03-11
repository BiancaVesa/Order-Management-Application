package dataAccessLayer;

import model.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Clasa OrderItemDAO are rolul de a implementa operatiile propriu-zise pe tabelul OrderItem, cu ajutorul clasei AbstractDAO, care primeste ca parametru generic clasa corespunzatoare in Java (OrderItem).
 */
public class OrderItemDAO extends AbstractDAO<OrderItem> {
    /**
     * Ofera metodei add, mostenita de la clasa AbstractDAO, valorile de inserat.
     * @param orderID ID-ul order-ului
     * @param clientId ID-ul clientului
     * @param totalPrice pretul total al unui order per client
     */
    public static void addOrderItem(int orderID, int clientId, float totalPrice) {
        Integer oID = orderID, cID = clientId;
        Float p = totalPrice;

        List<Object> list = new ArrayList<Object>();
        list.add(oID);
        list.add(cID);
        list.add(p);
        (new OrderItemDAO()).add(list);
    }

    /**
     * Returneaza obiectul OrderItem obtinut in urma apelarii metodei find, mostenita de la clasa AbstraDAO, cu coloana "orderId" si ID-ul order-ului ca parametri.
     */
    public static OrderItem getOrderItem(int orderId){
        return (new OrderItemDAO()).find("orderId", orderId);
    }

    /**
     * Ofera metodei update, mostenita de la clasa AbstractDAO, valorile de modificat si criteriul de cautare.
     * @param orderId ID-ul order-ului
     * @param price pretul order-ului
     */
    public static void updateOrderItem(int orderId, float price){
        (new OrderItemDAO()).update("totalPrice", price, "orderID", orderId);
    }

    /**
     * Returneaza un List cu toate obiectele OrderItem obtinute in urma apelarii metodei report, mostenita de la AbstractDAO.
     */
    public static List<OrderItem> reportOrderItem() {
        return (new OrderItemDAO()).report();
    }

}
