/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.test;

/**
 *
 * @author Bahi
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.NumericShaper;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ArabicDigitTextFieldExample {

    public static void main(String[] args) {
     SwingUtilities.invokeLater(new Runnable() {

         @Override
         public void run() {
          new ArabicDigitTextFieldExample().createGUI();
         }
     });
    }

    public void createGUI() {
     JFrame frame = new JFrame();
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     JTextField textField = new JTextField();
     KeyListener keyListener = new KeyListener() {

         private NumericShaper shaper = NumericShaper
              .getShaper(NumericShaper.ARABIC);

         @Override
         public void keyPressed(KeyEvent event) {

         }

         @Override
         public void keyReleased(KeyEvent event) {

         }

         @Override
         public void keyTyped(KeyEvent event) {
          char[] array = new char[] { event.getKeyChar() };
          shaper.shape(array, 0, 1);
          event.setKeyChar(array[0]);
         }
     };

     textField.addKeyListener(keyListener);

     frame.add(textField);
     frame.pack();
     frame.setLocationRelativeTo(null);
     frame.setVisible(true);
    }

}