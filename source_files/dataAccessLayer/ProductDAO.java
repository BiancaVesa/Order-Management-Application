package dataAccessLayer;

import model.Product;

import java.util.ArrayList;
import java.util.List;
/**
 * Clasa Product DAO are rolul de a implementa operatiile propriu-zise pe tabelul OrderItem, cu ajutorul clasei AbstractDAO, care primeste ca parametru generic clasa corespunzatoare in Java (OrderItem).
 */
public class ProductDAO extends AbstractDAO<Product> {

    /**
     * Ofera metodei find, mostenita de la clasa AbstractDAO, parametri de cautare (coloana "nume" si String-ul cu numele produsului).
     * @param name numele produsului
     * @return true daca metoda find a gasit un produs in tabel; false in caz contrar
     */
    public static boolean findProduct(String name){
        if ((new ProductDAO()).find("name", name) != null)
            return true;
        return false;
    }
    /**
     * Returneaza obiectul Product obtinut in urma apelarii metodei find, mostenita de la clasa AbstraDAO, cu coloana "name" si numele produsului ca parametri.
     */
    public static Product getProduct(String name){
        return (new ProductDAO()).find("name", name);
    }

    /**
     * Ofera metodei update, mostenita de la clasa AbstractDAO, valorile de modificat si criteriul de cautare.
     * @param name numele produslui
     * @param quantity cantitatea produsului
     */
    public static void updateProduct(String name, int quantity){
        (new ProductDAO()).update("quantity", quantity, "name", name);
    }

    /**
     * Ofera metodei add, mostenita de la clasa AbstractDAO, valorile de inserat.
     * @param name numele produsului
     * @param quantity cantitatea produslui
     * @param price pretul produsului
     * @param id ID-ul produsului
     */
    public static void addProduct(String name, int quantity, float price, int id){
        Integer q = quantity, i = id;
        Float p = price;
        List<Object> list = new ArrayList<Object>();
        list.add(name);
        list.add(q);
        list.add(p);
        list.add(i);
        (new ProductDAO()).add(list);
    }

    /**
     * Ofera metodei find, mostenita de la clasa AbstractDAO ,numele produsului pe care sa il stearga.
     * @param name numele produsului
     */
    public static void delProduct(String name){
        (new ProductDAO()).delete(name);
    }

    /**
     * Returneaza un List cu toate obiectele Product obtinute in urma apelarii metodei report, mostenita de la clasa AbstractDAO.
     */
    public static List<Product> reportProduct(){
        return (new ProductDAO()).report();
    }
}
