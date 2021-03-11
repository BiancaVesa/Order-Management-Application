package model;
/***
 * Order este clasa principala pe care o folosim pentru a crea entitati reprezentative unor comenzi plasate la un depozit.
 */
public class Order {
    /***
     * clientul care plaseaza comanda
     */
    private String client;
    /**
     * produsul care este comandat
     */
    private String product;
    /**
     * cantitatea de produs care se comanda
     */
    private int prodQty;
    /**
     * numarul dupa care este identificata comanda in baza de date
     */
    private int orderId;

    /***
     * Construieste un order cu parametri dati.
     */
    public Order(String client, String product, int prodQty, int orderId) {
        super();
        this.client = client;
        this.orderId = orderId;
        this.product = product;
        this.prodQty = prodQty;
    }

    /***
     * Construieste un order cu valori implicite.
     */
    public Order(){
        this.client = null;
        this.orderId = -1;
        this.product = null;
        this.prodQty = -1;
    }

    /**
     * Returneaza ID-ul comenzii.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Returneaza cantitatea de produs comandata.
     */
    public int getProdQty() {
        return prodQty;
    }

    /**
     * Returneaza numele clientului care a plasat comanda.
     */
    public String getClient() {
        return client;
    }

    /**
     * Returneaza numele produsului comandat.
     */
    public String getProduct() {
        return product;
    }

    /**
     * Converteste valoarea ID-ului comenzii la String.
     */
    public String orderIdToString(){
        Integer i = getOrderId();
        return i.toString();
    }

    /**
     * Converteste cantitatea produsului comandat la String.
     */
    public String prodQtyToString(){
        Integer i = getProdQty();
        return i.toString();
    }

    /**
     * Modifica valoarea numelui clientului.
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Modifica valoarea ID-ului comenzii.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Modifica cantitatea de produs comandata.
     */
    public void setProdQty(int prodQty) {
        this.prodQty = prodQty;
    }

    /**
     * Modifica numele produsului comandat.
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Returneaza un String care contine parametri obiectului.
     */
    public String toString(){
        return "OrderID: "+orderId+", client: "+client+", product: "+product+", quantity: "+prodQty;
    }
}
