package com.gdit.capture.run;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
public class RunnableExample implements Runnable {

    public void run(){
        System.out.println("Run");
    }

    public static void main(String args[]){
        RunnableExample ex = new RunnableExample();

            ex.run();
    }
}