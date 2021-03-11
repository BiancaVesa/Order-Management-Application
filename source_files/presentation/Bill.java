package presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Bill {
    /**
     * Creeaza un document PDF, cu numele dat, in care se scrie fiecare order plasat de clientii din baza de date.
     * @param docOrder numele documentului PDF
     * @param orderText textul ce trebuie scris pe chitanta
     */
    public static void CreateBill(String docOrder, String orderText) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(docOrder));

            document.open();
            document.add(new Paragraph(orderText));
            document.close();
        } catch (FileNotFoundException e) {
        } catch (DocumentException e) {
        }
    }
}
