package com.gdit.capture.run;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import SK.gnome.capabilities.MorenaCapabilities;
import SK.gnome.morena.MorenaImage;
import SK.gnome.twain.TwainException;
import SK.gnome.twain.TwainManager;
import SK.gnome.twain.TwainSource;
import com.asprise.util.tiff.TIFFReader;
import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.entity.*;
import com.gdit.capture.gui.CategoriesDlg;
import com.gdit.capture.gui.NorthPanel;
import com.gdit.capture.gui.ScannerSettingPanel;
import com.gdit.capture.main.ImagePanel;
import com.gdit.capture.model.BatchTreeItem;
import com.gdit.capture.model.CaptureStatus;
import com.gdit.capture.model.DocTreeItem;
import com.gdit.capture.model.IconNode;
import com.gdit.capture.model.IconNodeRenderer;
import com.gdit.capture.model.ImageTreeItem;
import com.gdit.capture.model.ImageListModel;
//import com.gdit.capture.service.SyncFiles;
//import com.gdit.capture.service.SyncFilesService;

import com.gdit.capture.model.ImageUtils;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.AbstractAction;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import com.gdit.capture.model.TreeTransferHandler;
import com.gdit.image.ImageLoader;
import jai.IconJAI;
import jai.JAIImageReader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.renderable.ParameterBlock;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.swing.*;
import javax.swing.tree.*;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;
import org.apache.commons.io.FileUtils;
import org.tempuri.CompressToTiff;

/**
 *
 * @author bahy
 */
public class ScanExceptionMain extends JFrame {

    private JButton selectSourceBttn;
    private JButton replaceWithBtn;
    private JButton scanButton;
    private JButton logoutBtn;
    private Image image;
    private int pixelType;
    private JPopupMenu m_popup;
    private MorenaImage morenaImage;
    BufferedImage bimg = null;
    BufferedImage[] bimgs = new BufferedImage[1];
    // Map<String, ImageItem> imagesPathMap;
    private Category category;
    private Rep rep;
    private JButton changeCategoryButton;
    private JButton horizonDeleteButton;
    private int screenHeight;
    private int screenWidth;
    private JTextField txtReason;
    private JLabel reasonLabel;
    //private NorthPanel northPanel;
    private String path;
    private HashMap<String, String> capMap;
    private Disk disk;
    private boolean scanning;
    private TwainSource source;
    private NorthPanel northPanel;
    private boolean stillScanning;
    private IconNode parentNode;
    private CaptureHome dao;

    public ScanExceptionMain(ResourceBundle bundle, Locale locale, Users user, Rep rep) {
        try {
            me = this;

            this.locale = locale;
            this.bundle = bundle;
            this.user = user;
            this.rep = rep;
            dao = new CaptureHome();
            if (rep != null && rep.getCategories().size() > 0) {
                category = (Category) rep.getCategories().toArray()[0];
            }
            //    properties = readSettingFile("C:/.capture/sysoptions.properties");

            ScannerHome scanHome = new ScannerHome();
            List<Scanner> scanners = scanHome.getAllCaps();
            capMap = new HashMap<String, String>();
            for (Scanner scanner : scanners) {
                capMap.put(scanner.getCapName(), scanner.getCapValue());
            }
            init();
            String cmd = "C:/debug/startupCapture.exe  C:/debug/CSImageProcessingDemo.exe ";
            Process process = Runtime.getRuntime().exec(cmd);
            
        } catch (IOException ex) {
            Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Properties readSettingFile(String filePath) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(filePath));
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (locale == null) {
            locale = new Locale("ar");
        }
        //  locale = new Locale("ar");
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("com/gdit/bundle/capture", locale);
        }
        m_popup = new JPopupMenu();
        jSplitPane1 = new JideSplitPane();
        centerPanel = new ImagePan();
//        northPanel = new NorthPanel();
        mainDocPanel = new JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        leftScrollPane = new JScrollPane();
        leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        leftScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tree = new JTree();
        tree.setDragEnabled(true);
        tree.setDropMode(DropMode.ON_OR_INSERT);
        tree.setTransferHandler(new TreeTransferHandler());
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
        expandTree(tree);
        label = new JLabel();
        reasonLabel = new JLabel(bundle.getString("reason.label"));
        txtReason = new JTextField();
        txtReason.setSize(150, 30);
        //   tree.addTreeSelectionListener(this);
        add(getToolBar(), BorderLayout.NORTH);
        add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.setProportionalLayout(true);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setDividerSize(5);
        jSplitPane1.addPane(jScrollPane2);
        jSplitPane1.addPane(leftScrollPane);
        mainImagePanel = new JPanel();
        northPanel = new NorthPanel();
        mainImagePanel.setLayout(new BorderLayout());
        mainImagePanel.add(getHorizontalToolBar(), BorderLayout.NORTH);
        mainImagePanel.add(northPanel, BorderLayout.PAGE_START);
        mainImagePanel.add(getVerticalToolBar(), BorderLayout.EAST);
        //   centerPanel.setPreferredSize(new Dimension(screenWidth - 300, screenHeight - 100));
        centerPanel.setBackground(Color.ORANGE);
        mainImagePanel.add(jScrollPane1);
        leftScrollPane.setViewportView(mainImagePanel);
        jSplitPane1.setShowGripper(true);
        //jScrollPane2.setViewportView(tree);
        jScrollPane2.setViewportView(tree);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        screenHeight = (int) screenSize.height - 100;
        screenWidth = (int) screenSize.width - 350;
        centerPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        jScrollPane1.setViewportView(centerPanel);
        double[] ds = new double[]{0.2};
        jSplitPane1.setProportions(ds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //     m_popup.setSize(40,40);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                windowClosedAction(evt);
            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                windowClosingAction(evt);
            }
        });
        AbstractAction m_action = new AbstractAction("rename") {
            private TreePath m_clickedPath;

            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog(bundle.getString("message.rename"));
                if (newName == null || newName.trim().equals("")) {
                    return;
                }
                if (node.getUserObject() instanceof BatchTreeItem) {
                    Capture capture = ((BatchTreeItem) node.getUserObject()).getCapture();
                    capture.setName(newName);
                    dao.merge(capture);
                    //  ((BatchTreeItem) node.getUserObject()).setName(newName);
                    tree.updateUI();
                } else if (node.getUserObject() instanceof Capture) {
                    Capture capture = (Capture) node.getUserObject();
                    capture.setName(newName);
                    dao.merge(capture); 
                    //   ((Capture) node.getUserObject()).setName(newName);
                    tree.updateUI();
                } else if (node.getUserObject() instanceof ImageTreeItem) {
                    ImageTreeItem selectedImageItem = (ImageTreeItem) node.getUserObject();
                    Capture capture = selectedImageItem.getCapture();
                    capture.setName(newName);
                    dao.merge(capture); 
                    node.setUserObject(newName);
                    tree.updateUI();
                }
            }
        };
        m_popup.add(m_action);
        m_popup.addSeparator();
        a2 = new AbstractAction("paste") {
            public void actionPerformed(ActionEvent e) {
                pasteToNode = node;
                ArrayList<IconNode> movedChildren = Collections.list(movedNode.children());
                for (IconNode child : movedChildren) {
                    node.add(child);
                }
                System.out.println(node.getChildCount());
                imageModel.removeNodeFromParent(movedNode);
                tree.updateUI();
                dirty = true;
            }
        };
        a1 = new AbstractAction("move") {
            public void actionPerformed(ActionEvent e) {
                movedNode = node;
                a2.setEnabled(true);

            }
        };
        m_popup.add(a1);
        a2.setEnabled(false);
        m_popup.add(a2);
        tree.add(m_popup);
        tree.addMouseListener(new PopupTrigger());
        rotateRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.rotateRight(90);
                jScrollPane1.setViewportView(centerPanel);
                zoom++;
//                int w = 800;
//                int h = 800;
//                zoom++;
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

                centerPanel.rotate(90);
                jScrollPane1.setViewportView(centerPanel);
                zoom--;
//                int w = 800;
//                int h = 800;
//                zoom--;
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
                centerPanel.zoomout();
                jScrollPane1.validate();

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
        selectBatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    if (batch != null) {
//                        CaptureHome dao = new CaptureHome();
//                        batch.setLocked(false);
//                        dao.attachDirty(batch);
//                        dao.updateLock(batch);
//                        disk = batch.getDisk();
//                    }
                    new QAPatchesDlg(me, user, locale, bundle, category,dao);

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    CaptureHome.close();
                }
            }
        });
        docButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    DefaultMutableTreeNode localNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    String[] textMessages = new String[3];
                    textMessages[0] = bundle.getString("yes");
                    textMessages[1] = bundle.getString("no");
                    textMessages[2] = bundle.getString("cancel");
                    JOptionPane jop = new JOptionPane("هل تريد اضافه ملف جديد", JOptionPane.WARNING_MESSAGE,
                            JOptionPane.YES_NO_CANCEL_OPTION, null, textMessages);
                    JDialog jopDialog = jop.createDialog(null, bundle.getString("delete.title"));
                    jopDialog.setVisible(true);
                    String result = (String) jop.getValue();
                    if (result.equals(bundle.getString("yes"))) {
                        if (localNode.getUserObject() instanceof ImageTreeItem) {
                            CaptureHome dao = new CaptureHome();
                            String docName = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                            Capture newCapture = new Capture();
                            newCapture.setBarcode(docName);
                            newCapture.setName(docName);
                            newCapture.setCapture(batch);
                            newCapture.setStatus(CaptureStatus.ExceptionMode);
                            newCapture.setType(2);
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
                          
                            String docName = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                            Capture newCapture = new Capture();
                            newCapture.setBarcode(docName);
                            newCapture.setName("Doc " + batch.getCaptures().size());
                            newCapture.setCapture(batch);
                            newCapture.setStatus(CaptureStatus.ExceptionMode);
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
                    }
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

                    } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                        Capture itemCapture = (Capture) lastIndexedNode.getUserObject();
                        CaptureHome dao = new CaptureHome();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
                        CaptureHome.close();
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();

                    } else if (lastIndexedNode.getUserObject() instanceof BatchTreeItem) {
                        BatchTreeItem item = (BatchTreeItem) lastIndexedNode.getUserObject();
                        CaptureHome dao = new CaptureHome();
                        Capture itemCapture = item.getCapture();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
                        dao.updateDeleted(itemCapture);
                        CaptureHome.close();
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();

                    }

                }
                //               }
            }
        });
        releaseBatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (batch != null) {
                        CaptureHome dao = new CaptureHome();
                        batch.setLocked(false);
                        dao.merge(batch);
                        //dao.updateLock(batch);
                        CaptureHome.close();
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
        replaceWithBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                        ImageTreeItem item = (ImageTreeItem) lastIndexedNode.getUserObject();

                        MorenaCapabilities morenaCapabilities = new MorenaCapabilities(centerPanel);
                        TwainSource source = (TwainSource) morenaCapabilities.getSource();
                        scanButton.setEnabled(false);
                        morenaImage = new MorenaImage(source);
                        image = Toolkit.getDefaultToolkit().createImage(morenaImage);
                        bimg = ImageGenerator.createBufferedImage(image);
                        bimgs[0] = bimg;

                        int height = (int) (centerPanel.getHeight() * 0.93);
                        int width = (int) (centerPanel.getWidth() * 0.8);
                        ImageIcon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                        JLabel label = new JLabel();
                        label.setIcon(icon);
                        item.setImage(icon);
                        centerPanel.removeAll();
                        centerPanel.add(label);
                        validate();
                        String path = docDir + "/" + item.getName();
                        System.out.println(path);
                        if (pixelType == TwainSource.TWPT_BW || pixelType == TwainSource.TWPT_GRAY) {
                            try {
                                TIFFWriter.preferredResolution = 300;
                                TIFFWriter.createTIFFFromImages(bimgs, new File(path + ".tif"));
                            } catch (IOException ex) {
                                Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                // ImageIO.write(bimg, "JPEG", new File(docDir + "/" + "page" + (count) + ".jpg"));
                                TIFFWriter.preferredResolution = 300;
                                TIFFWriter.createTIFFFromImages(bimgs, TIFFWriter.TIFF_CONVERSION_NONE, TIFFWriter.TIFF_COMPRESSION_PACKBITS, new File(path + ".tif"));
                            } catch (IOException ex) {
                                Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    } else {
                        JOptionPane.showConfirmDialog(me, "You must select Image");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                } finally {
                    scanButton.setEnabled(true);
                }
            }
        });
        indexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    //    if (lastIndexedNode.getUserObject() instanceof BatchTreeItem) {
//                        BatchTreeItem item = (BatchTreeItem) lastIndexedNode.getUserObject();
//                        Capture relaseedCap = item.getCapture();
//
//                        if (relaseedCap.getRefused() != null && relaseedCap.getRefused()) {
//                            int result = JOptionPane.showConfirmDialog(me, "The Batch Has Refused Pages Are You sure You want Sent to Index");
//                            if (result == JOptionPane.YES_OPTION) {
////                                batch.setStatus(CaptureStatus.IndexMode);
////                                dao.attachDirty(relaseedCap);
//                                imageModel.removeNodeFromParent(lastIndexedNode);
//                                tree.updateUI();
//                                centerPanel.removeAll();
//                                centerPanel.validate();
//                                jScrollPane1.repaint();
//                            }
//                        }

                    
                    Capture doc = (Capture) batch.getCaptures().toArray()[0];
                    if (batch != null) {
                        UsersAudit audit = new UsersAudit();
                        audit.setBatchId(batch.getId());
                        audit.setUserId(user.getId());
                        audit.setModuleId(4);
                        audit.setAction(1);
                        audit.setDocId(doc.getId());
                        audit.setStatus(CaptureStatus.IndexMode);
                        audit.setLocked(false);
                        audit.setAuditDate(dao.getSysDate());
                        UsersAuditHome auditHome = new UsersAuditHome();
                        auditHome.persist(audit);
                        auditHome.close();
                        batch.setStatus(CaptureStatus.IndexMode);
                        imageModel.removeNodeFromParent((MutableTreeNode) root.children().nextElement());
                        tree.updateUI();
                        batch = null;
                    }
//                    } else {
//                        JOptionPane.showConfirmDialog(me, "You must select Batch");
//                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                // CaptureHome dao = null;
                zoom = 0;
                try {

                    IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                    if (lastIndexedNode == null) {
                        return;
                    }
                    if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                        ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
                        Capture capPage = img.getCapture();

                        File scanPath = new File("c:\\.capture\\" + rep.getId() + "\\" + batch.getId() + "\\" + img.getName() + ".tif");
//                        DiskHome diskDao = new DiskHome();
//                        disk = diskDao.getAllDisk().get(0);
                        disk = batch.getDisk();
                        
                        String path = null;
                        if(category.isCreateFolder()){
                            path = disk.getPath()+ "/" +category.getId()+"/view/"+ batch.getId() + "/" + img.getName() + ".tif";
                        }
                        else{
                             path = disk.getPath()+"/view/"+ batch.getId() + "/" + img.getName() + ".tif";
                        }
                        
                        
//                        String path = disk.getViewPath() + "/" + batch.getId() + "/" + img.getName() + ".tif";
                        File imgeFile = new File(path);
                        File file = null;
                        if (scanPath.exists()) {
                            file = scanPath;

                            Image image = (Image) new TIFFReader(file).getPage(0);
                            centerPanel.setImg(image);
                            centerPanel.setImageHeight(centerPanel.getHeight());
                            centerPanel.setImageWidth(centerPanel.getWidth());
                            centerPanel.setAngle(0);
                            centerPanel.addImage(image);
                            jScrollPane1.setViewportView(centerPanel);
                            return;
                        } else if (!imgeFile.exists()) {
                            file = new File(path);
                            JOptionPane.showConfirmDialog(me, "لم يتم نقل الصورة حتي الان");
                            return;
                        }
//                            if (!imgeFile.exists()) {
//                                JOptionPane.showConfirmDialog(me, "لم يتم نقل الصورة حتي الان");
//                                return;
//                            }
                        if (capPage.getRefused()) {
//                                String cmd = "C:/debug/CSImageProcessingDemo.exe "
//                                        + "C:/.EXC/" + batch.getId() + "/" + img.getCapture().getPath();
                            String cmd = "C:/debug/intermediateConsole.exe "
                                    + path;
                            Process process = Runtime.getRuntime().exec(cmd);
                            // int i = process.exitValue();
                            //   System.out.println("Iiiiii  "+i);
                            int i = process.waitFor();
                            process.destroy();

                            Image image = (Image) new TIFFReader(new File(path)).getPage(0);
                            centerPanel.setImg(image);
                            centerPanel.setImageHeight(centerPanel.getHeight());
                            centerPanel.setImageWidth(centerPanel.getWidth());
                            centerPanel.setAngle(0);
                            centerPanel.addImage(image);
                            jScrollPane1.setViewportView(centerPanel);
                        } else {
                            Image image = (Image) new TIFFReader(new File(path)).getPage(0);
                            centerPanel.setImg(image);
                            centerPanel.setImageHeight(centerPanel.getHeight());
                            centerPanel.setImageWidth(centerPanel.getWidth());
                            centerPanel.setAngle(0);
                            centerPanel.addImage(image);
                            jScrollPane1.setViewportView(centerPanel);
                        }

                    } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                        Capture capture = (Capture) lastIndexedNode.getUserObject();
//                        PatientsDoc doc = barcodePanel.show(capture.getBarcode(), user.getId(), batch.getId());
//                        if (doc != null) {
//                            docName = doc.getDocNo();
//                        }
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
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dirty = true;
                    IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                    ImageTreeItem imgItem = (ImageTreeItem) lastIndexedNode.getUserObject();
                    Capture imgCap = imgItem.getCapture();
                    Image img = centerPanel.getImg();
                    BufferedImage bimg = ImageGenerator.createBufferedImage(img);
                    // String pagePath = "C:/.qa/" + batch.getId() + "/" + imgCap.getPath();
                    // Disk disk = batch.getDisk();
                    
                    String path = null;
                    if(category.isCreateFolder()){
                            path = disk.getPath()+ "/" +category.getId()+"/view/" ;
                        }
                        else{
                             path = disk.getPath()+"/view/";
                        }
                    
                    String tempPath = path+ "/EXE/" + batch.getId() + "/" + imgItem.getName() + ".tif";
                    File tempDir = new File(path + "/EXE/" + batch.getId());
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
                    TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},
                            TIFFWriter.TIFF_CONVERSION_TO_GRAY,
                            TIFFWriter.TIFF_COMPRESSION_DEFLATE,
                            new File(tempPath));
                    CompressToTiff service = new CompressToTiff();
                    int result = service.getCompressToTiffSoap().compressImageFullPath(path + "/EXE/" + batch.getId(),
                            path + "/" + batch.getId(), imgItem.getName() + ".tif");
                    if (result != 1) {
                        // JOptionPane.showMessageDialog(me, "حدث خطأ اثناء الحفظ يرجي اعادة الحفظ");
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
                    // JOptionPane.showMessageDialog(me, "حدث خطأ اثناء الحفظ يرجي اعادة الحفظ");
                }

            }
        });
        actualSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    centerPanel.setImageHeight(imageHeight);
                    centerPanel.setImageWidth(imageWidth);
                    centerPanel.repaint();
                    jScrollPane1.setViewportView(centerPanel);
                } catch (Exception ex) {
                    Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        horSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    centerPanel.setImageHeight(centerPanel.getHeight());
                    centerPanel.setImageWidth(imageWidth);
                    centerPanel.repaint();
                    jScrollPane1.setViewportView(centerPanel);
                } catch (Exception ex) {
                    Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fitSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    centerPanel.setImageHeight(centerPanel.getHeight());
                    centerPanel.setImageWidth(centerPanel.getWidth());
                    centerPanel.repaint();
                    jScrollPane1.setViewportView(centerPanel);
                } catch (Exception ex) {
                    Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        sharpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                if (lastIndexedNode == null) {
                    return;
                }
                ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
                File page = new File(img.getPath());
                PlanarImage pi = JAIImageReader.readImage(page.getPath());
                ParameterBlock pb1 = new ParameterBlock();

                RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
                pb1.addSource(pi);
                pb1.add(0.4);
                pb1.add(0.2);
                pb1.add(0.0F);
                pb1.add(0.0F);
                pb1.add(new InterpolationNearest());
                pb1.add(qualityHints);
                PlanarImage scaled = JAI.create("SubsampleAverage", pb1, null);
                img.setPlanarImage(scaled);
                pi = scaled;

                sharpParam = sharpParam + 10;
                ParameterBlock pb = new ParameterBlock();
                pb.addSource(pi);
                float kData[] = new float[9];

                float alpha;
                if (sharpParam > 150) {
                    alpha = (sharpParam - 125.0f) / 25.0f;
                } else {
                    alpha = sharpParam / 150.0f;
                }
                float beta = (1.0f - alpha) / 8.0f;
                for (int i = 0; i < 9; i++) {
                    kData[i] = beta;
                }
                kData[4] = alpha;

                KernelJAI k = new KernelJAI(3, 3, 1, 1, kData);
                pb.add(k);
                PlanarImage pi1 = JAI.create("convolve", pb);
                Icon icon = new IconJAI(pi1);
                label = new JLabel();
                label.setIcon(icon);
                jScrollPane1.setViewportView(label);
            }
        });
        blurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                if (lastIndexedNode == null) {
                    return;
                }
                ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
                File page = new File(img.getPath());
                PlanarImage pi = JAIImageReader.readImage(page.getPath());
                ParameterBlock pb1 = new ParameterBlock();

                RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
                pb1.addSource(pi);
                pb1.add(0.4);
                pb1.add(0.2);
                pb1.add(0.0F);
                pb1.add(0.0F);
                pb1.add(new InterpolationNearest());
                pb1.add(qualityHints);
                PlanarImage scaled = JAI.create("SubsampleAverage", pb1, null);
                img.setPlanarImage(scaled);
                pi = scaled;

                sharpParam = sharpParam - 10;

                //   pi = img.getPlanarImage();
                ParameterBlock pb = new ParameterBlock();
                pb.addSource(pi);
                float kData[] = new float[9];

                float alpha;
                if (sharpParam > 150) {
                    alpha = (sharpParam - 125.0f) / 25.0f;
                } else {
                    alpha = sharpParam / 150.0f;
                }
                float beta = (1.0f - alpha) / 8.0f;
                for (int i = 0; i < 9; i++) {
                    kData[i] = beta;
                }
                kData[4] = alpha;

                KernelJAI k = new KernelJAI(3, 3, 1, 1, kData);
                pb.add(k);
                PlanarImage pi1 = JAI.create("convolve", pb);
                Icon icon = new IconJAI(pi1);
                // label = new JLabel();
                label.setIcon(icon);
                jScrollPane1.setViewportView(label);
            }
        });
        imageProcessingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cmd = "C:/debug/CSImageProcessingDemo.exe " + "C:/.EXC/" + batch.getId() + "/" + currentImage.getPath();
                    Process process = Runtime.getRuntime().exec(cmd);
                    // int i = process.exitValue();
                    //   System.out.println("Iiiiii  "+i);
                    int i = process.waitFor();
                    process.destroy();
                    IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                    ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
                    if (img.getImage() == null) {

                        Image image = null;
                        imgLoader = new ImageLoader(rep.getPath() + "/" + batch.getId(), img.getCapture().getPath(), img.getCapture().getPath());
                        if (img.getCapture().getPath().endsWith(".tif")) {
                            // TIFFReader reader = new TIFFReader(page);
                            currentImage = img.getCapture();
                            File localPage = new File("C:/.EXC/" + batch.getId() + "/" + img.getCapture().getPath());
                            if (localPage.exists()) {
                                TIFFReader reader = new TIFFReader(localPage);
                                image = (Image) reader.getPage(0);
                                image.flush();
                            } else {
                                image = imgLoader.getBimage();
                            }
                        } else if (img.getPath().endsWith(".jpg")) {
                            image = imgLoader.getBimage();
                        }
                        if (img.getCapture().getPath().endsWith(".jpg")) {
                            BufferedImage bi = new BufferedImage(500, 800, BufferedImage.TYPE_INT_RGB);
                            Graphics2D bg = bi.createGraphics();
                            bg.drawImage(image.getScaledInstance(500, 800, 1), 0, 0, 500, 800, 0, 0, 500, 800, null);
                            bg.dispose();//cleans up resources
                            label.setIcon(new ImageIcon(bi));
                        } else {
//                                BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
//                                Graphics2D bg = bi.createGraphics();
//                                bg.drawImage(image.getScaledInstance(800, 800, Image.SCALE_SMOOTH), 0, 0, 800, 800, 0, 0, 800, 800, null);
//                                bg.dispose();//cleans up resources
//                                label.setIcon(new ImageIcon(bi));
                            //    BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
                            centerPanel.setImageHeight(centerPanel.getHeight());
                            centerPanel.setImageWidth(centerPanel.getWidth());
                            centerPanel.setAngle(0);
                            centerPanel.addImage(image);
                            jScrollPane1.setViewportView(centerPanel);
                        }

//                            File page = new File(img.getPath());
//                            System.out.println(img.getPath());
//                            Image image = null;
//                            ImageLoader imgLoader = new ImageLoader(rep.getPath()+"/"+batch.getId(), img.getCapture().getPath(), img.getCapture().getPath());
//                            if (img.getPath().endsWith(".tif")) {
//                               // TIFFReader reader = new TIFFReader(page);
//                                image = imgLoader.getBimage();
//                            } else if (img.getPath().endsWith(".jpg")) {
//                                image = imgLoader.getBimage();
//                            }
//                            if (img.getPath().endsWith(".jpg")) {
//                                BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
//                                Graphics2D bg = bi.createGraphics();
//                                bg.drawImage(image.getScaledInstance(800, 800, 1), 0, 0, 800, 800, 0, 0, 800, 800, null);
//                                bg.dispose();//cleans up resources
//                                label.setIcon(new ImageIcon(bi));
//                            } else {
//                                BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
//                                Graphics2D bg = bi.createGraphics();
//                                bg.drawImage(image.getScaledInstance(800, 800, Image.SCALE_SMOOTH), 0, 0, 800, 800, 0, 0, 800, 800, null);
//                                bg.dispose();//cleans up resources
//                                label.setIcon(new ImageIcon(bi));
//                            }
//                            label.setPreferredSize(new Dimension(800, 800));
//                            jScrollPane1.setViewportView(label);
//                            String barcode = img.getCapture().getCapture().getBarcode();
////                            PatientsDoc doc = barcodePanel.show(barcode, user.getId(), batch.getId());
////                            if (doc != null) {
////                                docName = doc.getDocNo();
////                            }



                    } else {
                        label.setIcon(img.getImage());
                        jScrollPane1.setViewportView(label);
                        String barcode = img.getCapture().getCapture().getBarcode();
//                            PatientsDoc doc = barcodePanel.show(barcode, user.getId(), batch.getId());
//                            if (doc != null) {
//                                docName = doc.getDocNo();
//                            }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        createBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                if (lastIndexedNode.getUserObject() instanceof Capture) {
                    CaptureHome capHome = new CaptureHome();
                    Capture capture = ((Capture) lastIndexedNode.getUserObject());
                    String barcode = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                    System.out.println(capture.getId() + "  " + barcode);
                    capture.setBarcode(barcode);
                    capHome.merge(capture);
                    //  capHome.commit();
                    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/barcode32.png"));
                    IconNode parentNode = new IconNode(capture, true, icon, capture.getBarcode());
                    parentNode.setUserObject(capture);
                    parentNode.setIconName(capture.getBarcode());
                    parentNode.setIcon(icon);
                    lastIndexedNode.setUserObject(parentNode);
                    tree.updateUI();

                } else {
                    JOptionPane.showMessageDialog(me, bundle.getString("barcode.doc.error"));
                }

            }
        });

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        TreeCellEditor editor = new LeafCellEditor(tree, renderer);
        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode(rep.getName());
        String rootName = rep.getName() + "--" + category.getName();
        root = new IconNode(rootName);
        root1.add(root);
        imageModel = new ImageListModel(root);
        tree.setModel(imageModel);
        disableActions(true);
        changeScreenDirection(this, locale);

    }// </edit

    private void windowClosedAction(java.awt.event.WindowEvent evt) {
    }

    private void windowClosingAction(java.awt.event.WindowEvent evt) {
        releaseBatch();
    }

    private void releaseBatch() {
        try {
            CaptureHome dao = new CaptureHome();
            //  batch.setStatus(CaptureStatus.ExceptionMode);
            batch.setLocked(false);
            dao.merge(batch);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CaptureHome.close();
            //   Capture.close();
        }
    }

    public static BufferedImage rotate(BufferedImage img, int angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawImage(img, null, 0, 0);
        return dimg;
    }

    private void saveBatch() {
        try {
            Enumeration children = root.children();
            List<IconNode> docs = new ArrayList<IconNode>();
            IconNode parent = (IconNode) children.nextElement();
            Enumeration parentChildren = parent.children();
            while (parentChildren.hasMoreElements()) {
                IconNode child = (IconNode) parentChildren.nextElement();
                docs.add(child);
            }
            for (IconNode doc : docs) {
                //   System.out.println(doc.getUserObject().getClass());
                Capture docCapture = (Capture) doc.getUserObject();
                Enumeration docChildren = doc.children();
                while (docChildren.hasMoreElements()) {
                    IconNode child = (IconNode) docChildren.nextElement();
                    ImageTreeItem item = null;
                    if (child.getUserObject() instanceof ImageTreeItem) {
                        item = (ImageTreeItem) child.getUserObject();
                    } else if (child.getUserObject() instanceof IconNode) {
                        item = (ImageTreeItem) ((IconNode) child.getUserObject()).getUserObject();
                    }
                    Capture childCapture = item.getCapture();
                    childCapture.setCapture(docCapture);
                    dao.merge(childCapture); 
                }
            }
            System.out.println(docs.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void expandTree(JTree tree) {
        DefaultMutableTreeNode root =
                (DefaultMutableTreeNode) tree.getModel().getRoot();
        Enumeration e = root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode) e.nextElement();
            if (node.isLeaf()) {
                continue;
            }
            int row = tree.getRowForPath(new TreePath(node.getPath()));
            tree.expandRow(row);
        }
    }

    public void centerDLG(JDialog dlg) {

        java.awt.Point parentLoc = this.getLocationOnScreen();
        double cx = parentLoc.getX() + this.getWidth() / 2;
        double cy = parentLoc.getY() + this.getHeight() / 2;
        Point childLoc = new Point((int) cx - dlg.getWidth() / 2, (int) cy - dlg.getHeight() / 2);
        dlg.setLocation(childLoc);
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

    public void fillTree() {
        try {
//            if (!checkDocuments()) {
//                JOptionPane.showMessageDialog(this, "يوجد هناك خطأ في رقم المريض");
//            }

            tree.setCellRenderer(new IconNodeRenderer());
            IconNode root1 = new IconNode(rep.getName());
            String rootName = rep.getName() + "--" + category.getName();
            root = new IconNode(rootName);
            root1.add(root);
            imageModel = new ImageListModel(root);
            CaptureHome dao = new CaptureHome();
            if (batch != null) {
                //  writeNextPages();
                // if (disk == null) {
                disk = batch.getDisk();
                //}
                BatchTreeItem batchItem = new BatchTreeItem();
                batchItem.setName(batch.getName());
                batchItem.setCapture(batch);
                IconNode grandNode = new IconNode(batchItem);
                repPath = category.getRep().getPath();
                String[] names = repPath.split("/");
                String path1 = names[names.length - 1];
//                String path = "\\\\" + properties.getProperty("server.ip") + "\\" + path1 + "\\" + batch.getId();
                imageModel.addLeaf(root, grandNode);
                for (Capture parent : batch.getCaptures()) {
                    cap = parent;
                    if (parent.getCaptures() == null || parent.getCaptures().size() <= 0) {
                        continue;
                    }

                    ImageIcon ic = new ImageIcon(getClass().getClassLoader().getResource("resources/barcode32.png"));
                    parentNode = new IconNode(parent, true, ic, parent.getBarcode());
                    parentNode.setIconName(parent.getBarcode());

                    // parentNode.setIconName(parent.getBarcode());
                    imageModel.addLeaf(grandNode, parentNode);

                    // List<Capture> pages = dao.getDocRefusedPages(parent, CaptureStatus.ExceptionMode);
                    for (Capture son : parent.getCaptures()) {
                        if (!son.getDeleted()) {
                            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/scan.jpg"));
                            ImageTreeItem sonNode = new ImageTreeItem();
                            sonNode.setName(son.getName());
                            sonNode.setCapture(son);
                            IconNode child = null;
                            if (son.getRefused() && son.getRefuseNote() != null && son.getRefuseNote().equalsIgnoreCase("Re Scan Page")) {
                                child = new IconNode(sonNode, false, scaleImage(icon.getImage(), 16, 16), son.getPath());
                            } else if (son.getRefused()) {
                                ImageIcon delete = new ImageIcon(getClass().getClassLoader().getResource("resources/delete1.jpg"));
                                child = new IconNode(sonNode, false, scaleImage(delete.getImage(), 16, 16), son.getPath());

                            } else {
                                child = new IconNode(sonNode);
                            }
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
        }
    }

    private void disableActions(boolean flag) {
        deleteButton.setEnabled(flag);
        imageProcessingButton.setEnabled(flag);
        releaseBatchButton.setEnabled(flag);
//        correctButton.setEnabled(flag);
        indexButton.setEnabled(flag);
//        rotateLeft.setEnabled(flag);
        rotateRight.setEnabled(flag);
    }

    private JToolBar getToolBar() {
        if (toolBar == null) {
            toolBar = new JToolBar();
            toolBar.setFloatable(false);
            toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.LINE_AXIS));
            toolBar.add(getSelectBatchButton());
            toolBar.add(getScanButton());
            toolBar.add(getSelectSourceBttn());
            //        toolBar.add(getReplaceWithBtn());
            //    toolBar.add(getCorrectButton());
            toolBar.add(getIndexButton());
            //      toolBar.add(getZoomInBtn());
            //    toolBar.add(getZoomOutBtn());
            //    toolBar.add(getRotateRightBttn());
            toolBar.add(getDeleteButton());
            toolBar.add(getClearifyButton());
            toolBar.add(getReleaseBatch());
            toolBar.add(getCreateDocButton());
            toolBar.add(getSaveButton());
            toolBar.add(getCreateChangeCategory());
            toolBar.add(getSaveButton());
            toolBar.add(getCreateBarcodeButton());
//            toolBar.add(Box.createHorizontalStrut(430));
//            toolBar.add(getLogoutButton());
//            toolBar.add(northPanel);
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

    private JButton getCorrectButton() {
        if (correctButton == null) {
            correctButton = new JButton();
            correctButton.setText(bundle.getString("page.done"));
            //   scanButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/done.jpg"));
            icon = scaleImage(icon.getImage());
            correctButton.setIcon(icon);
            correctButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            correctButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return correctButton;
    }

    private JButton getReplaceWithBtn() {
        if (replaceWithBtn == null) {
            replaceWithBtn = new JButton();
            replaceWithBtn.setToolTipText(bundle.getString("replace"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/replace.jpg"));
            icon = scaleImage(icon.getImage());
            replaceWithBtn.setIcon(icon);
            replaceWithBtn.setPreferredSize(new java.awt.Dimension(60, 53));
            replaceWithBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
            replaceWithBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return replaceWithBtn;
    }

    private JButton getScanButton() {
        if (scanButton == null) {
            scanButton = new JButton(new AcquireImageAction());
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/scan.jpg"));
            scanButton.setIcon(scaleImage(icon.getImage()));
            scanButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            scanButton.setHorizontalTextPosition(SwingConstants.CENTER);

            scanButton.setText(bundle.getString("scan"));
            //   scanButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        }

        return scanButton;
    }

    public void changeCategory(Category category) {
        this.category = category;
        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode(rep.getName());
        String rootName = rep.getName() + "--" + category.getName();
        root = new IconNode(rootName);
        root1.add(root);
        imageModel = new ImageListModel(root);
        tree.setModel(imageModel);
    }

    private Component getHorizontalToolBar() {
        horizontalToolBar = new JToolBar();
        horizontalToolBar.setFloatable(false);

        horizontalToolBar.add(reasonLabel);
        horizontalToolBar.add(txtReason);
        //  horizontalToolBar.add(txtPages);
        return horizontalToolBar;
    }

    private JButton getHorizonDeleteButton() {
        if (horizonDeleteButton == null) {
            horizonDeleteButton = new JButton();
            horizonDeleteButton.setToolTipText(bundle.getString("delete"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/delete1.jpg"));
            icon = scaleImage(icon.getImage());
            horizonDeleteButton.setIcon(icon);
            deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return horizonDeleteButton;
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

    private JButton getActualSizeButton() {
        if (actualSizeButton == null) {
            actualSizeButton = new JButton();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/actual.png"));
            icon = scaleImage(icon.getImage());
            actualSizeButton.setIcon(icon);
        }
        return actualSizeButton;
    }

    private JButton getHorSizeButton() {
        if (horSizeButton == null) {
            horSizeButton = new JButton();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/hor.png"));
            icon = scaleImage(icon.getImage());
            horSizeButton.setIcon(icon);
        }
        return horSizeButton;
    }

    private JButton getFitSizeButton() {
        if (fitSizeButton == null) {
            fitSizeButton = new JButton();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/fit.png"));
            icon = scaleImage(icon.getImage());
            fitSizeButton.setIcon(icon);
        }
        return fitSizeButton;
    }

    private JButton getSharpButton() {
        if (sharpButton == null) {
            sharpButton = new JButton();
            sharpButton.setToolTipText(bundle.getString("sharp"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/sharp.png"));
            icon = scaleImage(icon.getImage());
            sharpButton.setIcon(icon);
        }
        return sharpButton;
    }

    private JButton getBlurButton() {
        if (blurButton == null) {
            blurButton = new JButton();
            blurButton.setToolTipText(bundle.getString("blur"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/blur.png"));
            icon = scaleImage(icon.getImage());
            blurButton.setIcon(icon);
        }
        return blurButton;
    }

    private JToolBar getVerticalToolBar() {
        verticalToolBar = new JToolBar();
        verticalToolBar.setFloatable(false);
        JTextField txtPages = new JTextField();
        txtPages.setPreferredSize(new Dimension(5, 5));
        txtPages.setSize(5, 5);
        txtPages.setText("1 of 1");
        txtPages.setEnabled(false);
        verticalToolBar.add(getFitSizeButton());
        verticalToolBar.add(getHorSizeButton());
        verticalToolBar.add(getActualSizeButton());
        verticalToolBar.add(getZoomInBtn());
        verticalToolBar.add(getZoomOutBtn());
        verticalToolBar.add(getHorizonDeleteButton());
        verticalToolBar.add(getRotateLeftBttn());
        verticalToolBar.add(getRotateRightBttn());
        verticalToolBar.add(getSharpButton());
        verticalToolBar.add(getBlurButton());

        verticalToolBar.setOrientation(1);
        verticalToolBar.add(getReplaceWithBtn());

        return verticalToolBar;
    }

    private class AcquireImageAction extends AbstractAction implements Runnable {

        AcquireImageAction() {
            super();
        }

        public void actionPerformed(ActionEvent e) {
            try {
                /**
                 * Set to the Twain or Sane source the last used capabilities
                 * (options).
                 */
                SwingWorker swingWorker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        //    acquireImage();
                        scan();
                        return null;
                    }

                    @Override
                    protected void done() {
                        scanButton.setText(bundle.getString("scan"));
                        scanButton.setEnabled(true);
                        imageModel.reload();
                    }
                };
                swingWorker.execute();
                SwingUtilities.invokeLater(this);
            } catch (Exception ex) {
                Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
        }
    }

    public String acquireImage() {
        //Constructor sets to the Twain or Sane source the last used capabilities (options).

        //     MorenaCapabilities morenaCapabilities = new MorenaCapabilities(this);

        //  TwainSource source = (TwainSource) morenaCapabilities.getSource();
        try {
            TwainSource source = TwainManager.getDefaultSource();
//            source.setTransferCount(-1);
//            source.setAutoScan(true);
//            source.setMaxBatchBuffers(99);
//            source.setVisible(false);
//            source.setIndicators(false);
//            source.setFeederEnabled(true);
//            source.setDuplexEnabled(northPanel.getChkDuplex().isSelected());
//            source.setAutoFeed(northPanel.getChkAdf().isSelected());
//            //       String sourceName = "TWAIN_" + source.toString();
//            //  writePropertiesFile(sourceName);
//            source.setBitDepth(1);
//            source.setResolution(Double.valueOf(northPanel.getTxtDpi().getText()));
//            source.setXResolution(Double.valueOf(northPanel.getTxtDpi().getText()));
//            source.setYResolution(Double.valueOf(northPanel.getTxtDpi().getText()));
//            if (northPanel.getRadioBW().isSelected()) {
//                //  source.setBitDepth(1);
//                source.setPixelType(source.TWPT_BW);
//                pixelType = TwainSource.TWPT_BW;
//
//            } else if (northPanel.getRadioRGB().isSelected()) {
//                //  source.setBitDepth(8);
//                source.setPixelType(TwainSource.TWPT_RGB);
//                pixelType = TwainSource.TWPT_RGB;
//            }
//
//
//            if (northPanel.getCboSize().equals("A4")) {
//                source.setSupportedSizes(TwainSource.TWSS_A4LETTER);
//            } else if (northPanel.getCboSize().equals("A3")) {
//                source.setSupportedSizes(TwainSource.TWSS_A3);
//            }
            CaptureHome capDao = new CaptureHome();
            pixelType = source.getPixelType();
            String page = capDao.getBatchPages(batch.getId());
            count = Integer.valueOf(page.split("page")[1]);
            do {
                scanButton.setEnabled(false);
                //  scanButton.setText(bundle.getString("scan") + " " + count);
                morenaImage = new MorenaImage(source);
                image = Toolkit.getDefaultToolkit().createImage(morenaImage);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ++count;
                            bimg = ImageGenerator.createBufferedImage(image);
                            bimgs[0] = bimg;

                            int height = (int) (centerPanel.getHeight() * 0.93);
                            int width = (int) (centerPanel.getWidth() * 0.8);
                            ImageIcon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING));
                            label.setIcon(icon);
                            jScrollPane1.setViewportView(label);
                            //  String path = docDir + "/" + "page" + (count) + ".tif";
                            String imgPath = path + "/page" + count + ".tif";
                            if (pixelType == TwainSource.TWPT_BW || pixelType == TwainSource.TWPT_GRAY) {
                                TIFFWriter.preferredResolution = 300;
                                TIFFWriter.createTIFFFromImages(bimgs, new File(imgPath));
                            } else {
                                ImageIO.write(bimg, "JPEG", new File(imgPath));
                            }
                            Capture c = new Capture();
                            c.setCapture(cap);
                            c.setName("page" + count);
                            c.setCategory(category);
                            c.setPath("page" + count + ".tif");
                            c.setType(3);//batch = 1 document =2 image = 3
                            c.setStatus(CaptureStatus.ScanMode);
                            // }
                            dao.persist(c); 
                            bimg.flush();
                            //      CaptureHome.close();
                            ImageTreeItem item = new ImageTreeItem();
                            item.setName("page" + (count));
                            item.setImage(icon);
                            item.setPath(path);
                            item.setCapture(c);
                            IconNode parent = (IconNode) tree.getLastSelectedPathComponent();
                            parent.add(new IconNode(item));
                            tree.updateUI();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            CaptureHome.close(); 
                        }
                    }
                });

            } while (source.hasMoreImages());

            tree.updateUI();
        } catch (Exception ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                TwainManager.close();
                CaptureHome.close();
            } catch (TwainException ex) {
                Logger.getLogger(ScanExceptionMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return batchName;
    }

    public void scan() throws TwainException {
        try {
            ArrayList<Object> children = Collections.list(root.children());
            if (children.size() <= 0) {
                JOptionPane.showMessageDialog(this, bundle.getString("error.no.batch"));
                return;
            } else if (children.size() > 1) {
                DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (lastIndexedNode == null || !(lastIndexedNode.getUserObject() instanceof BatchTreeItem)) {
                    JOptionPane.showMessageDialog(this, bundle.getString("warning.message.select.batch"));
                    return;
                }
            }
            docDir = "c:\\.capture\\" + rep.getId() + "\\" + batch.getId();
            File dir = new File(docDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            ScannerHome scanHome = new ScannerHome();
            CaptureHome capDao = new CaptureHome();
            List<Scanner> scanners = scanHome.getAllCaps();
            capMap = new HashMap<String, String>();
            for (Scanner cap : scanners) {
                capMap.put(cap.getCapName(), cap.getCapValue());
            }
            scanning = true;
            source = TwainManager.getDefaultSource();
            source.setTransferCount(-1);
            source.setAutoScan(true);
            source.setMaxBatchBuffers(99);
            source.setVisible(Boolean.valueOf(capMap.get("Visible")));
            source.setIndicators(Boolean.valueOf(capMap.get("Visible")));
            source.setCompression(TwainSource.TWCP_GROUP4);
            //source.setDuplexEnabled(Boolean.valueOf(capMap.get("DuplexEnabled")));
            source.setDuplexEnabled(northPanel.getChkDuplex().isSelected());
            source.setResolution(Integer.valueOf(capMap.get("YResolution")));
            source.setXResolution(Integer.valueOf(capMap.get("XResolution")));
            source.setAutoBright(Boolean.valueOf(capMap.get("AutoBright")));
            source.setAutomaticDeskew(Boolean.valueOf(capMap.get("AutoDeskw")));
            source.setAutomaticBorderDetection(Boolean.valueOf(capMap.get("AutoBorder")));
            source.setAutomaticRotate(Boolean.valueOf(capMap.get("AutoRotate")));
//            source.setAutoFeed(Boolean.valueOf(capMap.get("AutoFeed")));
            source.setAutoFeed(Boolean.valueOf(northPanel.getChkAdf().isSelected()));
            source.setBarCodeDetectionEnabled(Boolean.valueOf(capMap.get("Barcode")));
            String page = capDao.getBatchPages(batch.getId());
            count = Integer.valueOf(page.split("page")[1]);
            if (capMap.get("PixelType").equals("BW")) {
                //  source.setBitDepth(1);
                source.setPixelType(source.TWPT_BW);
                pixelType = TwainSource.TWPT_BW;

            } else if (capMap.get("PixelType").equalsIgnoreCase("GRAY")) {
                //  source.setBitDepth(8);

                source.setPixelType(TwainSource.TWPT_GRAY);
                //source.setBitDepth(24);
                pixelType = TwainSource.TWPT_GRAY;
            } else if (capMap.get("PixelType").equalsIgnoreCase("RGB")) {
                source.setPixelType(TwainSource.TWPT_RGB);
                pixelType = TwainSource.TWPT_RGB;
            } else if (capMap.get("PixelType").equalsIgnoreCase("PALLETE")) {
                source.setPixelType(TwainSource.TWPT_RGB);
                pixelType = TwainSource.TWPT_RGB;
            }


//            if (capMap.get("SupportedSizes").equals("A4LETTER")) {
//                source.setSupportedSizes(TwainSource.TWSS_A4LETTER);
//            } else if (capMap.get("SupportedSizes").equals("A3")) {
//                source.setSupportedSizes(TwainSource.TWSS_A3);
//            } else if (capMap.get("SupportedSizes").equals("NONE")) {
//                source.setSupportedSizes(TwainSource.TWSS_NONE);
//            }
            if (northPanel.getCboSize().getSelectedItem().equals("A4")) {
                source.setSupportedSizes(TwainSource.TWSS_A4LETTER);
            } else if (northPanel.getCboSize().getSelectedItem().equals("A3")) {
                source.setSupportedSizes(TwainSource.TWSS_A3);
            } else if (northPanel.getCboSize().getSelectedItem().equals("Auto")) {
                source.setSupportedSizes(TwainSource.TWSS_NONE);
            }
            if (source != null) {
                stillScanning = true;
                //            changeScannerSetting(source);
                MorenaImage image = null;
                BufferedImage[] bimgs = null;
                //     int count = batchesCountMap.get(batch.getId());
                do {
                    ++count;
                    image = new MorenaImage(source);
                    Image img = Toolkit.getDefaultToolkit().createImage(image);
                    bimg = ImageGenerator.createBufferedImage(img);
                    bimgs = new BufferedImage[]{bimg};
                    String path = docDir + "/" + "page" + (count);
                    //  System.out.println(path);
                    String pagePath = "page" + count + ".tif";
                    if (pixelType == TwainSource.TWPT_BW || pixelType == TwainSource.TWPT_GRAY) {
                        if (capMap.get("BWEXT").equalsIgnoreCase("TIF")) {
                            WriteTiffImages tiff = new WriteTiffImages(capMap.get("PixelType"));
                            tiff.setBimg(bimg);
                            tiff.setDpi(300);
                            pagePath = "page" + count + ".tif";
                            tiff.setPath(path + ".tif");
                            tiff.start();
                        } else {
                            pagePath = "page" + count + ".jpg";
                            ImageIO.write(bimg, "JPEG", new File(path + ".jpg"));
                        }
                    } else {
                        if (capMap.get("COLOREXT").equalsIgnoreCase("JPG")) {
                            pagePath = "page" + count + ".jpg";
                            ImageIO.write(bimg, "JPEG", new File(path + ".jpg"));
                        } else {


                            TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},
                                    TIFFWriter.TIFF_CONVERSION_TO_GRAY,
                                    TIFFWriter.TIFF_COMPRESSION_DEFLATE,
                                    new File(path));

                        }
                    }

                    int i = source.getBarCodeMaxRetries();

                    Capture c = new Capture();
                    c.setCapture(cap);
                    c.setName("page" + count);
                    c.setCategory(category);
                    c.setPath(pagePath);
                    c.setType(3);//batch = 1 document =2 image = 3
                    c.setStatus(CaptureStatus.ScanMode);
                    c.setDeleted(false);
                    c.setCreatedDate(dao.getSysDate());
                    c.setUsers(user);
                    dao.persist(c);
                    //                   batchesCountMap.put(batch.getId(), count);
                    

//                    centerPanel.setImageHeight(centerPanel.getHeight());
//                    centerPanel.setImageWidth(centerPanel.getWidth());
//                    centerPanel.addImage(bimg);
//                    jScrollPane1.setViewportView(centerPanel);


                    centerPanel.setImg(img);
                    centerPanel.setImageHeight(centerPanel.getHeight());
                    centerPanel.setImageWidth(centerPanel.getWidth());
                    centerPanel.setAngle(0);
                    centerPanel.addImage(img);
                    jScrollPane1.setViewportView(centerPanel);

//                    validate();
                    ImageTreeItem item = new ImageTreeItem();
                    item.setName("page" + (count));
                    item.setPath(docDir + "/" + pagePath);
                    item.setCapture(c);
                    FileUtils.copyFile(new File(docDir + "\\" + pagePath), new File(disk.getPath() + "/" + batch.getId() + "\\" + pagePath));
                    CompressToTiff service = new CompressToTiff();
                    int result = service.getCompressToTiffSoap().compressImageFullPath(disk.getViewPath() + "/QA/" + batch.getId(),
                            disk.getViewPath() + "/" + batch.getId(), pagePath);
                    //                  imageModel.reload();
                    imageModel.addLeaf(parentNode, new IconNode(item));
                } while (source.hasMoreImages());
                tree.updateUI();
                JOptionPane.showMessageDialog(this, "تم الانتهاء من المسح الضوئي");
                scanning = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            scanning = false;
            stillScanning = false;
            TwainManager.close();
            bimg = null;
            bimgs = null;


        }
    }

    private JButton getSelectSourceBttn() {
        if (selectSourceBttn == null) {
            selectSourceBttn = new JButton(new SelectSourceAction());
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/filter.jpg"));
            selectSourceBttn.setIcon(scaleImage(icon.getImage()));
            selectSourceBttn.setVerticalTextPosition(SwingConstants.BOTTOM);
            selectSourceBttn.setHorizontalTextPosition(SwingConstants.CENTER);

            selectSourceBttn.setText(bundle.getString("select.source"));
        }

        return selectSourceBttn;
    }

    private JButton getIndexButton() {
        if (indexButton == null) {
            indexButton = new JButton();
            indexButton.setText(bundle.getString("index.send"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/index.jpg"));
            icon = scaleImage(icon.getImage());
            indexButton.setIcon(icon);
            indexButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            indexButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return indexButton;
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

    private JButton getLogoutButton() {
        if (logoutBtn == null) {
            logoutBtn = new JButton(bundle.getString("logout"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/logout.jpg"));
            logoutBtn.setIcon(scaleImage(icon.getImage()));
//            logoutBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
//            logoutBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return logoutBtn;
    }

    private class SelectSourceAction extends AbstractAction {

        SelectSourceAction() {
            super();
        }

        public void actionPerformed(ActionEvent e) {
            try {
                selectSource();
            } catch (TwainException ex) {
                Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void selectSource() throws TwainException {
        String sourceName = null;
        String TWAIN_ = "TWAIN_";
        try {
            TwainSource src = TwainManager.selectSource(null);
            if (null != src) {
                sourceName = TWAIN_ + src.toString();
            }
            try {
                TwainManager.close();
            } catch (TwainException e2) {
                e2.printStackTrace();
            }
        } catch (Exception ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (null != sourceName) {
            try {
//                properties = new Properties();
//                properties.setProperty("selectedSource", sourceName);
//                //write properties scanner capabilities file
//                writePropertiesFile(sourceName);
//                File file = new File(System.getProperty("user.home") + "/.morena/" + "MorenaCapabilities.properties");
//                if (!file.exists()) {
//                    file.getParentFile().mkdirs();
//                }
//                properties.store(new FileOutputStream(file), null);
                selectedSource = sourceName;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(centerPanel, ex.getMessage(), "Exception during storing the properties file", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
        // acquireImage();

    }

    private void writePropertiesFile(String sourceName) {
//        SyncFilesService service = new SyncFilesService();
//        SyncFiles port = service.getSyncFilesPort();

        try {
//            String compName = InetAddress.getLocalHost().getHostName();
//            String in = port.sendSetting(compName);
//            String realname = showTrueName(new StringBuffer(sourceName));
//
//            String path = System.getProperty("user.home") + "/.morena/" + realname.trim() + ".properties";
//            FileOutputStream out = new FileOutputStream(path);
//            out.write(in.getBytes());
//            out.close();
        } catch (Exception ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String showTrueName(StringBuffer sourceName) {
        for (int i = 0; i < sourceName.toString().toCharArray().length; i++) {
            char c = sourceName.toString().toCharArray()[i];
            if (c == ' ' || c == '-' || c == '+') {
                sourceName.deleteCharAt(i);
            }
        }
        return sourceName.toString();
    }

    private JButton getRotateLeftBttn() {
        if (rotateLeft == null) {
            rotateLeft = new JButton();
            rotateLeft.setToolTipText(bundle.getString("rotate.left"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/left.jpg"));
            icon = scaleImage(icon.getImage());
            rotateLeft.setIcon(icon);
        }

        return rotateLeft;
    }

    private JButton getRotateRightBttn() {
        if (rotateRight == null) {
            rotateRight = new JButton();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/right.jpg"));
            icon = scaleImage(icon.getImage());
            rotateRight.setIcon(icon);
//            rotateRight.setVerticalTextPosition(SwingConstants.BOTTOM);
//            rotateRight.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        return rotateRight;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton(bundle.getString("delete"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/delete1.jpg"));
            icon = scaleImage(icon.getImage());
            deleteButton.setIcon(icon);
//            deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
//            deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return deleteButton;
    }

    private JButton getClearifyButton() {
        if (imageProcessingButton == null) {
            imageProcessingButton = new JButton(bundle.getString("image.process"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/process.jpg"));
            icon = scaleImage(icon.getImage());
            imageProcessingButton.setIcon(icon);
            imageProcessingButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            imageProcessingButton.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return imageProcessingButton;
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

    private JButton getZoomOutBtn() {
        if (zoomOutBtn == null) {
            zoomOutBtn = new JButton();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/zoomout.jpg"));
            icon = scaleImage(icon.getImage());
            zoomOutBtn.setIcon(icon);
        }

        return zoomOutBtn;
    }

    private JButton getZoomInBtn() {
        if (zoomInBtn == null) {
            zoomInBtn = new JButton();
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/zoomin.jpg"));
            icon = scaleImage(icon.getImage());
            zoomInBtn.setIcon(icon);
        }

        return zoomInBtn;
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
        setTitle("GDocScanException");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // System.exit(0);
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
        // TODO code application logic here
//        ScanExceptionMain main = new ScanExceptionMain();
//
//
//        //  main.setSize(800, 600);
//        main.centerScreen();
//        main.setVisible(true);
    }

    class PopupTrigger extends MouseAdapter {

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                int x = e.getX();
                int y = e.getY();
                Component cmp = tree.getComponentAt(x, y);
                TreePath path = tree.getPathForLocation(x, y);
                node = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (path != null) {
                    if (tree.isExpanded(path)) {
                        //   m_action.putValue(Action.NAME, "Collapse");
                    } else {
                        //   m_action.putValue(Action.NAME, "Expand");
                    }
                    m_popup.show(tree, x, y);
                    //   m_clickedPath = path;
                }
            }
        }
    }

    private void writeNextPages() {

        FetchBatchThread thread = new FetchBatchThread();

        File file = new File("C:/.EXC/" + batch.getId());
        file.mkdirs();
        thread.setPath("C:/.EXC/");
        thread.setType("EXC");
        thread.setBatch(batch);
        thread.start();
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
    private boolean dirty;
    private DefaultMutableTreeNode node;
    private DefaultMutableTreeNode movedNode;
    private DefaultMutableTreeNode pasteToNode;
    private Action a1;
    private Action a2;
    private int count;
    private Users user;
    private boolean ocred;
    private String barcode;
    private ScanExceptionMain me;
    private Capture batch;
    private boolean disabled;
    private String repPath;
    private String docDir;
    private String batchName;
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
    //private Properties properties;
    private String selectedSource;
    private javax.swing.JTable propTable;
    private ImagePan centerPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private JideSplitPane jSplitPane1;
    private JTree tree;
    private JPopupMenu popupMenu;
    private JButton correctButton;
    private JButton selectBatchButton;
    private JButton zoomOutBtn;
    private JButton zoomInBtn;
    private JButton releaseBatchButton;
    private JButton docButton;
    private JButton imageProcessingButton;
    private JButton deleteButton;
    private JButton rotateRight;
    private JButton rotateLeft;
    private JButton saveButton;
    private JButton indexButton;
    private JToolBar toolBar;
    private JToolBar verticalToolBar;
    private JToolBar horizontalToolBar;
    private JPanel mainImagePanel;
    private JPanel mainDocPanel;
    private JButton actualSizeButton;
    private JButton horSizeButton;
    private JButton fitSizeButton;
    private JButton sharpButton;
    private JButton blurButton;
    private JButton createBarcodeButton;
    private JLabel label;
    private JScrollPane leftScrollPane;
    private int zoom;
    private int sharpParam = 150;
    private PlanarImage pi;
    private int imageHeight;
    private int imageWidth;
    private ImageLoader imgLoader;
    private Capture currentImage;
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getAnonymousLogger();
}
