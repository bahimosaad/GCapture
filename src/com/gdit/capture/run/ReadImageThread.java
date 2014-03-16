/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.run;

import com.asprise.util.tiff.TIFFReader;
import com.asprise.util.tiff.TIFFWriter;
import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import com.gdit.capture.entity.Server;
import com.gdit.capture.entity.ServerHome;
import com.gdit.image.ImageLoader;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;

/**
 *
 * @author admin
 */
public class ReadImageThread extends Thread {

    private Capture batch;
    private Capture page;
    private String path;
    private BufferedImage bimg;

    @Override
    public void run() {
        FileOutputStream fout = null;
        FileInputStream fin = null;
        try {
            File imgFile = new File(path);
            ServerHome serverHome = new ServerHome();
            Server server = serverHome.getAllServer().get(0);

            if (!imgFile.exists()) {
                Rep rep = new RepHome().getAllRep().get(0);
                if (!imgFile.getParentFile().exists()) {
                    imgFile.getParentFile().mkdirs();
                }
                /*****************************************************************************************/
//                ImageLoader imgLoader = new ImageLoader(rep.getPath() + "/" + batch.getId(), page.getPath(), page.getPath());
//                bimg = imgLoader.getBimage();
                NtlmPasswordAuthentication authentication =
                        new NtlmPasswordAuthentication(server.getDomain(), server.getUser(), server.getPwd());
                UniAddress domain = UniAddress.getByName(server.getIp());
                SmbSession.logon(domain, authentication);
                // SmbFile parentFile = new SmbFile("smb://" + server.getIp() + "/"+"W$/scan" + "/" + batch,authentication);
                String path = "w$/ytech/gip/" +  batch + "/" + imgFile.getName();
                String fullPath = "smb://" + server.getIp() + "/" + path;

                SmbFile pageFile = new SmbFile(fullPath, authentication);
               // File f = new File(page);
                //InputStream in = new FileInputStream(path);
               // System.out.println(batch.getId() + "/" + page.getPath());
                // note the different format
                //File file = new File(path); // note the different format
//                if (!pageFile.exists()) {
//                    pageFile.createNewFile();
//                }
                InputStream in = pageFile.getInputStream();
                
                OutputStream out = new FileOutputStream(imgFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                in.close();
                out.close();
                /*****************************************************************************************/
//                TIFFReader reader = new TIFFReader(new File("C:/.capture/141/" + batch.getId() + "/" + page.getPath()));
//                bimg = ImageGenerator.createBufferedImage((Image) reader.getPage(0));
//                fout = new FileOutputStream(pageFile);
//                fin = new FileInputStream(new File("C:/.capture/141/" + batch.getId() + "/" + page.getPath()));
//
//                byte[] buf = new byte[1024];
//                int len;
//                while ((len = fin.read(buf)) > 0) {
//                    fout.write(buf, 0, len);
//                }
                /******************************************************************************************/
//                TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg},
//                        TIFFWriter.TIFF_CONVERSION_TO_GRAY,
//                        TIFFWriter.TIFF_COMPRESSION_DEFLATE,
//                        new File(path));
                /*******************************************************************************************/
            }
        } catch (Exception ex) {
            Logger.getLogger(ReadImageThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RepHome.close();
            try {
                fin.close();
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadImageThread.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
    }

    public Capture getBatch() {
        return batch;
    }

    public void setBatch(Capture batch) {
        this.batch = batch;
    }

    public Capture getPage() {
        return page;
    }

    public void setPage(Capture page) {
        this.page = page;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BufferedImage getBimg() {
        return bimg;
    }

    public void setBimg(BufferedImage bimg) {
        this.bimg = bimg;
    }
}
