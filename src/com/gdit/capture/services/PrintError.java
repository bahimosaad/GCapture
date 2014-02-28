/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureDocument;
import com.gdit.capture.entity.CaptureDocumentHome;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class PrintError {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PrintError().printPages();
    }

    public void printPages() {
        
        CaptureHome dao = new CaptureHome();
        CaptureDocumentHome docHome = new CaptureDocumentHome();
        Set<Capture> setBatches = new HashSet<Capture>();
        List<Capture> pages = dao.getErrorPages(1L, 1900000L);
        for (Capture page : pages) {
            Capture doc = page.getCapture();
            Capture batch = doc.getCapture();
           
            if(page.getName().equals("page1") || page.getName().equals("page2") ||page.getName().equals("page3") )
 //           if (setBatches.add(batch)) {
                if (doc.getBarcode() != null && !doc.getBarcode().equals("null")) {
                    CaptureDocument d = docHome.findById(Long.valueOf(doc.getBarcode()));
                    File file  = new File("W:/ytech/gip/"+batch.getId());
                    
                    if (d != null && file.list().length > 0) {
                        System.out.print(batch.getId() + "    ");
                        System.out.print(page.getName() + "    ");
                         System.out.print(d.getYer() + "    ");
                          System.out.print(d.getJrnlDate() + "    ");
                        System.out.print(d.getLineNo() + "    ");
                        System.out.print(d.getJrnlType() + "    ");
                        
                        System.out.println();
                    } else {
                        System.out.print(batch.getId() + "    ");
                        System.out.print(page.getName() + "    ");
                        System.out.print(doc.getBarcode() + "    ");
                        System.out.println();
                    }
                    
                } else {
//                    System.out.print(batch.getId() + "    ");
//                //    System.out.print(page.getName() + "    ");
//                    System.out.println();
                }
 //           }
        }
    }
    
    public void printBatches() {
        
        CaptureHome dao = new CaptureHome();
        CaptureDocumentHome docHome = new CaptureDocumentHome();
        Set<Capture> batches = dao.getErrorBarches(1900000L);
        for (Capture batch : batches) {
            
            
            if (batch.getBarcode() != null && !batch.getBarcode().equals("null")) {
                CaptureDocument d = docHome.findById(Long.valueOf(batch.getBarcode()));
                if (d != null) {
                    System.out.print(batch.getId() + "    ");                    
                    System.out.print(d.getYer() + "    ");
                    System.out.print(d.getJrnlDate() + "    ");
                    System.out.print(d.getLineNo() + "    ");
                    System.out.print(d.getColumnNo() + "    ");
                    System.out.print(d.getCorridorNo() + "    ");
                    System.out.print(d.getBoxNo() + "    ");
                    System.out.print(d.getFileNo() + "    ");
                    System.out.println();
                } else {
                    System.out.print(batch.getId() + "    ");                    
                    System.out.print(batch.getBarcode() + "    ");
                    System.out.println();
                }
                
            } else {
//                System.out.print(batch.getId() + "    "); 
//                System.out.println();
            }
        }
    }
    //   }
}
