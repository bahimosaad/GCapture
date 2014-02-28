/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.image;

/**
 *
 * @author admin
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ImageRotate {
   private static final String IMAGE_PATH = "C:\\.qa\\209162\\page5.jpg";

   public static void main(String[] args) {
      try {
         BufferedImage image = ImageIO.read(new File(IMAGE_PATH));
         ImageIcon icon = new ImageIcon(image);
         JOptionPane.showMessageDialog(null, new JLabel(icon));

         icon = new ImageIcon(rotate(image, 20));
         JOptionPane.showMessageDialog(null, new JLabel(icon));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private static Image rotate(Image image, double degs) {
      int width = image.getWidth(null);
      int height = image.getHeight(null);
      BufferedImage temp = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
      Graphics2D g2 = temp.createGraphics();
      g2.rotate(Math.toRadians(degs), height / 2, height / 2);
      g2.drawImage(image, 0, 0, Color.WHITE, null);
      g2.dispose();
      return temp;
   }
}