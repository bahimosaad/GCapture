package com.gdit.capture.run;

import com.asprise.util.tiff.TIFFReader;
import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.entity.Scanner;
import com.gdit.capture.entity.*;
import com.gdit.capture.gui.CategoriesDlg;
import com.gdit.capture.gui.LoginFrame;
import com.gdit.capture.gui.PatienDocPanel;
import com.gdit.capture.gui.ScannerSettingPanel;
import com.gdit.capture.main.ImagePanel;
import com.gdit.capture.model.*;
import com.gdit.image.ImageLoader;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.Dialog.ModalityType;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;
import org.tempuri.CompressToTiff;
/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

/**
 *
 * @author bahy
 */
public class QAMain extends JFrame implements Runnable {

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
    private HashMap<String, String> capMap;
    private SmbFile batchFile;
    private Map<String, SmbFile> images;
    private NtlmPasswordAuthentication authentication;
    private Server server;
    private BufferedImage currentBimg;
    private Disk disk;
    private CaptureHome dao;

    /**
     * @param args the command line arguments
     */
    public QAMain(ResourceBundle bundle, Locale locale, Users user, Rep rep) {
        me = this;
        this.locale = locale;
        this.bundle = bundle;
        this.user = user;
        this.rep = rep;
        if (rep != null && rep.getCategories().size() > 0) {
            category = (Category) rep.getCategories().toArray()[0];
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centerScreen();
        setVisible(true);
        dao = new CaptureHome();
        //  properties = readSettingFile("C:/.capture/sysoptions.properties");
        //getBarcodes("C:/.capture\\barcode.properties");
//        init();
        // fillTree();
    }

//    private Properties readSettingFile(String filePath) {
//        Properties prop = new Properties();
//        try {
//            prop.load(new FileInputStream(filePath));
//            return prop;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Override
    public void run() {
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

        ScannerHome scanHome = new ScannerHome();
        List<Scanner> scanners = scanHome.getAllCaps();
        capMap = new HashMap<String, String>();
        for (Scanner cap : scanners) {
            capMap.put(cap.getCapName(), cap.getCapValue());
        }
        jSplitPane1 = new JideSplitPane();


        centerPanel = new ImagePan();

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        //   tree.addTreeSelectionListener(this);
//        TextNodeRenderer renderer = new TextNodeRenderer();
//        tree.setCellRenderer(renderer);
//
//        tree.setCellEditor(new TextNodeEditor(tree));
//        tree.setEditable(true);

        add(getToolBar(), BorderLayout.NORTH);
        add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.setProportionalLayout(true);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setDividerSize(5);
        jSplitPane1.addPane(jScrollPane2);
        jSplitPane1.addPane(jScrollPane1);
        jSplitPane1.setShowGripper(true);
        jScrollPane2.setViewportView(tree);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        centerPanel.setPreferredSize(new Dimension(screenWidth - 300, screenHeight - 100));
        //  centerPanel.add(label);
        jScrollPane1.setViewportView(centerPanel);
        double[] ds = new double[]{0.2};
        jSplitPane1.setProportions(ds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        vertSplit = new JideSplitPane();
//        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        jScrollPane2.setViewportView(vertSplit);
//    //    barcodePanel = new BarcodePanel();
//        double[] ds1 = new double[]{0.7};
//        vertSplit.setProportionalLayout(true);
//        vertSplit.setOneTouchExpandable(true);
//        vertSplit.setContinuousLayout(false);
//        vertSplit.setOrientation(vertSplit.VERTICAL_SPLIT);
//        vertSplit.setDividerSize(5);
//
//        JScrollPane qPane = new JScrollPane(tree,
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        vertSplit.addPane(qPane);
//      //  vertSplit.addPane(barcodePanel);
//        vertSplit.setProportions(ds1);
        refuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    String refuseNote = JOptionPane.showInputDialog(bundle.getString("refuse.note"));
//                    Capture selected = (Capture) lastIndexedNode.getUserObject();
                    if (tree.getLastSelectedPathComponent() instanceof IconNode) {
                        IconNode selectedNode = (IconNode) tree.getLastSelectedPathComponent();
                        Capture page = (Capture) selectedNode.getUserObject();
                        page.setRefused(true);
                        page.setRefuseNote(refuseNote);
                        dao.merge(page);
                        IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                        CaptureTreeNode pr = (CaptureTreeNode) lastIndexedNode.getParent();
                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/refuse.png"));
                        IconNode temp = new IconNode(page, false, icon, "refused");
                        int index = pr.getIndex(lastIndexedNode);
                        imageModel.insertNodeInto(temp, pr, index);
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
//                        imageModel.reload();
//                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/refuse.png"));
//                        selectedNode.setIcon(icon);
//                        imageModel.reload();
                    }
                    if (tree.getLastSelectedPathComponent() instanceof DefaultMutableTreeNode) {
//                        CaptureHome dao = new CaptureHome();
//                        batch.setStatus(CaptureStatus.ExceptionMode);
//                        batch.setRefused(true);
//                        batch.setRefuseNote(refuseNote);
//                        batch.setLocked(false);
//                        // batch.setName(docName);
//                        dao.attachDirty(batch);
// //                        dao.updateStatus(batch);
////                        dao.updateLock(batch); 
//                        
//                        IconNode node = (IconNode) root.children().nextElement();
//                        DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
//                        imageModel.removeNodeFromParent(lastIndexedNode);
                        UsersAudit audit = new UsersAudit();
                        audit.setBatchId(batch.getId());
                        audit.setUserId(user.getId());
                        audit.setModuleId(3);
                        audit.setAction(2);
                        audit.setStatus(CaptureStatus.ExceptionMode);
                        audit.setLocked(false);
                        audit.setAuditDate(dao.getSysDate());
                        UsersAuditHome auditHome = new UsersAuditHome();
                        auditHome.persist(audit);
                        auditHome.close();
                        root.removeAllChildren();
                        tree.updateUI();
                        //PrintBarcode.print(String.valueOf(batch.getName()), "KKUH        ", "Vascular Lab", "KSU");
                    } //                    else if (selected.getType()==3) {
                    ////                        ImageTreeItem item = (ImageTreeItem) lastIndexedNode.getUserObject();
                    ////                        Capture cap = item.getCapture();
                    ////                        CaptureHome dao = new CaptureHome();
                    //                        cap = selected;
                    //                        Capture parent = cap.getCapture().getCapture();
                    //                        cap.setRefused(true);
                    //                        cap.setRefuseNote(refuseNote);
                    //                        parent.setRefused(true);
                    //                        dao.attachDirty(cap);
                    //                        dao.attachDirty(parent);
                    //                        
                    //                        IconNode pr = (IconNode) lastIndexedNode.getParent();
                    //                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/refuse.png"));
                    //                        IconNode temp = new IconNode(selected, false, icon, cap.getName());
                    //                        int index = pr.getIndex(lastIndexedNode);
                    ////                        imageModel.insertNodeInto(temp, pr, index + 1);
                    ////                        imageModel.removeNodeFromParent(lastIndexedNode);
                    //                        
                    ////                        tree.updateUI();
                    //                        imageModel.reload();
                    //                    } 
//                    else if (selected.getType() == 2) {
//                        // ImageItem item = (ImageItem) lastIndexedNode.getUserObject();
////                        Capture cap = ((DocTreeItem) lastIndexedNode.getUserObject()).getCapture();
////                        CaptureHome dao = new CaptureHome();
//                        cap = selected;
//                        Capture parent = cap.getCapture();
//                        cap.setRefused(true);
//                        cap.setRefuseNote(refuseNote);
//                        dao.attachDirty(cap);
//                        dao.attachDirty(parent);
//
////                        IconNode pr = (IconNode) lastIndexedNode.getParent();
//                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/refuse.png"));
//                        IconNode temp = new IconNode(cap, true, icon, cap.getBarcode());
//                        Enumeration en = lastIndexedNode.children();
//                        while (en.hasMoreElements()) {
//                            TreeNode n = (TreeNode) en.nextElement();
//                            temp.add((MutableTreeNode) n);
//                            en = lastIndexedNode.children();
//                        }
//
//                        int index = lastIndexedNode.getIndex(lastIndexedNode);
//                        imageModel.insertNodeInto(temp, lastIndexedNode, index + 1);
//                        imageModel.removeNodeFromParent(lastIndexedNode);
//                        tree.updateUI();
//                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                }
            }
        });
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (batch != null) {
                    CaptureHome dao = new CaptureHome();
                    batch.setLocked(false);
                    dao.attachDirty(batch);
                    dao.updateLock(batch);

                    imageModel.removeNodeFromParent((MutableTreeNode) root.children().nextElement());
                    tree.updateUI();
                    centerPanel.removeAll();
                    centerPanel.validate();
                    jScrollPane1.repaint();
                }

                me.dispose();
                LoginFrame login = new LoginFrame();
                login.centerScreen();
                login.setVisible(true);

            }
        });
        selectBatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    dirty = false;
//                    if (batch != null) {
////                        CaptureHome dao = new CaptureHome();
//                        batch.setLocked(false);
//                        dao.merge(batch);
//                        dao.updateLock(batch);
//                        
//                    }

                    new QAPatchesDlg(me, user, locale, bundle, category, dao);
                } catch (Exception ex) {
                    ex.printStackTrace();
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
                        CaptureHome dao = new CaptureHome();
                        Capture itemCapture = item.getCapture();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);

                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();

                    } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                        Capture itemCapture = (Capture) lastIndexedNode.getUserObject();
                        CaptureHome dao = new CaptureHome();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);

                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
                        centerPanel.removeAll();
                        centerPanel.validate();
                        jScrollPane1.repaint();
                    } else if (lastIndexedNode.getUserObject() instanceof BatchTreeItem) {
                        BatchTreeItem item = (BatchTreeItem) lastIndexedNode.getUserObject();
                        Capture itemCapture = item.getCapture();
                        CaptureHome dao = new CaptureHome();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
                        dao.updateDeleted(itemCapture);

                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();

                    }

                }
            }
        });
        releaseBatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker swingWorker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() {
                        try {

                            if (root.children().hasMoreElements()) {
                                batch.setLocked(false);
                                dao.merge(batch);
//                                dao.updateLock(batch);

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        root.removeAllChildren();
                        tree.updateUI();
                        centerPanel.setImg(null);
                        centerPanel.setImageHeight(centerPanel.getHeight());
                        centerPanel.setImageWidth(centerPanel.getWidth());
                        centerPanel.setAngle(0);
                        jScrollPane1.setViewportView(centerPanel);
                    }
                };
                swingWorker.execute();

            }
        });
        indexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (batch != null) {
//                        CaptureHome dao = new CaptureHome();
                        if (dao.isRefusedPages(batch)) {
                            String[] textMessages = new String[2];
                            textMessages[0] = bundle.getString("yes");
                            textMessages[1] = bundle.getString("no");
                            JOptionPane jop = new JOptionPane("المعاملة تحتوي علي صور مرفوضة هل تريد ارسالها الي الفهرسة", JOptionPane.WARNING_MESSAGE,
                                    JOptionPane.YES_NO_OPTION, null, textMessages);
                            JDialog jopDialog = jop.createDialog(null, "تحذير من الارسال الي الفهرسة");
                            jopDialog.setVisible(true);
                            String result = (String) jop.getValue();
                            if (result.equals(bundle.getString("yes"))) {
//                                batch.setStatus(CaptureStatus.IndexMode);
//                                batch.setLocked(false);
//                                batch.setName(docName);
//                                 batch.attachDirty(batch);
//                               // 
//                                  dao.updateStatus(batch);
//                                IconNode node = (IconNode) root.children().nextElement();
//                                imageModel.removeNodeFromParent(node);
                                UsersAudit audit = new UsersAudit();
                                audit.setBatchId(batch.getId());
                                audit.setUserId(user.getId());
                                audit.setModuleId(3);
                                audit.setAction(1);
                                audit.setStatus(CaptureStatus.IndexMode);
                                audit.setLocked(false);
                                audit.setAuditDate(dao.getSysDate());
                                UsersAuditHome auditHome = new UsersAuditHome();
                                auditHome.persist(audit);
                                //  auditHome.commit();
                                auditHome.close();
                                root.removeAllChildren();
                                tree.updateUI();
                                centerPanel.setImg(null);
                                centerPanel.setImageHeight(centerPanel.getHeight());
                                centerPanel.setImageWidth(centerPanel.getWidth());
                                centerPanel.setAngle(0);
//                        centerPanel.addImage(null);
                                jScrollPane1.setViewportView(centerPanel);
                            }

                        } else {
//                            batch.setStatus(CaptureStatus.IndexMode);
//                            batch.setLocked(false);
//                            batch.setName(docName);
//                            batch.attachDirty(batch);
//                            dao.updateStatus(batch);
                            //   
//                            IconNode node = (IconNode) root.children().nextElement();
//                            imageModel.removeNodeFromParent(node);
                            UsersAudit audit = new UsersAudit();
                            audit.setBatchId(batch.getId());
                            audit.setUserId(user.getId());
                            audit.setModuleId(3);
                            audit.setAction(1);
                            audit.setStatus(CaptureStatus.IndexMode);
                            audit.setLocked(false);
                            audit.setAuditDate(dao.getSysDate());
                            UsersAuditHome auditHome = new UsersAuditHome();
                            auditHome.persist(audit);
                            auditHome.close();

                            root.removeAllChildren();
                            tree.updateUI();

                            centerPanel.setImg(null);
                            centerPanel.setImageHeight(centerPanel.getHeight());
                            centerPanel.setImageWidth(centerPanel.getWidth());
                            centerPanel.setAngle(0);
//                        centerPanel.addImage(null);
                            jScrollPane1.setViewportView(centerPanel);


//                            centerPanel.removeAll();
//                            centerPanel.validate();
//                            jScrollPane1.repaint();
                        }
                        //  PrintBarcode.print(String.valueOf(batch.getName()), "KKUH", "Vascular Lab", "KSU");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    //
                }
            }
        });
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                zoom = 0;
                try {

//                    DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
//                    Capture capture = (Capture) lastIndexedNode.getUserObject() ;
//                    if (lastIndexedNode == null) {
//                        return;
//                    }
                    if (tree.getLastSelectedPathComponent() instanceof IconNode) {
//                        Capture img =  (Capture) lastIndexedNode.getUserObject();
//                        ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
//                        DiskHome diskDao = new DiskHome();
//                        disk = diskDao.getAllDisk().get(0);
                        IconNode node = (IconNode) tree.getLastSelectedPathComponent();
                        Capture capture = (Capture) node.getUserObject();
                        disk = batch.getDisk();
                        String path = null;
                        if (category.isCreateFolder()) {
                            path = disk.getPath() + "/" + category.getId() + "/view/" + batch.getId() + "/" + capture.getPath();
                        } else {
                            path = disk.getPath() + "/view/" + batch.getId() + "/" + capture.getPath();
                        }
                        File file = new File(path);
                        Image image = (Image) new TIFFReader(file).getPage(0);
                        currentBimg = ImageGenerator.createBufferedImage(image);
                        centerPanel.setImg(image);
                        centerPanel.setImageHeight(centerPanel.getHeight());
                        centerPanel.setImageWidth(centerPanel.getWidth());
                        centerPanel.setAngle(0);
                        centerPanel.addImage(image);
                        jScrollPane1.setViewportView(centerPanel);

                    } //                    else if (lastIndexedNode.getUserObject() instanceof Capture) {
                    //                        //   Capture capture = (Capture) lastIndexedNode.getUserObject();
                    ////                        PatientsDoc doc = barcodePanel.show(capture.getBarcode(), user.getId(), batch.getId());
                    ////                        if (doc != null) {
                    ////                            docName = doc.getDocNo();
                    ////                        }
                    //                    } 
                    else if (tree.getLastSelectedPathComponent() instanceof Capture) {
                        //barcodePanel.show(n ull, user.getId(), batch.getId());
                        centerPanel.setImg(null);
                        centerPanel.setImageHeight(centerPanel.getHeight());
                        centerPanel.setImageWidth(centerPanel.getWidth());
                        centerPanel.setAngle(0);
                        jScrollPane1.setViewportView(centerPanel);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
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
                centerPanel.rotateRight();
                jScrollPane1.setViewportView(centerPanel);
//                int w = 800;
//                int h = 800;
                zoom++;
//                ImageIcon icon = (ImageIcon) label.getIcon();
//                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//                Graphics2D bg = bi.createGraphics();
//                bg.rotate(Math.toRadians(90), w / 2, h / 2);
//                bg.drawImage(icon.getImage(), 0, 0, w, h, 0, 0, w, h, null);
//                bg.dispose();//cleans up resources
//                label.setIcon(new ImageIcon(bi));
//                label.setPreferredSize(new Dimension(icon.getIconHeight(), icon.getIconWidth()));
//                jScrollPane1.setViewportView(label);
            }
        });
        rotateLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.rotate();
                jScrollPane1.setViewportView(centerPanel);
//                  rotateLeft();
//                int w = 800;
//                int h = 800;
                zoom--;
//                ImageIcon icon = (ImageIcon) label.getIcon();
//                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//                Graphics2D bg = bi.createGraphics();
//                bg.rotate(Math.toRadians(-90), w / 2, h / 2);
//                bg.drawImage(icon.getImage(), 0, 0, w, h, 0, 0, w, h, null);
//                bg.dispose();//cleans up resources
//                label.setIcon(new ImageIcon(bi));
//                label.setPreferredSize(new Dimension(icon.getIconHeight(), icon.getIconWidth()));
//                jScrollPane1.setViewportView(label);
            }
        });
        rotateRight90.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.rotateRight(90);
                jScrollPane1.setViewportView(centerPanel);
//                int w = 800;
//                int h = 800;
                zoom++;
//                ImageIcon icon = (ImageIcon) label.getIcon();
//                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//                Graphics2D bg = bi.createGraphics();
//                bg.rotate(Math.toRadians(90), w / 2, h / 2);
//                bg.drawImage(icon.getImage(), 0, 0, w, h, 0, 0, w, h, null);
//                bg.dispose();//cleans up resources
//                label.setIcon(new ImageIcon(bi));
//                label.setPreferredSize(new Dimension(icon.getIconHeight(), icon.getIconWidth()));
//                jScrollPane1.setViewportView(label);
            }
        });
        rotateLeft90.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.rotate(90);
                jScrollPane1.setViewportView(centerPanel);
//                  rotateLeft();
//                int w = 800;
//                int h = 800;
                zoom--;
//                ImageIcon icon = (ImageIcon) label.getIcon();
//                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//                Graphics2D bg = bi.createGraphics();
//                bg.rotate(Math.toRadians(-90), w / 2, h / 2);
//                bg.drawImage(icon.getImage(), 0, 0, w, h, 0, 0, w, h, null);
//                bg.dispose();//cleans up resources
//                label.setIcon(new ImageIcon(bi));
//                label.setPreferredSize(new Dimension(icon.getIconHeight(), icon.getIconWidth()));
//                jScrollPane1.setViewportView(label);
            }
        });
        zoomInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ImageIcon icon = (ImageIcon) label.getIcon();
//                Image scaledImage = icon.getImage();
//                float scaleFactor = 0.9f;
//                int width = scaledImage.getWidth(null);
//                int height = scaledImage.getHeight(null);
//                int scaledH = (int) (scaledImage.getHeight(null) * scaleFactor + .5f);
//                BufferedImage image = new BufferedImage(scaledH, scaledH, BufferedImage.TYPE_INT_RGB);
//                Graphics2D g2d = image.createGraphics();
//                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//                g2d.drawImage(scaledImage, 0, 0, scaledH, scaledH, null);
//                g2d.dispose();
//                scaledImage = image;
//                icon.setImage(scaledImage);
//                label.setIcon(icon);
//                jScrollPane1.setViewportView(label);
                centerPanel.zoomout();
                jScrollPane1.validate();
            }
        });
        zoomOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ImageIcon icon = (ImageIcon) label.getIcon();
//                Image scaledImage = icon.getImage();
//                float scaleFactor = 1.1f;
//                int width = scaledImage.getWidth(null);
//                int height = scaledImage.getHeight(null);
//                int scaledH = (int) (scaledImage.getHeight(null) * scaleFactor + .5f);
//                BufferedImage image = new BufferedImage(scaledH, scaledH, BufferedImage.TYPE_INT_RGB);
//                Graphics2D g2d = image.createGraphics();
//
//                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//                g2d.drawImage(scaledImage, 0, 0, scaledH, scaledH, null);
//                g2d.scale(scaledH, scaledH);
//                g2d.dispose();
//                scaledImage = image;
//                icon.setImage(scaledImage);
//                label = new JLabel(icon);
//                jScrollPane1.setViewportView(label);
                centerPanel.zoomin();
                jScrollPane1.validate();
            }
        });
        docButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
//                    DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (tree.getLastSelectedPathComponent() instanceof IconNode) {
//                        CaptureHome dao = new CaptureHome();
                        IconNode selectedNode = (IconNode) tree.getLastSelectedPathComponent();
                        String docName = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                        Capture newCapture = new Capture();
                        newCapture.setBarcode(docName);
                        newCapture.setName("Doc " + batch.getCaptures().size());
                        newCapture.setCapture(batch);
                        newCapture.setStatus(CaptureStatus.QAMode);
                        newCapture.setType(2);
                        newCapture.setDeleted(false);
                        newCapture.setRep(batch.getRep());
                        newCapture.setCategory(batch.getCategory());
                        newCapture.setDisk(batch.getDisk());
                        newCapture.setUsers(batch.getUsers());
                        newCapture.setComputer(batch.getComputer());

                        dao.persist(newCapture);
                        //  

                        CaptureTreeNode docItem = new CaptureTreeNode(newCapture, imageModel);
                        IconNode newParent = new IconNode(docItem);

//                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                        DefaultMutableTreeNode oldParent = (DefaultMutableTreeNode) selectedNode.getParent();
                        int selectedIndex = oldParent.getIndex(selectedNode);
                        ArrayList<IconNode> nodes = Collections.list(oldParent.children());
                        for (int i = selectedIndex; i < nodes.size(); i++) {
                            IconNode node = (IconNode) nodes.get(i);
                            //  Capture nodeCapture = imagesPathMap.get(selectedNode.getUserObject().toString()).getCapture();
                            Capture nodeCapture = (Capture) selectedNode.getUserObject();
                            nodeCapture.setCapture(newCapture);
                            dao.merge(nodeCapture);
//                            System.out.println(" Node      " + node.getUserObject().toString());
                            newParent.add(node);
                        }
//                        int index = root.getIndex(oldParent); 
                        ((DefaultMutableTreeNode) oldParent.getParent()).add(newParent);
                        tree.updateUI();
                    } //                    else if (tree.getLastSelectedPathComponent() instanceof DefaultMutableTreeNode) {
                    ////                        CaptureHome dao = new CaptureHome();
                    //                        String docName = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                    //                        Capture newCapture = new Capture();
                    //                        newCapture.setBarcode(docName);
                    //                        newCapture.setName("Doc " + batch.getCaptures().size());
                    //                        newCapture.setCapture(batch);
                    //                        newCapture.setStatus(CaptureStatus.QAMode);
                    //                        newCapture.setDeleted(false);
                    //                        newCapture.setType(2);
                    //                        dao.persist(newCapture);
                    //                        //     batch = dao.findById(batch.getId());
                    //                        IconNode newParent = new IconNode(newCapture);
                    //                        IconNode grandNode = (IconNode) root.children().nextElement();
                    ////                        imageModel.addLeaf(grandNode, newParent);
                    //                        grandNode.add(newParent);
                    //                        //   root.add(newParent);
                    //                        tree.updateUI();
                    //                    } 
                    else {
                        JOptionPane.showMessageDialog(me, bundle.getString("error.message.create.doc"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tree.getLastSelectedPathComponent() instanceof IconNode) {


                        dirty = true;
                        IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                        Capture capture = (Capture) lastIndexedNode.getUserObject();
                        Image img = centerPanel.getImg();
                        BufferedImage bimg = currentBimg;
                        // String pagePath = "C:/.qa/" + batch.getId() + "/" + imgCap.getPath();
                        // Disk disk = batch.getDisk();
                        String path = null;
                        if (category.isCreateFolder()) {
                            path = disk.getPath() + "/" + category.getId() + "/view/";
                        } else {
                            path = disk.getPath() + "/view/";
                        }
                        String tempPath = path + "/QA/" + batch.getId() + "/" + capture.getName() + ".tif";
                        File tempDir = new File(path + "/QA/" + batch.getId());
                        if (!tempDir.exists()) {
                            tempDir.mkdirs();
                        }
                        if (centerPanel.getAngle() != 0) {
                            bimg = ImageUtils.rotateImage(img, centerPanel.getAngle());
                            ImageGenerator.createBufferedImage(img);
                        } else {
                            bimg = (BufferedImage) new TIFFReader(new File(tempPath)).getPage(0);
                        }
                        /**
                         * ******************************************************************
                         */
                        /**
                         * ******************************************************************
                         */
//                    File parent = new File(imgCap.getCapture().getCapture().getName());
//                    parent.mkdirs();
//                    WriteTiffImages tiff = new WriteTiffImages(capMap.get("PixelType"));
//                    tiff.setBimg(bimg);
//                    tiff.setDpi(300);
//
//                    img.flush();
//                    bimg.flush();
//
//                    System.out.println(tempPath);
//                    tiff.setPath(tempPath);
//                    tiff.start();
                        TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},
                                TIFFWriter.TIFF_CONVERSION_TO_GRAY,
                                TIFFWriter.TIFF_COMPRESSION_DEFLATE,
                                new File(tempPath));
                        CompressToTiff service = new CompressToTiff();
                        int result = service.getCompressToTiffSoap().compressImageFullPath(path + "/QA/" + batch.getId(),
                                path + "/" + batch.getId(), capture.getName() + ".tif");
                        if (result != 1) {
                            // JOptionPane.showMessageDialog(me, "حدث خطأ اثناء الحفظ يرجي اعادة الحفظ");
                        }
                    }
                } catch (Exception ex) {
                    // Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
                    // JOptionPane.showMessageDialog(me, "حدث خطأ اثناء الحفظ يرجي اعادة الحفظ");
                }
            }
        });
        createBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CaptureTreeNode localNode = (CaptureTreeNode) tree.getLastSelectedPathComponent();
                if (localNode.getUserObject() instanceof Capture) {
                    Capture doc = (Capture) localNode.getUserObject();
                    if (doc.getType() == 2) {
//                        CaptureHome capHome = new CaptureHome();
                        String barcode = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                        doc.setBarcode(barcode);
                        dao.merge(doc);
                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/barcode32.png"));
                        localNode.setUserObject(doc);
//                        localNode.setIcon(icon);
                        tree.updateUI();
                    } else {
                        JOptionPane.showMessageDialog(me, bundle.getString("barcode.doc.error"));
                    }
                } else {
                    JOptionPane.showMessageDialog(me, bundle.getString("barcode.doc.error"));
                }
            }
        });
        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode(rep.getName());
        String rootName = rep.getName() + "--" + category.getName();
        root = new IconNode(rootName);
//        root1.add(root);
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

//    public void createServerFile(String batch) {
//        try {
//            RepHome repHome = new RepHome();
//            rep = repHome.getAllRep().get(0);
//            ServerHome serverHome = new ServerHome();
//            Server server = serverHome.getAllServer().get(0);
//            NtlmPasswordAuthentication authentication =
//                    new NtlmPasswordAuthentication(server.getDomain(), server.getUser(), server.getPwd());
//            String localPath = batch + "/";
//            File localBatch = new File(localPath);
//            File batchDir = new File(rep.getScanPath() + "/" + batch);
//            batchDir.mkdirs();
//            for (File f : localBatch.listFiles()) {
//                UniAddress domain = UniAddress.getByName(server.getIp());
//                SmbSession.logon(domain, authentication);
//                InputStream in = new FileInputStream(f);
//                String path = rep.getScanPath() + "/" + batch + "/" + f.getName();
//                String fullPath = "smb://" + server.getIp() + "/" + path;
//                SmbFile file = new SmbFile(fullPath, authentication);
//                // note the different format
//                //File file = new File(path); // note the different format
//                SmbFile parentFile = new SmbFile(file.getParent(), authentication);
//                if (!parentFile.exists()) {
//                    parentFile.mkdirs();
//                }
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//                OutputStream out = file.getOutputStream();
//                byte[] buf = new byte[1024];
//                int len;
//                while ((len = in.read(buf)) > 0) {
//                    out.write(buf, 0, len);
//                }
//                in.close();
//                out.close();
//            }
//        } catch (Exception ex) {
//            //  Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
//            ex.printStackTrace();
//        }
//
//
//    }
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
                BufferedImage dimg = new BufferedImage(scale, scale, img.getType());
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

//    private Properties getBarcodes(String path) {
//        properties = new Properties();
//        try {
//            properties.load(new FileInputStream(path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return properties;
//    }
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

    public void oldFillTree() {
        try {
//            if (!checkDocuments()) {
//                JOptionPane.showMessageDialog(this, "يوجد هناك خطأ في رقم المريض");
//            }

            tree.setCellRenderer(new IconNodeRenderer());
//            IconNode root1 = new IconNode(rep.getName());
//            String rootName = rep.getName() + "--" + category.getName();
//            root = new IconNode(rootName);
//            root1.add(root);
//            imageModel = new ImageListModel(root);
            if (batch != null) {
                BatchTreeItem batchItem = new BatchTreeItem();
                batchItem.setName(batch.getName());
                batchItem.setCapture(batch);
                IconNode grandNode = new IconNode(batchItem);
//                repPath = category.getRep().getPath();
//                String[] names = repPath.split("/");
//                String path1 = names[names.length - 1];
//                String path = "\\\\" + properties.getProperty("server.ip") + "\\" + path1 + "\\" + batch.getId();
//                imageModel.addLeaf(root, grandNode);
                root.add(grandNode);
                for (Capture parent : batch.getCaptures()) {
                    if (parent.getCaptures() == null || parent.getCaptures().size() <= 0) {
                        continue;
                    }

                    for (Capture doc : batch.getCaptures()) {
                        grandNode.add(new CaptureTreeNode(doc, imageModel));
                    }
//                    DocTreeItem docItem = new DocTreeItem(parent);
//                    docItem.setName(parent.getBarcode());
//                    IconNode parentNode = null;
//                    if (parent.getBarcode() == null) {
//                        parentNode = new IconNode(parent);
//                    } else {
//                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/barcode32.png"));
//                        parentNode = new IconNode(parent, true, icon, parent.getBarcode());
//                    }
//                    // parentNode.setIconName(parent.getBarcode());
//                    imageModel.addLeaf(grandNode, parentNode);
//                    for (Capture son : parent.getCaptures()) {
//                        if (!son.getDeleted()) {
//                            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/scan.jpg"));
//
//                            ImageTreeItem sonNode = new ImageTreeItem();
//                            sonNode.setName(son.getName( ));
////                            sonNode.setPath(path + "/" + son.getPath());
//                            sonNode.setCapture(son);
//                            IconNode child = null;
//                            if (son.getRefused() && son.getRefuseNote() != null && son.getRefuseNote().equalsIgnoreCase("Re Scan Page")) {
//                                child = new IconNode(sonNode, false, scaleImage(icon.getImage(), 16, 16), son.getPath());
//                            } else if (son.getRefused()) {
//                                ImageIcon delete = new ImageIcon(getClass().getClassLoader().getResource("resources/delete1.jpg"));
//                                child = new IconNode(sonNode, false, scaleImage(delete.getImage(), 16, 16), son.getPath());
//
//                            } else {
//
//
//                                child = new IconNode(sonNode);
//                            }
//                            imageModel.addLeaf(parentNode, child);
//                            
//                        }
//                    }
                }
            }

            imageModel.reload();
//            tree.setModel(imageModel);
//            tree.updateUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Capture.close();
        }
    }

    public void fillTree() {

//        FillTree fillTree = new FillTree();
//        fillTree.start();

        DefaultMutableTreeNode batchNode = new DefaultMutableTreeNode(batch);
        root.add(batchNode);
//        Capture b = dao.findById(batch.getId());
//        System.out.println(" DOCS  " + b.getCaptures().size() + "  " + b.getCaptures().toArray()[0]);
        for (Capture doc : batch.getCaptures()) {
//            IconNode[] nodes = new FindChildrenTreeNode(doc, imageModel).loadChildren(imageModel);
//            IconNode docNode = new IconNode(doc);

            batchNode.add(new CaptureTreeNode(doc, imageModel));

        }
        tree.setModel(imageModel);
        LazyLoadingTreeController controller = new LazyLoadingTreeController(imageModel);
        tree.addTreeWillExpandListener(controller);
        imageModel.reload();
    }

    private void disableActions(boolean flag) {
        deleteButton.setEnabled(flag);
//        exceptionButton.setEnabled(flag);
        releaseBatchButton.setEnabled(flag);
        refuseButton.setEnabled(flag);
        indexButton.setEnabled(flag);
        // rotateLeft.setEnabled(flag);
        // rotateRight.setEnabled(flag);
    }

    private JToolBar getToolBar() {
        if (toolBar == null) {
            toolBar = new JToolBar();
            toolBar.setFloatable(false);
            toolBar.add(getSelectBatchButton());
            toolBar.add(getRefuseButton());
            toolBar.add(getIndexButton());
            toolBar.add(getRotateRight90Bttn());
            toolBar.add(getRotateLeft90Bttn());
            toolBar.add(getZoomInBtn());
            toolBar.add(getZoomOutBtn());
            toolBar.add(getRotateRightBttn());
            toolBar.add(getRotateLeftBttn());
            toolBar.add(getDeleteButton());
            toolBar.add(getReleaseBatch());
            toolBar.add(getCreateDocButton());
            toolBar.add(getSaveButton());
            toolBar.add(getCreateChangeCategory());
            toolBar.add(getCreateBarcodeButton());
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
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/rotateRight32.png"));
            icon = scaleImage(icon.getImage());
            rotateRight.setIcon(icon);
            rotateRight.setVerticalTextPosition(SwingConstants.BOTTOM);
            rotateRight.setHorizontalTextPosition(SwingConstants.CENTER);
            rotateRight.setEnabled(false);
        }
        return rotateRight;
    }

    private JButton getRotateLeftBttn() {
        if (rotateLeft == null) {
            rotateLeft = new JButton();
            rotateLeft.setText(bundle.getString("rotate.left"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/rotateLeft32.png"));
            icon = scaleImage(icon.getImage());
            rotateLeft.setIcon(icon);
            rotateLeft.setVerticalTextPosition(SwingConstants.BOTTOM);
            rotateLeft.setHorizontalTextPosition(SwingConstants.CENTER);
            rotateLeft.setEnabled(false);
        }
        return rotateLeft;
    }

    private JButton getRotateRight90Bttn() {
        if (rotateRight90 == null) {
            rotateRight90 = new JButton(bundle.getString("rotate.right.90"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/right.jpg"));
            icon = scaleImage(icon.getImage());
            rotateRight90.setIcon(icon);
            rotateRight90.setVerticalTextPosition(SwingConstants.BOTTOM);
            rotateRight90.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return rotateRight90;
    }

    private JButton getRotateLeft90Bttn() {
        if (rotateLeft90 == null) {
            rotateLeft90 = new JButton();
            rotateLeft90.setText(bundle.getString("rotate.left.90"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/left.jpg"));
            icon = scaleImage(icon.getImage());
            rotateLeft90.setIcon(icon);
            rotateLeft90.setVerticalTextPosition(SwingConstants.BOTTOM);
            rotateLeft90.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return rotateLeft90;
    }

    private ImageIcon scaleImage(Image img) {
        Image scaledImage = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);

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
            docButton.setEnabled(true);
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
            saveButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return saveButton;
    }

    private JButton getZoomOutBtn() {
        if (zoomOutBtn == null) {
            zoomOutBtn = new JButton(bundle.getString("zoomin"));
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
            zoomInBtn = new JButton(bundle.getString("zoomout"));
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

    private JButton getCreateBarcodeButton() {
        if (createBarcodeButton == null) {
            createBarcodeButton = new JButton(bundle.getString("barcode"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/barcode128_a.png"));
            icon = scaleImage(icon.getImage());
            createBarcodeButton.setIcon(icon);
            createBarcodeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            createBarcodeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return createBarcodeButton;
    }

    public void centerScreen() {
        setTitle("GDocScanQA");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (root.getChildCount() > 0) {
//                    CaptureHome dao = new CaptureHome();
                    batch.setLocked(false);
                    dao.attachDirty(batch);
//                    dao.updateLock(batch);

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

    public BufferedImage rotate(BufferedImage img, int angle) {

        com.gdit.image.RotateImage rotateImg = new com.gdit.image.RotateImage();
        return rotateImg.rotate(img, centerPanel.getAngle());

    }

    public void release() {
        SwingWorker swingWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                try {

                    if (root.children().hasMoreElements()) {
                        batch.setLocked(false);
                        dao.attachDirty(batch);
//                        dao.updateLock(batch);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                root.removeAllChildren();
                tree.updateUI();
            }
        };
        swingWorker.execute();

    }

    public static void main(String[] args) {
//         TODO code application logic here
//         QAMain main = new QAMain();
//          main.setSize(800, 600);
//        main.centerScreen();
//         main.setVisible(true);
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
    private QAMain me;
    private Capture batch;
    private boolean disabled;
//    private String repPath;
    private String docDir;
    private String batchName;
    private Category category;
    private Capture cap;
    private static JFrame viewFrame;
    private DefaultTreeModel imageModel;
    private IconNode root;
    private String docId;
    //  private String batchId;
    private ResourceBundle bundle;
    private int rotate;
    private int zoomout;
    private ScannerSettingPanel settPanel;
    private Locale locale;
    private ImagePanel selected = null;
//    private Properties properties;
    private String selectedSource;
    private javax.swing.JTable propTable;
    private ImagePan centerPanel;
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
    private JButton rotateRight90;
    private JButton rotateLeft90;
    private JButton createBarcodeButton;
    private JButton indexButton;
    private JToolBar toolBar;
    private boolean dirty;
    private ImageLoader imgLoader;
    private Capture lastPage;
    private int i;
//    private JTextField batchText;
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getAnonymousLogger();

    private void writeNextPage() {

        try {
//            ServerHome serverHome = new ServerHome();
//              server = serverHome.getAllServer().get(0);
//
//            authentication =
//                    new NtlmPasswordAuthentication(server.getDomain(), server.getUser(), server.getPwd());
//            UniAddress domain = UniAddress.getByName(server.getIp());
//            SmbSession.logon(domain, authentication);
//            // SmbFile parentFile = new SmbFile("smb://" + server.getIp() + "/"+"W$/scan" + "/" + batch,authentication);
//            String path = "w$/ytech/gip/" + batch + "/";
//            String fullPath = "smb://" + server.getIp() + "/" + path;
//
//            batchFile = new SmbFile(fullPath, authentication);
//            //  SmbFile file = batchFile.listFiles()[0];
//            images = new HashMap<String, SmbFile>();
//            for (SmbFile file : batchFile.listFiles()) {
//                images.put(file.getName(), batchFile);
//            }
//            //images = Arrays.asList(list); 
//            System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        FetchBatchThread thread = new FetchBatchThread();
//        File file = new File("C:/.qa/" + batch.getId());
//        file.mkdir();
//        thread.setPath("C:/.qa/");
//        thread.setType("QA");
//        thread.setBatch(batch);
//        thread.start();
    }

    public class FillTree extends Thread {

        public void run() {

            DefaultMutableTreeNode batchNode = new DefaultMutableTreeNode(batch);
            root.add(batchNode);
            Capture b = dao.findById(batch.getId());
            System.out.println(" DOCS  " + b.getCaptures().size() + "  " + b.getCaptures().toArray()[0]);
            for (Capture doc : batch.getCaptures()) {
//            IconNode[] nodes = new FindChildrenTreeNode(doc, imageModel).loadChildren(imageModel);
//            IconNode docNode = new IconNode(doc);
                batchNode.add(new CaptureTreeNode(doc, imageModel));
            }
            tree.setModel(imageModel);
            final LazyLoadingTreeController controller = new LazyLoadingTreeController(imageModel);
            tree.addTreeWillExpandListener(controller);
            imageModel.reload();
        }
    }
}
