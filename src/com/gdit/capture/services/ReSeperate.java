/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ReSeperate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
       
        List<Capture> batches = dao.getReSeperated(181);
        System.out.println();
    }
}
