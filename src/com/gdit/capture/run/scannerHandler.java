package com.gdit.capture.run;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
class scannerHandler implements Runnable {
    Thread runner;
    public scannerHandler() {
       runner = new Thread(this);
       runner.start();
    }

    public void run() {
        //Display info about this particular thread
        //  code for scanning and print

    }
}


