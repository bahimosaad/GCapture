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
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class MoveBatches {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new MoveBatches().copy("W:/09-10-2012");
    }

    public void copy(String from) {
        File fromFile = new File(from);

        for (File batch : fromFile.listFiles()) {
            try {

                 if (batch.listFiles() != null && batch.listFiles().length > 0) {
                    for (File page : batch.listFiles()) {
                        Path fromPath = Paths.get("W:/", "09-10-2012/" + batch.getName()+"/"+page.getName());
                        Path toPath = Paths.get("W:/scan/"+ batch.getName());
                        System.out.println(page.getName());
                        Files.move(fromPath, toPath.resolve(fromPath.getFileName()),
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                 else{
                     Path fromPath = Paths.get("W:/", "09-10-2012/" + batch.getName());
                        Path toPath = Paths.get("W:/scan/");
                        System.out.println(batch.getName());
                        Files.move(fromPath, toPath.resolve(fromPath.getFileName()),
                                StandardCopyOption.REPLACE_EXISTING);
                 }
               
            } catch (IOException ex) {
                Logger.getLogger(MoveBatches.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
