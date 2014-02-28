/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.util.List;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class Sync {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            CompressToTiff soap = new CompressToTiff(); 
            File source = new File("F:/25022013"); 
            for (File batch : source.listFiles()) {
              //  if (source.length() != dest.length()) {
                 for(String page:batch.list()){
                    System.out.println("F:/25022013/" + batch.getName()+"/"+page);
                     soap.getCompressToTiffSoap().compressImageFullPath("F:/25022013/" + batch.getName(), "F:/scan/" + batch.getName(),page);
                 }
                //}
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}
