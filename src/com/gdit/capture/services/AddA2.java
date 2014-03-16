/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.util.List;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class AddA2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        File file = new File("G:/A2");
        CaptureHome dao = new CaptureHome();
        
        for(File pFile:file.listFiles()){
            String[] names = pFile.getName().split("\\.");
            String barcode = names[0];
            List<Capture> docs = dao.findByBrcode(barcode); 
            if(docs.size() == 1){
                Capture batch = docs.get(0).getCapture();
                List<Capture> pages = dao.getBatchPages(batch);
                Capture page = new Capture();
                page.setCapture(docs.get(0));
                page.setStatus(4);
                page.setType(3);
                page.setName("page"+pages.size()+1);
                page.setPath(pFile.getName());
                page.setRefuseNote("A2");
                
                dao.persist(page);
                
                 CompressToTiff soap = new CompressToTiff();
                 String path = batch.getDisk().getViewPath().replace("$", ":");
                 soap.getCompressToTiffSoap().compressImageFullPath("G:/A2",path+"/"+batch.getId() , pFile.getName());
                 System.out.println(names[0]+"      "+batch.getId()+"       "+pages.size());
            }
            else if(docs.size() > 1){
                 System.out.println(names[0]+"      Double");
            }
            else{
                 System.out.println(names[0]+"      NOTDOUND");
            }
            //System.out.println(names[0]);
        }
//        CompressToTiff soap = new CompressToTiff();
//        int result =  soap.getCompressToTiffSoap().compressImageFullPath("G:/A2", "G:/a", "22618.2");
        
    }
}
