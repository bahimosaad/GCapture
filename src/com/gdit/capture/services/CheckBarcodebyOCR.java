/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureDocument;
import com.gdit.capture.entity.CaptureDocumentHome;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Disk;
import com.gdit.capture.entity.DiskHome;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.run.ReadBarcode;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class CheckBarcodebyOCR {

    private final JTextArea output;
    private Rep reps;

    public CheckBarcodebyOCR(Rep rep) {
        this.reps = rep;
        JFrame frame = new JFrame();

        frame.setTitle("Barcode OCR Service");
        frame.setSize(600, 600);
        frame.setAlwaysOnTop(true);
        output = new JTextArea("Test");
        output.setAutoscrolls(true);
        JScrollPane scroll = new JScrollPane();
        output.setAutoscrolls(true);
        scroll.setViewportView(output);
        JButton ocrBtn = new JButton("OCR");
        ocrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ocr(reps);
//                test();
            }
        });
        frame.add(ocrBtn, BorderLayout.NORTH);
        frame.add(output);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }

    private void test() {
        CaptureHome dao = new CaptureHome();
//            ReadBarcode reader = new ReadBarcode();


        List<Capture> batches = dao.getUnBarcoded();
        for (Capture batch : batches) {
             
            output.append(batch.getId() + "");
            System.out.println(batch.getId());
//                List<Capture> pages = dao.getBatchUnDeletedPages(batch);
//                batch.setBarcoded(true);
//                dao.attachDirty(batch);
//                if (pages.isEmpty() || batch.getComputer() == null || batch.getDisk() == null) {
//                    continue;
//                }
//                Capture first = pages.get(0);
//                File imgFile = new File(batch.getDisk().getPath() + "/scan/"
//                        + batch.getId() + "//" + first.getPath());
//                File viewFile = new File(batch.getDisk().getViewPath() + "/view/"
//                        + batch.getId() + "//" + first.getPath());
//                if (viewFile.exists()) {
//                    String barcode = "";
////                        barcode = reader.scanBarcode(imgFile);
////                        if (!barcode.equals("")) {
////                            Capture doc = (Capture) batch.getCaptures().toArray()[0];
////                            doc.setName(barcode.trim());
////                            doc.setBarcode(barcode.trim());
////                            dao.attachDirty(doc);
////                            continue;
////                        }
//                    String result = null;
//                    try {
//                        result = Tesseract.getInstance().doOCR(imgFile);
//                    } catch (TesseractException ex) {
//                        Logger.getLogger(CheckBarcodebyOCR.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    String[] splits = result.split("\\n");
//                    if (splits[0].length() > 14) {
//                        continue;
//                    }
//                    barcode = splits[0].replaceAll("\\s", "");
////                        for (String split : splits) {
////                            String str = split.trim();
////                            if (isLong(str)) {
////                                barcode = str;
////                                break;
////                            }
////                        }
//
//
//                    if (isLong(barcode)) {
//                        Capture doc = (Capture) batch.getCaptures().toArray()[0];
//                        doc.setName(barcode.trim());
//                        doc.setBarcode(barcode.trim());
//                        dao.attachDirty(doc);
//                        System.out.println(batch.getId() + "      " + barcode);
////                       output.append("\n"+ batch.getId() + "      " + barcode);
//                    }
//
//                }
        }

    }

    /**
     * @param args the command line arguments
     */
    public void ocr(Rep rep) {
        // TODO code application logic here
        try {
            CaptureHome dao = new CaptureHome();
//            ReadBarcode reader = new ReadBarcode(); 
            List<Capture> batches = dao.getUnBarcoded(rep);
            for (Capture batch : batches) {
                output.append("\n" + batch.getId());
                System.out.println(batch.getId());
                List<Capture> pages = dao.getBatchUnDeletedPages(batch);
                batch.setBarcoded(true);
                dao.attachDirty(batch);
                if (pages.isEmpty() || batch.getComputer() == null || batch.getDisk() == null) {
                    continue;
                }
                Capture first = pages.get(0);
                File imgFile = new File(batch.getDisk().getPath() + "/scan/"
                        + batch.getId() + "//" + first.getPath());
                File viewFile = new File(batch.getDisk().getViewPath() + "/view/"
                        + batch.getId() + "//" + first.getPath());
                if (viewFile.exists()) {
                    String barcode = "";
//                        barcode = reader.scanBarcode(imgFile);
//                        if (!barcode.equals("")) {
//                            Capture doc = (Capture) batch.getCaptures().toArray()[0];
//                            doc.setName(barcode.trim());
//                            doc.setBarcode(barcode.trim());
//                            dao.attachDirty(doc);
//                            continue;
//                        }
                    String result = Tesseract.getInstance().doOCR(imgFile);
                    String[] splits = result.split("\\n");
                    if (splits[0].length() > 14) {
                        continue;
                    }
                    barcode = splits[0].replaceAll("\\s", "");
//                        for (String split : splits) {
//                            String str = split.trim();
//                            if (isLong(str)) {
//                                barcode = str;
//                                break;
//                            }
//                        }


                    if (isLong(barcode)) {
                        Capture doc = (Capture) batch.getCaptures().toArray()[0];
                        doc.setName(barcode.trim());
                        doc.setBarcode(barcode.trim());
                        dao.attachDirty(doc);
                        System.out.println(batch.getId() + "      " + barcode);
//                       output.append("\n"+ batch.getId() + "      " + barcode);
                    }

                }
//                    else {
//                        File from = new File("\\\\" + batch.getComputer().getName() + "\\141\\" + batch.getId());
//                        if(!from.exists()){
//                            from = new File("G:/" + batch.getComputer().getName() + "\\20042013\\" + batch.getId());
//                        }
//                        File to = new File(batch.getDisk().getPath() + "\\" + batch.getId());
//                        File tof = new File(batch.getDisk().getViewPath() + "\\" + batch.getId());
//                        if (!to.exists() || to.list() == null || to.list().length <= 1) {
//                            org.apache.commons.io.FileUtils.copyDirectory(from, to);
//                            CompressToTiff service = new CompressToTiff();
//                            service.getCompressToTiffSoap().compressFolderFullPath(disk.getPath() + "/" + batch.getId(),
//                                    disk.getViewPath() + "/" + batch.getId());
//
//                            String result = Tesseract.getInstance().doOCR(imgFile);
//                            String[] splits = result.split("\\n");
//                            if (splits[0].length() > 14) {
//                                continue;
//                            }
//                            String barcode = splits[0].replaceAll("\\s", "");
//
//                            if (isLong(barcode)) {
//                                Capture doc = (Capture) batch.getCaptures().toArray()[0];
//                                doc.setName(barcode.trim());
//                                doc.setBarcode(barcode.trim());
//                                dao.attachDirty(doc);
//                                System.out.println(batch.getId() + "      " + barcode);
////                             output.append("\n"+ batch.getId() + "      " + barcode);
//                            }
//                        } else if (!tof.exists() || tof.list() == null || tof.list().length <= 1) {
//                            CompressToTiff service = new CompressToTiff();
//                            service.getCompressToTiffSoap().compressFolderFullPath(disk.getPath() + "/" + batch.getId(),
//                                    disk.getViewPath() + "/" + batch.getId());
//
//                            String result = Tesseract.getInstance().doOCR(imgFile);
//                            String[] splits = result.split("\\n");
//                            if (splits[0].length() > 12) {
//                                continue;
//                            }
//                            String barcode = splits[0].replaceAll("\\s", "");
//
//                            if (isLong(barcode)) {
//                                Capture doc = (Capture) batch.getCaptures().toArray()[0];
//                                doc.setName(barcode.trim());
//                                doc.setBarcode(barcode.trim());
//                                dao.attachDirty(doc);
//                                System.out.println(batch.getId() + "      " + barcode);
////                             output.append("\n"+ batch.getId() + "      " + barcode);
//                            }
//                        }
//                    }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CaptureHome.close();
            DiskHome.close();
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

    public static void main(String[] args) {
        CheckBarcodebyOCR ocr = new CheckBarcodebyOCR(null);
//        ocr.ocr(null);
    }
}
