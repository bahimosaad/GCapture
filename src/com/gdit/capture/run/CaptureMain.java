package com.gdit.capture.run;

//bahi
import SK.gnome.capabilities.MorenaCapabilities;
import SK.gnome.morena.MorenaImage;
import SK.gnome.twain.TwainException;
import SK.gnome.twain.TwainManager;
import SK.gnome.twain.TwainSource;
import com.asprise.util.tiff.TIFFReader;
import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.entity.*;
import com.gdit.capture.gui.LoginFrame;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.gdit.capture.gui.ScannerSettingPanel;
import com.gdit.capture.main.ImagePanel;
import com.gdit.capture.model.BatchTreeItem;
import com.gdit.capture.model.CaptureStatus;
import com.gdit.capture.model.IconNode;
import com.gdit.capture.model.ImageTreeItem;
import com.gdit.capture.model.ImageListModel;

//import com.gdit.capture.service.SyncFiles;
//import com.gdit.capture.service.SyncFilesService;
import com.gdit.image.ImageUtil;
import com.jidesoft.utils.SwingWorker;
import java.awt.Dimension;
import javax.swing.JOptionPane;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.Position;
import javax.swing.tree.TreePath;

import com.gdit.capture.gui.CategoriesDlg;
import com.gdit.capture.gui.NorthPanel;
import com.gdit.capture.model.CONSTANTS;
import com.gdit.capture.model.TextNodeEditor;
import com.gdit.capture.model.TextNodeRenderer;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import jcifs.smb.SmbException;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;

import jfs.FilesSync;
import jfs.conf.JFSConst;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
public class CaptureMain extends JFrame {

    private MorenaCapabilities morenaCapabilities;
    private TwainSource source;
    private String repId;
    private Rep rep;
    private Map<Long, Integer> batchesCountMap = new HashMap<Long, Integer>();
    private JButton rotateLeft;
    private JButton rotateRight;
    private JButton zoomInBtn;
    private NorthPanel northPanel;
    private HashMap<String, String> capMap;
    private boolean scanning;
    private Disk disk;
    private Computers computer;
    private CaptureHome dao;

    public CaptureMain(ResourceBundle bundle, Locale locale, Users user, Disk disk, Computers computer, Category category, Rep rep) {
        try {
            me = this;
            this.disk = disk;
            this.bundle = bundle;
            this.locale = locale;
            this.user = user;
            this.rep = rep;
            this.computer = computer;
            this.category = category;
            dao = new CaptureHome();

//            if (rep != null && rep.getCategories().size() > 0) {
//                category = (Category) rep.getCategories().toArray()[0];
//            }
//            repId = String.valueOf(rep.getId());
            // getBarcodes("C:/.capture\\barcode.properties");
            byte[] bytes = null;
            try {
//                SyncFilesService service = new SyncFilesService();
//                SyncFiles port = service.getSyncFilesPort();
//                bytes = port.sendSystemOptions();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, bundle.getString("server.not.run"));
                e.printStackTrace();
                this.setVisible(false);
                return;
            }
            writeSysteOptions(bytes);
            properties = readSettingFile("C:/.capture/sysoptions.properties");
            init();
            fillTree();
            //checkProfile();
            //  disableActions(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkProfile() {
        File profileFile = new File("C:/.capture/profile.xml");
        if (!profileFile.exists()) {
            try {
                String mainElement = "jFileSync";
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element rootElement = document.createElement(mainElement);
                rootElement.setAttribute("sync", "13");
                rootElement.setAttribute("version", "2.2");
                rootElement.setAttribute("view", "24");
                document.appendChild(rootElement);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult result = new StreamResult(new FileOutputStream("C:/.capture/profile.xml"));
                transformer.transform(domSource, result);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void writeSysteOptions(byte[] bytes) {
        try {
//            FileOutputStream out = new FileOutputStream("C:/.capture/sysoptions.properties");
//            out.write(bytes);
//            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fillTree() {
        try {
//            CaptureHome dao = new CaptureHome();

            List<Capture> captures = dao.getUserCaptures(user, CaptureStatus.ScanMode, category);
            //  root = new DefaultMutableTreeNode("Scan");
            imageModel = new ImageListModel(root);
            if (captures != null && captures.size() > 0) {
                for (Capture bat : captures) {
//                File testFile = new File("C:/.capture/" + repId + "/" + bat.getId());
//                if (!testFile.exists()) {
//                    continue;
//                }
                    BatchTreeItem batchItem = new BatchTreeItem();
                    batchItem.setName(bat.getName());
                    batchItem.setCapture(bat);
                    IconNode grandNode = new IconNode(batchItem);

                    String path = computer.getScanPath() + "/" + bat.getId();
                    imageModel.addLeaf(root, grandNode);
                    for (Capture parent : bat.getCaptures()) {
                        if (parent.getCaptures() == null || parent.getCaptures().size() <= 0) {
                            continue;
                        }
//                    IconNode parentNode = new IconNode(parent.getName());
//                    imageModel.addLeaf(grandNode, parentNode);
                        for (Capture son : parent.getCaptures()) {
                            if (!son.getDeleted()) {
                                ImageTreeItem sonNode = new ImageTreeItem();
                                sonNode.setName(son.getName());
                                sonNode.setPath(path + "/" + son.getPath());
                                sonNode.setCapture(son);
                                File page = new File(path + "\\" + son.getPath());
//                            if (page == null || !page.exists()) {
//                                continue;
//                            }
                                IconNode child = new IconNode(sonNode);
                                imageModel.addLeaf(grandNode, child);
                            }
                        }
                    }

                }
            }
            tree.updateUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private class SendBatchAction extends AbstractAction implements Runnable {

        public SendBatchAction() {
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
                        sendBatch();

                        return null;
                    }

                    @Override
                    protected void done() {
                    }
                };
                swingWorker.execute();


            } catch (Exception ex) {
                Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
        }
    }

    public void createSharedFile(String path) {
        File shared = new File(path);
        shared.mkdirs();
        String s = "net share " + shared.getName() + "=" + path;
        System.out.println(s);
        try {
            Runtime.getRuntime().exec(s);
        } catch (IOException ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            TwainManager.close();
        } catch (TwainException e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(centerPanel, "No Scanners Connected ");
        } catch (Exception ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (null != sourceName) {
            try {
                properties = new Properties();
                properties.setProperty("selectedSource", sourceName);
                //write properties scanner capabilities file
                writePropertiesFile(sourceName);
                File file = new File(System.getProperty("user.home") + "/.morena/" + "MorenaCapabilities.properties");
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                }
                properties.store(new FileOutputStream(file), null);
                selectedSource = sourceName;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(centerPanel, ex.getMessage(), "Exception during storing the properties file", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
        // acquireImage();

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    public void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (locale == null) {
            locale = new Locale("ar");

        }
        //locale = new Locale("ar");
        bundle = ResourceBundle.getBundle("com/gdit/bundle/capture", locale);

        jSplitPane1 = new JideSplitPane();
        centerPanel = new ImagePan();

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        //   tree.addTreeSelectionListener(this);
        JPanel mainPanel = new JPanel();
        northPanel = new NorthPanel();
        mainPanel.add(getToolBar(), BorderLayout.NORTH);
        mainPanel.add(northPanel, BorderLayout.CENTER);
        // add(getToolBar(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.NORTH);
        add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.setProportionalLayout(true);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setDividerSize(5);
        jSplitPane1.addPane(jScrollPane2);
        jSplitPane1.addPane(jScrollPane1);
        jSplitPane1.setShowGripper(true);
        //jScrollPane2.setViewportView(tree);
        jScrollPane2.setViewportView(tree);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        centerPanel.setPreferredSize(new Dimension(screenWidth - 300, screenHeight - 100));
        jScrollPane1.setViewportView(centerPanel);
        double[] ds = new double[]{0.2};
        jSplitPane1.setProportions(ds);
        tree.setEnabled(!stillScanning);
//        TextNodeRenderer renderer = new TextNodeRenderer();
//        tree.setCellRenderer(renderer);

//        tree.setCellEditor(new TextNodeEditor(tree));
//        tree.setEditable(true);

        rotateRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.rotateRight();
                jScrollPane1.setViewportView(centerPanel);
            }
        });
        rotateLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.rotate();
                jScrollPane1.setViewportView(centerPanel);
            }
        });
        zoomInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.zoomout();
                jScrollPane1.validate();
            }
        });
        zoomOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.zoomin();
                jScrollPane1.validate();
            }
        });
        batchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBatch();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                //             if (lastIndexedNode != null && lastIndexedNode.isLeaf()) {
//                CaptureHome dao = new CaptureHome();
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
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
//                            File deleted = new File(item.getPath());
//                            deleted.delete();
//                        CaptureHome.close();
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
                        centerPanel.removeAll();
                        centerPanel.validate();
                        jScrollPane1.repaint();
                    } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                        Capture itemCapture = (Capture) lastIndexedNode.getUserObject();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
//                        Capture.close();
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
                        centerPanel.removeAll();
                        centerPanel.validate();
                        jScrollPane1.repaint();
                    } else if (lastIndexedNode.getUserObject() instanceof BatchTreeItem) {
                        BatchTreeItem item = (BatchTreeItem) lastIndexedNode.getUserObject();
                        Capture itemCapture = item.getCapture();
                        itemCapture.setDeleted(true);
                        dao.attachDirty(itemCapture);
                        dao.updateDeleted(itemCapture);
//                        CaptureHome.close();
                        imageModel.removeNodeFromParent(lastIndexedNode);
                        tree.updateUI();
                        centerPanel.removeAll();
                        centerPanel.validate();
                        jScrollPane1.repaint();
                    }

                }
                //               }
            }
        });
//        logoutBtn.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                me.dispose();
//                LoginFrame login = new LoginFrame();
//                login.centerScreen();
//                login.setVisible(true);
//            }
//        });
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
//                CaptureHome dao = new CaptureHome();
                try {
                    DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (lastIndexedNode == null) {
                        return;
                    }
                    if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                        centerPanel.setImageHeight(centerPanel.getHeight());
                        centerPanel.setImageWidth(centerPanel.getWidth());
                        centerPanel.setAngle(0);
                        ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
                        if (img.getPath().endsWith("tif")) {
                            File page = new File(img.getPath());
                            TIFFReader reader = new TIFFReader(page);
                            Image img1 = (Image) reader.getPage(0);
                            centerPanel.addImage(ImageGenerator.createBufferedImage(img1));
                            jScrollPane1.setViewportView(centerPanel);
                        } else {
                            centerPanel.addImage(ImageIO.read(new File(img.getPath())));
                            jScrollPane1.setViewportView(centerPanel);
                        }
                    } else if (lastIndexedNode.getUserObject() instanceof BatchTreeItem) {
                        if (!stillScanning) {
                            batchRoot = lastIndexedNode;
                            BatchTreeItem item = (BatchTreeItem) lastIndexedNode.getUserObject();
//                            batch = dao.findById(item.getCapture().getId());
                            batch = item.getCapture();
//                            cap = (Capture) batch.getCaptures().toArray()[0];
                           List<Capture> pages = dao.getBatchPages(batch);
                            System.out.println(batch.getName()+"  DOC SIZE "+pages.size());
                            batchesCountMap.put(batch.getId(),pages.size());

                            docDir = computer.getScanPath() + "/" + batch.getId();

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
//                    CaptureHome.close();
                }
            }
        });

        replaceWithBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (lastIndexedNode == null) {
                    return;
                }

                if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                    centerPanel.setImageHeight(centerPanel.getHeight());
                    centerPanel.setImageWidth(centerPanel.getWidth());
                    centerPanel.setAngle(0);
                    ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
                    if (img.getPath().endsWith("tif")) {
                        try {
                            scanPage(img.getPath(), img.getCapture());
                        } catch (TwainException ex) {
                            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
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
        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode(rep.getName());
        String rootName = rep.getName() + "--" + category.getName();
        root = new IconNode(rootName);
        // root1.add(root);
        imageModel = new ImageListModel(root);
        tree.setModel(imageModel);
        expandTree(tree);
        changeScreenDirection(this, locale);

    }// </editor-fold>

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

    public void changeCategory(Category category) {
        this.category = category;
        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode(rep.getName());
        String rootName = rep.getName() + "--" + category.getName();
        root = new IconNode(rootName);
        root1.add(root);
        imageModel = new ImageListModel(root);
        tree.setModel(imageModel);
        fillTree();
        expandTree(tree);
    }

    private JToolBar getToolBar() {
        if (toolBar == null) {
            toolBar = new JToolBar();
            toolBar.setFloatable(false);
            toolBar.add(getCreateBatch());
            toolBar.add(getScanButton());
            toolBar.add(getSelectSourceBttn());
            toolBar.add(getRotateRightBttn());
            toolBar.add(getRotateLeftBttn());
            toolBar.add(getReplaceWithBtn());
            toolBar.add(getZoomInBtn());
            toolBar.add(getZoomOutBtn());
            toolBar.add(getDeleteButton());
            toolBar.add(getSendBatch());
            toolBar.add(getCreateChangeCategory());
            toolBar.add(Box.createHorizontalStrut(100));


            //toolBar.add(getLogoutButton());
//            toolBar.add(northPanel);
        }

        return toolBar;
    }

    private JButton getScanButton() {
        if (scanButton == null) {
            scanButton = new JButton(new AcquireImageAction());
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/scan.jpg"));
            scanButton.setIcon(scaleImage(icon.getImage()));
            scanButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            scanButton.setHorizontalTextPosition(SwingConstants.CENTER);

            scanButton.setText(bundle.getString("scan"));
            //scanButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        }

        return scanButton;
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

    private JButton getZoomOutBtn() {
        if (zoomOutBtn == null) {
            zoomOutBtn = new JButton();
            zoomOutBtn.setText(bundle.getString("zoomout"));
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
            zoomInBtn = new JButton();
            zoomInBtn.setText(bundle.getString("zoomin"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/zoomin.jpg"));
            icon = scaleImage(icon.getImage());
            zoomInBtn.setIcon(icon);
            zoomInBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
            zoomInBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return zoomInBtn;
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

    private ImageIcon scaleImage(Image img) {
        Image scaledImage = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        return icon;
    }

    public ImageIcon scaleImage(Image img, int w, int h) {
        Image scaledImage = img.getScaledInstance(w, h, Image.SCALE_FAST);
        ImageIcon icon = new ImageIcon(scaledImage);
        return icon;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton(bundle.getString("delete"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/delete1.jpg"));
            deleteButton.setIcon(scaleImage(icon.getImage()));
            deleteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            deleteButton.setHorizontalTextPosition(SwingConstants.CENTER);

        }

        return deleteButton;
    }

    private JButton getReplaceWithBtn() {
        if (replaceWithBtn == null) {
            replaceWithBtn = new JButton(bundle.getString("replace"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/replace.jpg"));
            icon = scaleImage(icon.getImage());
            replaceWithBtn.setIcon(icon);
            replaceWithBtn.setPreferredSize(new java.awt.Dimension(60, 53));
            replaceWithBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
            replaceWithBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return replaceWithBtn;
    }

    private JButton getSendBatch() {
        if (sendBatchBtn == null) {
            sendBatchBtn = new JButton(new SendBatchAction());
            sendBatchBtn.setText(bundle.getString("send.batch"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/send.jpg"));
            icon = scaleImage(icon.getImage());
            sendBatchBtn.setIcon(icon);
            sendBatchBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
            sendBatchBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        return sendBatchBtn;
    }

    private JButton getCreateBatch() {
        if (batchButton == null) {
            batchButton = new JButton(bundle.getString("batch"));
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/batch.png"));
            icon = scaleImage(icon.getImage());
            batchButton.setIcon(icon);
            batchButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            batchButton.setHorizontalTextPosition(SwingConstants.CENTER);
            batchButton.setEnabled(!scanning);
        }

        return batchButton;
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

    private Properties getBarcodes(String path) {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
        }
        return properties;
    }

    public static void main(String args[]) {
        try {
            ServerHome serverHome = new ServerHome();
            Server serevr = serverHome.getAllServer().get(0);
            RepHome repHome = new RepHome();
            Rep rep = repHome.getAllRep().get(0);
//            NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication("", serevr.getUser(), serevr.getPwd());
//            UniAddress domain = UniAddress.getByName("192.168.1.1");
//            SmbSession.logon(domain, authentication);
//            String path = rep.getPath() + "/bahi/"  ;
//            SmbFile file = new SmbFile("smb://" + serevr.getIp() + "/" + path, authentication);
//            file.createNewFile();
            //   file.mkdirs();

            NtlmPasswordAuthentication authentication =
                    new NtlmPasswordAuthentication("", serevr.getUser(), serevr.getPwd());
            UniAddress domain = UniAddress.getByName(serevr.getIp());

            SmbSession.logon(domain, authentication);
            String path = "smb://" + serevr.getIp() + "/" + rep.getPath() + "/36";//"/e$/gdit/capture/36";
            String repPath = rep.getPath();
            //     String path = "smb://"+serevr.getIp()+"/"+"e$/gdit/capture"+"/36";//"/e$/gdit/capture/36";
            SmbFile sFile = new SmbFile(path, authentication);
            sFile.mkdir();
            sFile.createNewFile();

        } catch (MalformedURLException ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SmbException ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * *************************************** Capture Actions
     * *********************************************
     */
    public void createBatch() {
        try {
            //  String newName = JOptionPane.showInputDialog("Enter The Batch Name");

            //repPath = category.getRep().getPath();
            repPath = disk.getPath();
//            CaptureHome dao = new CaptureHome();
            long count = dao.getCountBatches(computer);
            batch = new Capture();
            batch.setRep(rep);
            batch.setType(1); //Batch 1 document type = 2   page type = 3
            batch.setUsers(user);
            // parentCapture.setName(String.valueOf(parentCapture.getId()));
            batch.setStatus(1);
            batch.setLocked(true);
            batch.setDeleted(false);
            batch.setCreatedDate(dao.getSysDate());
            batch.setCategory(category);
//            dao.persist(batch);
            batch.setName(computer.getScanPre() + (count + 1));
            batch.setDisk(disk);
            batch.setComputer(computer);
            dao.persist(batch);
//            CaptureHome.close();
            //    CaptureHome capDao = new CaptureHome();
            Capture sonCapture = new Capture();
            sonCapture.setCategory(category);
            sonCapture.setRep(rep);
            sonCapture.setDisk(disk);
            sonCapture.setComputer(computer);
            sonCapture.setCreatedDate(dao.getSysDate());
            sonCapture.setType(2); //Batch 1 document type = 2   page type = 3
            sonCapture.setCapture(batch);
            sonCapture.setPath("/" + batch.getId());
            sonCapture.setStatus(CaptureStatus.ScanMode);
            sonCapture.setUsers(user);
            sonCapture.setCreatedDate(new Date());
            sonCapture.setDeleted(false);
            //   docId = String.valueOf(capDao.persist(cap));
            dao.persist(sonCapture);
            batch.getCaptures().add(sonCapture);
            // docDir = System.getProperty("user.home") + "/.capture/" + repId + "/" + batchName;

            if (!stillScanning) {
                //       batchName = String.valueOf(batch.getId());
//                batch = parentCapture;
                batchName = batch.getName();
                cap = sonCapture;
                System.out.println("*******************************       " + batch.getId());
                docDir = computer.getScanPath() + "/" + batch.getId();
                File dir = new File(docDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                createServerBatch(batch.getId() + "");
            } else {
                System.out.println("*******************************       " + batch.getId());
                String newDocDir = computer.getScanPath() + "\\" + batch.getId();
                File dir = new File(newDocDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // createServerBatch(batch.getId() + "");
            }

            // count = 0;
            batchesCountMap.put(batch.getId(), 0);

            if (batchName == null || batchName.equals("")) {
                return;
            }
            ocred = false;
            BatchTreeItem batchItem = new BatchTreeItem();
            batchItem.setCapture(batch);
            batchItem.setName(batch.getName());

            DefaultMutableTreeNode newBatchRoot = new DefaultMutableTreeNode(batchItem);
            imageModel.addLeaf(root, newBatchRoot);
            if (!stillScanning) {
                batchRoot = newBatchRoot;
            }
            tree.updateUI();
            disableActions(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // System.out.println("*****************************************            " + parentCapture.getId());
//            CaptureHome.close();
        }
    }

    private Properties readSettingFile(String filePath) {
        Properties prop = new Properties();
        try {
//            prop.load(new FileInputStream(filePath));
            return prop;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendBatch() {
        try {
            scanning = true;
//            CaptureHome dao = new CaptureHome();
//            List<Capture> pages = dao.getBatchPages(batch);
//            File file = new File(computer.getScanPath() + "/" + batch.getId());
//            if (pages.size() > file.listFiles().length) {
//                int diff = pages.size() - file.listFiles().length;
//                List<Capture> deleted = dao.getBatchDeletedPages(batch);

//                for (Capture d : deleted) {
//                    File pfile = new File(docDir + "/" + d.getPath());
//                    if (!pfile.exists()) {
//                        --diff;
//                    }
//                }
//                if (diff > file.listFiles().length) {
//                    System.out.println(pages.size() + "          " + file.listFiles().length);
//                    JOptionPane.showMessageDialog(this, bundle.getString("error.page.write"));
//                    return;
//                }
//            }
            DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (!lastIndexedNode.children().hasMoreElements()) {
                JOptionPane.showMessageDialog(this, bundle.getString("error.empty.batch"));
                return;
            }
//             
            String f = new File(docDir).getAbsolutePath();
            boolean isBarcoded = false;
//            String capFile = disk.getPath() + "/" + String.valueOf(batch.getId());
//            System.out.println(capFile);
            //  port.createFile(capFile);//("", "", f, batch.getId(), repPath, isBarcoded);

            File scanFile = null;
            if (category.isCreateFolder()) {
                scanFile = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId());
            } else {
                scanFile = new File(disk.getPath() + "/scan/" + batch.getId());
            }
//            File to = disk.getPath() + "\\" + batch.getId();
            BatchObserver obs = new BatchObserver();
            obs.setDisk(disk);
            obs.setBatch(batch);
            obs.setFrom(new File(docDir));
            obs.setTo(scanFile);
            obs.setCategory(category);
            obs.start();
//

            batch.setLocked(false);
            batch.setStatus(CaptureStatus.QAMode);
            dao.attachDirty(batch);
//            dao.updateLock(batch);
//            dao.updateStatus(batch);


//            if (lastIndexedNode == null) {
//                DefaultMutableTreeNode node = new DefaultMutableTreeNode(batchName);
//                imageModel.removeNodeFromParent(node);
//            } else if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
//                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) lastIndexedNode.getParent();
//                imageModel.removeNodeFromParent(parent);
//            } else {
//                root.remove(lastIndexedNode);
//            }
            root.remove(lastIndexedNode);

            tree.updateUI();
            centerPanel.setImg(null);
            centerPanel.setImageHeight(centerPanel.getHeight());
            centerPanel.setImageWidth(centerPanel.getWidth());
            centerPanel.setAngle(0);
            jScrollPane1.setViewportView(centerPanel);

            //  editProfile(docDir, batch.getId());

//            if (isBarcoded) {
//                CaptureHome dao = new CaptureHome();
//                batch.setLocked(true);
//                batch.setStatus(CaptureStatus.QAMode);
//                batch.attachDirty(batch);
//                dao.updateLock(batch);
//                dao.updateStatus(batch);
//                //  port.barcodeImages(capFile, batch.getId());
//                return;
//            } else {
//                CaptureHome dao = new CaptureHome();
//                batch.setStatus(CaptureStatus.QAMode);
//                batch.setLocked(true);
//                batch.attachDirty(batch);
//                dao.updateLock(batch);
//                dao.updateStatus(batch);
//            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            scanning = false;
//            CaptureHome.close();
            // batch = null;
        }
    }

    public void createServerBatch(String fileName) {
        try {
//            ServerHome serverHome = new ServerHome();
//            Server serevr = serverHome.getAllServer().get(0);
//
//            NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication("", serevr.getUser(), serevr.getPwd());
//            UniAddress domain = UniAddress.getByName(serevr.getIp());
//            SmbSession.logon(domain, authentication);
//            String path = disk.getPath() + "/" + fileName;
//            SmbFile file = new SmbFile("smb://" + serevr.getIp() + "/" + path, authentication);
//
//            file.mkdirs();
            String path = null;
            if (category.isCreateFolder()) {
                path = disk.getPath() + "/" + category.getId() + "/scan/" + fileName;
            } else {
                path = disk.getPath() + "/" + fileName;
            }

            File batchFile = new File(path);
            batchFile.mkdirs();
        } catch (Exception ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void writePropertiesFile(String sourceName) {
        try {
//            SyncFilesService service = new SyncFilesService();
//            SyncFiles port = service.getSyncFilesPort();
            String compName = InetAddress.getLocalHost().getHostName();
            // String in = port.sendSetting(compName);

            String realname = showTrueName(new StringBuffer(sourceName));

            String path = "C:/.capture/scanner.properties";
            FileOutputStream out = new FileOutputStream(path);
            //   out.write(in.getBytes());
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String showTrueName(StringBuffer sourceName) {
        for (int i = 0; i < sourceName.toString().toCharArray().length; i++) {
            char c = sourceName.toString().toCharArray()[i];
            if (c == ' ' || c == '-' || c == '+') {
                sourceName.deleteCharAt(i);
            }
        }
        return sourceName.toString();
    }

    public void scan() throws TwainException {
//        javax.swing.SwingWorker swingWorker = new javax.swing.SwingWorker() {
//            @Override
//            protected Object doInBackground() {
        try {
            stillScanning = true;
            ArrayList<Object> children = Collections.list(root.children());
            if (children.size() <= 0) {
                JOptionPane.showMessageDialog(me, bundle.getString("error.no.batch"));
                return;
            } else if (children.size() > 1) {
                DefaultMutableTreeNode lastIndexedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (lastIndexedNode == null || !(lastIndexedNode.getUserObject() instanceof BatchTreeItem)) {
                    JOptionPane.showMessageDialog(me, bundle.getString("warning.message.select.batch"));
                    return;
                }
            }
//            CaptureHome dao = new CaptureHome();

            ScannerHome scanHome = new ScannerHome();
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
//            source.setBarCodeDetectionEnabled(Boolean.valueOf(capMap.get("Barcode")));
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

                //            changeScannerSetting(source);
                MorenaImage image = null;
                BufferedImage[] bimgs = null;
                int count = batchesCountMap.get(batch.getId());
                do {
                    count++;
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
                        }
                    } else {
                        pagePath = "page" + count + ".jpg";
                        ImageIO.write(bimg, "JPEG", new File(path + ".jpg"));
                    }
                    int i = source.getBarCodeMaxRetries();

                    Capture c = new Capture();
                    c.setCapture(cap);
                    c.setName("page" + count);
                    c.setCategory(category);
                    c.setRep(rep);
                    c.setDisk(disk);
                    c.setComputer(computer);
                    c.setCreatedDate(new Date());
                    c.setPath(pagePath);
                    c.setType(3);//batch = 1 document =2 image = 3
                    c.setStatus(CaptureStatus.ScanMode);
                    c.setDeleted(false);
                    c.setCreatedDate(dao.getSysDate());
                    c.setUsers(user);
                    dao.persist(c);
                    batchesCountMap.put(batch.getId(), count);
                    centerPanel.setImageHeight(centerPanel.getHeight());
                    centerPanel.setImageWidth(centerPanel.getWidth());
                    centerPanel.addImage(bimg);
                    jScrollPane1.setViewportView(centerPanel);
//                    validate();
                    ImageTreeItem item = new ImageTreeItem();
                    item.setName("page" + (count));
                    item.setPath(docDir + "/" + pagePath);
                    item.setCapture(c);
                    imageModel.addLeaf(batchRoot, new DefaultMutableTreeNode(item));
                    //                  imageModel.reload();
                } while (source.hasMoreImages());
                tree.updateUI();
                JOptionPane.showMessageDialog(me, "تم الانتهاء من المسح الضوئي");
                scanning = false;

            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            scanning = false;
            stillScanning = false;
            try {
                TwainManager.close();
            } catch (TwainException ex) {
                Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            bimg = null;
            bimgs = null;
        }


//            @Override
//            protected void done() {
//                scanning = false;
//                stillScanning = false;
//                try {
//                    TwainManager.close();
//                } catch (TwainException ex) {
//                    Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                bimg = null;
//                bimgs = null;
//            }
//        };
//        swingWorker.execute();
    }

    public void scanPage(String pageName, Capture c) throws TwainException {
        try {
            stillScanning = true;
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


            ScannerHome scanHome = new ScannerHome();
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

            if (northPanel.getCboSize().getSelectedItem().equals("A4")) {
                source.setSupportedSizes(TwainSource.TWSS_A4LETTER);
            } else if (northPanel.getCboSize().getSelectedItem().equals("A3")) {
                source.setSupportedSizes(TwainSource.TWSS_A3);
            } else if (northPanel.getCboSize().getSelectedItem().equals("Auto")) {
                source.setSupportedSizes(TwainSource.TWSS_NONE);
            }
            if (source != null) {

                //            changeScannerSetting(source);
                MorenaImage image = null;
                BufferedImage[] bimgs = null;
                do {

                    image = new MorenaImage(source);
                    Image img = Toolkit.getDefaultToolkit().createImage(image);
                    bimg = ImageGenerator.createBufferedImage(img);
                    bimgs = new BufferedImage[]{bimg};
                    String path = docDir + "/" + pageName;
                    //  System.out.println(path);

                    if (pixelType == TwainSource.TWPT_BW || pixelType == TwainSource.TWPT_GRAY) {
                        TIFFWriter.preferredResolution = 300;
                        if (capMap.get("BWEXT").equalsIgnoreCase("TIF")) {
                            if (pixelType == TwainSource.TWPT_BW) {
                                // TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg}, TIFFWriter.TIFF_CONVERSION_NONE, TIFFWriter.TIFF_COMPRESSION_NONE, new File(path));
                                TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},
                                        TIFFWriter.TIFF_CONVERSION_TO_BLACK_WHITE, TIFFWriter.TIFF_COMPRESSION_GROUP4, new File(path));

                            } else if (pixelType == TwainSource.TWPT_GRAY) {
                                TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},
                                        TIFFWriter.TIFF_CONVERSION_TO_GRAY,
                                        TIFFWriter.TIFF_COMPRESSION_DEFLATE,
                                        new File(path));
                            }
                        }
                    } else {

                        ImageIO.write(bimg, "JPEG", new File(path));
                    }
                    int i = source.getBarCodeMaxRetries();


                    centerPanel.setImageHeight(centerPanel.getHeight());
                    centerPanel.setImageWidth(centerPanel.getWidth());
                    centerPanel.addImage(bimg);
                    jScrollPane1.setViewportView(centerPanel);
//                    validate();
                    ImageTreeItem item = new ImageTreeItem();
                    item.setName(c.getName());
                    item.setPath(docDir + "/" + pageName);
                    item.setCapture(c);
                    imageModel.addLeaf(batchRoot, new DefaultMutableTreeNode(item));
                    //                  imageModel.reload();
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
//            CaptureHome.close();

        }
    }

    public void changeScannerSetting(TwainSource source) {
        Properties selectedScanner = new Properties();
        Properties scannerSetting = new Properties();
        String sourceName = null;
        try {

            selectedScanner.load(new FileInputStream(System.getProperty("user.home") + "/.morena/" + "MorenaCapabilities.properties"));
            String sn = selectedScanner.getProperty("selectedSource");
            sourceName = showTrueName(new StringBuffer(sn));
            scannerSetting.load(new FileInputStream("C:/.capture/scanner.properties"));
            try {
                source.setIndicators(Boolean.valueOf(scannerSetting.getProperty("Indicators")));
                source.setFeederEnabled(Boolean.valueOf(scannerSetting.getProperty("FeederEnabled")));
                source.setDuplexEnabled(Boolean.valueOf(scannerSetting.getProperty("DuplexEnabled")));
                source.setAutoFeed(Boolean.valueOf(scannerSetting.getProperty("AutoFeed")));
                source.setBitDepth(Integer.valueOf(scannerSetting.getProperty("BitDepth")));
                source.setXResolution(Double.valueOf(scannerSetting.getProperty("XResolution")));
                source.setYResolution(Double.valueOf(scannerSetting.getProperty("YResolution")));
                source.setResolution(Double.valueOf(scannerSetting.getProperty("YResolution")));
                if (scannerSetting.getProperty("PixelType").equals("BW")) {
                    source.setPixelType(TwainSource.TWPT_BW);
                    pixelType = TwainSource.TWPT_BW;
                } else if (scannerSetting.getProperty("PixelType").equals("GRAY")) {
                    source.setPixelType(TwainSource.TWPT_GRAY);
                    pixelType = TwainSource.TWPT_GRAY;
                } else if (scannerSetting.getProperty("PixelType").equals("RGB")) {
                    source.setPixelType(TwainSource.TWPT_RGB);
                    pixelType = TwainSource.TWPT_RGB;
                } else if (scannerSetting.getProperty("PixelType").equals("PALETTE")) {
                    source.setPixelType(TwainSource.TWPT_PALETTE);
                    pixelType = TwainSource.TWPT_PALETTE;
                }
                if (scannerSetting.getProperty("SupportedSizes").equals("A4LETTER")) {
                    source.setSupportedSizes(TwainSource.TWSS_A4LETTER);
                } else if (scannerSetting.getProperty("SupportedSizes").equals("A3")) {
                    source.setSupportedSizes(TwainSource.TWSS_A3);
                }

            } catch (TwainException ex) {
                ex.printStackTrace();
                //    JOptionPane.showMessageDialog(null, bundle.getString("twain.source.set.error"));

            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, bundle.getString("select.source.not.found"));

        }



    }

    private void disableActions(boolean flag) {
        deleteButton.setEnabled(flag);
//        replaceWithBtn.setEnabled(flag);
        sendBatchBtn.setEnabled(flag);
        scanButton.setEnabled(flag);
        //      selectSourceBttn.setEnabled(flag);
        //  createDocBtn.setEnabled(flag);
        rotateRight.setEnabled(flag);
    }

    public void centerScreen() {
        setTitle("GDocCapture");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
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

    public String save() {
        try {
//            SyncFilesService service = new SyncFilesService();
//            SyncFiles port = service.getSyncFilesPort();
//            String compName = InetAddress.getLocalHost().getHostName();
//
//            String ip = InetAddress.getLocalHost().getHostAddress();
//            String f = new File(docDir).getParentFile().getAbsolutePath().replace(":", "$");
//            port.sync(compName, ip, f, batchId, repPath);
//            CaptureHome dao = new CaptureHome();
//            cap.setSaved(true);
//            dao.merge(cap);
//            dao.commit();
//            dao.close();
            return batchName;
        } catch (Exception ex) {
            Logger.getLogger(CaptureMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Image loadImage(String f) throws Exception {
        Image im2 = null;
        java.awt.MediaTracker mt2 = null;
        java.io.FileInputStream in = null;
        byte[] b = null;
        int size = 0;
        in = new java.io.FileInputStream(f);
        if (in != null) {
            size = in.available();
            b = new byte[size];
            in.read(b);
            im2 = java.awt.Toolkit.getDefaultToolkit().createImage(b);
            in.close();
        }
        mt2 = new java.awt.MediaTracker(new Canvas());
        if (im2 != null) {
            if (mt2 != null) {
                mt2.addImage(im2, 0);
                mt2.waitForID(0);
            }
            int WidthIm = im2.getWidth(null);
        }
        BufferedImage input = new BufferedImage(im2.getWidth(null), im2.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = input.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, im2.getWidth(null), im2.getHeight(null));
        g.drawImage(im2, 0, 0, null);
        g.dispose();
        g = null;
        return input;


    }

    private BufferedImage createResizedCopy(Image originalImage,
            int scaledWidth, int scaledHeight,
            boolean preserveAlpha) {
        System.out.println("resizing...");
        int imageType = preserveAlpha ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();

        return scaledBI;
    }

    public JLabel scale(Image img, int width, int height)
            throws IOException {


        BufferedImage bimg = ImageGenerator.createBufferedImage(img);
        double longSideForSource = (double) Math.max(width, height);
        double longSideForDest = (double) 600;
        double multiplier = longSideForDest / longSideForSource;
        int destWidth = (int) (width * 1);
        int destHeight = (int) (height * 1);
        BufferedImage destImage = new BufferedImage(destWidth, destHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = destImage.createGraphics();
        AffineTransform affineTransform =
                AffineTransform.getScaleInstance(multiplier, multiplier);
        graphics.drawRenderedImage(bimg, affineTransform);

        ImageIcon icon = new ImageIcon(destImage);
        JLabel label = new JLabel(icon);
        return label;
        // ImageIO.write(bdest, "JPG", new File(dest));
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
    //    private MorenaCapabilities morenaCapabilities;
    // private TwainSource source;
    private static final Logger log = Logger.getAnonymousLogger();
    private boolean stillScanning;
    private Users user;
    private Capture batch;
    private Locale locale;
    private Category category;
    private boolean disabled;
    private boolean ocred;
    // private int count;
    private CaptureMain me;
    private String barcode;
    private String repPath;
    private String docDir;
    private String batchName;
    private Capture cap;
    private ImageListModel imageModel;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode batchRoot;
    //  private String batchId;
    private ResourceBundle bundle;
    private int rotate;
    private int zoomout;
    private ScannerSettingPanel settPanel;
    private ImagePanel selected = null;
    private Properties properties;
    private String selectedSource;
    private javax.swing.JTable propTable;
    private ImagePan centerPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private JideSplitPane jSplitPane1;
    private javax.swing.JTree tree;
    private JButton scanButton;
    private JButton batchButton;
    private JButton zoomOutBtn;
    private JButton sendBatchBtn;
    private JButton replaceWithBtn;
    private JButton deleteButton;
    private JButton logoutBtn;
    private JButton changeCategoryButton;
    private JButton selectSourceBttn;
    private JToolBar toolBar;
//    private JTextField batchText;
    private static final long serialVersionUID = 1L;
    /* Class Variables because of Internal Classess */
    MorenaImage morenaImage = null;
    Image image = null;
    BufferedImage bimg = null;
    BufferedImage[] bimgs;
    JDialog dlg = null;
    int pixelType = 0;
}
