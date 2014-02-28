/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import com.gdit.capture.entity.Server;
import com.gdit.capture.entity.ServerHome;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;

/**
 *
 * @author admin
 */
public class SyncQaDir {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException,
            SmbException, MalformedURLException, FileNotFoundException, IOException {
        try {
            // TODO code application logic here
            ServerHome serverHome = new ServerHome();
            Server server = serverHome.getAllServer().get(0);
            Rep rep = new RepHome().getAllRep().get(0);

            File qaDir = new File("D:/.qa");
            for (File batchFile : qaDir.listFiles()) {

                String batch = batchFile.getName();
                if (batchFile.isDirectory()) {
                    for (File f : batchFile.listFiles()) {
                        if (f.getName().endsWith(".tif")) {
                            NtlmPasswordAuthentication authentication =
                                    new NtlmPasswordAuthentication(server.getDomain(), server.getUser(), server.getPwd());
                            UniAddress domain = UniAddress.getByName(server.getIp());
                            SmbSession.logon(domain, authentication);
                            //   String pagePath = "D:/.QA/" + batch + "/" + page.getPath();
                            //      File localFile = new File(pagePath);
                            //File pageFile = new File("W:/Scan/" + batch.getId() + "/" + page.getPath()); 
                            SmbFile parentFile = new SmbFile("smb://" + server.getIp() + "/" + "W$/scan" + "/" + batch, authentication);
//                        if (!parentFile.exists()) {
//                            parentFile.mkdirs();
//                        }

                            String path = "W$/scan" + "/" + batch + "/" + f.getName();
                            String fullPath = "smb://" + server.getIp() + "/" + path;
                            SmbFile pageFile = new SmbFile(fullPath, authentication);
                            if (pageFile.length() <= (5 * 1024)) {
                                //   File f = new File(pagePath);
                                InputStream in = new FileInputStream(f);
                                System.out.println(batch + "/" + f.getName());
                                // note the different format
                                //File file = new File(path); // note the different format
                                if (!pageFile.exists()) {
                                    pageFile.createNewFile();
                                }
                                OutputStream out = pageFile.getOutputStream();
                                byte[] buf = new byte[1024];
                                int len;
                                while ((len = in.read(buf)) > 0) {
                                    out.write(buf, 0, len);
//                            }

                                    in.close();
                                    out.close();
                                    //             }

//                        CompressToTiff service = new CompressToTiff();
//                        // System.out.println(imgCap.getCapture().getCapture().getId());
//                        service.getCompressToTiffSoap().compressImage(batch.getId() + "", f.getName());
                                    //   f.delete();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
