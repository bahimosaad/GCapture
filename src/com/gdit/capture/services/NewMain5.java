/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NewMain5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        CaptureHome dao = new CaptureHome();
        List<Capture> batches = dao.getAllBatches(2);
        for(Capture batch:batches){
            
        }
    }
}
