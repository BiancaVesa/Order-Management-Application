package dataAccessLayer;

import model.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Clasa OrderDAO are rolul de a implementa operatiile propriu-zise pe tabelul Order, cu ajutorul clasei AbstractDAO, care primeste ca parametru generic clasa corespunzatoare in Java (Order).
 */
public class OrderDAO extends AbstractDAO<Order> {
    /**
     * Ofera metodei find mostenita de la clasa AbstractDAO parametri de cautare (coloana "client" si String-ul cu numele clientului).
     * @param clientName numele clientului
     * @return true daca metoda find a gasit un order in tabel; false in caz contrar
     */
    public static boolean findOrder(String clientName) {
        if ((new OrderDAO()).find("client", clientName) != null)
            return true;
        return false;
    }
    /**
     * Returneaza obiectul Order obtinut in urma apelarii metodei find, mostenita de la clasa AbstraDAO, cu coloana "client" si numele clientului ca parametri.
     */
    public static Order getOrder(String clientName) {
        return (new OrderDAO()).find("client", clientName);
    }

    /**
     * Ofera metodei add, mostenita de la clasa AbstractDAO, valorile de inserat.
     * @param name numele clientului
     * @param product numele produsului
     * @param quantity cantitatea produsului
     * @param id ID-ul order-ului
     */
    public static void addOrder(String name, String product, int quantity, int id) {
        Integer q = quantity, i = id;
        List<Object> list = new ArrayList<Object>();
        list.add(name);
        list.add(product);
        list.add(q);
        list.add(i);
        (new OrderDAO()).add(list);
    }

    /**
     * Returneaza toate obiectele Order obtinute in urma apelarii metodei report, mostenita de la AbstractDAO.
     */
    public static List<Order> reportOrder() {
        return (new OrderDAO()).report();
    }
}
