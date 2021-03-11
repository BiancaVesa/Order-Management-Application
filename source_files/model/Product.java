package model;
/***
 * Product este clasa principala pe care o folosim pentru a crea entitati reprezentative unor produse existente intr-un depozit.
 */
public class Product {
    /**
     * numele produsului
     */
    private String name;
    /**
     * cantitatea produsului
     */
    private int quantity;
    /**
     * pretul produsului
     */
    private float price;
    /**
     * numarul dupa care identificam produsul in baza de date a depozitului
     */
    private int productId;

    /**
     * Construieste un produs cu parametri dati.
     */
    public Product(String name, int quantity, float price, int productId) {
        super();
        this.name = name;
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Construieste un produs cu valori implicite.
     */
    public Product(){
        this.name = null;
        this.price = -1;
        this.productId = -1;
        this.quantity = -1;
    }

    /**
     * Returneaza pretul produsului.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Returneaza ID-ul produsului.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Returneaza cantitatea produsului.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returneaza numele produsului.
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica numele produsului.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Modifica pretul produsului.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Modifica ID-ul produsului.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Modifica cantitatea produsului.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Converteste ID-ul produsului la String.
     */
    public String prodIdToString(){
        Integer i = getProductId();
        return i.toString();
    }

    /**
     * Converteste pretul produsului la String.
     */
    public String priceToString(){
        Float f = getPrice();
        return f.toString();
    }

    /**
     * Converteste cantitatea produsului la String.
     */
    public String quantityToString(){
        Integer i = getQuantity();
        return i.toString();
    }

    /**
     * Returneaza un String care contine parametri obiectului.
     */
    public String toString(){
        return "id: "+productId+", name: "+name+", quantity: "+quantity+", price: "+price;
    }
}
