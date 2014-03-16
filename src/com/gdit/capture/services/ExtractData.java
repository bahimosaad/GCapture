/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.*;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ExtractData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        EDcoumentsHome docHome = new EDcoumentsHome();
        EPagesHome     pageHome = new EPagesHome();
    //    CaptureDocumentHome cdHome = new CaptureDocumentHome();
       // List<Capture> docs = dao.getAllDocs();
        List<String> barcodes = dao.getAllBarcodes("1222222222","1");
        for(String  barcode :barcodes){
            if(isLong(barcode)){
                List<Capture> repeted = dao.findByBrcode(barcode);
                for(Capture d:repeted){
                    if(!d.getCaptures().isEmpty() && (!d.getDeleted() || d.getCaptures().size() >5)  )
                    {
                     //  CaptureDocument cd = cdHome.findById(Long.valueOf(barcode));
                        EDocuments edoc = new EDocuments();
                        edoc.setDocId(d.getId());
                        edoc.setBatchId(d.getCapture().getId());
                     //   edoc.setdId(cd.getId());
                        edoc.setBarcode(barcode);
                        
                        docHome.attachDirty(edoc);
                        for(Capture page:d.getCaptures()){
                            System.out.println(page.getId());
                            EPages epage = new EPages();
                            epage.setPageName(page.getPath());
                            epage.setBatchId(page.getCapture().getCapture().getId());
                            epage.setDocId(d.getId());
                            epage.setPageId(page.getId());
                            epage.setPath("Z:/YTECH/"+page.getCapture().getCapture().getId()+"/"+page.getPath());
//                            epage.setdId(d.getId());
                            
                            pageHome.attachDirty(epage);
                        }
                    }
                }
            }
        }
        
    }
    
    private static boolean isLong(String barcode){
        try{
         Long val = Long.parseLong(barcode);
         return true;
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
