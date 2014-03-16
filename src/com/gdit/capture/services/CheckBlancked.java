/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CheckBlancked {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        
        CategoryHome catHome = new CategoryHome();
        Category cat = catHome.findById(141l);
        List<Capture> batches = dao.getAllBatches(cat, 2);
        for (Capture batch : batches) {
            if (batch.getCaptures().size() > 1) {
                Capture doc = dao.findById(batch.getId() + 1);
                if (doc != null) {
                    for (Capture page : doc.getCaptures()) {
                        String nmbr = page.getName().split("page")[1];
                        int x = Integer.parseInt(nmbr);
                        String preName = "page" + (x - 1);
                        Capture previous = dao.findByNameAndBatch(preName, batch);
                        if (previous != null) {
                            page.setCapture(previous.getCapture());
                            dao.merge(page);
                            System.out.println(batch.getId() + "      " + page.getName() + "     " + previous.getName());
                        }
                    }
                }
            }
        }
    }
}
