/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.run;

import com.gdit.capture.entity.*;
import com.gdit.capture.model.CONSTANTS;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;
import org.tempuri.CompressToTiff;

/**
 *
 * @author Bahi
 */
public class BatchObserver extends Thread {

    private List<BatchLog> suspendedBatches;
    private Properties properties;
    //  private JTextArea txtArea;
    private Disk disk;
    private String computerName;
    private File from;
    private File to;
    private Capture batch;
    private Category category;

    public void reScanBatches() {
        try {
//
            System.out.println("Re Scan Batches");

            // suspendedBatches = logHome.getAllBatchLog();
            //  for (BatchLog batchLog : suspendedBatches) {
            this.run();
            //}
        } catch (Exception ex) {
            ex.printStackTrace();
            java.awt.Toolkit.getDefaultToolkit().beep();
//            JOptionPane.showMessageDialog(this, ex.toString());
        } finally {
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

    @Override
    public void run() {
        try {
            //copy Batch to server
            org.apache.commons.io.FileUtils.copyDirectory(from, to);
            //compress Batch from scan Dir to view Dir
            File scanFile = null ;//new File(scan.getParent() + "/" + category.getId() + "/" + scan.getName()+"/"+batch.getId());
            File viewFile = null;//new File(view.getParent() + "/" + category.getId() + "/" + view.getName()+"/"+batch.getId());
            
            if(category.isCreateFolder()){
                scanFile = new  File(disk.getPath()+"/"+category.getId()+"/scan/"+"/"+batch.getId());
                viewFile = new  File(disk.getPath()+"/"+category.getId()+"/view/"+"/"+batch.getId());
            }
            else{
                scanFile = new  File(disk.getPath()+"/scan/"+"/"+batch.getId());
                viewFile = new  File(disk.getPath()+"/view/"+"/"+batch.getId());
            }
            CompressToTiff service = new CompressToTiff();
//            service.getCompressToTiffSoap().compressFolderFullPath(disk.getPath() + "/" + batch.getId(),
//                    disk.getViewPath() + "/" + batch.getId());
            System.out.println(scanFile.getPath());
            System.out.println(viewFile.getPath());
            service.getCompressToTiffSoap().compressFolderFullPath(scanFile.getPath(),viewFile.getPath());
            //barcode the batch


//            BatchLog batchLog = new BatchLog();
//            batchLog.setBatchId(batch.getId());
//            batchLog.setServerDir(to.getAbsolutePath());
//            batchLog.setHomeDir(from.getAbsolutePath());
//            batchLog.setLocked(true);
//            batchLog.setComputerName(computerName);
//            BatchLogHome logHome = new BatchLogHome();
//            logHome.attachDirty(batchLog);
//            logHome.commit();
//            BatchLogHome.close();


        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(BatchObserver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            CaptureHome.close();
        }


    }

     
    

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public File getFrom() {
        return from;
    }

    public void setFrom(File from) {
        this.from = from;
    }

    public File getTo() {
        return to;
    }

    public void setTo(File to) {
        this.to = to;
    }

    public Capture getBatch() {
        return batch;
    }

    public void setBatch(Capture batch) {
        this.batch = batch;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    
}
