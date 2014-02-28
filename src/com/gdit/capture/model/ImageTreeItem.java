/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.model;

import com.gdit.capture.entity.Capture;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.media.jai.PlanarImage;
import javax.swing.ImageIcon;

/**
 *
 * @author bahy
 */
public class ImageTreeItem extends CaptureNode implements Serializable {

    private ImageIcon image;
    private String path;
     private PlanarImage planarImage;


     
    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PlanarImage getPlanarImage() {
        return planarImage;
    }

    public void setPlanarImage(PlanarImage planarImage) {
        this.planarImage = planarImage;
    }
    

    @Override
    public String toString() {
        return name;
    }

    
}
