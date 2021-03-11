package businessLayer;

import dataAccessLayer.*;
import model.*;

import java.util.List;
/**
 * ProductBLL este clasa care asigura logica de manipulare a obiectelor Product, ce trebuie inserate in baza de date a depozitului.
 * Lucrul efectiv cu operatii pe order se realizeaza in clasa ProductDAO.
 */
public class ProductBLL {
    /**
     * Daca numele produsului, cantitatea si pretul acestuia sunt valide, il cauta in baza de date dupa nume.
     * Daca nu exista se va crea unul cu valorile date, altfel se adauga cantitatea noua la stocul deja existent.
     * @param name numele produsului
     * @param quantity cantitatea produsului
     * @param price pretul produslui
     * @param id ID-ul produsului in baza de date
     */
    public static void addProduct(String name, int quantity, float price, int id) {
        if (DataValidation.isValidString(name) && DataValidation.isValidQuantity(quantity) && DataValidation.isValidPrice(price)) {
            if (!ProductDAO.findProduct(name)) {
                ProductDAO.addProduct(name, quantity, price, id);
            } else {
                Product product = ProductDAO.getProduct(name);
                int qty = quantity + product.getQuantity();
                ProductDAO.updateProduct(name, qty);
            }
        }
    }

    /**
     * Sterge din baza de date produsul dat, daca numele acestuia este valid.
     */
    public static void delProduct(String name) {
        if (DataValidation.isValidString(name)) {
            ProductDAO.delProduct(name);
        }
    }
    /**
     * Returneaza toate produsele existente in baza de date a depozitului.
     */
    public static List<Product> report() {
        return ProductDAO.reportProduct();
    }
}
