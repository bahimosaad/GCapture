/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.*;
import java.util.List;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class CopyFromBackup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        List<Capture> pages = dao.getErrorPages(1L, 1900000L);
        try {
            for (Capture page : pages) {
                Capture batch = page.getCapture().getCapture();
                File unzipFile = new File("G:/unzip/"+batch+".zip");
                 
                if(!unzipFile.exists()){
                    Unzip unzip = new Unzip();
                    unzip.unzip("G:/13052012/"+batch.getId()+".zip","G:/unzip/"+batch.getId());
                }
                
                String sourcePage = "G:/unzip/" + batch.getId() + "/" + page.getPath();
                File destPage = new File("W:/scan/" + batch.getId() + "/" + page.getPath());
                File f = new File(sourcePage);
                destPage.createNewFile();
                if (!destPage.exists() && f.exists()) {
                    InputStream in = new FileInputStream(f);
                    System.out.println(batch.getId() + "   " + page.getId() + "/" + page.getPath());
                    // note the different format
                    //File file = new File(path); // note the different format

                    OutputStream out = new FileOutputStream(destPage);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();
                    //             }

                    CompressToTiff service = new CompressToTiff();
                //    System.out.println(imgCap.getCapture().getCapture().getId());
                    service.getCompressToTiffSoap().compressImage(batch.getId() + "", f.getName());
                    //   f.delete();

                    page.setRefuseNote("");
                    dao.attachDirty(page);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
