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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.FileUtils;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class CheckPage {

    //864929
    
    /**
     * @param args the command line arguments
     *///
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        //  ExecutorService pool = Executors.newFixedThreadPool(20);
        try {
            while (true) {
//                List<Capture> batches = dao.getUnSavedBatches();
                List<Capture> batches = dao.getUnSavedBatches();
                //555122
                //555633 //864929
                //547239
                //562536
                CompressToTiff soap = new CompressToTiff();
                for (Capture batch : batches) {
                    System.out.println(batch.getId());
                    Category category = batch.getCategory();
                    Disk disk = batch.getDisk();
                    String scan = "";
                    String view = "";
                    File scanFile;
                    File viewFile;
                    File nativeFile;
                    File localFile;

                    if (!category.isCreateFolder()) {
                        scan = disk.getPath() + "/scan/" + batch.getId();
                        view = disk.getPath() + "/view/" + batch.getId();
                        scanFile = new File(disk.getPath() + "/scan/" + batch.getId());
                        viewFile = new File(disk.getPath() + "/view/" + batch.getId());
                        nativeFile = new File("\\\\" + batch.getComputer().getName() + "/141/" + batch.getId());
                        localFile = new File("G:/" + batch.getComputer().getName() + "/01072013/" + batch.getId());

                    } else {
                        scan = disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId();
                        view = disk.getPath() + "/" + category.getId() + "/view/" + batch.getId();
                        scanFile = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId());
                        viewFile = new File(disk.getPath() + "/" + category.getId() + "/view/" + batch.getId());
                        nativeFile = new File("\\\\" + batch.getComputer().getName() + "/141/" + batch.getId());
                        localFile = new File("G:/" + batch.getComputer().getName() + "/01072013/" + batch.getId());

                    }


                    List<Capture> pages = dao.getBatchPages(batch);

                    if (pages == null || pages.size() < 2) {
                        continue;
                    }
                    if (!scanFile.exists() || scanFile.listFiles().length == 0) {
                        System.out.println(batch.getId() + " SCAN FOLDER IS EMPTY ");
                        if (localFile.exists()) {
                            FileUtils.copyDirectory(localFile, scanFile);
                            soap.getCompressToTiffSoap().compressFolderFullPath(scan, view);
                        } else if (nativeFile.exists()) {
                            FileUtils.copyDirectory(nativeFile, scanFile);
                            soap.getCompressToTiffSoap().compressFolderFullPath(scan, view);
                        } else {
                            batch.setRefuseNote("MISSED");
                            // batch.setLocked(true);
                            //  dao.attachDirty(batch);
                        }
                        continue;

                    }
                    if (!viewFile.exists() || pages.size() != scanFile.listFiles().length
                            || viewFile.listFiles().length != scanFile.listFiles().length) {
                        for (Capture doc : batch.getCaptures()) {
                            for (Capture page : doc.getCaptures()) {

                                File scanPage;
                                File viewPage;
                                File nativePage;
                                File localPage;

                                if (!category.isCreateFolder()) {
                                    scanPage = new File(disk.getPath() + "/scan/" + batch.getId() + "/" + page.getPath());
                                    viewPage = new File(disk.getPath() + "/view/" + batch.getId() + "/" + page.getPath());
                                    nativePage = new File("\\\\" + batch.getComputer().getName() + "/141/" + batch.getId() + "/" + page.getPath());
                                    localPage = new File("G:/" + batch.getComputer().getName() + "/01072013/" + batch.getId() + "/" + page.getPath());
                                } else {
                                    scanPage = new File(disk.getPath()+"/"+category.getId()+ "/scan/" + batch.getId() + "/" + page.getPath());
                                    viewPage = new File(disk.getPath()+"/"+category.getId()+"/view/" + batch.getId() + "/" + page.getPath());
                                    nativePage = new File("\\\\" + batch.getComputer().getName() + "/141/" + batch.getId() + "/" + page.getPath());
                                    localPage = new File("G:/" + batch.getComputer().getName() + "/01072013/" + batch.getId() + "/" + page.getPath());
                                }



                                page.setRefuseNote("ERROR");
                                dao.attachDirty(page);

                                if (!viewPage.exists()) {

                                    if (scanPage.exists()) {
                                        System.out.println(page.getId() + " NOT FOUND IN  VIEW");

                                        soap.getCompressToTiffSoap().compressImageFullPath(scan, view, page.getPath());
                                    } else if (localPage.exists()) {
                                        System.out.println(page.getId() + " NOT FOUND IN  SCAN");
                                        FileUtils.copyFile(localPage, scanPage);
                                        //    CompressToTiff soap = new CompressToTiff();
                                        soap.getCompressToTiffSoap().compressImageFullPath(scan, view, page.getPath());
                                    } else if (nativePage.exists()) {
                                        System.out.println(page.getId() + "  FOUND IN  Native");
                                        FileUtils.copyFile(nativePage, scanPage);
                                        //           CompressToTiff soap = new CompressToTiff();
                                        soap.getCompressToTiffSoap().compressImageFullPath(scan, view, page.getPath());
                                    }
                                }

                            }
                        }

                    }
                    batch.setSaved(true);
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
