/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DeleteEmptyDocs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            CaptureHome dao = new CaptureHome();
            List<Capture> docs = dao.getDocs(1L, 100000L);
            for (Capture doc : docs) {
                System.out.println(doc.getId());
                if (doc.getCaptures().isEmpty()) {
                    // System.out.println(doc.getId()+"   DELETED");
                     doc.setDeleted(true);
                     dao.merge(doc);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CaptureHome.close();
        }
    }
}
