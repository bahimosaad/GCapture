/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.test;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.utils.FindBackupFile;
import java.awt.font.NumericShaper;
import java.io.File;

/**
 *
 * @author Bahi
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         FindBackupFile bkup = new FindBackupFile();
         String  path = bkup.findFile("4911966", new File("G:/"));
        
    }
}
