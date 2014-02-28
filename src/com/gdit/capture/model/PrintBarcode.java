/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

/**
 *
 * @author bahy
 */
import javax.print.*;
import javax.print.DocFlavor;
import java.util.Date;
import java.text.SimpleDateFormat;

public class PrintBarcode {

    public static void print(String barcode, String date, String label, String docNo) {

        System.out.println("sputaIlRospo Zebra Print testing!");

        // Prepare date to print in dd/mm/yyyy format
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(now);

        // Search for an installed zebra printer...
        // is a printer with "zebra" in its name
        try {
            PrintService psZebra = null;
            String sPrinterName = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            for (int i = 0; i < services.length; i++) {
                sPrinterName = services[i].getName();
                if (sPrinterName.toLowerCase().indexOf("zdesigner") >= 0) {
                    psZebra = services[i];
                    break;
                } else if (sPrinterName.toLowerCase().indexOf("zebra") >= 0) {
                    psZebra = services[i];
                    break;
                }
            }
            if (psZebra == null) {
                System.out.println("Zebra printer is not found.");
                return;
            }
            System.out.println("Found printer: " + sPrinterName);
            DocPrintJob job = psZebra.createPrintJob();
            // Prepare string to send to the printer
            String s = "R0,0\n" + // Set Reference Point
                    "N\n" + // Clear Image Buffer
                    "ZB\n" + // Print direction (from Bottom of buffer)
                    "Q122,10\n" // Set label Length and gap
                    + "A250,0,0,3,1,1,N,\"" + docNo + "  " + date + "\"\n"
                    + "A250,18,0,3,1,1,N,\" " + label + "\"\n"
                    + "B250,40,0,1A,3,7,50,N,\"" + barcode + "\"\n"
                    + "A160,95,0,3,1,1,N,\"            " + barcode + "\"\n"
                    + "P1\n";   // Print content of buffer, 1 label
            byte[] by = s.getBytes();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            // MIME type = "application/octet-stream",
            // print data representation class name = "[B" (byte array).
            Doc doc = new SimpleDoc(by, flavor, null);

            //       System.out.println("Pronti alla stampa");
            job.print(doc, null);
            //         System.out.println("Stampa inviata");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PrintBarcode print = new PrintBarcode();
        print.print("12354", "12/10/1430", "Blood Investigation", "123654484");
    }
}
