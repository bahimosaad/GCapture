
import SK.gnome.morena.*;
import SK.gnome.twain.*;

import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.model.ImageTreeItem;
import com.gdit.capture.model.ImageListModel;
import com.gdit.image.PanelCanvas;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class ADFTwainExample extends JFrame implements ActionListener, Runnable {

    private JFrame frame;
    PanelCanvas centerPanel;
    JTree tree;
    ImageListModel imageModel;
    private DefaultMutableTreeNode root;
    private Button scanButton;
    private JButton helpButton;
    Thread thread = null;

    public ADFTwainExample() {

        JPanel panel = new JPanel();
        scanButton = new Button("Scan");
        panel.add(scanButton);
        helpButton = new JButton("Help");
        panel.add(helpButton);

        tree = new javax.swing.JTree();
        root = new DefaultMutableTreeNode("root");
        imageModel = new ImageListModel(root);
        tree.setModel(imageModel);
        centerPanel = new PanelCanvas();
        add(panel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(tree, BorderLayout.EAST);


        scanButton.addActionListener(this);

        helpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 10; i++) {
                    imageModel.addLeaf(root, new DefaultMutableTreeNode("test"));
                    tree.updateUI();

                }
            }
        });


    //    getContentPane().add(frame,BorderLayout.CENTER);
    }

    public static void main(String[] args) throws MorenaException {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                ADFTwainExample ex = new ADFTwainExample();
                ex.setSize(1024, 1024);

                ex.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ex.setVisible(true);
            }
        });



    // ex.scan();

    }

    public void scan() throws TwainException {
        try {

            TwainSource source = TwainManager.selectSource(null);
            System.err.println("Selected source is " + source);
            if (source != null) {
                source.setFeederEnabled(true);
                source.setAutoFeed(true);
//                source.setIndicators(false);
//                source.setVisible(false);
                //  source.setTransferCount(5);
                int count = 1;
                do {
                    MorenaImage image = new MorenaImage(source);
                    Image img = Toolkit.getDefaultToolkit().createImage(image);
                    BufferedImage bimg = ImageGenerator.createBufferedImage(img);
                    TIFFWriter.preferredResolution = 300;
                            TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},TIFFWriter.TIFF_CONVERSION_NONE,TIFFWriter.TIFF_COMPRESSION_PACKBITS, new File("C:/test/test" + count + ".tif"));
                           ImageIO.write(bimg, "JPEG",new File("C:/test/test3.jpg"));
                         TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg}, new File("C:/test/test" + count + ".tif"));
                   // ImageIO.write(bimg, "JPEG", new File("C:/test/test" + count + ".jpg"));
                    bimg.flush();
                    System.gc();
                    MediaTracker tracker = new MediaTracker(this);
                    tracker.addImage(img, 1);
                    count++;
                    try {
                        tracker.waitForAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ImageIcon icon = scaleImage(img, img.getWidth(null) / 4, img.getHeight(null) / 7);
                    JLabel label = new JLabel();
                    label.setIcon(icon);

                    //new ImageDisplayer(icon.getImage());
                    ImageTreeItem item = new ImageTreeItem();
                    item.setName("page" + (count));
                    item.setImage(icon);
                    item.setPath("path");

                    imageModel.addLeaf(root, new DefaultMutableTreeNode(item));
                    tree.updateUI();

                    centerPanel.removeAll();
                    centerPanel.addImage(img);
                    centerPanel.validate();
                    validate();
//                    setSize(1024, 1024);
//                    setVisible(true);

//                    frame.setSize(600, 600);
//                    frame.setVisible(true);

                    System.err.println("Size of acquired image " + (count++) + " is " + img.getWidth(null) + " x " + img.getHeight(null));
                } while (source.hasMoreImages());

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            TwainManager.close();
        }
    }

    private static ImageIcon scaleImage(Image img, int w, int h) {
        Image scaledImage = img.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
        ImageIcon icon = new ImageIcon(scaledImage);
        return icon;
    }

    private static void save(BufferedImage image, String ext) {
        String fileName = "C:/savingAnImage";
        File file = new File(fileName + "." + ext);
        try {
            ImageIO.write(image, ext, file);  // ignore returned boolean
        } catch (IOException e) {
            System.out.println("Write error for " + file.getPath() +
                    ": " + e.getMessage());
        }
    }

    private static BufferedImage toBufferedImage(Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

    public static BufferedImage getBufferedImageFromImage(Image paramImage) {
        if (paramImage == null) {
            return null;
        }
        //     Canvas localCanvas = new Canvas();
        //       new ImageIcon(paramImage);
        int i = paramImage.getWidth(null);
        int j = paramImage.getHeight(null);
        BufferedImage localBufferedImage = new BufferedImage(i, j, 1);
        Graphics2D localGraphics2D = localBufferedImage.createGraphics();
        localGraphics2D.drawImage(paramImage, 0, 0, null);
        localBufferedImage.flush();
        localGraphics2D.dispose();
        return localBufferedImage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            scan();
        } catch (TwainException ex) {
            Logger.getLogger(ADFTwainExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
    }
}
