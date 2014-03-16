package com.gdit.capture.run;

import com.asprise.util.tiff.TIFFReader;
import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.PatientsDoc;
import com.gdit.capture.entity.PatientsDocHome;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.Users;
import com.gdit.capture.entity.UsersAudit;
import com.gdit.capture.entity.UsersAuditHome;
import com.gdit.capture.gui.CategoriesDlg;
import com.gdit.capture.gui.LoginFrame;
import com.gdit.capture.gui.PatienDocPanel;
import com.gdit.capture.gui.ScannerSettingPanel;
import com.gdit.capture.main.ImagePanel;
import com.gdit.capture.model.BatchTreeItem;
import com.gdit.capture.model.CaptureStatus;
import com.gdit.capture.model.IconNode;
import com.gdit.capture.model.IconNodeRenderer;
import com.gdit.capture.model.ImageTreeItem;
import com.gdit.capture.model.ImageListModel;
import com.gdit.capture.model.ImageUtils;
import com.gdit.capture.model.PrintBarcode;
import com.gdit.image.ImageUtil;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.text.Position;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
public class UnOcredBatchMain extends JFrame {

    private JButton logoutBtn;
    private Rep rep;
    private JButton changeCategoryButton;
    private JButton zoomInBtn;
    private int screenHeight;
    private int screenWidth;
    private JLabel label = new JLabel();
    private BarcodePanel barcodePanel;
    private JideSplitPane vertSplit;
    private String docName;
    private JButton saveButton;
    private int zoom;
    private List<Capture> grands;
    private CaptureHome dao;

    /**
     * @param args the command line arguments
     */
    public UnOcredBatchMain(ResourceBundle bundle, Locale locale, Users user, Rep rep) {
        me = this;
        this.locale = locale;
        this.bundle = bundle;
        this.user = user;
        this.rep = rep;
        dao = new CaptureHome();
        if (rep != null && rep.getCategories().size() > 0) {
            category = (Category) rep.getCategories().toArray()[0];
        }
        properties = readSettingFile("C:/.capture/sysoptions.properties");
        //getBarcodes("C:/.capture\\barcode.properties");
     
        grands = dao.getUnOcredBatches();
        System.out.println("UnOcred " + grands.size());
        init();
//        CaptureHome.close();
        // fillTree();
    }

    private Properties readSettingFile(String filePath) {
        Properties prop = new Properties();
        try {
            //  prop.load(new FileInputStream(filePath));
            return prop;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (locale == null) {
            locale = new Locale("ar");

        }
        // locale = new Locale("ar");
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("com/gdit/bundle/capture", locale);
        }

        jSplitPane1 = new JideSplitPane();


        centerPanel = new ImagePan();

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        //   tree.addTreeSelectionListener(this);

        add(getToolBar(), BorderLayout.NORTH);
        add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.setProportionalLayout(true);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setDividerSize(5);
        jSplitPane1.addPane(jScrollPane2);
        jSplitPane1.addPane(jScrollPane1);
        jSplitPane1.setShowGripper(true);
        //jScrollPane2.setViewportView(tree);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        centerPanel.setPreferredSize(new Dimension(screenWidth - 300, screenHeight - 100));
        centerPanel.add(label);
        jScrollPane1.setViewportView(centerPanel);
        double[] ds = new double[]{0.2};
        jSplitPane1.setProportions(ds);

        vertSplit = new JideSplitPane();
        jScrollPane2.setViewportView(vertSplit);
        barcodePanel = new BarcodePanel();
        double[] ds1 = new double[]{0.7};
        vertSplit.setProportionalLayout(true);
        vertSplit.setOneTouchExpandable(true);
        vertSplit.setContinuousLayout(true);
        vertSplit.setOrientation(vertSplit.VERTICAL_SPLIT);
        vertSplit.setDividerSize(5);
        vertSplit.addPane(tree);
        vertSplit.addPane(barcodePanel);
        vertSplit.setProportions(ds1);
        refuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                    String refuseNote = JOptionPane.showInputDialog(bundle.getString("refuse.note"));
                    if (lastIndexedNode == null || lastIndexedNode.getUserObject() instanceof BatchTreeItem) {
                        CaptureHome dao = new CaptureHome();
                        batch.setStatus(CaptureStatus.ExceptionMode);
                        batch.setRefused(true);
                        batch.setRefuseNote(refuseNote);
                        batch.setLocked(false);
                        batch.setName(docName);
                        dao.attachDirty(batch);
                        dao.updateStatus(batch);
                        dao.updateLock(batch);
                        CaptureHome.close();
                        IconNode node = (IconNode) root.children().nextElement();
                        imageModel.removeNodeFromParent(node);
                        tree.updateUI();
                        PrintBarcode.print(String.valueOf(batch.getName()), "KKUH", "Vascular Lab", "KSU");
                    } else if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                        ImageTreeItem item = (ImageTreeItem) lastIndexedNode.getUserObject();
                        Capture cap = item.getCapture();
                        CaptureHome dao = new CaptureHome();
                        Capture parent = cap.getCapture().getCapture();
                        cap.setRefused(true);
                        cap.setRefuseNote(refuseNote);
                        parent.setRefused(true);
                        dao.attachDirty(cap);
                        dao.attachDirty(parent);
                        CaptureHome.close();
                        //Capture.close();
                        IconNode pr = (IconNode) lastIndexedNode.getParent();
                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icons/delete.jpg"));
                        IconNode temp = new IconNode(item, false, scaleImage(icon.getImage(), 16, 16), cap.getName());
                        int index = pr.getIndex(lastIndexedNode);
                        imageModel.insertNodeInto(temp, pr, index + 1);
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();

                    } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                        // ImageItem item = (ImageItem) lastIndexedNode.getUserObject();
                        Capture cap = (Capture) lastIndexedNode.getUserObject();
                        CaptureHome dao = new CaptureHome();
                        Capture parent = cap.getCapture();
                        cap.setRefused(true);
                        cap.setRefuseNote(refuseNote);
                        dao.attachDirty(cap);
                        dao.attachDirty(parent);
                        CaptureHome.close();
                        //Capture.close();
                        IconNode pr = (IconNode) lastIndexedNode.getParent();
                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("icons/delete.jpg"));
                        IconNode temp = new IconNode(cap, true, scaleImage(icon.getImage(), 16, 16), cap.getName());
                        Enumeration en = lastIndexedNode.children();
                        while (en.hasMoreElements()) {
                            TreeNode n = (TreeNode) en.nextElement();
                            temp.add((MutableTreeNode) n);
                            en = lastIndexedNode.children();
                        }
                        UsersAudit audit = new UsersAudit();
                        audit.setBatchId(batch.getId());
                        audit.setUserId(user.getId());
                        audit.setModuleId(3);
                        audit.setAction(2);
                        audit.setAuditDate(new Date());
                        UsersAuditHome auditHome = new UsersAuditHome();
                        auditHome.persist(audit);
                        auditHome.commit();
                        int index = pr.getIndex(lastIndexedNode);
                        imageModel.insertNodeInto(temp, pr, index + 1);
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    CaptureHome.close();
                }
            }
        });
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                me.dispose();
//                LoginFrame login = new LoginFrame();
//                login.centerScreen();
//                login.setVisible(true);
                IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                String barcode = null;

                if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                    Capture cap = ((ImageTreeItem) lastIndexedNode.getUserObject()).getCapture().getCapture();
                    barcode = cap.getBarcode();
                } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                    Capture cap = ((Capture) lastIndexedNode.getUserObject()).getCapture();
                    barcode = cap.getBarcode();
                } else {
                    return;
                }
                PatientsDocHome dao = new PatientsDocHome();
                PatientsDoc doc = dao.findByById(barcode);
                JDialog dlg = new JDialog(me, ModalityType.TOOLKIT_MODAL);
                dlg.setSize(500, 500);
                Toolkit tk = Toolkit.getDefaultToolkit();
                Dimension screenSize = tk.getScreenSize();
                int screenHeight = screenSize.height / 2;
                int screenWidth = screenSize.width / 2;
                //setSize(screenWidth, screenHeight - 20);
                dlg.setLocation(screenWidth - dlg.getWidth() / 2, screenHeight - dlg.getHeight() / 2);
                PatienDocPanel panel = new PatienDocPanel(doc);
                dlg.add(panel);
                dlg.setVisible(true);
            }
        });
        selectBatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (batch != null) {
                        CaptureHome dao = new CaptureHome();
                        batch.setLocked(false);
                        dao.attachDirty(batch);
                        dao.updateLock(batch);
                    }

                    new UnOcredBatchDlg(me, user, locale, bundle, category, grands);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    CaptureHome.close();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                //             if (lastIndexedNode != null && lastIndexedNode.isLeaf()) {
                String[] textMessages = new String[3];
                textMessages[0] = bundle.getString("yes");
                textMessages[1] = bundle.getString("no");
                textMessages[2] = bundle.getString("cancel");
                JOptionPane jop = new JOptionPane(bundle.getString("delete.msg"), JOptionPane.WARNING_MESSAGE,
                        JOptionPane.YES_NO_CANCEL_OPTION, null, textMessages);
                JDialog jopDialog = jop.createDialog(null, bundle.getString("delete.title"));
                jopDialog.setVisible(true);
                String result = (String) jop.getValue();
                if (result.equals(bundle.getString("yes"))) {
                    if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                        ImageTreeItem item = (ImageTreeItem) lastIndexedNode.getUserObject();
                        Capture itemCapture = item.getCapture();
                        CaptureHome dao = new CaptureHome();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
                        CaptureHome.close();
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
//                        centerPanel.removeAll();
//                        centerPanel.validate();
//                        jScrollPane1.repaint();
                    } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                        Capture itemCapture = (Capture) lastIndexedNode.getUserObject();
                        CaptureHome dao = new CaptureHome();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
                        CaptureHome.close();
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
//                        centerPanel.removeAll();
//                        centerPanel.validate();
//                        jScrollPane1.repaint();
                    } else if (lastIndexedNode.getUserObject() instanceof BatchTreeItem) {
                        BatchTreeItem item = (BatchTreeItem) lastIndexedNode.getUserObject();
                        CaptureHome dao = new CaptureHome();
                        Capture itemCapture = item.getCapture();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
                        CaptureHome.close();
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
//                        centerPanel.removeAll();
//                        centerPanel.validate();
//                        jScrollPane1.repaint();
                    }

                }
            }
        });
        releaseBatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (batch != null) {
                        CaptureHome dao = new CaptureHome();
                        batch.setLocked(false);
                        dao.attachDirty(batch);
                        dao.updateLock(batch);
                        CaptureHome.close();
                        grands.remove(grands.indexOf(batch));
                        imageModel.removeNodeFromParent((MutableTreeNode) root.children().nextElement());
                        tree.updateUI();
                        centerPanel.removeAll();
                        centerPanel.validate();
                        jScrollPane1.repaint();
                    } else {
                        JOptionPane.showConfirmDialog(me, "You must select Batch");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    CaptureHome.close();
                    //   Capture.close();
                }

            }
        });
        indexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (batch != null) {
                        batch.setStatus(CaptureStatus.IndexMode);
                        batch.setLocked(false);
                        batch.setName(docName);
                        dao.attachDirty(batch);
                        CaptureHome dao = new CaptureHome();
                        dao.updateStatus(batch);
                        imageModel.removeNodeFromParent((MutableTreeNode) root.children().nextElement());
                        UsersAudit audit = new UsersAudit();
                        audit.setBatchId(batch.getId());
                        audit.setUserId(user.getId());
                        audit.setModuleId(3);
                        audit.setAction(1);
                        audit.setAuditDate(new Date());
                        UsersAuditHome auditHome = new UsersAuditHome();
                        auditHome.persist(audit);
                        auditHome.commit();

                        tree.updateUI();
                        centerPanel.removeAll();
                        centerPanel.validate();
                        jScrollPane1.repaint();
                        PrintBarcode.print(String.valueOf(batch.getName()), "KKUH", "Vascular Lab", "KSU");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                }
            }
        });
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                CaptureHome dao = null;
                zoom = 0;
                try {
                    IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                    if (lastIndexedNode == null) {
                        return;
                    }
                    if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                        ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
                        if (img.getImage() == null) {
                            File page = new File(img.getPath());
                            System.out.println(img.getPath());
                            Image image = null;
                            if (img.getPath().endsWith(".tif")) {
                                TIFFReader reader = new TIFFReader(page);
                                image = (Image) reader.getPage(0);
                            } else if (img.getPath().endsWith(".jpg")) {
                                image = ImageIO.read(page);
                            }
                            if (img.getPath().endsWith(".jpg")) {
                                BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
                                Graphics2D bg = bi.createGraphics();
                                bg.drawImage(image.getScaledInstance(800, 800, 1), 0, 0, 800, 800, 0, 0, 800, 800, null);
                                bg.dispose();//cleans up resources
                                label.setIcon(new ImageIcon(bi));
                            } else {
                                BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
                                Graphics2D bg = bi.createGraphics();
                                bg.drawImage(image.getScaledInstance(800, 800, Image.SCALE_SMOOTH), 0, 0, 800, 800, 0, 0, 800, 800, null);
                                bg.dispose();//cleans up resources
                                label.setIcon(new ImageIcon(bi));
                            }
                            label.setPreferredSize(new Dimension(800, 800));
                            jScrollPane1.setViewportView(label);
                            String barcode = img.getCapture().getCapture().getBarcode();
                            PatientsDoc doc = barcodePanel.show(barcode, user.getId(), batch.getId());
                            if (doc != null) {
                                docName = doc.getDocNo();
                            }
                        } else {
                            label.setIcon(img.getImage());
                            jScrollPane1.setViewportView(label);
                            String barcode = img.getCapture().getCapture().getBarcode();
                            PatientsDoc doc = barcodePanel.show(barcode, user.getId(), batch.getId());
                            if (doc != null) {
                                docName = doc.getDocNo();
                            }
                        }
                    } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                        Capture capture = (Capture) lastIndexedNode.getUserObject();
                        PatientsDoc doc = barcodePanel.show(capture.getBarcode(), user.getId(), batch.getId());
                        if (doc != null) {
                            docName = doc.getDocNo();
                        }
                    } else {
                        //barcodePanel.show(null, user.getId(), batch.getId());
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    CaptureHome.close();
                }
            }
        });
        changeCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoriesDlg dlg = new CategoriesDlg(me, true, rep, user, bundle);
                dlg.setVisible(true);


            }
        });
        rotateRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int w = 800;
                int h = 800;
                zoom++;
                ImageIcon icon = (ImageIcon) label.getIcon();
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics2D bg = bi.createGraphics();
                bg.rotate(Math.toRadians(90), w / 2, h / 2);
                bg.drawImage(icon.getImage(), 0, 0, w, h, 0, 0, w, h, null);
                bg.dispose();//cleans up resources
                label.setIcon(new ImageIcon(bi));
                label.setPreferredSize(new Dimension(icon.getIconHeight(), icon.getIconWidth()));
                jScrollPane1.setViewportView(label);
            }
        });
        rotateLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // centerPanel.rotate();
                //  rotateLeft();
                int w = 800;
                int h = 800;
                zoom--;
                ImageIcon icon = (ImageIcon) label.getIcon();
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics2D bg = bi.createGraphics();
                bg.rotate(Math.toRadians(-90), w / 2, h / 2);
                bg.drawImage(icon.getImage(), 0, 0, w, h, 0, 0, w, h, null);
                bg.dispose();//cleans up resources
                label.setIcon(new ImageIcon(bi));
                label.setPreferredSize(new Dimension(icon.getIconHeight(), icon.getIconWidth()));
                jScrollPane1.setViewportView(label);
            }
        });
        zoomInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // centerPanel.zoomin();
                jScrollPane1.validate();
            }
        });
        zoomOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // centerPanel.zoomout();
                jScrollPane1.validate();
            }
        });
        docButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (localNode.getUserObject() instanceof ImageTreeItem) {
                        String docName = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                        Capture newCapture = new Capture();
                        newCapture.setBarcode(docName);
                        newCapture.setName("Doc " + batch.getCaptures().size());
                        newCapture.setCapture(batch);
                        newCapture.setStatus(CaptureStatus.QAMode);
                        newCapture.setType(2);
                        newCapture.setDeleted(false);
                        dao.persist(newCapture);
                        //  CaptureHome.close();
                        IconNode newParent = new IconNode(newCapture);

                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                        IconNode oldParent = (IconNode) selectedNode.getParent();
                        int selectedIndex = oldParent.getIndex(selectedNode);
                        ArrayList<DefaultMutableTreeNode> nodes = Collections.list(oldParent.children());
                        for (int i = selectedIndex; i < nodes.size(); i++) {
                            DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes.get(i);
                            //  Capture nodeCapture = imagesPathMap.get(selectedNode.getUserObject().toString()).getCapture();
                            Capture nodeCapture = ((ImageTreeItem) node.getUserObject()).getCapture();
                            nodeCapture.setCapture(newCapture);
                            dao.attachDirty(nodeCapture);
                            System.out.println(" Node      " + node.getUserObject().toString());
                            newParent.add(node);
                        }
                        int index = root.getIndex(oldParent);
                        ((IconNode) oldParent.getParent()).add(newParent);
                        //imageModel.addLeaf(root,newParent);//, root, index + 1);
                        tree.updateUI();
                    } else if (localNode.getUserObject() instanceof BatchTreeItem) {
                        CaptureHome dao = new CaptureHome();
                        String docName = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                        Capture newCapture = new Capture();
                        newCapture.setBarcode(docName);
                        newCapture.setName("Doc " + batch.getCaptures().size());
                        newCapture.setCapture(batch);
                        newCapture.setStatus(CaptureStatus.QAMode);
                        newCapture.setDeleted(false);
                        newCapture.setType(2);
                        dao.persist(newCapture);
                        //     batch = dao.findById(batch.getId());
                        IconNode newParent = new IconNode(newCapture);
                        IconNode grandNode = (IconNode) root.children().nextElement();
                        imageModel.addLeaf(grandNode, newParent);
                        //   root.add(newParent);
                        tree.updateUI();
                    } else {
                        JOptionPane.showMessageDialog(me, bundle.getString("error.message.create.doc"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    CaptureHome.close();
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                    ImageTreeItem imgItem = (ImageTreeItem) lastIndexedNode.getUserObject();
                    ImageIcon icon = (ImageIcon) label.getIcon();
                    Image img = icon.getImage();
                    if (imgItem.getPath().endsWith(".jpg")) {
                        int w = zoom * 90;
                        rotate(w, imgItem.getPath());
                    } else {
                        int w = zoom * 90;
                        rotate(w, imgItem.getPath());
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode(rep.getName());
        String rootName = rep.getName() + "--" + category.getName();
        root = new IconNode(rootName);
        root1.add(root);
        imageModel = new ImageListModel(root);
        tree.setModel(imageModel);
        disableActions(true);
        changeScreenDirection(this, locale);

    }// </edit

    public void rotateLeft() {
        try {
            ImageIcon icon = (ImageIcon) label.getIcon();
            BufferedImage source = ImageGenerator.createBufferedImage(icon.getImage());
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(90), source.getHeight() / 2, source.getHeight() / 2);
            BufferedImage rotated = new BufferedImage(source.getHeight(), source.getWidth(), BufferedImage.TYPE_INT_RGB);
            Graphics2D gR = rotated.createGraphics();
            gR.drawRenderedImage(source, transform);
            gR.dispose();
            File output = new File("C:/desert.png");
            ImageIO.write(rotated, "PNG", output);
            //        label.setIcon(new ImageIcon(rotated));
            //        jScrollPane1.setViewportView(label);
        } catch (IOException ex) {
            Logger.getLogger(QAMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rotate(int angle, String path) {
        try {
            if (path.endsWith(".jpg")) {
                BufferedImage img = ImageIO.read(new File(path));
                int w = img.getWidth();
                int h = img.getHeight();
                int scale = w >= h ? w : h;
                BufferedImage dimg = dimg = new BufferedImage(scale, scale, img.getType());
                Graphics2D g = dimg.createGraphics();
                g.rotate(Math.toRadians(angle), scale / 2, scale / 2);
                g.drawImage(img, null, 0, 0);
                g.drawImage(img.getScaledInstance(scale, scale, 1), 0, 0, scale, scale, 0, 0, scale, scale, null);
                ImageIO.write(dimg, "JPEG", new File(path));
            } else {
                TIFFWriter writer = new TIFFWriter();
                TIFFReader readr = new TIFFReader(new File(path));
                Image image = (Image) readr.getPage(0);
                BufferedImage img = ImageGenerator.createBufferedImage(image);
                int w = img.getWidth();
                int h = img.getHeight();
                int scale = w >= h ? w : h;
                BufferedImage dimg = dimg = new BufferedImage(scale, scale, img.getType());
                Graphics2D g = dimg.createGraphics();
                g.rotate(Math.toRadians(angle), scale / 2, scale / 2);
                g.drawImage(img, null, 0, 0);
                g.drawImage(img.getScaledInstance(scale, scale, 1), 0, 0, scale, scale, 0, 0, scale, scale, null);
                writer.createTIFFFromImages(new BufferedImage[]{dimg}, new File(path));
            }


        } catch (IOException ex) {
            Logger.getLogger(QAMain.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void changeCategory(Category category) {
        System.out.println(this.category.getId() + " " + category.getId());
        if (this.category.getId() != category.getId()) {
            this.category = category;
            fillTree();
            try {
                batch.setLocked(false);
                dao.attachDirty(batch);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
            batch = null;
            imageModel.removeNodeFromParent((MutableTreeNode) root.children().nextElement());
            tree.updateUI();
            centerPanel.removeAll();
            centerPanel.validate();
            jScrollPane1.repaint();

        }

    }

    private Properties getBarcodes(String path) {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void changeScreenDirection(Component screen, Locale locale) {
        try {
            if (locale == null) {
                locale = new Locale("ar");

            }
            if (ComponentOrientation.getOrientation(locale).equals(ComponentOrientation.RIGHT_TO_LEFT)) {
                JideSwingUtilities.toggleRTLnLTR(screen);
                JideSwingUtilities.invalidateRecursively(screen);
                SwingUtilities.updateComponentTreeUI(screen);

            }
        } catch (Exception ex) {
            log.log(Level.SEVERE, "", ex);
        }
    }

    private Boolean checkDocuments() {
        try {
            String patientNo = "";
            PatientsDocHome docHome = new PatientsDocHome();
            for (Capture docCap : batch.getCaptures()) {
                if (docCap.getCaptures() != null && docCap.getCaptures().size() > 0 && docCap.getBarcode() != null && !docCap.getBarcode().equals("")) {
                    PatientsDoc doc = docHome.findById(Long.valueOf(docCap.getBarcode()));
                    if (patientNo.equals("")) {
                        patientNo = doc.getDocNo();
                    }
                    if (!patientNo.equals(doc.getDocNo())) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void fillTree() {
        try {
            if (!checkDocuments()) {
                JOptionPane.showMessageDialog(this, "يوجد هناك خطأ في رقم المريض");
            }
            tree.setCellRenderer(new IconNodeRenderer());
            IconNode root1 = new IconNode(rep.getName());
            String rootName = rep.getName() + "--" + category.getName();
            root = new IconNode(rootName);
            root1.add(root);
            imageModel = new ImageListModel(root);

            if (batch != null) {
                BatchTreeItem batchItem = new BatchTreeItem();
                batchItem.setName(batch.getName());
                batchItem.setCapture(batch);
                IconNode grandNode = new IconNode(batchItem);

                //  category = batch.getCategory();

                repPath = category.getRep().getPath();
                String[] names = repPath.split("/");
                String path1 = names[names.length - 1];
                String path = "\\\\" + properties.getProperty("server.ip") + "\\" + path1 + "\\" + batch.getId();
                imageModel.addLeaf(root, grandNode);
                for (Capture parent : batch.getCaptures()) {
                    if (parent.getCaptures() == null || parent.getCaptures().size() <= 0) {
                        continue;
                    }
                    IconNode parentNode = new IconNode(parent);
                    imageModel.addLeaf(grandNode, parentNode);
                    for (Capture son : parent.getCaptures()) {
                        if (!son.getDeleted()) {
                            ImageTreeItem sonNode = new ImageTreeItem();
                            sonNode.setName(son.getName());
                            sonNode.setPath(path + "/" + son.getPath());
                            sonNode.setCapture(son);
                            IconNode child = new IconNode(sonNode);
                            imageModel.addLeaf(parentNode, child);
                        }
                    }
                }
            }

            tree.setModel(imageModel);
            tree.updateUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CaptureHome.close();
            //Capture.close();
        }
    }

    private void disableActions(boolean flag) {
        deleteButton.setEnabled(flag);
//        exceptionButton.setEnabled(flag);
        releaseBatchButton.setEnabled(flag);
        refuseButton.setEnabled(flag);
        indexButton.setEnabled(flag);
        // rotateLeft.setEnabled(flag);
        rotateRight.setEnabled(flag);
    }

    private JToolBar getToolBar() {
        if (toolBar == null) {
            toolBar = new JToolBar();
            toolBar.setFloatable(false);
            toolBar.add(getSelectBatchButton());
            toolBar.add(getRefuseButton());
            toolBar.add(getIndexButton());
            toolBar.add(getRotateRightBttn());
            toolBar.add(getRotateLeftBttn());
            toolBar.add(getZoomInBtn());
            toolBar.add(getZoomOutBtn());
            toolBar.add(getDeleteButton());
            toolBar.add(getReleaseBatch());
            toolBar.add(getCreateDocButton());
            toolBar.add(getSaveButton());
            toolBar.add(getCreateChangeCategory());
            toolBar.add(Box.createHorizontalStrut(100));
            toolBar.add(getLogoutButton());

        }

        return toolBar;
    }

    private JButton getCreateChangeCategory() {
        if (changeCategoryButton == null) {
            changeCategoryButton = new JButton(bundle.getString("change.category"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/category.png"));
            icon = scaleImage(icon.getImage());
            changeCategoryButton.setIcon(icon);
            changeCategoryButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            changeCategoryButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return changeCategoryButton;
    }

    private JButton getRefuseButton() {
        if (refuseButton == null) {
            refuseButton = new JButton();
            refuseButton.setText(bundle.getString("page.refuse"));
            //   scanButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/refuse2.jpg"));
            icon = scaleImage(icon.getImage());
            refuseButton.setIcon(icon);
            refuseButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            refuseButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return refuseButton;
    }

    private JButton getIndexButton() {
        if (indexButton == null) {
            indexButton = new JButton();
            indexButton.setText(bundle.getString("approve"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/done.jpg"));
            icon = scaleImage(icon.getImage());
            indexButton.setIcon(icon);
            indexButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            indexButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return indexButton;
    }

    private JButton getRotateRightBttn() {
        if (rotateRight == null) {
            rotateRight = new JButton(bundle.getString("rotate.right"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/right.jpg"));
            icon = scaleImage(icon.getImage());
            rotateRight.setIcon(icon);
            rotateRight.setVerticalTextPosition(SwingConstants.BOTTOM);
            rotateRight.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return rotateRight;
    }

    private JButton getRotateLeftBttn() {
        if (rotateLeft == null) {
            rotateLeft = new JButton();
            rotateLeft.setText(bundle.getString("rotate.left"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/left.jpg"));
            icon = scaleImage(icon.getImage());
            rotateLeft.setIcon(icon);
            rotateLeft.setVerticalTextPosition(SwingConstants.BOTTOM);
            rotateLeft.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return rotateLeft;
    }

    private ImageIcon scaleImage(Image img) {
        Image scaledImage = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        return icon;
    }

    private ImageIcon scaleImage(Image img, int w, int h) {
        Image scaledImage = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        return icon;
    }

    private JButton getLogoutButton() {
        if (logoutBtn == null) {
            logoutBtn = new JButton(bundle.getString("logout"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/logout.jpg"));
            logoutBtn.setIcon(scaleImage(icon.getImage()));
            logoutBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
            logoutBtn.setHorizontalTextPosition(SwingConstants.CENTER);


        }

        return logoutBtn;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton(bundle.getString("delete"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/delete1.jpg"));
            icon = scaleImage(icon.getImage());
            deleteButton.setIcon(icon);
            deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return deleteButton;
    }

    private JButton getExceptionButton() {
        if (exceptionButton == null) {
            exceptionButton = new JButton(bundle.getString("exception.send"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/error.jpg"));
            icon = scaleImage(icon.getImage());
            exceptionButton.setIcon(icon);
            exceptionButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            exceptionButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return exceptionButton;
    }

    private JButton getReleaseBatch() {
        if (releaseBatchButton == null) {
            releaseBatchButton = new JButton(bundle.getString("release.batch"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/release.jpg"));
            icon = scaleImage(icon.getImage());
            releaseBatchButton.setIcon(icon);
            releaseBatchButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            releaseBatchButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return releaseBatchButton;
    }

    private JButton getCreateDocButton() {
        if (docButton == null) {
            docButton = new JButton(bundle.getString("create.doc"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/doc.jpg"));
            icon = scaleImage(icon.getImage());
            docButton.setIcon(icon);
            docButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            docButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return docButton;
    }

    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton();
            saveButton.setText(bundle.getString("save"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/save.jpg"));
            icon = scaleImage(icon.getImage());
            saveButton.setIcon(icon);
//            saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
//            saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return saveButton;
    }

    private JButton getZoomOutBtn() {
        if (zoomOutBtn == null) {
            zoomOutBtn = new JButton(bundle.getString("zoomout"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/zoomout.jpg"));
            icon = scaleImage(icon.getImage());
            zoomOutBtn.setIcon(icon);
            zoomOutBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
            zoomOutBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return zoomOutBtn;
    }

    private JButton getZoomInBtn() {
        if (zoomInBtn == null) {
            zoomInBtn = new JButton(bundle.getString("zoomin"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/zoomin.jpg"));
            icon = scaleImage(icon.getImage());
            zoomInBtn.setIcon(icon);
            zoomInBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
            zoomInBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return zoomInBtn;
    }

    private JButton getSelectBatchButton() {
        if (selectBatchButton == null) {
            selectBatchButton = new JButton(bundle.getString("batch.select"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/batch.png"));
            icon = scaleImage(icon.getImage());
            selectBatchButton.setIcon(icon);
            selectBatchButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            selectBatchButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return selectBatchButton;
    }

    public void centerScreen() {
        setTitle("GDocScanQA");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (root.getChildCount() > 0) {
                    CaptureHome dao = new CaptureHome();
                    batch.setLocked(false);
                    dao.attachDirty(batch);
                    dao.updateLock(batch);
                    CaptureHome.close();
                }
                System.exit(0);
            }
        });
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth, screenHeight - 20);
        setLocation(0, 0);

    }

    public static void main(String[] args) {
//         TODO code application logic here
//        UnOcredBatchMain main = new UnOcredBatchMain();
//        main.setSize(800, 600);
//        main.centerScreen();
//        main.setVisible(true);
    }

    public Capture getBatch() {
        return batch;
    }

    public void setBatch(Capture batch) {
        this.batch = batch;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    private Users user;
    private boolean ocred;
    private String barcode;
    private UnOcredBatchMain me;
    private Capture batch;
    private boolean disabled;
    private String repPath;
    private String docDir;
    private String batchName;
    private Category category;
    private Capture cap;
    private static JFrame viewFrame;
    private ImageListModel imageModel;
    private IconNode root;
    private String docId;
    //  private String batchId;
    private ResourceBundle bundle;
    private int rotate;
    private int zoomout;
    private ScannerSettingPanel settPanel;
    private Locale locale;
    private ImagePanel selected = null;
    private Properties properties;
    private String selectedSource;
    private javax.swing.JTable propTable;
    private JPanel centerPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private JideSplitPane jSplitPane1;
    private javax.swing.JTree tree;
    private JPopupMenu popupMenu;
    private JButton refuseButton;
    private JButton selectBatchButton;
    private JButton zoomOutBtn;
    private JButton releaseBatchButton;
    private JButton docButton;
    private JButton exceptionButton;
    private JButton deleteButton;
    private JButton rotateRight;
    private JButton rotateLeft;
    private JButton customDlgBttn;
    private JButton indexButton;
    private JToolBar toolBar;
//    private JTextField batchText;
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getAnonymousLogger();
}
