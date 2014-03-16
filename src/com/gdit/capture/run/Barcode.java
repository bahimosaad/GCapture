package com.gdit.capture.run;

import com.asprise.util.tiff.TIFFReader;
import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.entity.Scanner;
import com.gdit.capture.entity.*;
import com.gdit.capture.gui.CategoriesDlg;
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
public class Barcode extends JFrame {

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
    private Capture doc;
    private CaptureHome dao;

    /**
     * @param args the command line arguments
     */
    public Barcode(ResourceBundle bundle, Locale locale, Users user, Rep rep) {
        me = this;
        this.locale = locale;
        this.bundle = bundle;
        this.user = user;
        this.rep = rep;
        dao = new CaptureHome();
        if (rep != null && rep.getCategories().size() > 0) {
            category = (Category) rep.getCategories().toArray()[0];
        }
        //  properties = readSettingFile("C:/.capture/sysoptions.properties");
        //getBarcodes("C:/.capture\\barcode.properties");
        init();
        // fillTree();
    }

    public Barcode() {
        me = this;
        RepHome repHome = new RepHome();
        rep = repHome.findById(new Short("141"));
        dao = new CaptureHome();
        if (rep != null && rep.getCategories().size() > 0) {
            category = (Category) rep.getCategories().toArray()[0];
        }
        //  properties = readSettingFile("C:/.capture/sysoptions.properties");
        //getBarcodes("C:/.capture\\barcode.properties");
        init();

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
    public void init() {
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
        selectBatchButton.addActionListener(new ActionListener() {
            

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                     dao = new CaptureHome();
                    dirty = false;
                    if (batch != null) {
                        
                        batch.setLocked(false);
                        dao.attachDirty(batch);
                        dao.updateLock(batch);
                    }
                    new QAPatchesDlg(me, user, locale, bundle, category,dao);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
//                    CaptureHome.close();
                }
            }
        });
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {
               
                zoom = 0;
                try {
                    IconNode lastIndexedNode = (IconNode) tree.getLastSelectedPathComponent();
                    if (lastIndexedNode == null) {
                        return;
                    }
                    if (lastIndexedNode.getUserObject() instanceof ImageTreeItem) {
                        ImageTreeItem img = (ImageTreeItem) lastIndexedNode.getUserObject();
                        //  int i = images.indexOf(batchFile.getName()+"/"+ img.getName()+".tif");
                        //      SmbFile imageFile = images.get(img.getName()+".tif");
//                        SmbFile imageFile = new SmbFile(batchFile.getPath() + "/" + img.getName() + ".tif", authentication);
//                        System.out.println("Image " + imageFile.length() + "   " + imageFile.getName());
                        Disk disk = batch.getDisk();
                        
                        File file = new File(disk.getViewPath()+"/view/"+ batch.getId() + "/" + img.getName() + ".tif");
                        if(!file.exists()){
                            file = new File(disk.getPath()+"/scan/"+ batch.getId() + "/" + img.getName() + ".tif");
                        }
                        Image image = (Image) new TIFFReader(file).getPage(0);
                        currentBimg = ImageGenerator.createBufferedImage(image);
                        centerPanel.setImg(image);
                        centerPanel.setImageHeight(centerPanel.getHeight());
                        centerPanel.setImageWidth(centerPanel.getWidth());
                        centerPanel.setAngle(0);
                        centerPanel.addImage(image);
                        jScrollPane1.setViewportView(centerPanel);

                    } else if (lastIndexedNode.getUserObject() instanceof Capture) {
                        //   Capture capture = (Capture) lastIndexedNode.getUserObject();
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
//                    CaptureHome.close();
                }
            }
        });
        createBarcodeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IconNode localNode = (IconNode) tree.getLastSelectedPathComponent();
              //  if (localNode.getUserObject() instanceof DocTreeItem) {
//                    CaptureHome capHome = new CaptureHome();
//                    Capture capture = ((DocTreeItem) localNode.getUserObject()).getCapture();
                    String barcode = JOptionPane.showInputDialog(bundle.getString("barcode.enter.name"));
                    doc.setBarcode(barcode);
                    doc.setBarcoded(true);
                    dao.merge(doc);
                    
                    batch.setBarcoded(true);
                    batch.setLocked(false);
                    dao.merge(batch);
                   
                    //  capHome.commit();
                    DocTreeItem docItem = new DocTreeItem(doc);
                    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/barcode32.png"));
                    //IconNode parentNode = new IconNode(docItem, true, icon);
                    localNode.setUserObject(docItem);
                    localNode.setIcon(icon);
                    tree.updateUI();
//                } else {
//                    JOptionPane.showMessageDialog(me, bundle.getString("barcode.doc.error"));
//                }
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
            if (batch != null) {
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
                    if (parent.getCaptures() == null || parent.getCaptures().size() <= 0) {
                        continue;
                    }
                    DocTreeItem docItem = new DocTreeItem(parent);
                      doc = parent;
                    docItem.setName(parent.getBarcode());
                    IconNode parentNode = null;
                    if (parent.getBarcode() == null) {
                        parentNode = new IconNode(docItem);
                    } else {
                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/barcode32.png"));
                        parentNode = new IconNode(parent.getBarcode(), true, icon, parent.getBarcode());
                    }
                    // parentNode.setIconName(parent.getBarcode());
                    imageModel.addLeaf(grandNode, parentNode);
                    for (Capture son : parent.getCaptures()) {
                        if (!son.getDeleted()) {
                            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/scan.jpg"));

                            ImageTreeItem sonNode = new ImageTreeItem();
                            sonNode.setName(son.getName());
//                            sonNode.setPath(path + "/" + son.getPath());
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
//            CaptureHome.close();
            //Capture.close();
        }
    }

    private void disableActions(boolean flag) {
//        deleteButton.setEnabled(flag);
////        exceptionButton.setEnabled(flag);
//        releaseBatchButton.setEnabled(flag);
//        refuseButton.setEnabled(flag);
//        indexButton.setEnabled(flag);
//        // rotateLeft.setEnabled(flag);
//        rotateRight.setEnabled(flag);
    }

    private JToolBar getToolBar() {
        if (toolBar == null) {
            toolBar = new JToolBar();
            toolBar.setFloatable(false);
            toolBar.add(getSelectBatchButton());
//            toolBar.add(getRefuseButton());
//            toolBar.add(getIndexButton());
//            toolBar.add(getRotateRight90Bttn());
//            toolBar.add(getRotateLeft90Bttn());
//            toolBar.add(getZoomInBtn());
//            toolBar.add(getZoomOutBtn());
//            toolBar.add(getRotateRightBttn());
//            toolBar.add(getRotateLeftBttn());
//            toolBar.add(getDeleteButton());
//            toolBar.add(getReleaseBatch());
//            toolBar.add(getCreateDocButton());
//            toolBar.add(getSaveButton());
//            toolBar.add(getCreateChangeCategory());
            toolBar.add(getCreateBarcodeButton());
//            toolBar.add(Box.createHorizontalStrut(100));
//            toolBar.add(getLogoutButton());

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
        setTitle("Barcode ");
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                if (root.getChildCount() > 0) {
                   
                    batch.setLocked(false);
                    dao.attachDirty(batch);
                    dao.updateLock(batch);
                    
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

    public static void main(String[] args) {
//         TODO code application logic here
        Barcode main = new Barcode();
        main.setSize(800, 600);
        main.centerScreen();
        main.setVisible(true);
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
    private Barcode me;
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

    
}
