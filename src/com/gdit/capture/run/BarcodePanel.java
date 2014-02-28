/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BarcodePanel.java
 *
 * Created on May 3, 2011, 3:08:51 PM
 */
package com.gdit.capture.run;

import com.gdit.capture.entity.Investigation;
import com.gdit.capture.entity.InvestigationHome;
import com.gdit.capture.entity.NsecHejraToGregorianHome;
import com.gdit.capture.entity.PatientsDoc;
import com.gdit.capture.entity.PatientsDocHome;
import com.gdit.capture.entity.UsersAudit;
import com.gdit.capture.entity.UsersAuditHome;
import com.gdit.capture.model.HijriCalendar;
import com.gdit.capture.model.PrintBarcode;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author bahi
 */
public class BarcodePanel extends javax.swing.JPanel {

    private DefaultComboBoxModel investModel;
    private int userId;
    private PatientsDoc doc;
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/gdit/bundle/capture");
    private PatientsDocHome docDao;
    private long batchId;
    /** Creates new form BarcodePanel */
    public BarcodePanel() {
        initComponents();
    }

    public BarcodePanel(PatientsDoc doc) {
        this.doc = doc;
        initComponents();
        init("update");
        //  show(doc);
    }

    public PatientsDoc show(String barcode,int userId,long batchId) {
        try {
            this.userId = userId;
            this.batchId = batchId;
             docDao = new PatientsDocHome();
             doc = docDao.findById(Long.valueOf(barcode));
            if (doc == null) {
                clear();
                return null;
            }
            init("update");
            txtDocNo.setText(doc.getDocNo());
            investigationsCombo.setSelectedItem(doc.getInvestigation());
            chkImage.setSelected(doc.getImg());
            String date = doc.getInvestigationDate();
            String[] formats = date.split("/");
            txtDay.setText(formats[0]);
            txtMonth.setText(formats[1]);
            txtYear.setText(formats[2]);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            PatientsDocHome.close();
            return doc;
        }
    }

    private void init(final String action) {
        InvestigationHome invDao = new InvestigationHome();
        investModel = new DefaultComboBoxModel(invDao.getAllInvestigation().toArray());
        investigationsCombo.setModel(investModel);
        txtDocNo.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();
                    investigationsCombo.requestFocus();
                }
            }
        });
        investigationsCombo.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();
                    txtDay.requestFocus();
                }
            }
        });
        txtDay.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();
                    txtMonth.requestFocus();
                }
            }
        });
        txtMonth.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();
                    txtYear.requestFocus();
                }
            }
        });

        txtYear.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();
                    chkImage.requestFocus();
                }
            }
        });
        updateButton.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();
                    // save();
                    txtDocNo.requestFocus();
                }
            }
        });
        chkImage.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    Toolkit.getDefaultToolkit().beep();
                    if (action.equals("save")) {
                        save();
                    } else if (action.equals("update")) {
                        update();
                    }
                    txtDocNo.requestFocus();
                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chkImage = new javax.swing.JCheckBox();
        updateButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDocNo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        txtDay = new javax.swing.JTextField();
        investigationsCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtMonth = new javax.swing.JTextField();

        updateButton.setText("تعديل");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("D");

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/gdit/bundle/capture"); // NOI18N
        jLabel7.setText(bundle.getString("image")); // NOI18N

        jLabel4.setText("Y");

        jLabel5.setText("M");

        jLabel1.setText("No");

        txtDay.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDayLostFocus(evt);
            }
        });

        investigationsCombo.setEditor(null);

        jLabel2.setText("Inv");

        txtMonth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMonthLostFocus(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel2, 0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDay, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkImage)
                        .addGap(18, 18, 18)
                        .addComponent(updateButton))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtDocNo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(investigationsCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, 187, Short.MAX_VALUE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDocNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(investigationsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(txtMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkImage)
                    .addComponent(jLabel7)
                    .addComponent(updateButton))
                .addContainerGap(78, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        update();
}//GEN-LAST:event_updateButtonActionPerformed

    private void txtDayLostFocus(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDayLostFocus
        // TODO add your handling code here:
        String day = txtDay.getText();
        if (Integer.valueOf(day) > 0 & Integer.valueOf(day) <= 9) {
            txtDay.setText("0" + day);
        }
}//GEN-LAST:event_txtDayLostFocus

    private void txtMonthLostFocus(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonthLostFocus
        // TODO add your handling code here:
        String month = txtMonth.getText();
        if (Integer.valueOf(month) > 0 & Integer.valueOf(month) <= 9) {
            txtMonth.setText("0" + month);
        }
}//GEN-LAST:event_txtMonthLostFocus

    public void save() {
        try {
            if (!check()) {
                return;
            }
            PatientsDocHome patientDocHome = new PatientsDocHome();
            PatientsDoc patientDoc = new PatientsDoc();
            patientDoc.setDocNo(txtDocNo.getText());
            String date = "";
            if (Integer.valueOf(txtYear.getText()) < 1433) {
                date = HijriCalendar.toGregString(txtDay.getText() + "/" + txtMonth.getText() + "/" + txtYear.getText());
                System.out.println(date);
            } else {
                date = txtDay.getText() + "/" + txtMonth.getText() + "/" + txtYear.getText();
            }
            patientDoc.setInvestigationDate(date);
            if (!chkImage.isSelected()) {
                patientDoc.setInvestigation((Investigation) investModel.getSelectedItem());
            }
            patientDoc.setImg(chkImage.isSelected());
            patientDoc.setUserId(userId);
            patientDocHome.persist(patientDoc);
            PrintBarcode.print(String.valueOf(patientDoc.getId()), date, patientDoc.getInvestigation() == null ? "" : patientDoc.getInvestigation().getName(), txtDocNo.getText());
            clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update() {
        try {
            if (!check()) {
                return;
            }
            doc.setDocNo(txtDocNo.getText());
            String date = "";
            if (Integer.valueOf(txtYear.getText()) < 1433) {
                date = HijriCalendar.toGregString(txtDay.getText() + "/" + txtMonth.getText() + "/" + txtYear.getText());
                System.out.println(date);
            } else {
                date = txtDay.getText() + "/" + txtMonth.getText() + "/" + txtYear.getText();
            }
            doc.setInvestigationDate(date);
            if (!chkImage.isSelected()) {
                doc.setInvestigation((Investigation) investModel.getSelectedItem());
            }
            doc.setImg(chkImage.isSelected());
            //     patientDoc.setUserId(userId);
            docDao.attachDirty(doc);
            docDao.commit();
            if(!chkImage.isSelected())
                PrintBarcode.print(String.valueOf(doc.getId()), date, doc.getInvestigation() == null ? "" : doc.getInvestigation().getName(), txtDocNo.getText());
            UsersAudit audit = new UsersAudit();
            UsersAuditHome auditDao = new UsersAuditHome();
            audit.setAction(3);
            audit.setModuleId(3);
            audit.setBatchId(batchId);
            audit.setAuditDate(new Date());
            audit.setUserId(userId);
            auditDao.persist(audit);
            auditDao.commit();
            // clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clear() {
        txtDay.setText("");
        txtDocNo.setText("");
        txtMonth.setText("");
        txtYear.setText("");
        chkImage.setSelected(false);
    }

    private boolean check() {
        try {
            if (txtDay.getText().trim().equals("") || txtMonth.getText().trim().equals("") || txtYear.getText().trim().equals("")) {
                String message = bundle.getString("parient.doc.date.required");
                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                int day = 0;
                int month = 0;
                int year = 0;
                try {
                    day = Integer.valueOf(txtDay.getText());
                    month = Integer.valueOf(txtMonth.getText());
                    year = Integer.valueOf(txtYear.getText());
                } catch (NumberFormatException ex) {
                    String message = bundle.getString("patient.doc.date.false");
                    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                NsecHejraToGregorianHome dao = new NsecHejraToGregorianHome();
                if (year > 1432) {

                    try {
                        String d = txtDay.getText() + "/" + txtMonth.getText() + "/" + txtYear.getText();
                        if (isValidDate(d)) {
                            return true;
                        } else {
                            String message = bundle.getString("patient.doc.date.false");
                            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    } catch (Exception e) {
                        String message = bundle.getString("patient.doc.date.false");
                        JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }


                } else if (day > 30 || month > 12 || year > 1450 || year > 1450 || (day == 30 && !dao.checkHejriDate(day, month, year))) {
                    String message = bundle.getString("patient.doc.date.false");
                    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            }
            if (txtDocNo.getText().trim().equals("")) {
                String message = bundle.getString("parient.doc.no.required");
                JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            NsecHejraToGregorianHome.close();
        }

    }

    public boolean isValidDate(String inDate) {

        if (inDate == null) {
            return false;
        }

        //set the format to use as a constructor argument
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if (inDate.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }

        dateFormat.setLenient(false);

        try {
            //parse the inDate parameter
            Date date = dateFormat.parse(inDate.trim());
            if (date.before(new Date())) {
                return true;
            } else {
                return false;
            }
            // return true;
        } catch (ParseException pe) {
            return false;
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkImage;
    private javax.swing.JComboBox investigationsCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtDay;
    private javax.swing.JTextField txtDocNo;
    private javax.swing.JTextField txtMonth;
    private javax.swing.JTextField txtYear;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
