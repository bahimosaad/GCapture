/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import org.tempuri.CompressToTiff;

/**
 *
 * @author Administrator
 */
public class ConvertThread implements Runnable {

    private String batchId;

    @Override
    public void run() {
       // System.out.println(batchId);
        CompressToTiff soap = new CompressToTiff();
        soap.getCompressToTiffSoap().compressFolder(batchId);
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    
    
}
