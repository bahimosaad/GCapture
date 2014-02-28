/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CheckPages {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        List<Capture> pages = dao.getAllPages(0L);
        for(Capture page:pages){
            if(!page.getDeleted()){
                String path = "W:/ytech/gip/"+page.getCapture().getCapture()+"/"+page.getPath();
                File pageFile = new File(path);
                if(!pageFile.exists()){
                    System.out.println(page.getCapture().getCapture()+"    "+page.getName());
                }
            }
        }
    }
}
