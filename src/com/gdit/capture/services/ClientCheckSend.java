/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import com.gdit.capture.entity.Computers;
import com.gdit.capture.entity.ComputersHome;
import com.gdit.capture.entity.Disk;
import com.gdit.capture.entity.DiskHome;
import com.gdit.capture.run.BatchObserver;
import java.io.File;
import java.net.InetAddress;
import java.util.List;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class ClientCheckSend {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            CaptureHome dao = new CaptureHome();
            ComputersHome computerDao = new ComputersHome();
            DiskHome diskDao = new DiskHome();
            String computername = InetAddress.getLocalHost().getHostName();
            Computers computer = computerDao.findByName(computername.toUpperCase());
            Disk disk = diskDao.getAllDisk().get(0);
            //       do {
            List<Capture> docs = dao.getComputerUnBarcodedDocs(computer);
            for (Capture doc : docs) {
                System.out.println(doc.getCapture().getId());
                File tof = new File(disk.getPath() + "/" + doc.getCapture().getId());
                if (tof == null || tof.list() == null || tof.list().length <= 1) {
                    org.apache.commons.io.FileUtils.copyDirectory(new File(computer.getScanPath() + "/" + doc.getCapture().getId()), tof);
                    CompressToTiff service = new CompressToTiff();
                    service.getCompressToTiffSoap().compressFolderFullPath(disk.getPath() + "/" + doc.getCapture().getId(),
                            disk.getViewPath() + "/" + doc.getCapture().getId());
                    System.out.println(doc.getCapture().getId() + "  " + tof.getAbsolutePath());
                }

            }
            //           }while(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
