package com.gdit.capture.run;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
import java.awt.Image;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ImageConsumer;
/*      */ import java.awt.image.ImageProducer;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Hashtable;
/*      */
/*      */public class ImageGenerator
/*      */   implements ImageConsumer
/*      */ {
/*      */   private Object holder;
/*      */   private ColorModel colorModel;
/*      */   private Hashtable properties;
/*      */   private WritableRaster raster;
/*      */   private int width;
/*      */   private int height;
/*      */   private BufferedImage image;
/*      */   private int[] intBuffer;
/*      */   private boolean loadComplete;
/*      */
/*      */   ImageGenerator()
/*      */   {
/* 3122 */     this.holder = new Object();
/* 3123 */     this.width = -1;
/* 3124 */     this.height = -1;
/* 3125 */     this.loadComplete = false;
/*      */   }
/*      */
/*      */   public void imageComplete(int status)
/*      */   {
/* 3140 */     if ((status == 3) ||
/* 3141 */       (status == 4) ||
/* 3142 */       (status == 1))
/*      */     {
/* 3144 */       synchronized (this.holder)
/*      */       {
/* 3146 */         this.loadComplete = true;
/* 3147 */         this.holder.notify();
/*      */       }
/*      */     }
/*      */     else
/* 3151 */       System.err.println("Some other value passed to complete");
/*      */   }
/*      */
/*      */   public void setColorModel(ColorModel model)
/*      */   {
/* 3162 */     this.colorModel = model;
/* 3163 */     createImage();
/*      */   }
/*      */
/*      */   public void setDimensions(int w, int h)
/*      */   {
/* 3174 */     this.width = w;
/* 3175 */     this.height = h;
/* 3176 */     createImage();
/*      */   }
/*      */
/*      */   public void setHints(int flags)
/*      */   {
/*      */   }
/*      */
/*      */   public void setPixels(int x, int y, int w, int h, ColorModel model, byte[] pixels, int offset, int scansize)
/*      */   {
/* 3210 */     if (this.loadComplete) {
/* 3211 */       return;
/*      */     }
/* 3213 */     if ((this.intBuffer == null) || (pixels.length > this.intBuffer.length)) {
/* 3214 */       this.intBuffer = new int[pixels.length];
/*      */     }
/* 3216 */     for (int i = pixels.length; --i >= 0; ) {
/* 3217 */       this.intBuffer[i] = (pixels[i] & 0xFF);
/*      */     }
/* 3219 */     this.raster.setPixels(x, y, w, h, this.intBuffer);
/*      */   }
/*      */
/*      */   public void setPixels(int x, int y, int w, int h, ColorModel model, int[] pixels, int offset, int scansize)
/*      */   {
/* 3243 */     if (this.loadComplete) {
/* 3244 */       return;
/*      */     }
/* 3246 */     this.image.setRGB(x, y, w, h, pixels, offset, scansize);
/*      */   }
/*      */
/*      */   public void setProperties(Hashtable props)
/*      */   {
/* 3256 */     this.properties = props;
/* 3257 */     createImage();
/*      */   }
/*      */
/*      */     BufferedImage getImage()
/*      */   {
/* 3272 */     if (!(this.loadComplete))
/*      */     {
/* 3274 */       synchronized (this.holder)
/*      */       {
/*      */         try
/*      */         {
/* 3278 */           this.holder.wait();
/*      */         }
/*      */         catch (InterruptedException localInterruptedException)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */
/* 3286 */     return this.image;
/*      */   }
/*      */
/*      */   void reset()
/*      */   {
/* 3295 */     synchronized (this.holder)
/*      */     {
/* 3297 */       this.holder.notify();
/*      */     }
/*      */
/* 3300 */     this.loadComplete = false;
/* 3301 */     this.colorModel = null;
/* 3302 */     this.raster = null;
/* 3303 */     this.properties = null;
/* 3304 */     this.image = null;
/* 3305 */     this.width = -1;
/* 3306 */     this.height = -1;
/*      */   }
/*      */
/*      */   private void createImage()
/*      */   {
/* 3318 */     if ((this.image != null) ||
/* 3319 */       (this.width == -1) ||
/* 3320 */       (this.colorModel == null) || (this.loadComplete)) {
/* 3321 */       return;
/*      */     }
/* 3323 */     this.raster = this.colorModel.createCompatibleWritableRaster(this.width, this.height);
/*      */
/* 3325 */     boolean premult = this.colorModel.isAlphaPremultiplied();
/* 3326 */     this.image = new BufferedImage(this.colorModel, this.raster, premult, this.properties);
/*      */   }
/*      */
/*      */   public static BufferedImage createBufferedImage(Image img)
/*      */   {
/* 3339 */     if (img instanceof BufferedImage) {
/* 3340 */       return ((BufferedImage)img);
/*      */     }
/* 3342 */     ImageProducer prod = img.getSource();
/*      */
/* 3344 */     ImageGenerator gen = new ImageGenerator();
/* 3345 */     prod.startProduction(gen);
/*      */
/* 3347 */     return gen.getImage();
/*      */   }
/*      */ }

/* Location:           D:\New Folder (2)\Downloads\Compressed\JTwain-eval\JTwain.jar
 * Qualified Name:     com.asprise.util.jtwain.ImageGenerator
 * JD-Core Version:    0.5.3
 */