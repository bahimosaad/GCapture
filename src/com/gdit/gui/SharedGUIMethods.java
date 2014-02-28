/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

/**
 *
 * @author ehab
 */
public class SharedGUIMethods {
    public static void centerWindow(final Component target) {
       if (target != null) {
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           Dimension dialogSize = target.getSize();

           if (dialogSize.height > screenSize.height) {
               dialogSize.height = screenSize.height;
           }
           if (dialogSize.width > screenSize.width) {
               dialogSize.width = screenSize.width;
           }

           target.setLocation((screenSize.width - dialogSize.width) / 2,
                   (screenSize.height - dialogSize.height) / 2);
       }
   }

}
