/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.run;

import com.gdit.capture.entity.BatchLog;
import com.gdit.capture.entity.BatchLogHome;

/**
 *
 * @author admin
 */
public class BatchBarcoded {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            BatchBarcoded barcodeClass = new BatchBarcoded();
            // TODO code application logic here
            BatchLogHome batchHome = new BatchLogHome();
            while (true) {
                BatchLog batch = batchHome.findBarcodedBatch();
                if (batch != null) {
                    barcodeClass.barcodeImages(batch.getServerDir(), batch.getBatchId());
                    batchHome.delete(batch);
                    batchHome.commit();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean barcodeImages(String path, long grandID) throws Exception {
        try {
            ReadBarcode barcodeReader = new ReadBarcode();
            return barcodeReader.readBracode(path, grandID);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
