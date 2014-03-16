/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        RepHome repHome = new RepHome();
        Rep rep = repHome.findById(new Short("203"));
        List<Capture> batches = dao.getMissedSeperated(rep, 4,10);
        for (Capture batch : batches) {
            System.out.println(batch.getId() + "     " + batch.getCaptures().size());
            List<Capture> docs = dao.getWrongSeperatedDocs(batch);
            for (Capture doc : docs) {
                int prev = 0;
                for (Capture page : doc.getCaptures()) {
                    String nmbr = page.getName().split("page")[1];
                    int x = Integer.parseInt(nmbr);
                    if (prev == 0) {
                        prev = x;
                        continue;
                    }
                    if (x != prev + 1) {
                        System.out.println(doc.getId() + "        " + page.getName());
                        Capture blankPage = dao.findByNameAndBatch("page"+(x-1), batch);
                        if(blankPage!=null){
                            System.out.println(blankPage.getCapture().getId()+"     "+blankPage.getCapture().getName());
                            blankPage.setType(2);
                            blankPage.setBlancked(true);
                            blankPage.setStatus(4);
                            blankPage.setName("DOC");
                            dao.merge(blankPage);
                            page.setCapture(blankPage);
                            dao.merge(page);
                        }
                        else{
                            blankPage = dao.findByNameAndParent("page"+prev, batch);
                             System.out.println(blankPage.getCapture().getId());
                        }
                    }
                    prev = x;
                }
            }

        }


    }
}
