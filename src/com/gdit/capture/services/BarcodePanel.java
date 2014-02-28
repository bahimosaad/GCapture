/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.DiskHome;
import com.gdit.capture.entity.Rep;
import java.io.File;
import java.util.List;
import net.sourceforge.tess4j.Tesseract;

/**
 *
 * @author Administrator
 */
public class BarcodePanel extends javax.swing.JPanel {

    /**
     * Creates new form BarcodePanel
     */
    private Rep rep;
    
    public BarcodePanel(Rep rep) {
        initComponents();
        this.rep = rep;
//        seperate();
    }
    
    
    public void seperate() {
        try {

//            
            CaptureHome dao = new CaptureHome();
//            ReadBarcode reader = new ReadBarcode();
            

            while (true) {
                List<Capture> batches = dao.getUnBarcoded(rep);
                Capture batch = batches.get(0);
//                for (Capture batch : batches) {

//                    output.setText(output.getText()+"\n "+batch.getId());
                    System.out.println( batch.getId());
                    List<Capture> pages = dao.getBatchUnDeletedPages(batch);
                    batch.setBarcoded(true);
                    dao.attachDirty(batch);
                    if (pages.isEmpty() || batch.getComputer() == null || batch.getDisk() == null) {
                        continue;
                    }
                    Capture first = pages.get(0);
                    File imgFile = new File(batch.getDisk().getPath() + "/scan"
                            + batch.getId() + "//" + first.getPath());
                    File viewFile = new File(batch.getDisk().getViewPath() + "/view"
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
                            output.setText(output.getText()+"\n"+ batch.getId() + "      " + barcode);
//                       output.append("\n"+ batch.getId() + "      " + barcode);
                        }

                    }
//                      else {
//                        File from = new File("\\\\" + batch.getComputer().getName() + "\\141\\" + batch.getId());
//                        if (!from.exists()) {
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
//                                output.append("\n"+batch.getId() + "      " + barcode);
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
//                                output.append("\n"+batch.getId() + "      " + barcode);
////                             output.append("\n"+ batch.getId() + "      " + barcode);
//                            }
//                        }
//                    }
//                }
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
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextPane();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");

        jScrollPane1.setViewportView(output);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane output;
    // End of variables declaration//GEN-END:variables
}
