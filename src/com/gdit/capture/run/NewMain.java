package com.gdit.capture.run;

import com.gdit.capture.entity.*;
import com.java4less.vision.VisionException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.hibernate.transaction.JBossTransactionManagerLookup;

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
        Image img = ImageIO.read(new File("\\\\192.168.1.14\\kkuh\\1126/page5.jpg"));

        RenderedImage image = JAI.create("fileload", "\\\\192.168.1.14\\kkuh\\1126/page1.tif");

        ImageIcon icon = new ImageIcon(img.getScaledInstance(600, 800, Image.SCALE_SMOOTH));
        final JLabel label = new JLabel();

        label.setIcon(icon);
        final JPanel centerPanel = new JPanel();
        centerPanel.add(label);
        JFrame frame = new JFrame();
        frame.setSize(768, 1300);
        frame.add(centerPanel);
        JButton button = new JButton("test");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Image img = null;
                try {
                    img = ImageIO.read(new File("\\\\192.168.1.14\\kkuh\\1126/page8.jpg"));
                } catch (IOException ex) {
                    Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
                }

                RenderedImage image = JAI.create("fileload", "\\\\192.168.1.14\\kkuh\\1126/page1.tif");

                ImageIcon icon = new ImageIcon(img.getScaledInstance(600, 800, Image.SCALE_SMOOTH));
                 
                label.setIcon(icon);
                centerPanel.validate();
            }
        });
        frame.add(button,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}
