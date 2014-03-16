/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.test;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.CategoryHome;
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
        CategoryHome catDao = new CategoryHome();
        Category cat = catDao.findById(141L);
        List<Capture> batches = dao.getAllBatches(cat, 4);
        for(Capture batch :batches){
            List<Capture> docs = dao.getIndexedDocs(batch,4);
            if(docs.size() > 10){
                for(Capture doc:docs){
                    if(doc.getCaptures()!=null && doc.getCaptures().size() >0){
                        for(Capture page:doc.getCaptures()){
                            if(page.getType()==3){
                                System.out.println(batch.getId()+"  "+doc.getId());
                            }
                        }
                        
                    }
                }
            }
        }
    }
}
