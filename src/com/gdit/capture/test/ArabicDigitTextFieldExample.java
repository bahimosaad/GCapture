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
import java.awt.ComponentOrientation;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.NumericShaper;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ArabicDigitTextFieldExample {

    private String out = "";

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
        final JTextField textField = new JTextField();
        final Map digits = new HashMap();
        textField.setLocale(new Locale("Ar"));
        textField.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//        textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//        textField.setAlignmentX(JTextField.RIGHT_ALIGNMENT);

        for (char i = 0; i <= 9; i++) {
            digits.put(new Character((char) (i + 48)), new Character((char) (i + 1632)));
//            System.out.println(new Character((char) (i+48 )));
        }

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
//                char[] array = new char[]{event.getKeyChar()};
////          shaper.shape(array, 0, 1);
//
////           Integer c = new Integer(event.getKeyChar());
//                for (char c : array) {
//                    if (digits.get(c) != null) {
//                        out = out + digits.get(c);
//                    }
//                    else{
//                        out = out+c;
//                    }
//                }
//                out = out.replace("/", "\\");
//                System.out.println(out);
////               event.setKeyChar(out.toCharArray());
//                event.setKeyChar(' ');
//                textField.setText(out);

            }
        };

        textField.addKeyListener(keyListener);

        frame.add(textField);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
