/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.*;
import java.util.List;
import java.util.zip.*;
/**
 * 
 * @author admin
 */
public class ZipBatches {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome home = new CaptureHome();
        ZipBatches zip = new ZipBatches();
        List<Capture> batches =  home.getAllBatches();
        for(Capture batch:batches){
           zip.zip("W:/ytech/gip/"+batch.getId(),"F:/ytech/gip/backup/"+batch.getId());
        }
    }
    
    public void zip(String from,String to){
    try {
            File inFolder = new File(from);
            File outFolder = new File(to+".zip");
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
            BufferedInputStream in = null;
            byte[] data = new byte[1000];
            String files[] = inFolder.list();
            for (int i = 0; i < files.length; i++) {
                in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), 1000);
                out.putNextEntry(new ZipEntry(files[i]));
                int count;
                while ((count = in.read(data, 0, 1000)) != -1) {
                    out.write(data, 0, count);
                }
                out.closeEntry();
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
