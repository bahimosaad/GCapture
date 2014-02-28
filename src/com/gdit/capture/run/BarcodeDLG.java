package com.gdit.capture.run;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.model.CaptureStatus;
import com.jidesoft.list.DefaultDualListModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.event.*;

/* ListDemo.java requires no other files. */
public class BarcodeDLG extends JDialog
        implements ListSelectionListener {

    private JList list;
    private DefaultListModel listModel;
    private static final String fireString = "Fire";
    private static final String searchString = "Search";
    private JButton fireButton;
    private JButton searchButton;
    private JTextField txtSearch;
    private ResourceBundle bundle;
    private Locale locale;
    private JFrame parent;
    private BarcodeDLG me;

    public BarcodeDLG(JFrame parent, Locale locale, ResourceBundle bundle,Properties properties) {

        super(parent, ModalityType.DOCUMENT_MODAL);
        me = this;
        this.bundle = bundle;
        this.locale = locale;
        this.parent = parent;
        listModel = new DefaultListModel();

        for (Object key: properties.keySet()) {
            String val = key +" = "+properties.getProperty(key.toString());
            listModel.addElement(val);
        }
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);



        fireButton = new JButton(fireString);
        fireButton.setActionCommand(fireString);
        fireButton.addActionListener(new FireListener());

        searchButton = new JButton(searchString);
        searchButton.setActionCommand(searchString);
        searchButton.addActionListener(new SearchListener());

        txtSearch = new JTextField();
        txtSearch.setSize(50,10);

       // String name = listModel.getElementAt(list.getSelectedIndex()).toString();

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(fireButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));


        buttonPane.add(txtSearch);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));

        buttonPane.add(searchButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));


        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);

        setSize(250, 600);
        setVisible(true);
    }

    class FireListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            String barcode =  (String) list.getModel().getElementAt(index);
            if(parent instanceof  CaptureMain){
                 ((CaptureMain)parent).setBarcode(barcode);
            }else if(parent instanceof QAMain){
                 ((QAMain)parent).setBarcode(barcode);
            }
           
            //parent.fillTree();
            me.dispose();
            CaptureHome.close();

        }
    }

    class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            CaptureHome dao = new CaptureHome();
//            List<Capture> caps = dao.findByName(txtSearch.getText(),CaptureStatus.QAMode);
//            if (cap != null) {
//                listModel = new DefaultDualListModel();
//                listModel.addElement(cap);
//                list.setModel(listModel);
//            }
//            CaptureHome.close();
        }
    }

    //This listener is shared by the text field and the hire button.
    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                fireButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                fireButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        //     QAPatchesDlg frame = new QAPatchesDlg();
        //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.

        //    frame.getContentPane().setOpaque(true); //content panes must be opaque
        //Display the window.
        //   frame.pack();
        //  frame.setVisible(true);
    }

    private void centerScreen() {
        setTitle("CenteredFrame");
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        });
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height/2;
        int screenWidth = screenSize.width/2;
        setSize(screenWidth, screenHeight - 20);
        setLocation(0, 0);

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }
}
