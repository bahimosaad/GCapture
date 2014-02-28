/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddFieldPanel.java
 *
 * Created on Sep 23, 2010, 1:00:17 PM
 */
package com.indexing.admin.view;

import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import com.indexing.Statics;
import com.gdit.capture.entity.DocumentData;
import com.gdit.capture.entity.DocumentDataHome;
import com.gdit.capture.entity.Field;
import com.gdit.capture.entity.FieldHome;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.utils.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author ehab
 */
public class AddFieldPanel extends javax.swing.JPanel {

    public void preInitComponents() {
        typesList = new Vector<String>();
        typesList.add(Statics.STRING_VAL);
        typesList.add(Statics.NUMBER_VAL);
        typesList.add(Statics.BOOLEAN_VAL);
        typesList.add(Statics.DATE_VAL);
        typesList.add(Statics.LIST_VAL);
        itemsModel = new DefaultListModel();
        CategoryHome categoryHome = new CategoryHome();
        categories = categoryHome.getAllCategory();
        Collections.sort(categories);
        documentsCHKBOX = new ArrayList<JCheckBox>();

    }

    /** Creates new form AddFieldPanel */
    public AddFieldPanel() {
        preInitComponents();
        initComponents();
        postInitComponents();
    }

    public void postInitComponents() {
        itemsLIST.setModel(itemsModel);
        if (categories != null) {
            for (Category category : categories) {
                documentsCHKBOX.add(new JCheckBox(category.getName()));
            }
        }

        if (documentsCHKBOX != null) {
            if (!documentsCHKBOX.isEmpty()) {
                for (JCheckBox jCheckBox : documentsCHKBOX) {
                    catPanel.add(jCheckBox);
                }
            }
        }

        bundle = ResourceBundle.getBundle("com/gdit/bundle/capture", systemLocal);
        setTexts();
        changePanelsDirections();
//        centerWindow(this);
    }

    private void setTexts() {
        fieldNameLBL.setText(bundle.getString("index.admin.field.name"));
        fieldAliaLBL.setText(bundle.getString("index.admin.field.alias"));
        fieldTypeLBL.setText(bundle.getString("index.admin.field.type"));
        jButton1.setText(bundle.getString("index.admin.field.add"));
        selectAllCHKBOX.setText(bundle.getString("index.admin.select.all"));
        formatLBL.setText(bundle.getString("index.admin.date.format"));
        addBTN.setText(bundle.getString("index.admin.add.btn"));
        deleteBTN.setText(bundle.getString("index.admin.delete.btn"));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("index.admin.categs")));
    }

    private void changePanelsDirections() {
        changeDirection(this);
        changeDirection(jScrollPane1);
        changeDirection(formatPanel);
        changeDirection(addListPanel);
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
            }else if (layout instanceof BorderLayout){
                System.out.println("BorderLayout");
            }
        }
    }

//    public static void centerWindow(final Component target) {
//        if (target != null) {
//            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//            Dimension dialogSize = target.getSize();
//
//            if (dialogSize.height > screenSize.height) {
//                dialogSize.height = screenSize.height;
//            }
//            if (dialogSize.width > screenSize.width) {
//                dialogSize.width = screenSize.width;
//            }
//
//            target.setLocation((screenSize.width - dialogSize.width) / 2,
//                    (screenSize.height - dialogSize.height) / 2);
//        }
//    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateFormatPanel = new javax.swing.JPanel();
        formatLBL = new javax.swing.JLabel();
        dateFormateCOMBO = new javax.swing.JComboBox();
        addListPanel = new javax.swing.JPanel();
        newListItemTXT = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemsLIST = new javax.swing.JList();
        addBTN = new javax.swing.JButton();
        deleteBTN = new javax.swing.JButton();
        emptyPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        catPanel = new javax.swing.JPanel();
        selectAllCHKBOX = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        formatPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        fieldTypeCOMBO = new javax.swing.JComboBox();
        fieldAliasTXT = new javax.swing.JTextField();
        fieldNameTXT = new javax.swing.JTextField();
        fieldTypeLBL = new javax.swing.JLabel();
        fieldAliaLBL = new javax.swing.JLabel();
        fieldNameLBL = new javax.swing.JLabel();

        formatLBL.setText("Format");

        dateFormateCOMBO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "dd/MM/yyyy", "MM/dd/yyyy", "dd-MM-yyyy", "MM-dd-yyyy", "dd/MM/yyyy HH:mm a", "MM/dd/yyyy HH:mm K", "dd-MM-yyyy HH:mm a", "MM-dd-yyyy HH:mm K" }));

        javax.swing.GroupLayout dateFormatPanelLayout = new javax.swing.GroupLayout(dateFormatPanel);
        dateFormatPanel.setLayout(dateFormatPanelLayout);
        dateFormatPanelLayout.setHorizontalGroup(
            dateFormatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dateFormatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(formatLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateFormateCOMBO, 0, 179, Short.MAX_VALUE)
                .addContainerGap())
        );
        dateFormatPanelLayout.setVerticalGroup(
            dateFormatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dateFormatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dateFormatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(formatLBL)
                    .addComponent(dateFormateCOMBO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        newListItemTXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newListItemTXTKeyPressed(evt);
            }
        });

        jScrollPane3.setViewportView(itemsLIST);

        addBTN.setText("Add");
        addBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBTNActionPerformed(evt);
            }
        });

        deleteBTN.setText("Delete");

        javax.swing.GroupLayout addListPanelLayout = new javax.swing.GroupLayout(addListPanel);
        addListPanel.setLayout(addListPanelLayout);
        addListPanelLayout.setHorizontalGroup(
            addListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(newListItemTXT, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addGroup(addListPanelLayout.createSequentialGroup()
                        .addComponent(addBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBTN)))
                .addContainerGap())
        );
        addListPanelLayout.setVerticalGroup(
            addListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newListItemTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBTN)
                    .addComponent(deleteBTN))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addContainerGap())
        );

        emptyPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Categories"));

        catPanel.setAutoscrolls(true);
        catPanel.setLayout(new java.awt.GridLayout(15, 1));

        selectAllCHKBOX.setText("Select ALL");
        selectAllCHKBOX.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectAllCHKBOXItemStateChanged(evt);
            }
        });
        catPanel.add(selectAllCHKBOX);

        jScrollPane1.setViewportView(catPanel);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        formatPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane2.setViewportView(formatPanel);

        jButton1.setText("Add...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        fieldTypeCOMBO.setModel(new DefaultComboBoxModel(typesList));
        fieldTypeCOMBO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fieldTypeCOMBOItemStateChanged(evt);
            }
        });

        fieldTypeLBL.setText("Field Type");

        fieldAliaLBL.setText("Field Alias");

        fieldNameLBL.setText("Field Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(fieldTypeLBL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fieldAliaLBL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fieldNameLBL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldAliasTXT)
                    .addComponent(fieldNameTXT, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addComponent(fieldTypeCOMBO, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldNameLBL)
                    .addComponent(fieldNameTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldAliaLBL)
                    .addComponent(fieldAliasTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldTypeLBL)
                    .addComponent(fieldTypeCOMBO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fieldTypeCOMBOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fieldTypeCOMBOItemStateChanged
        //TODO  Slwo refresh swing try to solve it;
        SwingWorker sw = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                formatPanel.removeAll();
                if (fieldTypeCOMBO.getSelectedItem().equals(Statics.DATE_VAL)) {
                    formatPanel.add(dateFormatPanel);
                } else if (fieldTypeCOMBO.getSelectedItem().equals(Statics.LIST_VAL)) {
                    formatPanel.add(addListPanel);
//        } else if (fieldTypeCOMBO.getSelectedItem().equals(Statics.BOOLEAN_VAL)) {
//
//        } else if (fieldTypeCOMBO.getSelectedItem().equals(Statics.NUMBER_VAL)) {
//
//        } else if (fieldTypeCOMBO.getSelectedItem().equals(Statics.STRING_VAL)) {

                } else {
                    formatPanel.add(emptyPanel);
                }
                formatPanel.validate();
//                changeDirection(formatPanel);
                return null;
            }
        };
        sw.execute();

    }//GEN-LAST:event_fieldTypeCOMBOItemStateChanged

    private void newListItemTXTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newListItemTXTKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            addBTNActionPerformed(null);
        }
    }//GEN-LAST:event_newListItemTXTKeyPressed

    private void addBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBTNActionPerformed
        itemsModel.addElement(newListItemTXT.getText());
        newListItemTXT.setText("");
        itemsLIST.validate();
    }//GEN-LAST:event_addBTNActionPerformed

    private void selectAllCHKBOXItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectAllCHKBOXItemStateChanged
        if (selectAllCHKBOX.isSelected()) {
            for (JCheckBox docCHK : documentsCHKBOX) {
                docCHK.setSelected(true);
            }
        } else {
            for (JCheckBox docCHK : documentsCHKBOX) {
                docCHK.setSelected(false);
            }
        }
    }//GEN-LAST:event_selectAllCHKBOXItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Field filed = collectData();
        FieldHome fieldHome = new FieldHome();
        fieldHome.persist(filed);
        JOptionPane.showMessageDialog(this, "Adding to DB");
        clearGUI();
    }//GEN-LAST:event_jButton1ActionPerformed

    private Field collectData() {
        Field field = new Field();
        String fieldName = fieldNameTXT.getText();
        String fieldAlias = fieldAliasTXT.getText();
        String fieldType = (String) fieldTypeCOMBO.getSelectedItem();

        field.setName(fieldName);
        field.setAlias(fieldAlias);
        field.setType(fieldType);

        if (fieldType.equals(Statics.DATE_VAL)) {
            String dateFormat = (String) dateFormateCOMBO.getSelectedItem();
            field.setDateFormat(dateFormat);
        } else if (fieldType.equals(Statics.LIST_VAL)) {
//            ListModel itemsModel = itemsLIST.getModel();
            for (int i = 0; i < itemsModel.getSize(); i++) {
                String item = (String) itemsModel.getElementAt(i);
                field.getListData().add(item);
            }
        }
        for (int i = 0; i < documentsCHKBOX.size(); i++) {
            JCheckBox jCheckBox = documentsCHKBOX.get(i);
            if (jCheckBox.isSelected()) {
                new CategoryHome().persist(categories.get(i));
                field.getAssociatedCategories().add(categories.get(i));
            }
        }
        return field;
    }

    private void clearGUI() {
        fieldNameTXT.setText("");
        fieldAliasTXT.setText("");
        fieldTypeCOMBO.setSelectedIndex(0);
        selectAllCHKBOX.setSelected(false);
        selectAllCHKBOXItemStateChanged(null);
    }

    private Properties loadProperties(File propsFile) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(propsFile);
        props.load(fis);
        fis.close();
        return props;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBTN;
    private javax.swing.JPanel addListPanel;
    private javax.swing.JPanel catPanel;
    private javax.swing.JPanel dateFormatPanel;
    private javax.swing.JComboBox dateFormateCOMBO;
    private javax.swing.JButton deleteBTN;
    private javax.swing.JPanel emptyPanel;
    private javax.swing.JLabel fieldAliaLBL;
    private javax.swing.JTextField fieldAliasTXT;
    private javax.swing.JLabel fieldNameLBL;
    private javax.swing.JTextField fieldNameTXT;
    private javax.swing.JComboBox fieldTypeCOMBO;
    private javax.swing.JLabel fieldTypeLBL;
    private javax.swing.JLabel formatLBL;
    private javax.swing.JPanel formatPanel;
    private javax.swing.JList itemsLIST;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField newListItemTXT;
    private javax.swing.JCheckBox selectAllCHKBOX;
    // End of variables declaration//GEN-END:variables
    private Vector<String> typesList;
    private DefaultListModel itemsModel;
    private List<JCheckBox> documentsCHKBOX;
    private List<Category> categories;
    private Locale systemLocal = new Locale("ar");
    private ResourceBundle bundle;
}
