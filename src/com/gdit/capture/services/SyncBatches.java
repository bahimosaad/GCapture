/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import com.gdit.capture.entity.Disk;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.tempuri.CompressToTiff;

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
        try {
            CaptureHome dao = new CaptureHome();
//            CategoryHome catDao = new CategoryHome();
//            Category cat = catDao.findById(141L);
            List<Capture> batches = dao.getAllBatches(4);
            CompressToTiff soap = new CompressToTiff();
            
            for (Capture batch : batches) {
                Category category = batch.getCategory();
                Disk disk = batch.getDisk();
                File scan = null;
                File view = null;
                
                 if (!category.isCreateFolder()) {
                       
                        scan = new File(disk.getPath() + "/scan/" + batch.getId());
                        view = new File(disk.getPath() + "/view/" + batch.getId());
                        
                    } else {
                        scan = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId());
                        view = new File(disk.getPath() + "/" + category.getId() + "/view/" + batch.getId());
                       
                    }
                
                 if (scan.exists() && view.exists()) {
                    if (scan.list().length != view.list().length) {
                        System.out.println(batch.getId() + "   " + scan.listFiles().length + "   " + view.listFiles().length);
                        for (String page : scan.list()) {
                            File pageFile = new File(view, page);
                            if (!pageFile.exists()) {
                                soap.getCompressToTiffSoap().compressImageFullPath(scan.getAbsolutePath(),
                                        view.getAbsolutePath(), page);
                                System.out.println(pageFile.getAbsolutePath());
                            }
                        }
                    }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
