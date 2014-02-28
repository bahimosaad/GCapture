/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.image;

import com.gdit.capture.run.ImageGenerator;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author bahy
 */
public class RotateImage {

    public   BufferedImage rotate(BufferedImage sourceBI,int degree) {


        AffineTransform at = new AffineTransform();

        // scale image
        at.scale(1.0, 1.0);

        // rotate 45 degrees around image center
        
        at.rotate(degree*Math.PI / 180.0, sourceBI.getWidth() / 1.0, sourceBI.getHeight() / 1.0);

        /*
         * translate to make sure the rotation doesn't cut off any image data
         */
        AffineTransform translationTransform;
        translationTransform = findTranslation(at, sourceBI);
        at.preConcatenate(translationTransform);

        // instantiate and apply affine transformation filter
        BufferedImageOp bio;
        bio = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage destinationBI = bio.filter(sourceBI, null);

        return destinationBI;

//    int frameInsetsHorizontal = frameInsets.right + frameInsets.left;
//    int frameInsetsVertical = frameInsets.top + frameInsets.bottom;
//    setSize(destinationBI.getWidth() + frameInsetsHorizontal, destinationBI
//        .getHeight()
//        + frameInsetsVertical);
//    show();
    }

    private   AffineTransform findTranslation(AffineTransform at, BufferedImage bi) {
        Point2D p2din, p2dout;

        p2din = new Point2D.Double(0.0, 0.0);
        p2dout = at.transform(p2din, null);
        double ytrans = p2dout.getY();

        p2din = new Point2D.Double(0, bi.getHeight());
        p2dout = at.transform(p2din, null);
        
        double xtrans = p2dout.getX();

        AffineTransform tat = new AffineTransform();
        
        tat.translate(-xtrans, -ytrans);
       
        return tat;
    }

    public static void main(String[] args) throws IOException{
//        String path ="C:/Documents and Settings/All Users.WINDOWS/Documents/My Pictures/Sample Pictures";
//         BufferedImage bimg = ImageIO.read(new File(path+"/winter.jpg"));
//         Image img = rotate(bimg, 90);
//         ImageIO.write(ImageGenerator.createBufferedImage(img),"JPEG",new File(path+"/winter90.jpg"));
       
       
    }
}
