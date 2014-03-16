/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author Administrator
 */
public class ReadBarcode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        List<Capture> batches = dao.getUnBarcoded(1L);
        Tesseract instance = Tesseract.getInstance();
        for (Capture batch : batches) {

            String path = batch.getDisk().getPath().replace("$", ":");
            File imgFile = new File(path + "/" + batch + "/page1.tif");
            if (!imgFile.exists()) {
                imgFile = new File(path + "/" + batch + "/page2.tif");
            }
            if (!imgFile.exists()) {
                imgFile = new File(path + "/" + batch + "/page3.tif");
            }
            if (imgFile.exists()) {
                try {
                    System.out.println(batch.getId());
                    String result = instance.doOCR(imgFile);

                    String[] splits = result.split("\\n");
                    String barcode = splits[0];
                    if (isLong(barcode.trim())) {
                        List<Capture> docs = dao.GetSons(batch);
                        for (Capture doc : docs) {
                            doc.setBarcode(barcode.trim());
                            dao.attachDirty(doc);

                            System.out.println(batch.getId() + "      " + splits[0]);
                        }
                    }
                    else {
                       //  System.out.println(result);
                    }
                } catch (TesseractException ex) {
                    Logger.getLogger(ReadBarcode.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean isLong(String str) {
        try {
            long number = Long.parseLong(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
