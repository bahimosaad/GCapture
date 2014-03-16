/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.Disk;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import java.io.File;
import java.util.List;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author Administrator
 */
public class NewMain1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TesseractException {
        // TODO code application logic here
         CaptureHome dao = new CaptureHome();
        RepHome repHome = new RepHome();
        Rep rep = repHome.findById(new Short("203"));
        List<Capture> batches = dao.getMissedSeperated(rep, 4,1);
        for(Capture batch:batches){
//             batch.setBarcoded(true);
//                dao.attachDirty(batch);
                System.out.println(batch.getId());
                List<Capture> pages = dao.getBatchUnDeletedPages(batch);
                Category category = batch.getCategory();
                Disk disk = batch.getDisk();
                String path = "";
                if (category.isCreateFolder()) {
                    path = disk.getPath() + "/" + category.getId() + "/scan/" + batch.getId() + "/";
                } else {
                    path = disk.getPath() + "/scan/" + batch.getId() + "/";
                }
                Capture doc = null;
                for (Capture page : pages) {
                    File file = new File(path + "/" + page.getPath());
                    String result = Tesseract.getInstance().doOCR(file);
                    //Empty page!!
                    if (result.equals("") || result.contains("Empty")) {
                        page.setType(2);
                        page.setCapture(batch);
                        page.setName("DOC");
                        page.setBlancked(true);
                        dao.attachDirty(page);
                        doc = page;
                       System.out.print("\n" + page.getId() + "    BLANK");
                    } else {
                        System.out.print("\n" + page.getName());
                        if (doc != null) {
                            page.setCapture(doc);
                            dao.attachDirty(page);
                        }

                    }
                }
        }
    }
}
