package presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Se ocupa cu management-ul tabelelor PDF.
 */
public class PDFTable {
    /**
     * Creeaza un tabel de inserat intr-un document PDF, cu parametri dati.
     * @param nrColumns numarul de coloane ale tabelului
     * @param columnNames lista cu numele fiecarei coloane
     * @return tabel de inserat in document PDF
     */
    public static PdfPTable createTable(int nrColumns, ArrayList<String> columnNames) {
        PdfPTable table = new PdfPTable(nrColumns);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (String c : columnNames)
            table.addCell(c);
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        return table;
    }

    /**
     * Adauga randuri noi in tabel
     * @param row continutul fiecarei celule din rand
     * @param table tabelul in care adaugam randul
     */
    public static void addRows(ArrayList<String> row, PdfPTable table) {
        for (String r : row) {
            table.addCell(r);
        }
    }

    /**
     * Creeaza un document PDF, in care adauga un tabel.
     * @param docName numele documentului pe care vrem sa il cream
     * @param table tabelul de adaugat in document
     */
    public static void addTableToDoc(String docName, PdfPTable table){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(docName));
            document.open();
            document.add(table);
            document.close();
        } catch (FileNotFoundException e) {
        } catch (DocumentException e) {
        }
    }
}
