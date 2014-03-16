/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NewMain2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         CaptureHome dao = new CaptureHome();
        CategoryHome catHome = new CategoryHome();
        Category cat = catHome.findById(141L);
        List<Capture> batches = dao.getAllBatches(cat, 4);
        for(Capture batch:batches){ 
            List<Capture> docs  = dao.getIndexedDocs(batch, 4);
             System.out.println(batch.getId()+"     "+docs.size());
             if(docs.size() <=10){
                 for (Capture doc : docs) {
                     System.out.println("    " + doc.getPath() + "      " + doc.getCaptures().size());
                     if(doc.getCaptures()!=null && doc.getCaptures().size() <=0){
                         doc.setDeleted(true);
                         dao.merge(doc);
                     }
                 }
             }
        }
    }
}
