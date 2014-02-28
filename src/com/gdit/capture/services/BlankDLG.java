/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.Disk;
import com.gdit.capture.entity.Rep;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.sourceforge.tess4j.Tesseract;

/**
 *
 * @author Administrator
 */
public class BlankDLG extends javax.swing.JDialog  {

    /**
     * Creates new form BlankDLG
     */
    public BlankDLG(java.awt.Frame parent, boolean modal, Rep rep) {
        super(parent, modal);
        initComponents();
        repName.setText(rep.getName());
        setVisible(true);
        this.rep = rep;
        try {
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    public void run() {
        try {
            
            CaptureHome dao = new CaptureHome();

//            List<Capture> batches = dao.getUnBlankedbatches(rep, 2);
//            for (Capture batch : batches) {
            while (true) {
                batch = dao.getSingleBlankBatch(rep, 2);
                if (batch != null) {
                    batch.setLocked(true);
                    dao.merge(batch);
                    output.append("\n" + batch.getId());

                    System.out.println(batch.getId());
//                List<Capture> pages = dao.getBatchUnDeletedPages(batch);
                    processBatch thread = new processBatch();
//                thread.start();
                    thread.setBatch(batch);
                    thread.setDao(dao);
                    thread.run();
                }
                output.append("Threr is no Baches to Seperate");
                break;
            }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

        repName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        repName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        repName.setForeground(new java.awt.Color(153, 0, 51));
        repName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        repName.setText("jLabel1");

        output.setColumns(20);
        output.setRows(5);
        jScrollPane1.setViewportView(output);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(repName, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(repName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * @param args the command line arguments
     */
    private Capture batch;
    private CaptureHome dao;
    private Rep rep;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea output;
    private javax.swing.JLabel repName;
    // End of variables declaration//GEN-END:variables

    class processBatch  {

         
        public void run() {
            try {
                Category category = batch.getCategory();
                Disk disk = batch.getDisk();
                String path = "";
                if (category.isCreateFolder()) {
                    path = disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId() + "/";
                } else {
                    path = disk.getPath() + "/scan/" + batch.getId() + "/";
                }
                if (new File(path).list().length <= 0) {
                    return;
                }
                Capture doc = null;
                for (Capture document : batch.getCaptures()) {
                    {
                        for (Capture page : document.getCaptures()) {
                            File file = new File(path + "/" + page.getPath());
                            System.out.println(file.getAbsolutePath());
                            String result = Tesseract.getInstance().doOCR(file);
                            //Empty page!!
                            if (result.equals("") || result.toUpperCase().contains("EMPTY")) {
                                page.setType(2);
                                page.setCapture(batch);
                                page.setName("DOC");
                                page.setBlancked(true);
                                dao.attachDirty(page);
                                doc = page;
                                output.append("\n" + page.getId() + "    BLANK");
                            } else {
                                output.append("\n" + page.getName());
                                if (doc != null) {
                                    page.setCapture(doc);
                                    dao.attachDirty(page);
                                }

                            }
                        }
                    }
                }

                batch.setBarcoded(true);
                dao.attachDirty(batch);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        private Capture batch;
        private CaptureHome dao;

        public Capture getBatch() {
            return batch;
        }

        public void setBatch(Capture batch) {
            this.batch = batch;
        }

        public CaptureHome getDao() {
            return dao;
        }

        public void setDao(CaptureHome dao) {
            this.dao = dao;
        }
    }
}
