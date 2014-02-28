package com.gdit.capture.ocr;

import com.gdit.capture.entity.Rep;
import com.gdit.capture.entity.RepHome;
import com.gdit.capture.entity.Server;
import com.gdit.capture.entity.ServerHome;
import com.java4less.ocr.tess3.OCRFacade;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;
import org.apache.poi.util.IOUtils;

public class OCRTest {

    /**
     * @param args
     */
    public void ocrToPDF(String batch, String destPath) {
        OCRFacade facade = new OCRFacade();

        Document document = null;
        OutputStream pdfFile = null;
        try {
            RepHome repHome = new RepHome();
            Rep rep = repHome.getAllRep().get(0);
            ServerHome serverHome = new ServerHome();
            Server server = serverHome.getAllServer().get(0);
            pdfFile = new FileOutputStream(new File(destPath + "/" + batch + ".pdf"));

            NtlmPasswordAuthentication authentication =
                    new NtlmPasswordAuthentication("", server.getUser(), server.getPwd());
            UniAddress domain = UniAddress.getByName(server.getIp());
            SmbSession.logon(domain, authentication);
            document = new Document();
            PdfWriter.getInstance(document, pdfFile);
            document.open();
            SmbFile batchFile = new SmbFile("smb://" + server.getIp() + "/" + rep.getPath() + "/" + batch + "/", authentication);
            for (SmbFile file : batchFile.listFiles()) {
                if (file.getName().endsWith(".tif") || file.getName().endsWith(".jpg")) {
                    System.out.println(file.getName());
                    String ext = file.getName().split("\\.")[1];
                    byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                    String text = facade.recognizeImage(bytes, ext, "eng");
                    document.add(new Paragraph(text));
                    document.add(new Paragraph(new Date().toString()));
                }
            }
            document.close();
            pdfFile.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            document.close();
            // pdfFile.close();
        }
    }
    
    public static void main(String[] args){
        OCRFacade facade = new OCRFacade();
        String text = facade.recognizeFile("W:/ytech/gip/107/page1.tif", "en");
        System.out.println(text);
    }
}
