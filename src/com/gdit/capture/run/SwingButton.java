package com.gdit.capture.run;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ehab
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingButton {
    public static void main(String args[]) {
    JFrame frame = new JFrame("Title");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    final JButton button = new JButton("Press Here");
    ActionListener action = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Clicked");
        SwingWorker worker = new SwingWorker() {
          protected String doInBackground() throws InterruptedException {
            Thread.sleep(3000);
            return null;
          }
          protected void done() {
            String label = button.getText();
            button.setText(label + "0");
          }
        };
        worker.execute();
      }
    };
    button.addActionListener(action);
    frame.add(button, BorderLayout.CENTER);
    frame.setSize(200, 200);
    frame.setVisible(true);
  }


}
