package dataAccessLayer;

import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa ClientDAO are rolul de a implementa operatiile propriu-zise pe tabelul Client, cu ajutorul clasei AbstractDAO, care primeste ca parametru generic clasa corespunzatoare in Java (Client).
 */
public class ClientDAO extends AbstractDAO<Client> {
    /**
     * Ofera metodei find, mostenita de la clasa AbstractDAO, parametri de cautare (coloana "nume" si String-ul cu numele clientului).
     * @param name numele clientului
     * @return true daca metoda find a gasit un client in tabel; false in caz contrar
     */
    public static boolean findClient(String name){
        if ((new ClientDAO()).find("name", name) != null)
            return true;
        return false;
    }

    /**
     * Ofera metodei add, mostenita de la clasa AbstractDAO, valorile de inserat.
     * @param name numele clientului
     * @param address adresa clientului
     * @param id ID-ul clientului
     */
    public static void addClient(String name, String address, int id){
        Integer i = id;
        List<Object> list = new ArrayList<Object>();
        list.add(name);
        list.add(address);
        list.add(i);
        (new ClientDAO()).add(list);
    }

    /**
     * Returneaza obiectul Client obtinut in urma apelarii metodei find, mostenita de la clasa AbstraDAO, cu coloana "name" si numele clientului ca parametri.
     */
    public static Client getClient(String name){
        return (new ClientDAO()).find("name", name);
    }

    /**
     * Ofera metodei find, mostenita de la clasa AbstractDAO ,numele clientului pe care sa il stearga.
     * @param name numele clientului
     */
    public static void delClient(String name){
        (new ClientDAO()).delete(name);
    }

    /**
     * Returneaza toate obiectele Client obtinute in urma apelarii metodei report, mostenita de la clasa AbstractDAO.
     */
    public static List<Client> reportClient(){
        return (new ClientDAO()).report();
    }
}
