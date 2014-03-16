/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import de.nava.informa.utils.FileUtils;
import java.io.File;
import org.hsqldb.lib.FileUtil;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class CheckPageThread extends Thread {

    /**
     * @param args the command line arguments
     */
    private Capture page;
    private String path;
    private CaptureHome dao;

    @Override
    public void run() {

        dao = new CaptureHome();
        try {
            Capture batch = page.getCapture().getCapture();
            if(batch == null || batch.getComputer()==null){
                System.out.println("NULL   "+page.getId());
                return;
            }
            String scan = "\\\\192.168.1.3/scan/" + batch.getId();
            String view = "\\\\192.168.1.3/scan/" + batch.getId();
            File scanPage = new File("\\\\192.168.1.3/scan/" + batch.getId() + "/" + page.getPath());
            File viewPage = new File("\\\\192.168.1.3/scan/" + batch.getId() + "/" + page.getPath());
            File localPage = new File("\\\\" + batch.getComputer().getName() + "/141/" + batch.getId() + "/" + page.getPath());
            if (!viewPage.exists()) {
                page.setRefuseNote("ERROR");
//                dao.commit();
                if (scanPage.exists()) {
                    System.out.println(page.getId() + " NOT FOUND IN  VIEW");
                    CompressToTiff soap = new CompressToTiff();
                    soap.getCompressToTiffSoap().compressImageFullPath(scan, view, page.getPath());
                } else {
                    System.out.println(page.getId() + " NOT FOUND IN  VIEW");
                    FileUtils.copyFile(localPage, scanPage);
                    CompressToTiff soap = new CompressToTiff();
                    soap.getCompressToTiffSoap().compressImageFullPath(scan, view, page.getPath());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CaptureHome.close();
        }

    }

    public Capture getPage() {
        return page;
    }

    public void setPage(Capture batch) {
        this.page = batch;
    }

    public CaptureHome getDao() {
        return dao;
    }

    public void setDao(CaptureHome dao) {
        this.dao = dao;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
}
