package com.indexing;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bahy
 */
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

public class ImageGenerator implements ImageConsumer {

    private Object holder;
    private ColorModel colorModel;
    private Hashtable properties;
    private WritableRaster raster;
    private int width;
    private int height;
    private BufferedImage image;
    private int[] intBuffer;
    private boolean loadComplete;

    public ImageGenerator() {
        this.holder = new Object();
        this.width = -1;
        this.height = -1;
        this.loadComplete = false;
    }

    public void imageComplete(int status) {
        if ((status == 3)
                || (status == 4)
                || (status == 1)) {
            synchronized (this.holder) {
                this.loadComplete = true;
                this.holder.notify();
            }
        } else {
            System.err.println("Some other value passed to complete");
        }
    }

    public void setColorModel(ColorModel model) {
        this.colorModel = model;
        createImage();
    }

    public void setDimensions(int w, int h) {
        this.width = w;
        this.height = h;
        createImage();
    }

    public void setHints(int flags) {
    }

    public void setPixels(int x, int y, int w, int h, ColorModel model, byte[] pixels, int offset, int scansize) {
        if (this.loadComplete) {
            return;
        }
        if ((this.intBuffer == null) || (pixels.length > this.intBuffer.length)) {
            this.intBuffer = new int[pixels.length];
        }
        for (int i = pixels.length; --i >= 0;) {
            this.intBuffer[i] = (pixels[i] & 0xFF);
        }
        this.raster.setPixels(x, y, w, h, this.intBuffer);
    }

    public void setPixels(int x, int y, int w, int h, ColorModel model, int[] pixels, int offset, int scansize) {
        if (this.loadComplete) {
            return;
        }
        this.image.setRGB(x, y, w, h, pixels, offset, scansize);
    }

    public void setProperties(Hashtable props) {
        this.properties = props;
        createImage();
    }

    BufferedImage getImage() {
        if (!(this.loadComplete)) {
            synchronized (this.holder) {
                try {
                    this.holder.wait();
                } catch (InterruptedException localInterruptedException) {
                }
            }
        }

        return this.image;
    }

    void reset() {
        synchronized (this.holder) {
            this.holder.notify();
        }

        this.loadComplete = false;
        this.colorModel = null;
        this.raster = null;
        this.properties = null;
        this.image = null;
        this.width = -1;
        this.height = -1;
    }

    private void createImage() {
        if ((this.image != null)
                || (this.width == -1)
                || (this.colorModel == null) || (this.loadComplete)) {
            return;
        }
        this.raster = this.colorModel.createCompatibleWritableRaster(this.width, this.height);

        boolean premult = this.colorModel.isAlphaPremultiplied();
        this.image = new BufferedImage(this.colorModel, this.raster, premult, this.properties);
    }

    public static BufferedImage createBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return ((BufferedImage) img);
        }
        ImageProducer prod = img.getSource();

        ImageGenerator gen = new ImageGenerator();
        prod.startProduction(gen);

        return gen.getImage();
    }
}

/* Location:           D:\New Folder (2)\Downloads\Compressed\JTwain-eval\JTwain.jar
 * Qualified Name:     com.asprise.util.jtwain.ImageGenerator
 * JD-Core Version:    0.5.3
 */
