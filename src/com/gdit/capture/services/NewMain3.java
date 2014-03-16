/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import com.gdit.capture.entity.Computers;
import com.gdit.capture.entity.Disk;
import com.gdit.capture.utils.FindBackupFile;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class NewMain3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        CaptureHome dao = new CaptureHome();
//        CategoryHome catHome = new CategoryHome();
//        Category c = catHome.findById(101L);
        List<Capture> batches = dao.getAllBatches(2, 5823072L);
//        List<Capture> batches = dao.getUnBarcoded(c);
        CompressToTiff soap = new CompressToTiff();

        for (Capture batch : batches) {

            System.out.println(batch.getId());
            try {
                Category category = batch.getCategory();
                Disk disk = batch.getDisk();
                Computers computer = batch.getComputer();
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

                if (!scanFile.exists() || scanFile.list() == null) {
                    scanFile.mkdir();
                }

                if(!viewFile.exists()){
                    viewFile.mkdirs();
                }
                if(scanFile.list().length!=viewFile.list().length){
                    System.out.println("NOT FOUND IN VIEW");
                    soap.getCompressToTiffSoap().compressFolderFullPath(scanFile.getAbsolutePath(),
                                viewFile.getAbsolutePath());
                }
                if (pages.size() == scanFile.list().length) {
                    System.out.println("OK");
                    continue;
                }

                if (computer == null) {
                    continue;
                }
                String computerName = computer.getName();

                File path1 = new File("G:/" + computerName);
                File path2 = new File("H:/" + computerName);
                for (String folder : path1.list()) {
                    if ((new File(path1.getAbsolutePath() + "/" + folder + "/" + batch.getId())).exists()) {
                        backUpFile = new File(path1.getAbsolutePath() + "/" + folder + "/" + batch.getId());
                        break;
                    }
                }
                if (backUpFile == null) {
                    for (String folder : path2.list()) {
                        if ((new File(path2.getAbsolutePath() + "/" + folder + "/" + batch.getId())).exists()) {
                            backUpFile = new File(path2.getAbsolutePath() + "/" + folder + "/" + batch.getId());
                            break;
                        }
                    }
                }

                if (!scanFile.exists() || scanFile.listFiles().length == 0) {
                    System.out.println(batch.getId() + " SCAN FOLDER IS EMPTY ");
                    if (backUpFile!=null && backUpFile.exists()) {
                        FileUtils.copyDirectory(backUpFile, scanFile);
                        soap.getCompressToTiffSoap().compressFolderFullPath(scanFile.getAbsolutePath(),
                                viewFile.getAbsolutePath());
                    } else {
                        batch.setRefuseNote("MISSED");
                        // batch.setLocked(true);
                          dao.merge(batch);
                    }
                    continue;
                }
// 5898909 5916076  5920172
//           
//            if (!viewFile.exists() || pages.size() != scanFile.listFiles().length
//                    || viewFile.listFiles().length != scanFile.listFiles().length) {
//                for (Capture doc : batch.getCaptures()) {
//                    for (Capture page : doc.getCaptures()) {
//
//                        File scanPage;
//                        File viewPage;
//                        File localPage;
//
//                        if (!category.isCreateFolder()) {
//                            scanPage = new File(disk.getPath() + "/scan/" + batch.getId() + "/" + page.getPath());
//                            viewPage = new File(disk.getPath() + "/view/" + batch.getId() + "/" + page.getPath());
//                            localPage = new File(backUpFile, page.getPath());
//                        } else {
//                            scanPage = new File(disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId() + "/" + page.getPath());
//                            viewPage = new File(disk.getPath() + "/" + category.getId() + "/view/" + batch.getId() + "/" + page.getPath());
//                              localPage = new File(backUpFile, page.getPath());
//                        }
//
//                      
//
//                        if (!viewPage.exists()) {
//
//                            if (scanPage.exists()) {
//                                System.out.println(page.getId() + " NOT FOUND IN  VIEW");
//
//                                soap.getCompressToTiffSoap().compressImageFullPath(scanFile.getAbsolutePath(), viewFile.getAbsolutePath(), page.getPath());
//                            } else if (localPage.exists()) {
//                                System.out.println(page.getId() + " NOT FOUND IN  SCAN");
//                                FileUtils.copyFile(localPage, scanPage);
//                                //    CompressToTiff soap = new CompressToTiff();
//                                soap.getCompressToTiffSoap().compressImageFullPath(scanFile.getAbsolutePath(), viewFile.getAbsolutePath(), page.getPath());
//                            } 
////                            else if (nativePage.exists()) {
////                                System.out.println(page.getId() + "  FOUND IN  Native");
////                                FileUtils.copyFile(nativePage, scanPage);
////                                //           CompressToTiff soap = new CompressToTiff();
////                                soap.getCompressToTiffSoap().compressImageFullPath(scan, view, page.getPath());
////                            }
//                        }
//
//                    }
//                }

//            }

            } catch (Exception ex) {
                ex.printStackTrace();
                batch.setRefuseNote("MISSED");
                dao.merge(batch);
            }
        }


    }
}
