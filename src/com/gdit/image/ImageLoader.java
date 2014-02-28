/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.image;

/**
 *
 * @author Bahi
 */
import com.asprise.util.tiff.TIFFReader;
import com.gdit.capture.entity.Server;
import com.gdit.capture.entity.ServerHome;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;

/**
 *
 * @author ehab
 */
public class ImageLoader {

    private String repProtocolPath;
    private String destPath;
    private String imageName;

    public ImageLoader(String repProtocolPath, String destPath, String imageName) {
        this.repProtocolPath = repProtocolPath;
        this.destPath = destPath;
        this.imageName = imageName;
    }

    private InputStream access(String path) {
        {
            InputStream in = null;
            try {
                ServerHome serverHome = new ServerHome();
                Server server = serverHome.getAllServer().get(0);
                NtlmPasswordAuthentication authentication =
                        new NtlmPasswordAuthentication(server.getDomain(), server.getUser(), server.getPwd());

                UniAddress domain = UniAddress.getByName(server.getIp());
                SmbSession.logon(domain, authentication);
                SmbFile file = new SmbFile("smb://" + server.getIp() + "/" + path, authentication);
//                  File file = new File(path);
//                FileInputStream fin = new FileInputStream(path);
                //              in = fin;
                in = file.getInputStream();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
//                try {
//                    in.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
            return in;
        }
    }

    public BufferedImage getBimage() {
        // FTPManger fTPManger = new FTPManger();
        BufferedImage bf = null;
        InputStream in = null;
        try {
            String src = repProtocolPath + "/" + imageName;
            File dest = new File(destPath + "/" + imageName);
           
            try {
                if (dest.exists()) {
                    if (imageName.endsWith(".tif")) {
                        TIFFReader reader = new TIFFReader(dest);
                        bf = (BufferedImage) reader.getPage(0);
                    } else {
                        bf = ImageIO.read(dest);
                    }
                } else {
                    // InputStream in = fTPManger.readInputStream(src);
                    if (src.endsWith("jpg")) {
                        //  bf = ImageIO.read(in);
                        bf = ImageIO.read(access(src));
                    } else if (src.endsWith("tif")) {
                        in = access(src);
                        TIFFReader reader = new TIFFReader(in);
                        bf = (BufferedImage) reader.getPage(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bf;
    }

//    public byte getImageBytes() {
//       // FTPManger fTPManger = new FTPManger();
//        BufferedImage bf = null;
//        try {
//            String src = repProtocolPath + "/"+ imageName;
//            File dest = new File(destPath + "/" + imageName);
//            try {
//                if (dest.exists()) {
//                        InputStream in = access(src);
//                        return in.;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public static void main(String[] args) {
        InputStream in = null;
        try {
            ServerHome serverHome = new ServerHome();
            Server server = serverHome.getAllServer().get(0);
            NtlmPasswordAuthentication authentication =
                    new NtlmPasswordAuthentication("localhost", "admin", "admin");
            UniAddress domain = UniAddress.getByName("localhost");
            SmbSession.logon(domain, authentication);
            SmbFile file = new SmbFile("smb://" + "localhost/" + "E$/gdit/capture");
            in = file.getInputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
