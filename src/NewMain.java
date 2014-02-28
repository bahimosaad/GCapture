
import com.asprise.util.tiff.TIFFReader;
import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.java4less.vision.VisionException;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import jfs.FilesSync;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jfs.conf.JFSConst;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws VisionException, IOException {
        try {
         //  BufferedImage bi = ImageIO.read(new File("C:/winter.jpg"));
             ImageIcon delete = new ImageIcon("..\\..\\..\\..\\resources/refuse.png");
            

        } catch (Exception ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static JLabel scale(Image img, int width, int height)
            throws IOException {

        BufferedImage bimg = ImageGenerator.createBufferedImage(img);
        double longSideForSource = (double) Math.max(width, height);
        double longSideForDest = (double) 600;
        double multiplier = 0.4;
        int destWidth = (int) (width * multiplier);
        int destHeight = (int) (height * multiplier);
        BufferedImage destImage = new BufferedImage(destWidth, destHeight,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = destImage.createGraphics();
        AffineTransform affineTransform =
                AffineTransform.getScaleInstance(multiplier, multiplier);
        graphics.drawRenderedImage(bimg, affineTransform);

        ImageIcon icon = new ImageIcon(destImage);
        JLabel label = new JLabel(icon);

        JPanel panel = new JPanel();

        panel.add(label);
        panel.validate();
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setVisible(true);
        return label;
        // ImageIO.write(bdest, "JPG", new File(dest));
    }

    private static void editProfile(String captureHome, long docId) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            String profilePath = JFSConst.HOME_DIR + File.separator + JFSConst.DEFAULT_PROFILE_FILE;
            Document doc = docBuilder.parse(new File(profilePath));
            // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList directories = doc.getElementsByTagName("directory");
            for (int s = 0; s < directories.getLength(); s++) {
                Node firstPersonNode = directories.item(s);
                if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap attrs = firstPersonNode.getAttributes();
                    Node node = attrs.getNamedItem("src");
                    if (node.getNodeValue().equalsIgnoreCase(captureHome)) {
//                        FilesSync jfs = new FilesSync();
//                        jfs.go();
                        break;
                    }
                } //end of if clause
            }
            Node fileSync = doc.getFirstChild();
            Node dir = doc.createElement("directory");
            Attr src = doc.createAttribute("src");
            src.setValue(captureHome);
            dir.getAttributes().setNamedItem(src);
            Attr tgt = doc.createAttribute("tgt");
            tgt.setValue("ext://192.168.1.1//..//gdit//capture" + "/" + docId);
            dir.getAttributes().setNamedItem(tgt);
            fileSync.appendChild(dir);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//initialize StreamResult with File object to save to file
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            String xmlString = result.getWriter().toString();
            File file = new File(profilePath);
            FileOutputStream out = new FileOutputStream(file);
            out.write(xmlString.getBytes());
            out.close();

            FilesSync jfs = new FilesSync();
            jfs.go();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
