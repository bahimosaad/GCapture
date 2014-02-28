/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.services;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Unzip {

    public static final void copyInputStream(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();
    }

    public static void main(String[] args) {
        Unzip unzip = new Unzip();
        String pth = "G:/18112012/";
        File file = new File(pth);
        for (String path : file.list()) {
            
            unzip.unzip(pth + path, "G:/18-11-2012/"+path.split("\\.")[0]);
        }
    }

    public void unzip(String path, String to) {
        Enumeration entries;
        ZipFile zipFile;

        try {
            zipFile = new ZipFile(path);

            entries = zipFile.entries();
            File file = new File(to);
            file.mkdir();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                if (entry.isDirectory()) {
                    // Assume directories are stored parents first then children.
              //      System.err.println("Extracting directory: " + entry.getName());
                    // This is not robust, just for demonstration purposes.
                    (new File(entry.getName())).mkdir();
                    continue;
                }

          //      System.err.println("Extracting file: " + entry.getName());
                copyInputStream(zipFile.getInputStream(entry),
                        new BufferedOutputStream(new FileOutputStream(to + "/" + entry.getName())));
            }

            zipFile.close();
        } catch (IOException ioe) {
            System.err.println("Unhandled exception:");
            ioe.printStackTrace();
            return;
        }
    }
}