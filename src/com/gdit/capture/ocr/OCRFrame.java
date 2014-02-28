/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OCRFrame.java
 *
 * Created on Jan 11, 2012, 2:00:16 PM
 */
package com.gdit.capture.ocr;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JCheckBox;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import com.gdit.capture.entity.Server;
import com.gdit.capture.entity.ServerHome;
import com.gdit.capture.model.CaptureStatus;
import com.java4less.ocr.tess3.OCRFacade;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;
import org.apache.poi.util.IOUtils;

/**
 *
 * @author admin
 */
public class OCRFrame extends javax.swing.JFrame {

    private ArrayList<JCheckBox> allRepCHKs;
    private ArrayList<JCheckBox> allCategoriesCHKs;
    private ArrayList<JCheckBox> allBatchesCHKs;
    private List<Category> allCategories;
    private List<Rep> allReps;
    private JFileChooser chooser;

    /** Creates new form OCRFrame */
    public OCRFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        initComponents();
        postinit();
    }

    public void postinit() {
        allRepCHKs = new ArrayList<JCheckBox>();
        RepHome repHome = new RepHome();
        allReps = repHome.getAllRep();
        for (Rep rep : allReps) {
            JCheckBox repCheckBox = new JCheckBox(rep.getName());
            repCheckBox.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent evt) {
                    repCheckBoxItemStateChanged(evt);
                }
            });
            allRepCHKs.add(repCheckBox);
            //repCheckBox.setSelected(true);
            repPanel.add(repCheckBox);
        }
        repPanel.repaint();

        allCategoriesCHKs = new ArrayList<JCheckBox>();
        allBatchesCHKs = new ArrayList<JCheckBox>();
//        CategoryHome catHome = new CategoryHome();
//        allCategories = catHome.getAllCategory();


    }

    private void repCheckBoxItemStateChanged(ItemEvent evt) {
        Object[] selectedReps = evt.getItemSelectable().getSelectedObjects();
        RepHome repHome = new RepHome();
        for (Object repName : selectedReps) {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                Rep rep = repHome.findRepByName(repName.toString());
                Set<Category> cats = rep.getCategories();
                for (Category category : cats) {
                    JCheckBox categCheckBox = new JCheckBox(category.getName());
                    categCheckBox.addItemListener(new java.awt.event.ItemListener() {

                        @Override
                        public void itemStateChanged(ItemEvent evt) {
                            categCheckBoxItemStateChanged(evt);
                        }
                    });
                    allCategoriesCHKs.add(categCheckBox);
                    catPanel.add(categCheckBox);
                }
                catPanel.repaint();
                this.validate();
            }
        }
    }

    private void categCheckBoxItemStateChanged(ItemEvent evt) {
//        Object[] selectedCats = evt.getItemSelectable().getSelectedObjects();
//        CategoryHome catHome = new CategoryHome();
//        CaptureHome capHome = new CaptureHome();
//        for (Object catName : selectedCats) {
//            if (evt.getStateChange() == ItemEvent.SELECTED) {
//                Category cat = catHome.findCategoryByName(catName.toString());
//                List<Capture> batches = capHom.ecreateQuery("select c from Capture c "
//                        + "Where c.type = 1 and c.status > 3 ").list();
//                for (Capture batch : batches) {
//                    JCheckBox batchCheckBox = new JCheckBox(batch.toString());
////                    batchCheckBox.addItemListener(new java.awt.event.ItemListener() {
////                        @Override
////                        public void itemStateChanged(ItemEvent evt) {
////                            categCheckBoxItemStateChanged(evt);
////                        }
////                    });
//                    allBatchesCHKs.add(batchCheckBox);
//                    batchesPanel.add(batchCheckBox);
//                }
//                batchesPanel.repaint();
//                this.validate();
//            }
//        }
    }

    public void ocrToPDF(String batch, String destPath) {
        OCRFacade facade = new OCRFacade();

        Document document = null;
        OutputStream pdfFile = null;
        try {
            RepHome repHome = new RepHome();
            Rep rep = repHome.getAllRep().get(0);
            ServerHome serverHome = new ServerHome();
            Server server = serverHome.getAllServer().get(0);
            if (radioBatch.isSelected()) {
            NtlmPasswordAuthentication authentication =
                    new NtlmPasswordAuthentication("", server.getUser(), server.getPwd());
            UniAddress domain = UniAddress.getByName(server.getIp());
            SmbSession.logon(domain, authentication);
                pdfFile = new FileOutputStream(new File(destPath + "/" + batch + ".pdf"));
                document = new Document();
                PdfWriter.getInstance(document, pdfFile);
                document.open();
                 SmbFile batchFile = new SmbFile("smb://" + server.getIp() + "/" + rep.getPath() + "/" + batch + "/", authentication);
//                File batchFile = new File(rep.getPath() + "/" + batch + "/");
                for (SmbFile file : batchFile.listFiles()) {
                    if (file.getName().endsWith(".tif") || file.getName().endsWith(".jpg")) {
                        System.out.println(file.getName());
                        String ext = file.getName().split("\\.")[1];
                         byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                       // byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
                        String text = facade.recognizeImage(bytes, ext, "eng");
                        document.add(new Paragraph(text));
                        document.add(new Paragraph(new Date().toString()));
                    }
                }
                document.close();
                pdfFile.close();
            } else if (radioFile.isSelected()) {
                File folder = new File(destPath + "/" + batch);
                folder.mkdirs();
                 SmbFile batchFile = new SmbFile("smb://" + server.getIp() + "/" + rep.getPath() + "/" + batch + "/");
                //File batchFile = new File("C:/.capture/141" + "/" + batch + "/");
                for (SmbFile file : batchFile.listFiles()) {
                    if (file.getName().endsWith(".tif") || file.getName().endsWith(".jpg")) {
                        Document doc = new Document();

                        String[] names = file.getName().split("\\.");
                        String fileName = names[0];
                        String ext = names[1];
                        FileOutputStream pdf = new FileOutputStream(new File(destPath + "/" + batch + "/" + fileName + ".pdf"));
                        PdfWriter.getInstance(doc, pdfFile);
                        doc.open();
                        doc.newPage();
                        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                        //byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
                        String text = facade.recognizeImage(bytes, ext, "eng");
                        if (text == null || text.trim().equals("")) {
                            text = "No Image Found";
                        }
                        doc.add(new Paragraph(text));
                        doc.add(new Paragraph(new Date().toString()));
                        doc.close();
                    pdf.close();
                    }
                    
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // document.close();
            // pdfFile.close();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDest = new javax.swing.JTextField();
        radioBatch = new javax.swing.JRadioButton();
        radioFile = new javax.swing.JRadioButton();
        repPanel = new javax.swing.JPanel();
        chkRepAll = new javax.swing.JCheckBox();
        catPanel = new javax.swing.JPanel();
        chkCatAll = new javax.swing.JCheckBox();
        serchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        batchesPanel = new javax.swing.JPanel();
        chkAllBatches = new javax.swing.JCheckBox();

        buttonGroup1.add(radioBatch);
        buttonGroup1.add(radioFile);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Destination");

        txtDest.setText("C:/pdf");

        radioBatch.setSelected(true);
        radioBatch.setText("Batch As One File");

        radioFile.setText("Document as File");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(txtDest, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioFile)
                            .addComponent(radioBatch))))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioBatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioFile)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        repPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Repositories"));
        repPanel.setLayout(new javax.swing.BoxLayout(repPanel, javax.swing.BoxLayout.PAGE_AXIS));

        chkRepAll.setText("Select All");
        chkRepAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRepAllActionPerformed(evt);
            }
        });
        repPanel.add(chkRepAll);

        catPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Categories"));
        catPanel.setLayout(new javax.swing.BoxLayout(catPanel, javax.swing.BoxLayout.PAGE_AXIS));

        chkCatAll.setText("Select All");
        chkCatAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCatAllActionPerformed(evt);
            }
        });
        catPanel.add(chkCatAll);

        serchButton.setText("Export");
        serchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);

        batchesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Batches"));
        batchesPanel.setLayout(new javax.swing.BoxLayout(batchesPanel, javax.swing.BoxLayout.PAGE_AXIS));

        chkAllBatches.setText("Select All");
        chkAllBatches.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkAllBatchesItemStateChanged(evt);
            }
        });
        batchesPanel.add(chkAllBatches);

        jScrollPane1.setViewportView(batchesPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(repPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(catPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(serchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(catPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                            .addComponent(repPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(serchButton)
                        .addGap(26, 26, 26))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkRepAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRepAllActionPerformed
        // TODO add your handling code here:
        for (JCheckBox jCheckBox : allRepCHKs) {
            jCheckBox.setSelected(chkRepAll.isSelected());
        }
    }//GEN-LAST:event_chkRepAllActionPerformed

    private void chkCatAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCatAllActionPerformed
        // TODO add your handling code here:
        for (JCheckBox jCheckBox : allCategoriesCHKs) {
            jCheckBox.setSelected(chkCatAll.isSelected());
        }
    }//GEN-LAST:event_chkCatAllActionPerformed

    private void chkAllBatchesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkAllBatchesItemStateChanged
        // TODO add your handling code here:
        for (JCheckBox batchChkBox : allBatchesCHKs) {
            batchChkBox.setSelected(chkAllBatches.isSelected());
        }
    }//GEN-LAST:event_chkAllBatchesItemStateChanged

    private void serchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchButtonActionPerformed
        // TODO add your handling code here:
        for (JCheckBox chkBatch : allBatchesCHKs) {
            if (chkBatch.isSelected()) {
                ocrToPDF(chkBatch.getText(), txtDest.getText());
            }
        }
    }//GEN-LAST:event_serchButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new OCRFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel batchesPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel catPanel;
    private javax.swing.JCheckBox chkAllBatches;
    private javax.swing.JCheckBox chkCatAll;
    private javax.swing.JCheckBox chkRepAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radioBatch;
    private javax.swing.JRadioButton radioFile;
    private javax.swing.JPanel repPanel;
    private javax.swing.JButton serchButton;
    private javax.swing.JTextField txtDest;
    // End of variables declaration//GEN-END:variables
}
