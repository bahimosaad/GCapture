/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services.importing;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Category;
import com.gdit.capture.entity.Disk;
import com.gdit.capture.entity.Rep;
import java.io.File;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CreqatePagesManual {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        Capture c = new Capture();
        
        //2997815  3300000
        //3300000  3600000
        //3600000  3900000
        //3900000
        
        List<Capture> batches = dao.getAllBatches(3600000L,3900000L);
        for (Capture batch : batches) {
            if (batch.getCaptures() != null && batch.getCaptures().size() > 0) {
                batch.setSaved(true);
                dao.merge(batch);
                System.out.println(batch.getName());
                continue;
            }
            Category cat = batch.getCategory();
            Rep rep = batch.getRep();
            Disk disk = batch.getDisk();
            Capture doc = new Capture();
            doc.setStatus(1);
            doc.setRep(rep);
            doc.setCategory(cat);
            doc.setCapture(batch);
            doc.setDisk(disk);
            dao.persist(doc);

            String scan = "";
            File scanFile;

            if (!cat.isCreateFolder()) {
                scan = disk.getPath() + "/scan/" + batch.getId();
                scanFile = new File(disk.getPath() + "/scan/" + batch.getId());
            } else {
                scan = disk.getPath() + "/" + cat.getId() + "/scan/" + batch.getId();
                scanFile = new File(disk.getPath() + "/" + cat.getId() + "/scan/" + batch.getId());
            }

            if (scanFile != null && scanFile.list() != null && scanFile.list().length > 0) {
                for (String name : scanFile.list()) {
                    if (name.contains("tif")) {
                        Capture page = new Capture();
                        page.setCapture(doc);
                        page.setPath(name);
                        page.setRep(rep);
                        page.setCategory(cat);
                        page.setDisk(disk);
                        page.setStatus(1);
                        dao.persist(page);
                        System.out.println(name);
                    }
                }
            }


            batch.setSaved(true);
            dao.merge(batch);

        }
    }
}
