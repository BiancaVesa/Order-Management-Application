package businessLayer;

import java.util.regex.*;

/**
 * DataValidation este clasa care se ocupa cu validarea datelor pe care vrem sa le inseram, cautam sau stergem din baza de date a depozitului.
 */
public class DataValidation {
    /**
     * Verifica daca String-ul dat ca parametru se potriveste cu un pattern, care corespunde cu modul in care arata un nume sau o adresa.
     * @param string String-ul pe care vrem sa il validam
     * @return true, daca String-ul este valid; false in caz contrar
     */
    public static boolean isValidString(String string) {
        String reg = "^[\\p{L} '-]+$";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    /**
     * Verifica daca cantitate este numar negativ.
     * @param qty cantitatea
     * @return true, daca este nr pozitiv; false, in caz contrar
     */
    public static boolean isValidQuantity(int qty) {
        if (qty > 0)
            return true;
        return false;
    }
    /**
     * Verifica daca pretul este numar negativ.
     * @param price pretul
     * @return true, daca este nr pozitiv; false, in caz contrar
     */
    public static boolean isValidPrice(float price) {
        if (price > 0)
            return true;
        return false;
    }
}
