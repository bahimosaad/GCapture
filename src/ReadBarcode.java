/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.asprise.util.tiff.TIFFReader;
import com.java4less.vision.Barcode1DReader;
import com.java4less.vision.BarcodeData;
import com.java4less.vision.RImage;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bahy
 */
public class ReadBarcode {

    /**
     * @param args the command line arguments
     */
    private Map<String,String> barcodes = new HashMap<String,String>();

    public static void main(String[] args) {
        // TODO code application logic here
        ReadBarcode reader = new ReadBarcode();
        File dir = new File("C:/Documents and Settings/bahy/.capture/41/859");
        Set<String> pages  = new HashSet<String>();
        try{
            for(File file :dir.listFiles()){
               String barcode = reader.scanBarcode(file);
               if(barcode!=null){
                   System.out.println(" Barcode   "+barcode);
                   reader.barcodes.put(file.getName(),barcode);
                   pages.add(file.getName());
               }
            }
            for(String s :pages){
                System.out.println(s);
            }
        }catch(Exception ex){
            ex.printStackTrace();
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

    public String scanBarcode(File file) {
        try {
            Barcode1DReader reader = new Barcode1DReader();

            reader.setSymbologies(Barcode1DReader.CODE128);

            // Image image = ImageIO.read(new File("C:/images/1.tif"));
            BufferedImage image = obtainBufferedImg(file);
            BarcodeData[] barcodes = reader.scan(new RImage((image)));
            StringBuffer buffer = new StringBuffer("");
            for (BarcodeData date : barcodes) {
                buffer.append(date.getValue());
            }
            System.gc();
            return buffer.toString();
        } catch (Exception ex) {
            Logger.getLogger(ReadBarcode.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public BufferedImage obtainBufferedImg(File file) {

        Image img = null;

        try {
//              img = ImageIO.read(file);
            TIFFReader reader = new TIFFReader(file);
            RenderedImage rm = reader.getPage(0);
            BufferedImage bi = convertRenderedImage(rm);

            img = bi;//.getSubimage(0, 0, 1000, 1000);

        } catch (Exception ex) {
            Logger.getLogger(ReadBarcode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ((BufferedImage) img);
    }

    public BufferedImage convertRenderedImage(RenderedImage img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        ColorModel cm = img.getColorModel();
        int width = img.getWidth();
        int height = img.getHeight();
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        Hashtable properties = new Hashtable();
        String[] keys = img.getPropertyNames();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                properties.put(keys[i], img.getProperty(keys[i]));
            }
        }
        BufferedImage result = new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
        img.copyData(raster);
        return result;
    }
}
