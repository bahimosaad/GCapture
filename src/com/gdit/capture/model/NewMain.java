/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Bahi
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        new NewMain().main();
    }
    
    
    public void main(){
         ImageIcon delete = new ImageIcon(getClass().getClassLoader().getResource("resources/refuse.png"));
        JFrame frame = new JFrame();
         frame.setSize(60,60);
         frame.setVisible(true);
         frame.setIconImage(delete.getImage());
         frame.add(new JLabel("Label",delete,JLabel.CENTER));
    }
}
