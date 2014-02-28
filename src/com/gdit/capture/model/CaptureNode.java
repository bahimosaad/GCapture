/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import com.gdit.capture.entity.Capture;

/**
 *
 * @author Bahi
 */
class CaptureNode {
 
    protected String name;
    protected Capture capture ;

    public CaptureNode(String name, Capture capture) {
        this.name = name;
        this.capture = capture;
    }

    public CaptureNode(String name) {
        this.name = name;
    }
    
    

    public CaptureNode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Capture getCapture() {
        return capture;
    }

    public void setCapture(Capture capture) {
        this.capture = capture;
    }

    
}
