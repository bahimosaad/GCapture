/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.test;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NewMain1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CategoryHome catHome = new CategoryHome();
        Category cat = catHome.findById(141L);
        CaptureHome capHome = new CaptureHome() ;
        List<Capture> batches = capHome.getWatingIndexing(cat);
        for(Capture batch :batches){
            System.out.println(batch.getId() + "    "+batch.getStatus());
        }
    }
}
