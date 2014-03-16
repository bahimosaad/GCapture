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
public class CheckSeperatedDocs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CaptureHome dao = new CaptureHome();
        List<Capture> docs = dao.getEmptySeperatedDocs();
        for (Capture doc : docs) {
            String name = doc.getPath();
            String p = name.split("\\.")[0];
            String nmbr = p.split("page")[1];
            int x = Integer.parseInt(nmbr);
            String pgName = "page" + (x + 1)  ;
            Capture page = dao.findByNameAndBatch(pgName, doc.getCapture());
            if (page != null) {
                page.setCapture(doc); 
                dao.merge(page);
                System.out.println(doc.getPath()+"      "+ page.getName());
            }
            else{
                Capture newPage = new Capture();
                newPage.setCapture(doc);
                newPage.setCategory(doc.getCategory());
                newPage.setComputer(doc.getComputer());
                newPage.setCreatedDate(doc.getCreatedDate());
                newPage.setDeleted(false);
                newPage.setDisk(doc.getDisk());
                newPage.setLocked(false);
                newPage.setName(pgName);
                newPage.setPath(pgName+".tif");
                newPage.setRep(doc.getRep());
                newPage.setType(3);
                newPage.setUsers(doc.getUsers());
                
                dao.persist(newPage);
                 System.out.println(doc.getPath()+"      "+newPage.getName()+"    new");
            }
        }


        CaptureHome.close();

    }
}
