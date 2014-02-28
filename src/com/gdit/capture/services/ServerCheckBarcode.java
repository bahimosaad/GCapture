/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Disk;
import com.gdit.capture.entity.DiskHome;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class ServerCheckBarcode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        DiskHome diskDao = new DiskHome();
         Disk disk = diskDao.getAllDisk().get(0);
        while (true) {
            List<Capture> batches = dao.getUnBarcoded(1L); 
            for (Capture batch : batches) {
                File from = new File("\\\\" + batch.getComputer().getName() + "\\141\\" + batch.getId());
                File to = new File(batch.getDisk().getPath() + "\\" + batch.getId());

                if (from.exists() && (to.listFiles() == null || to.listFiles().length <= 1)) {
                    try {
                        org.apache.commons.io.FileUtils.copyDirectory(from, to);
                        CompressToTiff service = new CompressToTiff();
                        service.getCompressToTiffSoap().compressFolderFullPath(disk.getPath() + "/" + batch.getId(),
                                disk.getViewPath() + "/" + batch.getId());
                        System.out.println(batch.getId() + "  " + to.getAbsolutePath());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ServerCheckBarcode.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
