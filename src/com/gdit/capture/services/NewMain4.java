/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.Computers;
import com.gdit.capture.entity.Disk;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class NewMain4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {
            CaptureHome dao = new CaptureHome();
            List<Capture> batches = dao.getBatchesNullComputeres(2);
            CompressToTiff soap = new CompressToTiff();
            for (Capture batch : batches) {
                System.out.println(batch.getId());
                Category category = batch.getCategory();
                Disk disk = batch.getDisk();

                File scanFile = null;
                File viewFile = null;
                File backUpFile = null;
                List<Capture> pages = dao.getBatchPages(batch);
                if (pages == null || pages.size() < 2) {
                    continue;
                }

                if (!category.isCreateFolder()) {
                    scanFile = new File(disk.getPath() + "/scan/" + batch.getId());
                    viewFile = new File(disk.getPath() + "/view/" + batch.getId());
                } else {
                    scanFile = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId());
                    viewFile = new File(disk.getPath() + "/" + category.getId() + "/view/" + batch.getId());
                }


                File path1 = new File("G:");
                File path2 = new File("H:");
                for (File folder : path1.listFiles()) {
                    if (folder.list() != null && folder.getName().contains("scan")) {
                        for (String backup : folder.list()) {
                            String path = path1.getAbsolutePath()  + folder.getName() + "/" + backup + "/" + batch.getId();
                            if ((new File(path)).exists()) {
                                backUpFile = new File(path1.getAbsolutePath() + "/" + folder.getName() + "/" + backup + "/" + batch.getId());
                                break;
                            }
                        }
                    }
                }
                if (backUpFile == null) {
                    for (File folder : path2.listFiles()) {
                        if (folder.list() != null && folder.getName().contains("scan")) {
                            for (String backup : folder.list()) {
                                String path = path2.getAbsolutePath()  + folder.getName() + "/" + backup + "/" + batch.getId();
                                if ((new File(path)).exists()) {
                                    backUpFile = new File(path2.getAbsolutePath() + "/" + folder.getName() + "/" + backup + "/" + batch.getId());
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!scanFile.exists() || scanFile.listFiles().length == 0) {
                    System.out.println(batch.getId() + " SCAN FOLDER IS EMPTY ");
                    if (backUpFile.exists()) {
                        FileUtils.copyDirectory(backUpFile, scanFile);
                        soap.getCompressToTiffSoap().compressFolderFullPath(scanFile.getAbsolutePath(),
                                viewFile.getAbsolutePath());
                    } else {
                        batch.setRefuseNote("MISSED");
                        // batch.setLocked(true);
                        //  dao.attachDirty(batch);
                    }
                    continue;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
