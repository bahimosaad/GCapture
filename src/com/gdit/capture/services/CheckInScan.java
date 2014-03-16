/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.model.CaptureStatus;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CheckInScan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao =new CaptureHome();
        List<Capture> batches = dao.getAllBatches(1);
        for(Capture batch:batches){
            List<Capture> pages = dao.getBatchPages(batch);
            if(pages.size()!=0){
                batch.setStatus(CaptureStatus.QAMode);
                dao.merge(batch);
                System.out.println(batch.getId()+"      "+pages.size());
            }
        }
    }
}
