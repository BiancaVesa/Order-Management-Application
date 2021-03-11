package model;

/***
 * Client este clasa principala pe care o folosim pentru a crea entitati reprezentative clientilor unui depozit.
 */
public class Client {
    /***
     * numele clientului
     */
    private String name;
    /***
     * adresa clientului
     */
    private String address;
    /***
     * numarul dupa care clientul este identificat in baza de date a depozitului
     */
    private int clientId;

    /***
     * Construieste un client cu parametri specificati.
     */
    public Client(String name, String address, int clientId) {
        super();
        this.clientId = clientId;
        this.name = name;
        this.address = address;
    }

    /***
     * Construieste un client cu valori implicite.
     */
    public Client() {
        this.clientId = -1;
        this.name = null;
        this.address = null;
    }

    /***
     * Returneaza adresa clientului
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Converteste ID-ul clientului la String.
     */
    public String clientIdToString() {
        Integer i = getClientId();
        return i.toString();
    }

    /**
     * Returneaza adresa clientului.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returneaza numele clientului
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica ID-ul clientului la valoarea data.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Modifica adresa clientului la valoarea data.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Modifica numele clientului la valoarea data.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returneaza un String care contine parametri obiectului.
     */
    public String toString() {
        return "id: " + clientId + ", name: " + name + ", address: " + address;
    }
}
