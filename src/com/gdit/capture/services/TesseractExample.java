/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

/**
 *
 * @author Bahi
 */
import com.gdit.capture.entity.CaptureDocument;
import com.gdit.capture.entity.CaptureDocumentHome;
import com.gdit.capture.ocr.*;
import java.io.File;
import java.util.List;
import net.sourceforge.tess4j.*;

public class TesseractExample {

    public static void main(String[] args) {
        File imageFile = new File("\\\\192.168.1.3/scan/647973/page1.tif");
        Tesseract instance = Tesseract.getInstance(); // JNA Interface Mapping
        // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            String[] splits = result.split("\\n");

//            String yer = splits[1].split(" ")[0];
//            int lineNo =Integer.parseInt(splits[3].split(" ")[0]);
//            String date = splits[4].split(" ")[0];
//            String[] dates = date.split("-");


//            CaptureDocumentHome docDao = new CaptureDocumentHome();
//            List<CaptureDocument> docs = docDao.findDocs(lineNo, yer, dates[2]+"-"+dates[1]+"-"+dates[0]) ;
            System.out.println(result);
            
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}