/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureDocument;
import com.gdit.capture.entity.CaptureDocumentHome;
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
public class ReadBrcodeByOcrData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // TODO code application logic here
            CaptureHome dao = new CaptureHome();
            List<Capture> batches = dao.getUnBarcoded();
//            com.gdit.capture.run.ReadBarcode reader = new com.gdit.capture.run.ReadBarcode();
            //1 1514882


            for (Capture batch : batches) {
                System.out.println(batch.getId());
                List<Capture> pages = dao.getBatchUnDeletedPages(batch);

                if (pages.isEmpty() || batch.getComputer() == null || batch.getDisk() == null) {
                    continue;
                }
                Capture first = pages.get(0);
                File imgFile = new File(batch.getDisk().getPath() + "/"
                        + batch.getId() + "//" + first.getPath());
                File viewFile = new File(batch.getDisk().getViewPath() + "/"
                        + batch.getId() + "//" + first.getPath());

                if (imgFile.exists() && imgFile.length() > 0) {
//                    String barcode = reader.scanBarcode(imgFile);
//                    if (!barcode.equals("")) {
//                        for (Capture doc : batch.getCaptures()) {
//                            System.out.println(batch.getId() + "   " + barcode);
//                            doc.setBarcode(barcode);
//                            dao.attachDirty(doc);
//
//                        }
//                    }
                    String result = Tesseract.getInstance().doOCR(imgFile);
                    String[] splits = result.split("\\n");
                    String yer = "";
                    String line = "";
                    String date = "";
                    for (int i = 0; i < splits.length; i++) {
                        String split = splits[i];
                        String[] temps = split.split(" ");
                        if (temps.length > 1 && ReadBarcode.isLong(temps[0])) {
                            yer = temps[0];
                            line = splits[i + 2].split(" ")[0];
                            date = splits[i + 3].split(" ")[0];
                            break;
                        }
                    }
                    String[] dates = date.split("-");
                    CaptureDocumentHome docDao = new CaptureDocumentHome();
                    if (ReadBarcode.isLong(line) && dates.length > 2) {
                        int lineNo = Integer.parseInt(line);

                        List<CaptureDocument> capDocs = docDao.findDocs(lineNo, yer, dates[2] + "-" + dates[1] + "-" + dates[0]);

                        if (capDocs.size() == 1) {
                            for (Capture doc : batch.getCaptures()) {
                                System.out.println(batch.getId() + "   " + capDocs.get(0).getId());
                                doc.setBarcode(capDocs.get(0).getId() + "");
                                dao.attachDirty(doc);

                            }
                        } else if (capDocs.size() == 2) {
                            CaptureDocument doc1 = capDocs.get(0);
                            CaptureDocument doc2 = capDocs.get(1);
                            if (doc1.getJrnlType().equals("A")) {
                                for (Capture doc : batch.getCaptures()) {
                                    System.out.println(batch.getId() + "   " + doc1.getId());
                                    doc.setBarcode(doc1.getId() + "");
                                    dao.attachDirty(doc);

                                }
                            } else {
                                for (Capture doc : batch.getCaptures()) {
                                    System.out.println(batch.getId() + "   " + doc2.getId());
                                    doc.setBarcode(doc2.getId() + "");
                                    dao.attachDirty(doc);

                                }
                            }

                        }
                    } else {
                        //check the first number get from barcode 
                        for (String nmbr : splits) {
                            if (ReadBarcode.isLong(nmbr.replaceAll("\\s", ""))) {
                                long id = Long.valueOf(nmbr.replaceAll("\\s", ""));
                                CaptureDocument d = docDao.findById(id);
                                if (d != null) {
                                    for (Capture doc : batch.getCaptures()) {
                                        System.out.println(batch.getId() + "   " + d.getId());
                                        doc.setBarcode(doc.getId() + "");
                                        dao.attachDirty(doc);
                                        break;

                                    }

                                }
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
}
