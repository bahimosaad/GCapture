/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model;

import com.gdit.capture.entity.Capture;

/**
 *
 * @author bahy
 */
public class BatchTreeItem extends CaptureNode{

    
    public BatchTreeItem(){

    }

    public BatchTreeItem(Capture capture){
        super.name = capture.getName();
        super.capture = capture;
    }

//    public Capture getCapture() {
//        return capture;
//    }
//
//    public void setCapture(Capture capture) {
//        this.capture = capture;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }



    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BatchTreeItem other = (BatchTreeItem) obj;
        if (this.capture != other.capture && (this.capture == null || !this.capture.equals(other.capture))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    

}
