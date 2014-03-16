/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.util.List;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class Sync {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        CompressToTiff soap = new CompressToTiff();
        soap.getCompressToTiffSoap().compressFolderFullPath("\\\\192.168.1.3\\181\\141\\scan\\2686840", "\\\\192.168.1.3\\181\\141\\view\\2686840");

    }
}
