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
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class UndeleteBatches {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        List<Capture> batches = dao.deletedBatches();
        CompressToTiff soap = new CompressToTiff();

        for (Capture batch : batches) {
            Disk disk = batch.getDisk();
            String path = disk.getPath().replace("$", ":");
            String viewPath = disk.getViewPath().replace("$", ":");
            File bFile = new File(path + "/" + batch.getId());
            if (bFile.exists() && bFile.list() != null && bFile.list().length > 1) {
                System.out.println(batch.getId() + "  " + bFile.list().length);
                soap.getCompressToTiffSoap().compressFolderFullPath(path + "/" + batch.getId(), viewPath + "/" + batch.getId());
                batch.setDeleted(false);
                dao.attachDirty(batch);
            }
        }

    }
}
