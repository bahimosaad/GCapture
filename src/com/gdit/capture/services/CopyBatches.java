/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class CopyBatches {

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        new CopyBatches().copy("F:/ytech/gip/exe/");

    }

    public void copy(String from) {
        File fromFile = new File(from);


        for (File batch : fromFile.listFiles()) {
            try {
                File to = new File("F:/Scan/" + batch.getName());
                if (!to.exists()) {
                    to.mkdirs();
                }
                if (batch.listFiles() != null && batch.listFiles().length > 0) {
                    for (File page : batch.listFiles()) {

                        Path fromPath = Paths.get(from + batch.getName() + "/" + page.getName());
                        Path toPath = Paths.get("F:/Scan/" + batch.getName());
                        if (!new File("F:/Scan/" + batch.getName() + "/" + page.getName()).exists()) {
                            System.out.println(batch.getName()+"    "+page.getName());
                            Files.copy(fromPath, toPath.resolve(fromPath.getFileName()),
                                    StandardCopyOption.REPLACE_EXISTING);
                        }


                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(MoveBatches.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
