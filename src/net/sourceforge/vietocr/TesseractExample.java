/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sourceforge.vietocr;

/**
 *
 * @author Bahi
 */
import java.io.File;
import net.sourceforge.tess4j.*;

public class TesseractExample {

    public static void main(String[] args) {
        File imageFile = new File("W:/scan/1/page1.tif");
        Tesseract instance = Tesseract.getInstance(); // JNA Interface Mapping
        // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            String[] splits = result.split("\\n");
            
            for(String split:splits){
                System.out.println(split);
            }
            
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}