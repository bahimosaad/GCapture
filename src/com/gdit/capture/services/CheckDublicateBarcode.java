/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureDocument;
import com.gdit.capture.entity.CaptureDocumentHome;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.Disk;
import java.io.File;
import java.sql.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.sourceforge.tess4j.Tesseract;

/**
 *
 * @author Administrator
 */
public class CheckDublicateBarcode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            CaptureHome dao = new CaptureHome();
            List<Object[]> list = dao.findDuplicateBarcode();
            Set<Capture> batches = new HashSet<Capture>();
            for (Object[] record : list) {
                String barcode = (String) record[0];
                List<Capture> docs = dao.findByBrcode(barcode);
                for (Capture doc : docs) {
                    // System.out.println(doc.getId());
                    File imgFile = null;
                    Capture batch = doc.getCapture();
                    Disk disk = batch.getDisk();
                    
                    Category category = batch.getCategory();
                    List<Capture> pages = dao.getBatchPages(batch);
                    if (pages.isEmpty() || batch.getComputer() == null || batch.getDisk() == null) {
                        continue;
                    }
                    Capture first = pages.get(0);
                     if (!category.isCreateFolder()) {
                        imgFile = new File(disk.getPath() + "/scan/" + batch.getId()+"/"+first.getPath()) ;
                     }else{
                         imgFile = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId()+"/"+first.getPath()) ;
                     }
                    
                    String result = Tesseract.getInstance().doOCR(imgFile);
                    String[] splits = result.split("\\n");
                    String yer = "";
                    String line = "";
                    String date = "";
                    for (int i = 0; i < splits.length; i++) {
                        String split = splits[i];
                        String[] temps = split.split(" ");
                        if (temps.length > 1 && isLong(temps[0])) {
                            yer = temps[0];
                            line = splits[i + 2].split(" ")[0];
                            date = splits[i + 3].split(" ")[0];
                            break;
                        }
                    }
                    String[] dates = date.split("-");
                    if (isLong(line) && dates.length > 2) {
                        int lineNo = Integer.parseInt(line);
                        CaptureDocumentHome docDao = new CaptureDocumentHome();
                        List<CaptureDocument> capDocs = docDao.findDocs(lineNo, yer, dates[2] + "-" + dates[1] + "-" + dates[0]);
                        if (capDocs.size() == 1 && batches.add(doc.getCapture())) {
                            System.out.println(doc.getCapture().getId() + "   " + capDocs.get(0).getId() + "  " + doc.getBarcode());
//                            doc.setBarcode(capDocs.get(0).getId() + "");
                          //  dao.attachDirty(doc);

                        } else {
                            System.out.println(doc.getCapture().getId() + "  " + doc.getBarcode());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CaptureHome.close();
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
