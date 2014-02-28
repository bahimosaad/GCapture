/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.asprise.util.tiff.TIFFReader;
import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.run.ImageGenerator;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class ReadA2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        TIFFReader reader = new TIFFReader(new File("G:/A2/42030.5"));
        System.out.println(reader.countPages());
        for(int i =0 ;i<reader.countPages();i++){
           Image img = (Image) reader.getPage(i);
           BufferedImage bimg =  ImageGenerator.createBufferedImage(img);
           TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg}, new File("G:/a/"+i+".tif"));
        }
    }
}
