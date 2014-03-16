/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.Disk;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Administrator
 */
public class SearchFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        List<Capture> batches = dao.getMissedBatches();
        for (Capture batch : batches) {
//            System.out.println(batch.getId());
            Disk disk = batch.getDisk();
            Category cat = batch.getCategory();

            File dest = null;
            if (!cat.isCreateFolder()) {
                dest = new File(disk.getPath() + "/scan/" + batch.getId());
            } else {
                dest = new File(disk.getPath() + "/" + cat.getId() + "/scan/" + batch.getId());
            }


            File source = null;
            File file1 = new File("H:\\scan1\\28092013\\" + batch.getId());
            File file2 = new File("H:\\scan2\\28092013\\" + batch.getId());
            File file3 = new File("H:\\scan3\\28092013\\" + batch.getId());
            File file4 = new File("H:\\scan4\\28092013\\" + batch.getId());
            File file6 = new File("H:\\scan6\\28092013\\" + batch.getId());

            if (file1.exists()) {
                source = file1;
            } else if (file2.exists()) {
                source = file2;
            } else if (file3.exists()) {
                source = file3;
            } else if (file4.exists()) {
                source = file4;
            } else if (file6.exists()) {
                source = file6;
            }

            if (source != null) {
                if (dest.list().length < source.list().length) {
                    System.out.println(dest + "   " + source.getAbsolutePath());

                    try {
                        FileUtils.copyDirectory(source, dest);
                    } catch (IOException ex) {
                        Logger.getLogger(SearchFile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    batch.setCopyStatus(1);
                    dao.merge(batch);

                }
            }


        }
    }
}
