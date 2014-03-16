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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author Administrator
 */
public class CheckBarcode {

    public static void main(String[] args) {
        CheckBarcode main = new CheckBarcode();
        main.check();
    }

    public void test() {
        try {
            CaptureDocumentHome docDao = new CaptureDocumentHome();
            File imgFile = new File("\\\\192.168.1.3\\scan\\166\\page1.tif");
            String result = Tesseract.getInstance().doOCR(imgFile);
            String[] splits = result.split("\\n");
            String yer = "";
            String line = "";
            String date = "";
            for (int i = 0; i < splits.length; i++) {
                String split = splits[i];
                if (split.contains("-")) {
                    String[] temps = split.split(" ");
                    if (temps.length > 1 && isLong(temps[0])) {
                        yer = temps[0];
                        line = splits[i + 2].split(" ")[0];
                        date = splits[i + 3].split(" ")[0];

                        String[] dates = date.split("-");
                        if (isLong(line) && dates.length > 2) {
                            int lineNo = Integer.parseInt(line);

                            List<CaptureDocument> capDocs = docDao.findDocs(lineNo, yer, dates[2] + "-" + dates[1] + "-" + dates[0]);
                            if (capDocs.size() == 1) {
                                System.out.println(capDocs.get(0).getId() + "  ");
                                //                                doc.setRefuseNote(capDocs.get(0).getId() + "");
                                //                                doc.setBarcoded(true);
                                //                                dao.attachDirty(doc);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (TesseractException ex) {
            Logger.getLogger(CheckBarcode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void check() {
        try {
            // TODO code application logic here
            CaptureHome dao = new CaptureHome();
            List<Capture> list = dao.getAllDocs();
            Set<Capture> batches = new HashSet<Capture>();
            CaptureDocumentHome docDao = new CaptureDocumentHome();
            for (Capture doc : list) {
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
                    imgFile = new File(disk.getPath() + "/scan/" + batch.getId() + "/" + first.getPath());
                } else {
                    imgFile = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId() + "/" + first.getPath());
                }
                if (!imgFile.exists()) {
                    continue;
                }
                String result = Tesseract.getInstance().doOCR(imgFile);
                String[] splits = result.split("\\n");
                String yer = "";
                String line = "";
                String date = "";
                for (int i = 0; i < splits.length; i++) {
                    String split = splits[i];
                    if (split.contains("-")) {
                        String[] temps = split.split(" ");
                        if (temps.length > 1 && isLong(temps[0])) {
                            yer = temps[0];
                            line = splits[i + 2].split(" ")[0];
                            date = splits[i + 3].split(" ")[0];

                            String[] dates = date.split("-");
                            if (isLong(line) && dates.length > 2) {
                                int lineNo = Integer.parseInt(line);

                                List<CaptureDocument> capDocs = docDao.findDocs(lineNo, yer, dates[2] + "-" + dates[1] + "-" + dates[0]);
                                if (capDocs.size() == 1  ) {
                                    System.out.println(doc.getCapture().getId() + "   " + capDocs.get(0).getId() + "  " + doc.getBarcode());
                                    doc.setRefuseNote(capDocs.get(0).getId() + "");


                                }
                                doc.setBarcoded(true);
                                dao.attachDirty(doc);
                                break;
                            }
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
