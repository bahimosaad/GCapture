/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.model;

import com.gdit.capture.entity.Capture;

/**
 *
 * @author admin
 */
public class DocTreeItem extends CaptureNode {

     
    public DocTreeItem(){

    }

    public DocTreeItem(Capture capture){
        this.name = capture.getBarcode();
        this.capture = capture;
    }

    
    



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
        final DocTreeItem other = (DocTreeItem) obj;
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
