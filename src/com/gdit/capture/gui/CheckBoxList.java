/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.gui;

/**
 *
 * @author bahy
 */
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CheckBoxList extends JList {

    protected static Border noFocusBorder =
            new EmptyBorder(1, 1, 1, 1);

    public CheckBoxList() {
        setCellRenderer(new CellRenderer());

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                int index = locationToIndex(e.getPoint());

                if (index != -1) {
                    JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
                    checkbox.setSelected(
                            !checkbox.isSelected());
                    repaint();
                }
            }
        });

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    protected class CellRenderer implements ListCellRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JCheckBox checkbox = (JCheckBox) value;
            checkbox.setBackground(isSelected ? getSelectionBackground() : getBackground());
            checkbox.setForeground(isSelected ? getSelectionForeground() : getForeground());
            checkbox.setEnabled(isEnabled());
            checkbox.setFont(getFont());
            checkbox.setFocusPainted(false);
            checkbox.setBorderPainted(true);
            checkbox.setBorder(isSelected ? UIManager.getBorder(
                    "List.focusCellHighlightBorder") : noFocusBorder);
            return checkbox;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(300, 300);
        final java.util.List<JCheckBox> list = new ArrayList<JCheckBox>();
        list.add(new JCheckBox("Ahmed"));
        list.add(new JCheckBox("Bahi"));
        list.add(new JCheckBox("Mona"));
        list.add(new JCheckBox("Alaa"));
        CheckBoxList chList = new CheckBoxList();
        chList.setListData(list.toArray());
        frame.getContentPane().add(chList, BorderLayout.CENTER);
        JButton btn = new JButton("Finish");
        btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for(JCheckBox item :list){
                    if(item.isSelected())
                        System.out.println(item.getText());
                }
            }
        });
        frame.add(btn,BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

