/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.test;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bahi
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map digits = new HashMap();
        for (char i = 0; i <= 9; i++) {
            digits.put( new Integer (i),new Character((char) (i + 1632)));
        }
        
       
        System.out.println(digits.get(8));

    }
}
