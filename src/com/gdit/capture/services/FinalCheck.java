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
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class FinalCheck {

    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        //  ExecutorService pool = Executors.newFixedThreadPool(20);
        try {
            while (true) {
                List<Capture> batches = dao.getAllBatches( 2);

                CompressToTiff soap = new CompressToTiff();
                for (Capture batch : batches) {
                    System.out.println(batch.getId());
                    Category category = batch.getCategory();
                    Disk disk = batch.getDisk();
                    String scan = "";
                    String view = "";
                    File scanFile;
                    File viewFile = null; 
                    if (!category.isCreateFolder()) {
                        scan = disk.getPath() + "/scan/" + batch.getId();
                        view = disk.getPath() + "/view/" + batch.getId();
                        scanFile = new File(disk.getPath() + "/scan/" + batch.getId());
                        viewFile = new File(disk.getPath() + "/view/" + batch.getId()); 
                    } else {
                        scan = disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId();
                        view = disk.getPath() + "/" + category.getId() + "/view/" + batch.getId();
                        scanFile = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId());
                        viewFile = new File(disk.getPath() + "/" + category.getId() + "/view/" + batch.getId());

                    }
                    List<Capture> pages = dao.getBatchPages(batch);
                    if (pages == null || pages.size() < 2) {
                        continue;
                    }

                    if (!viewFile.exists() || pages.size() != scanFile.listFiles().length
                            || viewFile.listFiles().length != scanFile.listFiles().length) {
                        for (Capture doc : batch.getCaptures()) {
                            for (Capture page : doc.getCaptures()) {

                                File scanPage;
                                File viewPage;

                                if (!category.isCreateFolder()) {
                                    scanPage = new File(disk.getPath() + "/scan/" + batch.getId() + "/" + page.getPath());
                                    viewPage = new File(disk.getPath() + "/view/" + batch.getId() + "/" + page.getPath());

                                } else {
                                    scanPage = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId() + "/" + page.getPath());
                                    viewPage = new File(disk.getPath() + "/" + category.getId() + "/view/" + batch.getId() + "/" + page.getPath());

                                }

                                page.setRefuseNote("ERROR");
                                dao.attachDirty(page);

                                if (!viewPage.exists()) {

                                    if (scanPage.exists()) {
                                        System.out.println(page.getId() + " NOT FOUND IN  VIEW");

                                        soap.getCompressToTiffSoap().compressImageFullPath(scan, view, page.getPath());
                                    }
                                }

                            }
                        }

                    }
                    batch.setCopyStatus(1);
                    dao.attachDirty(batch);

                }


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CaptureHome.close();
        }
    }
}
