package model;
/***
 * OrderItem este clasa principala pe care o folosim pentru a realiza legatura intre obiectele de tip Client si cele de tip Product.
 * Scopul sau este de a pastra comenzile intr-un singur exemplar, in conditiile plasarii mai multor comenzi de catre acelasi client.
 */
public class OrderItem {
    /**
     * ID-ul comenzii
     */
    private int orderId;
    /**
     * ID-ul clientului.
     */
    private int clientId;
    /**
     * pretul total al comenzii plasate de catre un client
     */
    private float totalPrice;

    /**
     * Construieste un OrderItem cu parametri dati.
     */
    public OrderItem(int orderId, int clientId, float totalPrice){
        super();
        this.clientId = clientId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }

    /**
     * Construieste un OrderItem cu valori implicite.
     */
    public OrderItem(){
        this.clientId = -1;
        this.orderId = -1;
        this.totalPrice = -1;
    }

    /**
     * Returneaza ID-ul comenzii.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Returneaza ID-ul clientului.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Returneaza pretul total al comenzii.
     */
    public float getTotalPrice() {
        return totalPrice;
    }


    /**
     * Modifica ID-ul comenzii.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Modifica ID-ul clientului.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Modifica pretul total al comenzii.
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

}
