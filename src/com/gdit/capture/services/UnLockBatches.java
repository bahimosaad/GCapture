/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Disk;
import java.io.File;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class UnLockBatches {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        List<Capture> batches = dao.getLockedBatches(2);
        for (Capture batch : batches) {
            Disk disk = batch.getDisk();
            File scanFile = new File(disk.getPath().replace("$", ":") + "/" + batch.getId());
            File viewFile = new File(disk.getViewPath().replace("$", ":") + "/" + batch.getId());

            if (viewFile.listFiles().length > 0) {
                System.out.println(batch.getId());
                batch.setRefuseNote("");
                batch.setLocked(false);
                dao.attachDirty(batch);
            }
        }
        CaptureHome.close();
    }
}
