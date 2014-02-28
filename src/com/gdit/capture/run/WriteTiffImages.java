package com.gdit.capture.run;

import com.asprise.util.tiff.TIFFWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bahy
 */
public class WriteTiffImages extends Thread {

    /**
     * @param args the command line arguments
     */
    private BufferedImage bimg;
    private String path;
    private int dpi;
    private String pixelType;

    public WriteTiffImages(String pixelType) {
        this.pixelType = pixelType;
    }

    @Override
    public void run() {
        try {
            TIFFWriter.preferredResolution = dpi;
            if (pixelType.equalsIgnoreCase("BW")) {
                // TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg}, TIFFWriter.TIFF_CONVERSION_NONE, TIFFWriter.TIFF_COMPRESSION_NONE, new File(path));
                TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},
                TIFFWriter.TIFF_CONVERSION_TO_BLACK_WHITE,TIFFWriter.TIFF_COMPRESSION_GROUP4, new File(path));

            } else if (pixelType.equalsIgnoreCase("GRAY")) { 
                TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},
                                                TIFFWriter.TIFF_CONVERSION_TO_GRAY,
                                                TIFFWriter.TIFF_COMPRESSION_DEFLATE,
                                                new File(path));
            } else {
                com.sun.media.jai.codec.TIFFEncodeParam params = new com.sun.media.jai.codec.TIFFEncodeParam();
                params.setCompression(com.sun.media.jai.codec.TIFFEncodeParam.COMPRESSION_JPEG_TTN2);
                FileOutputStream os = new FileOutputStream(path);
                javax.media.jai.JAI.create("encode", bimg, os, "TIFF", params);
            }
            // TODO code application logic here
        } catch (IOException ex) {
            Logger.getLogger(WriteTiffImages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage getBimg() {
        return bimg;
    }

    public void setBimg(BufferedImage bimg) {
        this.bimg = bimg;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
