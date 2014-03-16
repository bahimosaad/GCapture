/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services.importing;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class CreateBatchesManual {

    public static void main(String[] args) {
        File folder = new File("\\\\192.168.1.3/scan");
        String[] batches = folder.list();
        Arrays.sort(batches);
        CaptureHome dao = new CaptureHome();
        int i = 1;
        for (int j = (batches.length - 1); j > 0; j--) {
            String batch = batches[j];
           
            if (Long.valueOf(batch) > 2997815) {
                System.out.println(batch);
                i++;
                Capture cap = new Capture();
                cap.setId(Long.valueOf(batch));
                cap.setStatus(1);
                cap.setType(1);
                dao.attachDirty(cap);
            }
//            else{
//                break;
//            }
        }
        System.out.println(i);

    }
}
