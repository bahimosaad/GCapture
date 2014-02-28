/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.*;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SyncBatches {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        List<Capture> batches = dao.getAllBatches(2185086L);
        for (Capture batch : batches) {
            File wDir = new File("W:/SCAN/" + batch.getId());
            File fDir = new File("F:/SCAN/" + batch.getId());
          if (wDir.exists()) {
            //   System.out.println(batch.getId()+"   "+wDir.getAbsolutePath());
                for (String page : wDir.list()) {
                    File source = new File(wDir, page);
                    File dest = new File(fDir, page);
                    if ( !dest.exists() || dest.length() <4000) {
                        InputStream in = new FileInputStream(source);
                        System.out.println(batch.getId() + "   " + page);
                        OutputStream out = new FileOutputStream(dest);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        in.close();
                        out.close();
                    }
                }
            }
        }

    }
}
