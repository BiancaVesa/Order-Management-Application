package businessLayer;

import dataAccessLayer.*;
import presentation.*;
import model.*;

import java.util.List;

/**
 * ClientBLL este clasa care asigura logica de manipulare a obiectelor Client, ce trebuie inserate in baza de date a depozitului.
 * Lucrul efectiv cu operatii pe clienti se realizeaza in clasa ClientDAO.
 */
public class ClientBLL {

    /**
     * In cazul in care numele si adresa clientului sunt valide, se cauta clientul in baza de date, iar daca nu este gasit va fi adugat.
     * Daca numele si adresa nu sunt valide, sau clientul exista deja in baza de date, acesta nu va fi inserat.
     * @param name numele clientului
     * @param address adresa clientului
     * @param id ID-ul clientului
     */
    public static void addClient(String name, String address, int id) {
        if (DataValidation.isValidString(name) && DataValidation.isValidString(address)) {
            if (!ClientDAO.findClient(name)) {
                ClientDAO.addClient(name, address, id);
            }
        }
    }

    /**
     * Daca numele clientului este valid, va fi sters din baza de date.
     * @param name numele clientului
     */
    public static void delClient(String name) {
        if (DataValidation.isValidString(name)) {
            ClientDAO.delClient(name);
        }
    }

    /**
     * Returneaza toti clientii existenti in baza de date a depozitului.
     */
    public static List<Client> report() {
        return ClientDAO.reportClient();
    }

}
