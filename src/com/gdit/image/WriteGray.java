/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.image;

import com.asprise.util.tiff.TIFFReader;
import com.sun.media.jai.opimage.TIFFRIF;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author admin
 */
public class WriteGray {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        TIFFReader reader = new TIFFReader(new File("C:/209530/page1.tif"));
        BufferedImage bimg = (BufferedImage) reader.getPage(0);
        com.sun.media.jai.codec.TIFFEncodeParam params = new com.sun.media.jai.codec.TIFFEncodeParam();
        params.setCompression(com.sun.media.jai.codec.TIFFEncodeParam.COMPRESSION_JPEG_TTN2);
        FileOutputStream os = new FileOutputStream("C:/test/page1.tif");
        javax.media.jai.JAI.create("encode", bimg, os, "TIFF", params);
    }
}
