package com.gdit.capture.run;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import com.asprise.util.tiff.TIFFReader;
import com.gdit.capture.entity.BatchLogHome;
import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.model.CaptureStatus;
import com.java4less.vision.Barcode1DReader;
import com.java4less.vision.BarcodeData;
import com.java4less.vision.RImage;
import com.java4less.vision.VisionException;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author bahy
 */
public class ReadBarcode {

    /**
     * @param args the command line arguments
     */
    private Map<String, String> barcodes = new HashMap<String, String>();
    private Map<Integer, Long> parentsMap = new HashMap<Integer, Long>();
    private long grandId;

    public static void main(String[] args) {
        // TODO code application logic here
        ReadBarcode reader = new ReadBarcode();
        // reader.readBracode("C:\\.capture\\1\\14148", 14148);
        CaptureHome dao = new CaptureHome();
        List<Capture> batches = dao.getUnBarcoded(865976L);
        for (Capture batch : batches) {
            System.out.println("**********    "+batch.getId() +"  *************************");
            String barcode = null;
             
                File f = new File("\\\\192.168.1.3\\scan\\" + batch.getId() + "/page1.tif");

                if (f.exists()) {
                    barcode = reader.scanBarcode(f);

                }
            
//             
            if (barcode != null && !barcode.equals("")) {
                Capture doc = dao.GetSon(batch);
                doc.setBarcode(barcode);
                dao.attachDirty(doc);
            }
        }

    }

    public void ocr(String path, long grandID) {

        SortedSet<Integer> pages = new TreeSet<Integer>();
        try {
            File dir = new File(path);
            //  grandId =Long.valueOf(dir.getName());
            for (File file : dir.listFiles()) {
                String barcode = scanBarcode(file);
                if (barcode != null && barcode.length() > 2) {
                    System.out.println(" Barcode   " + barcode);
                    barcodes.put(file.getName(), barcode);
                    String tmp = new StringBuffer(file.getName()).delete(0, 4).toString().split(".tif")[0];
                    pages.add(Integer.valueOf(tmp));

                }
            }
            CaptureHome capDao = new CaptureHome();
            Capture grand = capDao.findById(grandID);
            Capture virtualParent = (Capture) grand.getCaptures().toArray()[0];
            Set<Capture> sons = virtualParent.getCaptures();
            grand.setStatus(3);
            capDao.attachDirty(grand);
            for (Integer s : pages) {
                Capture parent = new Capture();
                parent.setName(barcodes.get("page" + s + ".tif"));
                parent.setCapture(grand);
                parent.setType(2);
                parent.setStatus(3);
                parent.setPath("/" + grandId);
//                long id = dao.persistImage(parent);
//                parentsMap.put(s, id);
            }

            for (Capture son : sons) {

                String i = new StringBuffer(son.getName()).delete(0, 4).toString().split(".tif")[0];
                if (pages.contains(Integer.valueOf(i))) {
                    capDao.delete(son);
                    File barcodeFile = new File(dir.getAbsolutePath() + "/page" + i + ".tif");
                    barcodeFile.delete();
                    continue;
                }
                Integer pid = pages.headSet(Integer.valueOf(i)).last();
                Capture parent = capDao.findById(parentsMap.get(pid));
                if (parent != null) {
                    son.setCapture(parent);
                    son.setStatus(3);// 3 means ocred
                    capDao.attachDirty(son);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean readBracode(String path, long grandId) {
        try {
            CaptureHome capDao = new CaptureHome();
            Capture grand = capDao.findById(grandId);
            Capture virtualParent = (Capture) grand.getCaptures().toArray()[0];
            Capture barcodeParent = null;
            int docCount = grand.getCaptures().size();
            //Loop for the all capture pages inside the default document
            //Scan Every Page for barcode and if it has put in barcodeCapture Set
            for (Capture capturePage : virtualParent.getCaptures()) {
                if (!capturePage.getDeleted()) {
                    String pagePath = path + "\\" + capturePage.getPath();
                    File pageFile = new File(pagePath);
                    if (!pageFile.exists()) {
                        return false;
                    }
                    String barcode = scanBarcode(pageFile);
                    if (!barcode.equals("")) {

                        barcodeParent = new Capture();
                        barcodeParent.setName("Doc " + docCount++);
                        barcodeParent.setCapture(grand);
                        barcodeParent.setBarcode(barcode);
                        barcodeParent.setType(2);
                        barcodeParent.setStatus(CaptureStatus.QAMode);
                        barcodeParent.setDeleted(false);
                        barcodeParent.setLocked(false);
                        barcodeParent.setRefused(false);
                        capDao.persist(barcodeParent);
                        CaptureHome.close();
                        capturePage.setCapture(barcodeParent);
                        //  capDao.attachDirty(capturePage);
                    } else if (barcodeParent != null) {
                        //       System.out.println(barcodeParent.getId());
                        capturePage.setCapture(barcodeParent);
                        //  capturePage.attachDirty(capturePage);
//                        Capture.close();
                    }
                    //System.out.println("Barcodesss    "+barcode);

                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }

//        grand.setStatus(3);
//        dao.attachDirty(grand);
//        CaptureHome.close();
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
            if (!buffer.toString().equals("")) {
                System.out.println(" ****************** Barcode " + buffer.toString());
            }
            return buffer.toString();
        } catch (VisionException ex) {
            Logger.getLogger(ReadBarcode.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (java.lang.RuntimeException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public BufferedImage obtainBufferedImg(File file) {

        Image img = null;

        try {
//              img = ImageIO.read(file);
            if (file.getAbsolutePath().endsWith(".tif")) {
                TIFFReader reader = new TIFFReader(file);
                Image rm = (Image) reader.getPage(0);
                BufferedImage bi = ImageGenerator.createBufferedImage(rm);
                img = bi;//.getSubimage(0, 0, 1000, 1000);
            } else {
                return ImageIO.read(file);
            }

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
