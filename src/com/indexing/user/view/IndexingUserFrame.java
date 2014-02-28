/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IndexingUserFrame.java
 *
 * Created on 24/09/2010, 09:44:33 م
 */
package com.indexing.user.view;

import com.asprise.util.tiff.TIFFReader;
import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import com.gdit.capture.entity.DocumentData;
import com.gdit.capture.entity.DocumentDataHome;
import com.gdit.capture.entity.Field;
import com.gdit.capture.entity.FieldHome;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.model.BatchTreeItem;
import com.gdit.capture.model.CaptureStatus;
import com.gdit.capture.model.ImageTreeItem;
import com.gdit.capture.model.ImageListModel;
import com.indexing.Statics;
import com.indexing.util.ImagePan02;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author ehab
 */
public class IndexingUserFrame extends javax.swing.JFrame {

    /** Creates new form IndexingUserFrame */
    public IndexingUserFrame() {
        preInitComponents();
        initComponents();
        postInitComponents();
    }
    private void preInitComponents(){
         try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            getContentPane().setBackground(new Color(236,233,216));
            
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void postInitComponents() {
        pan = new ImagePan02();
        imagePanel.setViewportView(pan);
        CategoryHome categoryHome = new CategoryHome();
        allCategories = categoryHome.getAllCategory();
        Collections.sort(allCategories);
        bundle = ResourceBundle.getBundle("com/gdit/bundle/capture", systemLocal);
        setTexts();
        changePanelsDirections();
        centerWindow(this);
    }

    private void setTexts(){
        fileMenu.setText(bundle.getString("index.menu.file"));
        loadBatch.setText(bundle.getString("index.load.batch"));
        releasBatch.setText(bundle.getString("index.release.batch"));
        exit.setText(bundle.getString("index.exit"));
        helpMenu.setText(bundle.getString("index.menu.help"));
        help.setText(bundle.getString("index.help"));
        aboutMenuItem.setText(bundle.getString("index.about"));

        prvBTN.setText(bundle.getString("index.prv"));
        nextBTN.setText(bundle.getString("index.next"));
        autoBTN.setText(bundle.getString("index.auto"));

        saveBTN.setText(bundle.getString("index.save"));

        viewerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("index.viewer")));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("index.tree")));
        imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("index.image")));

    }

    private void changePanelsDirections(){
        changeDirection(fieldsPanel);
        changeDirection(jToolBar1);
        changeDirection(viewerPanel);
        changeDirection(jMenuBar1);
        changeDirection(statusPanel);
    }

//    private void addBTNActionPerformed(ActionEvent evt) throws IOException, BiffException, WriteException {
//        Capture capture = CURRENT_IMG.getCapture();
//        List<String> values = new ArrayList<String>();
//        values.add(TREE_COUNTER + "");
//        values.add(capture.getId()+"");
//        String docVal = CURRENT_IMG.getCapture().getCapture().getName();
//        Document _document = new Document();
//        _document.setDocValue(docVal);
//        int idx = Collections.binarySearch(allDocuments, _document);
//        if (idx >= 0) {
//            Document doc = allDocuments.get(idx);
//            values.add(doc.getDocValue() == null ? "" : doc.getDocValue());
//            values.add(doc.getDocCode() == null ? "" : doc.getDocCode());
//        } else {
//            values.add("");
//            values.add("");
//        }
//        values.add(CURRENT_IMG.getName());
//        //
//        rep = RUNNING_CAPTURE.getCategory().getRep();
//        repPath = rep.getPath();
//        String[] names = repPath.split("/");
//        String path = "\\\\server\\" + names[names.length - 1]
//                + "\\" + RUNNING_CAPTURE.getName()+"\\"
//                + CURRENT_IMG.getPath();
//        //
//        values.add(path);
//        for (Component component : fieldsComponents) {
//            if (component instanceof JTextField && component.isVisible()) {
//                values.add(((JTextField) component).getText());
//            } else if (component instanceof JComboBox && component.isVisible()) {
//                values.add(((JComboBox) component).getSelectedItem().toString());
//            } else {
//                values.add("");
//            }
//        }
//        // add values to XLS
//        ExcelUtil excelUtil = new ExcelUtil();
//        String fileName = Statics.XLS_FOLDER_PATH + RUNNING_CAPTURE.getName() + ".xls";
//        WritableWorkbook wwb = excelUtil.readSchemaFile(new File(fileName));
//        excelUtil.addValues(wwb, values);
//        //update to indexed
//
//        capture.setStatus(CaptureStatus.INDEXED);
//        CaptureHome captureHome = new CaptureHome();
//        captureHome.updateCaptureState(capture);
//
//        clearGUI();
//        nextBTNActionPerformed(evt);
//    }
    private void clearGUI() {
        for (Component component : fieldsComponents) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            }
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

        imgLBL = new javax.swing.JLabel();
        batchesDLG = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        capturesLIST = new javax.swing.JList();
        openBatchBTN = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        prvBTN = new javax.swing.JButton();
        nextBTN = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        maxBTN = new javax.swing.JButton();
        minBTN = new javax.swing.JButton();
        autoBTN = new javax.swing.JButton();
        statusPanel = new javax.swing.JPanel();
        statusLBL = new javax.swing.JLabel();
        saveBTN = new javax.swing.JButton();
        viewerPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        imagePanel = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        fieldsPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        loadBatch = new javax.swing.JMenuItem();
        releasBatch = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        help = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        jScrollPane3.setViewportView(capturesLIST);

        openBatchBTN.setText("فتح");
        openBatchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBatchBTNActionPerformed(evt);
            }
        });

        jButton7.setText("إلغاء");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout batchesDLGLayout = new javax.swing.GroupLayout(batchesDLG.getContentPane());
        batchesDLG.getContentPane().setLayout(batchesDLGLayout);
        batchesDLGLayout.setHorizontalGroup(
            batchesDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(batchesDLGLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(batchesDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addGroup(batchesDLGLayout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openBatchBTN, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                .addContainerGap())
        );
        batchesDLGLayout.setVerticalGroup(
            batchesDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, batchesDLGLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(batchesDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(openBatchBTN)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        prvBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        prvBTN.setMnemonic(KeyEvent.VK_LEFT);
        prvBTN.setText("<< Prv");
        prvBTN.setToolTipText("");
        prvBTN.setFocusable(false);
        prvBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        prvBTN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        prvBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prvBTNActionPerformed(evt);
            }
        });
        jToolBar1.add(prvBTN);

        nextBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/prev.png"))); // NOI18N
        nextBTN.setMnemonic(KeyEvent.VK_RIGHT);
        nextBTN.setText("Next >>");
        nextBTN.setFocusable(false);
        nextBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nextBTN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nextBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBTNActionPerformed(evt);
            }
        });
        jToolBar1.add(nextBTN);
        jToolBar1.add(jSeparator1);

        maxBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/max.png"))); // NOI18N
        maxBTN.setText("   +   ");
        maxBTN.setFocusable(false);
        maxBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        maxBTN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(maxBTN);

        minBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/min.png"))); // NOI18N
        minBTN.setText("   -   ");
        minBTN.setFocusable(false);
        minBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        minBTN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        minBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minBTNActionPerformed(evt);
            }
        });
        jToolBar1.add(minBTN);

        autoBTN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/auto.png"))); // NOI18N
        autoBTN.setText("auto");
        autoBTN.setFocusable(false);
        autoBTN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        autoBTN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(autoBTN);

        statusPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        statusLBL.setText("...");

        saveBTN.setText("Save...");
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addComponent(statusLBL, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(saveBTN)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(statusLBL)
                .addComponent(saveBTN))
        );

        viewerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Viewer"));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tree"));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Indexing");
        tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(tree);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
        );

        imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));
        imagePanel.setPreferredSize(new java.awt.Dimension(400, 600));

        javax.swing.GroupLayout viewerPanelLayout = new javax.swing.GroupLayout(viewerPanel);
        viewerPanel.setLayout(viewerPanelLayout);
        viewerPanelLayout.setHorizontalGroup(
            viewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE))
        );
        viewerPanelLayout.setVerticalGroup(
            viewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(imagePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
        );

        fieldsPanel.setLayout(new java.awt.GridLayout(0, 3));
        jScrollPane1.setViewportView(fieldsPanel);

        fileMenu.setText("File");
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuActionPerformed(evt);
            }
        });

        loadBatch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        loadBatch.setText("load Batch");
        loadBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBatchActionPerformed(evt);
            }
        });
        fileMenu.add(loadBatch);

        releasBatch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        releasBatch.setText("Releas Batch");
        releasBatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                releasBatchActionPerformed(evt);
            }
        });
        fileMenu.add(releasBatch);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exit.setText("Exit");
        fileMenu.add(exit);

        jMenuBar1.add(fileMenu);

        helpMenu.setText("Help");

        help.setText("Help");
        helpMenu.add(help);

        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE)
                    .addComponent(viewerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuActionPerformed
    }//GEN-LAST:event_fileMenuActionPerformed

    private void releasBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_releasBatchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_releasBatchActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        batchesDLG.dispose();
        RUNNING_CAPTURE = null;
        statusLBL.setText("...");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void openBatchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBatchBTNActionPerformed
        batchesDLG.dispose();
        RUNNING_CAPTURE = (Capture) capturesLIST.getSelectedValue();
        statusLBL.setText("current Batch: " + RUNNING_CAPTURE.getName());
        allFields = new FieldHome().listAllFields();
        fillTree();
        buildFieldsPanel();
    }//GEN-LAST:event_openBatchBTNActionPerformed

    private void treeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeValueChanged
        try {
            DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (lastIndexedNode == null) {
                imgLBL.setIcon(null);
            } else if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                CURRENT_IMG = (ImageTreeItem) lastIndexedNode.getUserObject();
                imgLBL.setIcon(CURRENT_IMG.getImage());
                statusLBL.setText(RUNNING_CAPTURE.getName() + " \\ "
                        + CURRENT_IMG.getCapture().getCapture().getName()
                        + " \\ " + CURRENT_IMG.getName());
            } else if (lastIndexedNode.getUserObject() instanceof String) {
                imgLBL.setIcon(null);
                statusLBL.setText(RUNNING_CAPTURE.getName() + " \\ " + lastIndexedNode.getUserObject());
            } else {
                imgLBL.setIcon(null);
                statusLBL.setText(RUNNING_CAPTURE.getName());
            }
            imgLBL.repaint();
            imgLBL.validate();
            pan.removeAll();
            pan.add(imgLBL);
            pan.validate();
            pan.repaint();
            imagePanel.validate();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }//GEN-LAST:event_treeValueChanged

    private void nextBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBTNActionPerformed
        System.out.println("TREE_COUNTER: " + TREE_COUNTER);
        if (TREE_COUNTER >= 0) {
            try {
                Capture old_document = null;
                Capture new_document = null;
                DefaultMutableTreeNode object = (DefaultMutableTreeNode) allTreePathes.get(TREE_COUNTER - 1).getLastPathComponent();
                old_document = ((ImageTreeItem) object.getUserObject()).getCapture().getCapture();
                DefaultMutableTreeNode object02 = (DefaultMutableTreeNode) allTreePathes.get(TREE_COUNTER).getLastPathComponent();
                new_document = ((ImageTreeItem) object02.getUserObject()).getCapture().getCapture();
                if (old_document != null) {
                    if (!new_document.equals(old_document)) {
                        int option = JOptionPane.showConfirmDialog(this, "Save Document?");
                        switch (option) {
                            case JOptionPane.YES_OPTION:
                                saveData();
                            case JOptionPane.NO_OPTION:
                                clearGUI();
                                break;
                            case JOptionPane.CANCEL_OPTION:
                                return;
                        }
                    } else {
                    }
                }
            } catch (Exception e) {
            }

            TreePath treePath = allTreePathes.get(TREE_COUNTER);
            tree.setSelectionPath(treePath);
            tree.expandPath(treePath);
            tree.scrollPathToVisible(treePath);
            DefaultMutableTreeNode def = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            ImageTreeItem imageItem = (ImageTreeItem) def.getUserObject();
            Capture current_document = imageItem.getCapture().getCapture();

            List<Integer> enabledIndexex = getEnabledFields(current_document);
            disableOtherFields(enabledIndexex);
            TREE_COUNTER++;
        }

        if (TREE_COUNTER >= allTreePathes.size()) {
            nextBTN.setEnabled(false);
        } else {
            nextBTN.setEnabled(true);
        }
        prvBTN.setEnabled(true);

    }//GEN-LAST:event_nextBTNActionPerformed

    private void prvBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prvBTNActionPerformed
        System.out.println("TREE_COUNTER: " + TREE_COUNTER);
        if (TREE_COUNTER > 0) {
            TREE_COUNTER--;
            TreePath treePath = allTreePathes.get(TREE_COUNTER);
            tree.setSelectionPath(treePath);
            tree.expandPath(treePath);
            tree.validate();
            tree.revalidate();
            tree.updateUI();

            DefaultMutableTreeNode def = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            ImageTreeItem imageItem = (ImageTreeItem) def.getUserObject();
            Capture current_document = imageItem.getCapture().getCapture();
            List<Integer> enabledIndexex = getEnabledFields(current_document);
            disableOtherFields(enabledIndexex);
        }
        if (TREE_COUNTER <= 0) {
            prvBTN.setEnabled(false);
//            hideFieldsPanel();
        } else {
            prvBTN.setEnabled(true);
            showFieldsPanel();
        }
        nextBTN.setEnabled(true);
    }//GEN-LAST:event_prvBTNActionPerformed

    private void loadBatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBatchActionPerformed
        CaptureHome dao = new CaptureHome();
        java.util.List<Capture> grands = dao.getWatingIndexing();
        DefaultListModel capturesLISTModel = new DefaultListModel();
        capturesLIST.setModel(capturesLISTModel);
        capturesLIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (Capture grand : grands) {
            capturesLISTModel.addElement(grand);
        }
        batchesDLG.pack();
        centerWindow(batchesDLG);
        batchesDLG.setVisible(true);
        batchesDLG.setModal(true);
    }//GEN-LAST:event_loadBatchActionPerformed

    private void saveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBTNActionPerformed
        saveData();
//        try {
//            SyncFilesService service = new SyncFilesService();
//            SyncFiles port = service.getSyncFilesPort();
//            InputStream in = new FileInputStream(Statics.XLS_FOLDER_PATH + RUNNING_CAPTURE.getName() + ".xls");
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            int b;
//            while ((b = in.read()) != -1) {
//                out.write(b);
//            }
//            byte[] data = out.toByteArray();
//
//            port.recieveFile(data, RUNNING_CAPTURE.getName() + ".xls");
//            Capture batch = batchItem.getCapture();
//            batch.setStatus(CaptureStatus.INDEXED);
//            new CaptureHome().updateCaptureState(batch);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_saveBTNActionPerformed

    private void minBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minBTNActionPerformed
    }//GEN-LAST:event_minBTNActionPerformed

    private void traverse(JTree tree) {
        TreeModel model = tree.getModel();
        allTreePathes = new ArrayList<TreePath>();
        if (model != null) {
            Object rootx = model.getRoot();
            walk(model, rootx);
        } else {
//            System.out.println("Tree is empty.");
        }
    }

    protected void walk(TreeModel model, Object o) {
        int cc;
        cc = model.getChildCount(o);
        for (int i = 0; i < cc; i++) {
            Object child = model.getChild(o, i);
            if (model.isLeaf(child)) {
                allTreePathes.add(new TreePath(child));
                tree.expandPath(new TreePath(child));
            } else {
//                System.out.print(child.toString() + "--");
                walk(model, child);
            }
        }
    }

    private void fillTree() {
        try {
            root = new DefaultMutableTreeNode("indexing");
            imageModel = new ImageListModel(root);
            if (RUNNING_CAPTURE != null) {
                batchItem = new BatchTreeItem();
                batchItem.setName(RUNNING_CAPTURE.getName());
                batchItem.setCapture(RUNNING_CAPTURE);
                DefaultMutableTreeNode grandNode = new DefaultMutableTreeNode(batchItem);
                Category category = RUNNING_CAPTURE.getCategory();
                rep = category.getRep();
                repPath = rep.getPath();
                String[] names = repPath.split("/");
                String path = repPath + "\\" + RUNNING_CAPTURE.getName();
                System.out.println("path : " + path);
                imageModel.addLeaf(root, grandNode);
                for (Capture parent : RUNNING_CAPTURE.getCaptures()) {
                    if (parent.getCaptures() == null || parent.getCaptures().size() <= 0) {
                        continue;
                    }
                    DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parent.getName());
                    imageModel.addLeaf(grandNode, parentNode);
                    for (Capture son : parent.getCaptures()) {
                        ImageTreeItem sonNode = new ImageTreeItem();
                        sonNode.setName(son.getName());
                        sonNode.setPath(son.getPath());
                        sonNode.setCapture(son);
                        TIFFReader reader = new TIFFReader(new File(path + "\\" + son.getPath()));
                        Image img = (Image) reader.getPage(0);
                        ImageIcon icon = scaleImage(img, img.getWidth(null) / 2, img.getHeight(null) / 4);
                        sonNode.setImage(icon);
                        imageModel.addLeaf(parentNode, new DefaultMutableTreeNode(sonNode));
                    }
                }
            }
            tree.setModel(imageModel);
            tree.updateUI();

            traverse(tree);
            TREE_COUNTER = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CaptureHome.close();
        }
    }

    private ImageIcon scaleImage(Image img, int w, int h) {
        Image scaledImage = img.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
        ImageIcon icon = new ImageIcon(scaledImage);
        return icon;
    }

//    private void buildXLS() {
//        String fileName = Statics.XLS_FOLDER_PATH + RUNNING_CAPTURE.getName() + ".xls";
//        ExcelUtil excelUtil = new ExcelUtil();
//        labels = new ArrayList<String>();
//        labels.add(Statics.PAGE_ID_LBL);
//        labels.add(Statics.PAGE_IMG_ID_LBL);
//        labels.add(Statics.PAGE_DOC_VAL);
//        labels.add(Statics.PAGE_DOC_CODE);
//        labels.add(Statics.PAGE_NAME_LBL);
//        labels.add(Statics.PAGE_PATH_LBL);
//        allFields = new FieldHome().listAllFields();
//        Collections.sort(allFields);
//        for (Field field : allFields) {
//            labels.add(field.getName() + " (" + field.getAlias() + ")");
//        }
//        try {
//            WritableWorkbook wr = excelUtil.createSchemaFile(fileName);
//            excelUtil.addColumnsLables(wr, labels);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void buildFieldsPanel() {
        fieldsComponents = new ArrayList<Component>();
        fieldsLabels = new ArrayList<JLabel>();
        errorFieldsLabels = new ArrayList<JLabel>();
        for (final Field field : allFields) {
            // lable
            JLabel fieldlabel = new JLabel(field.getName() + "(" + field.getAlias() + ")");
            JLabel errorLable = new JLabel("");
            errorLable.setForeground(new Color(0, 102, 51));
            fieldsLabels.add(fieldlabel);
            fieldsPanel.add(fieldlabel);
            // component
            Component fieldComponent = null;
            if (field.getType().equals(Statics.STRING_VAL)) {
                fieldComponent = new JTextField();
            } else if (field.getType().equals(Statics.NUMBER_VAL)) {
                fieldComponent = new JTextField();
                fieldComponent.addFocusListener(new java.awt.event.FocusAdapter() {

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        fieldComponentFocusLost(evt, field);
                    }
                });
                errorLable.setText("Value must be a Number");
            } else if (field.getType().equals(Statics.DATE_VAL)) {
                fieldComponent = new JTextField();
                fieldComponent.addFocusListener(new java.awt.event.FocusAdapter() {

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        fieldComponentFocusLost(evt, field);
                    }
                });
                errorLable.setText(field.getDateFormat());
            } else if (field.getType().equals(Statics.BOOLEAN_VAL)) {
                fieldComponent = new JComboBox(new String[]{"Yes", "No"});
            } else if (field.getType().equals(Statics.LIST_VAL)) {
                fieldComponent = new JComboBox(field.getListData().toArray());
            }
            fieldsComponents.add(fieldComponent);
            fieldsPanel.add(fieldComponent);

            errorFieldsLabels.add(errorLable);
            fieldsPanel.add(errorLable);
        }
//        fieldsPanel.add(new JLabel(""));
//        fieldsPanel.add(addBTN);
        hideFieldsPanel();
    }

    private void fieldComponentFocusLost(FocusEvent evt, Field field) {
        Object source = evt.getSource();
        String text = ((JTextField) source).getText();
        String message = new String();
        boolean b = false;
        if (field.getType().equals(Statics.DATE_VAL)) {
            b = isValidDate(text, field.getDateFormat());
            message = "Data format error";
        } else if (field.getType().equals(Statics.NUMBER_VAL)) {
            b = isValidNumber(text);
            message = "field is not a number";
        }
        if (!b) {
            JOptionPane.showMessageDialog(this, message);
            ((JTextField) evt.getSource()).requestFocus();

        }
    }

    private List<Integer> getEnabledFields(Capture document) {
        List<Integer> enabledFields = new ArrayList<Integer>();
        for (int i = 0; i < allFields.size(); i++) {
            Field field = allFields.get(i);
            for (Category category : field.getAssociatedCategories()) {
                if (document.getCategory().getName().equals(category.getName())) {
                    enabledFields.add(new Integer(i));
                    System.out.println("<<<<<<<<<<<<");
                }
            }
        }
        return enabledFields;
    }

    private void disableOtherFields(List<Integer> enabledIndexes) {
        Collections.sort(enabledIndexes);
        for (int i = 0; i < fieldsComponents.size(); i++) {
            int idx = Collections.binarySearch(enabledIndexes, i);
            if (idx >= 0) {
                fieldsComponents.get(i).setVisible(true);
                fieldsLabels.get(i).setVisible(true);
                errorFieldsLabels.get(i).setVisible(true);
            } else {
                fieldsComponents.get(i).setVisible(false);
                fieldsLabels.get(i).setVisible(false);
                errorFieldsLabels.get(i).setVisible(false);
            }
        }
        showFieldsPanel();
        fieldsPanel.validate();
        fieldsPanel.repaint();
    }

    private void hideFieldsPanel() {
        jScrollPane1.setViewportView(new JPanel());
    }

    private void showFieldsPanel() {
        jScrollPane1.setViewportView(fieldsPanel);
    }

    private void saveData() {
        Long doc_id = null;
        Long field_id = null;
        String field_value = null;
        // 1. get Current Doc
        Capture document = CURRENT_IMG.getCapture().getCapture();
        doc_id = document.getId();
        // 2. get Enabled Fileds Components
        // 3. get associated filed id
        for (int i = 0; i < allFields.size(); i++) {
            Field field = allFields.get(i);
            Component fieldComponent = fieldsComponents.get(i);
            if (fieldComponent.isVisible()) {
                field_id = field.getId();
                if (fieldComponent instanceof JTextField) {
                    field_value = ((JTextField) fieldComponent).getText();
                } else if (fieldComponent instanceof JComboBox) {
                    field_value = ((JComboBox) fieldComponent).getSelectedItem().toString();
                }
                DocumentData documentData = new DocumentData(0L, field_id, doc_id, field_value);
                new DocumentDataHome().persist(documentData);
                // update Batch
                CaptureHome captureHome = new CaptureHome();
                Capture batch = batchItem.getCapture();
                batch.setStatus(CaptureStatus.INDEXED);
                captureHome.updateCaptureState(batch);
                // update document
                document.setStatus(CaptureStatus.INDEXED);
                captureHome.updateCaptureState(document);
                //update pages to
            }
        }
    }

    private boolean isValidDate(String date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String errorMessage = "";
        Date testDate = null;
        try {
            testDate = sdf.parse(date);
        } catch (ParseException e) {
            errorMessage = "the date you provided is in an invalid date"
                    + " format.";
            System.out.println(errorMessage);
            return false;
        }
        if (!sdf.format(testDate).equals(date)) {
            errorMessage = "The date that you provided is invalid.";
            System.out.println(errorMessage);
            return false;
        }
        return true;
    }

    private boolean isValidNumber(String number) {
        try {
            float f = Float.parseFloat(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void changeDirection(Component component) {
        boolean orientation = component.getComponentOrientation().isLeftToRight();
        boolean isEn = new Locale("en").equals(systemLocal);
        if (orientation != isEn) {
            JideSwingUtilities.setLocaleRecursively(component, systemLocal);
            JideSwingUtilities.toggleRTLnLTR(component);
            JideSwingUtilities.invalidateRecursively(component);
            SwingUtilities.updateComponentTreeUI(component);

        }
        if (component instanceof JPanel) {
            JPanel panel = (JPanel) component;
            LayoutManager layout = panel.getLayout();
            if (layout instanceof FlowLayout) {
                if (isEn && ((FlowLayout) layout).getAlignment() != FlowLayout.CENTER) {
                    ((FlowLayout) layout).setAlignment(FlowLayout.LEFT);
                } else {
                    ((FlowLayout) layout).setAlignment(FlowLayout.RIGHT);
                }
            } else if (layout instanceof GridBagLayout) {
            }
        }
    }

    public static void centerWindow(final Component target) {
       if (target != null) {
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           Dimension dialogSize = target.getSize();

           if (dialogSize.height > screenSize.height) {
               dialogSize.height = screenSize.height;
           }
           if (dialogSize.width > screenSize.width) {
               dialogSize.width = screenSize.width;
           }

           target.setLocation((screenSize.width - dialogSize.width) / 2,
                   (screenSize.height - dialogSize.height) / 2);
       }
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new IndexingUserFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton autoBTN;
    private javax.swing.JDialog batchesDLG;
    private javax.swing.JList capturesLIST;
    private javax.swing.JMenuItem exit;
    private javax.swing.JPanel fieldsPanel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem help;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JScrollPane imagePanel;
    private javax.swing.JLabel imgLBL;
    private javax.swing.JButton jButton7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem loadBatch;
    private javax.swing.JButton maxBTN;
    private javax.swing.JButton minBTN;
    private javax.swing.JButton nextBTN;
    private javax.swing.JButton openBatchBTN;
    private javax.swing.JButton prvBTN;
    private javax.swing.JMenuItem releasBatch;
    private javax.swing.JButton saveBTN;
    private javax.swing.JLabel statusLBL;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTree tree;
    private javax.swing.JPanel viewerPanel;
    // End of variables declaration//GEN-END:variables
    private static Capture RUNNING_CAPTURE;
    private DefaultMutableTreeNode root;
    private ImageListModel imageModel;
    private String repPath;
    private Rep rep;
    List<TreePath> allTreePathes;
    private static int TREE_COUNTER;
    private static ImageTreeItem CURRENT_IMG = null;
    private List<String> labels;
    private List<Field> allFields;
    private List<Component> fieldsComponents;
    private List<JLabel> fieldsLabels;
    private List<JLabel> errorFieldsLabels;
//    private JButton addBTN;
    private BatchTreeItem batchItem;
    private List<Category> allCategories;
    private ImagePan02 pan;
    private Locale systemLocal = new Locale("ar");
    private ResourceBundle bundle;
}
