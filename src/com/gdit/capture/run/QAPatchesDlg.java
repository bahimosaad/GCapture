package com.gdit.capture.run;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.Users;
import com.gdit.capture.model.CaptureStatus;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.event.*;

/* ListDemo.java requires no other files. */
public class QAPatchesDlg extends JDialog
        implements ListSelectionListener {

    private JList list;
    private DefaultListModel listModel;
    private static String fireString = "Fire";
    private static String searchString = "Search";
    private static String barcodeString;
    private JButton fireButton;
    private JButton searchButton;
    private JButton barcodeButton;
    private JTextField txtSearch;
    private ResourceBundle bundle;
    private Locale locale;
    private JFrame parent;
    private QAPatchesDlg me;
    private Users user;
    private Category category;
    private JScrollPane listScrollPane;
    private CaptureHome dao;

    public QAPatchesDlg(JFrame parent, Users user, Locale locale, 
            ResourceBundle bundle, Category category,CaptureHome dao) {

        super(parent, false);
        me = this;
        this.bundle = bundle;
        this.locale = locale;
        this.parent = parent;
        this.user = user;
        this.category = category;
        this.dao = dao;
        listModel = new DefaultListModel();

        fireString = bundle.getString("patch.select");
        searchString = bundle.getString("patch.search");
//        CaptureHome dao = new CaptureHome();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        java.util.List<String> grands = null;
        if (parent instanceof QAMain) {
            grands = dao.getBatcheNamesByStatus(CaptureStatus.QAMode, category);
            System.out.println("GRANDS    " + grands.size());
        } else if (parent instanceof Barcode) {
            grands = dao.getUnBarcodedNames(category);
        } else if (parent instanceof ScanExceptionMain) {
            //  grands = dao.getBatchesByStatus(CaptureStatus.ExceptionMode, category);
            grands = dao.getBatcheNamesByStatus(CaptureStatus.ExceptionMode, category);
            // System.out.println("Grands "+grands.size());
        }  
        else if (parent instanceof UnOcredBatchMain) {
            grands = dao.getUnOcredBatcheNames(category);
        }
        for (String grand : grands) {
            listModel.addElement(grand);
        }
        //        CaptureHome.close();
        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        //  list.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(list);
        fireButton = new JButton(fireString);
        fireButton.registerKeyboardAction(fireButton.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_FOCUSED);

        fireButton.registerKeyboardAction(fireButton.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_FOCUSED);
        fireButton.setActionCommand(fireString);
        fireButton.addActionListener(new FireListener());


        searchButton = new JButton(searchString);
        searchButton.setActionCommand(searchString);
        searchButton.addActionListener(new SearchListener());

        barcodeButton = new JButton("بحث لباركود");
        barcodeButton.setActionCommand(barcodeString);
        barcodeButton.addActionListener(new BarcodeActionListener());
        txtSearch = new JTextField();
        txtSearch.setSize(50, 10);

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
        buttonPane.add(barcodeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));


        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);

        add(buttonPane, BorderLayout.PAGE_END);

        setSize(350, 600);
        this.centerScreen();
        setVisible(true);
        // CaptureHome.close();
    }

    class FireListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            SwingWorker swingWorker = new SwingWorker() {
                @Override
                protected Object doInBackground() {
                    try {
                        //This method can be called only if
                        //there's a valid selection
                        //so go ahead and remove whatever's selected.
                        int index = list.getSelectedIndex();

                        if (parent instanceof QAMain) {
                            ((QAMain) parent).release();
                            String batchName = (String) list.getModel().getElementAt(index);
//                            CaptureHome dao = new CaptureHome();
                            Capture batch = dao.findByName(batchName, 2, category);
                            batch.setLocked(true);
                            dao.attachDirty(batch);
//                            dao.updateLock(batch);
                            ((QAMain) parent).setBatch(batch); 
                            ((QAMain) parent).fillTree(); 
                        } else if (parent instanceof Barcode) {
                            String batchName = (String) list.getModel().getElementAt(index);
//                            CaptureHome dao = new CaptureHome();
                            Capture batch = dao.findByName(batchName, category);
                            batch.setLocked(true);
                            dao.attachDirty(batch);
//                            dao.updateLock(batch);
                            ((Barcode) parent).setBatch(batch);
                            ((Barcode) parent).fillTree();
                        } else if (parent instanceof ScanExceptionMain) {
                            String batchName = (String) list.getModel().getElementAt(index);
//                            CaptureHome dao = new CaptureHome();
                            Capture batch = dao.findByName(batchName, 3, category);
                            batch.setLocked(true);
                            dao.attachDirty(batch);
//                            dao.updateLock(batch);
                            ((ScanExceptionMain) parent).setBatch(batch);
                            ((ScanExceptionMain) parent).fillTree();
                        } else if (parent instanceof UnOcredBatchMain) {
                            String batchName = (String) list.getModel().getElementAt(index);
//                            CaptureHome dao = new CaptureHome();
                            Capture batch = dao.findByName(batchName, 2, category);
                            batch.setLocked(true);
                            dao.attachDirty(batch);
//                            dao.updateLock(batch);
                            ((UnOcredBatchMain) parent).setBatch(batch);
                            ((UnOcredBatchMain) parent).fillTree();

                        }
                         me.dispose();
                         
                       
                    } catch (Exception ex) {
                       ex.printStackTrace();
                    }  
                     return null;
                }

                @Override
                protected void done() {
                    me.dispose();
                }
            };
            swingWorker.execute();

        }
    }

    class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            CaptureHome dao = new CaptureHome();
            if (parent instanceof QAMain) {
                java.util.List<String> caps = dao.findByNames(txtSearch.getText().toUpperCase(), CaptureStatus.QAMode, category);

                if (caps != null) {
                    listModel = new DefaultListModel();
                    for (String cap : caps) {
                        listModel.addElement(cap);


                    }
                    list.setModel(listModel);
                }
            } else if (parent instanceof ScanExceptionMain) {
                java.util.List<String> caps = dao.findByNames(txtSearch.getText().toUpperCase(), CaptureStatus.ExceptionMode, category);
                if (caps != null) {
                    listModel = new DefaultListModel();
                    for (String cap : caps) {
                        listModel.addElement(cap);

                    }
                    list.setModel(listModel);
                }
            }
//            CaptureHome.close();
        }
    }

    class BarcodeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            CaptureHome dao = new CaptureHome();
            if (parent instanceof QAMain) {
                java.util.List<String> caps = dao.findByBarcode(txtSearch.getText(), CaptureStatus.QAMode, category);
                if (caps != null) {
                    listModel = new DefaultListModel();
                    for (String cap : caps) {
//                       Capture doc = dao.findByName(cap);
                        listModel.addElement(cap);


                    }
                    list.setModel(listModel);
                }
            } else if (parent instanceof ScanExceptionMain) {
                java.util.List<String> caps = dao.findByBarcode(txtSearch.getText(), CaptureStatus.ExceptionMode, category);
                if (caps != null) {
                    listModel = new DefaultListModel();
                    for (String cap : caps) {
//                        Capture doc = dao.findByName(cap);
//                        Capture c = dao.findByName(doc.getCapture().getName());
                        listModel.addElement(cap);

                    }
                    list.setModel(listModel);
                }
            }

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
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
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
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                    }
                });
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height / 2;
        int screenWidth = screenSize.width / 2;
        //setSize(screenWidth, screenHeight - 20);
        setLocation(screenWidth - this.getWidth() / 2, screenHeight - this.getHeight() / 2);



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
