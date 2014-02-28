/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.run;

import com.gdit.capture.entity.Capture;
import com.gdit.capture.entity.CaptureHome;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author admin
 */
public class FetchBatchThread extends Thread {

    private Capture batch;
    private String path;
    private String type;
     
    @Override
    public void run() {
        if(type.equals("QA"))
                {
                    List<Capture> pages = new CaptureHome().getBatchPages(batch);
                    ExecutorService pool = Executors.newFixedThreadPool(3);
                    for (Capture page : pages) {
                        ReadImageThread thread = new ReadImageThread();
                        thread.setBatch(batch);
                        thread.setPage(page);
                        File file = new File(path + batch.getId() + "/" + page.getPath());
                        thread.setPath(file.getAbsolutePath());
                        pool.submit(thread);
                        //   }
                    }
              
                }
                else if (type.equals("EXC"))
                {
                    List<Capture> refusedPages = new CaptureHome().getRefusedBatchPages(batch);
                    ExecutorService pool = Executors.newFixedThreadPool(3);
                    for (Capture page : refusedPages) {
                        ReadImageThread thread = new ReadImageThread();
                        thread.setBatch(batch);
                        thread.setPage(page);
                        File file = new File(path + batch.getId() + "/" + page.getPath());
                        thread.setPath(file.getAbsolutePath());
                        pool.submit(thread);
                        //   }
                    }
              
                }
        }
    

    public Capture getBatch() {
        return batch;
    }

    public void setBatch(Capture batch) {
        this.batch = batch;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
