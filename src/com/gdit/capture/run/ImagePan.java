package com.gdit.capture.run;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bahy
 */
import com.asprise.util.tiff.TIFFReader;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePan extends JPanel {

    //   private Image image;
    private Integer imageWidth;
    private Integer imageHeight;
    public static final int ROTATE_ANGLE_OFFSET = 90;
    private double translateX;
    private double translateY;
    private double scale = 1;
    private Image img;
    private BufferedImage bimg;
    private static ImagePan canvas;
    private TranslateHandler translater;
    private int offset = 1;
    private int angle;

    public void addImage(Image image) {
        this.img = image;
        canvas = this;
//        imageWidth = image.getWidth(null);
//        imageHeight = image.getHeight(null);

        translater = new TranslateHandler();
        canvas.addMouseListener(translater);
        canvas.addMouseMotionListener(translater);
        canvas.addMouseWheelListener(new ScaleHandler());
    }

    public void addImage(BufferedImage image) {
        this.bimg = image;
        this.img = bimg;
        canvas = this;
//        imageWidth = image.getWidth(null);
//        imageHeight = image.getHeight(null);

        translater = new TranslateHandler();
        canvas.addMouseListener(translater);
        canvas.addMouseMotionListener(translater);
        canvas.addMouseWheelListener(new ScaleHandler());
    }

    public void addImage(ImageIcon image) {
        //    this.image = image;
        canvas = this;
        this.img = image.getImage();
        imageWidth = image.getIconWidth();
        imageHeight = image.getIconHeight();

        translater = new TranslateHandler();
        canvas.addMouseListener(translater);
        canvas.addMouseMotionListener(translater);
        canvas.addMouseWheelListener(new ScaleHandler());
    }

    public void zoomin() {
        //   translater = new TranslateHandler();
        translater.zoomin();
    }

    public void zoomout() {
        //    translater = new TranslateHandler();
        translater.zoomout();
        
    }

//    public Image getImage() {
//      //  return image;
//    }
    @Override
    public void paintComponent(Graphics g) {

        AffineTransform tx = new AffineTransform();
        tx.translate(translateX, translateY);
        tx.scale(scale, scale);


        removeAll();
        if (bimg == null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.white);
            
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setTransform(tx);
            g2.setComposite(AlphaComposite.Src);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (imageWidth != null) {
                g2.rotate(Math.toRadians(angle), imageWidth / 2, imageHeight / 2);
                g2.drawImage(img,0, 0, imageWidth, imageHeight, null);
                //  g2.rotate(90);
            }
            else if(img!=null){
                imageWidth  = img.getWidth(null);
                imageHeight = img.getHeight(null);
              //  g2.rotate(Math.toRadians(angle), imageWidth / 2, imageHeight / 2);
                g2.drawImage(img, 100, 0, imageWidth, imageHeight, null);
            }
        } else {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.white);
            
            g2.fillRect(100, 0, getWidth(), getHeight());
            g2.setTransform(tx);
            g2.setComposite(AlphaComposite.Src);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            if (imageWidth != null) {
                g2.rotate(Math.toRadians(angle), imageWidth / 2, imageHeight / 2);
                g2.drawImage(bimg, 0, 0, imageWidth, imageHeight, null);
            }

        }
    }

    public void rotate(int offset) {
        angle -= offset;
//        if (angle <= 0) {
//            angle = 360;
//        }
        repaint();
    }

    public void rotateRight(int offset) {
        angle += offset;
//        if (angle <= 0) {
//            angle = 360;
//        }
        repaint();
    }
    public void rotate() {
        angle -= offset;
//        if (angle <= 0) {
//            angle = 360;
//        }
        repaint();
    }

    public void rotateRight() {
        angle += offset;
//        if (angle <= 0) {
//            angle = 360;
//        }
        repaint();
    }

    private static class TranslateHandler implements MouseListener,
            MouseMotionListener {

        private int lastOffsetX;
        private int lastOffsetY;

        public void mousePressed(MouseEvent e) {
            // capture starting point
            lastOffsetX = e.getX();
            lastOffsetY = e.getY();
        }

        public void mouseDragged(MouseEvent e) {
            // new x and y are defined by current mouse location subtracted
            // by previously processed mouse location
//            int newX = e.getX() - lastOffsetX;
//            int newY = e.getY() - lastOffsetY;
//
//            // increment last offset to last processed by drag event.
//            lastOffsetX += newX;
//            lastOffsetY += newY;
//
//            // update the canvas locations
//            canvas.translateX += newX;
//            canvas.translateY += newY;
//
//            // schedule a repaint.
//            canvas.repaint();
        }

        public void zoomin() {
            canvas.setBackground(Color.WHITE);
            canvas.scale += (.01);
            // don't cross negative threshold.
            // also, setting scale to 0 has bad effects
            canvas.scale = Math.max(0.00001, canvas.scale);
            canvas.repaint();
            
        }

        public void zoomout() {
            canvas.scale -= (.01);
            // don't cross negative threshold.
            // also, setting scale to 0 has bad effects
            canvas.scale = Math.max(0.00001, canvas.scale);
            canvas.repaint();
        }

        public void mouseClicked(MouseEvent e) {
            {

             //   zoomin();
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    private static class ScaleHandler implements MouseWheelListener {

        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

//                // make it a reasonable amount of zoom
//                // .1 gives a nice slow transition
//                canvas.scale += (.001 * e.getWheelRotation());
//
//                // don't cross negative threshold.
//                // also, setting scale to 0 has bad effects
//                canvas.scale = Math.max(0.00001, canvas.scale);
//                canvas.repaint();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        BufferedImage img = null;
        try {
            TIFFReader reader = new TIFFReader(new File("C:/test/1.tif"));
            //   imagePan.addImage(new ImageIcon("C:/test/" + selectedNode.getUserObject().toString() + ".jpg"));
            Image img1 = (Image) reader.getPage(0);
            img = ImageGenerator.createBufferedImage(img1);

            img = ImageIO.read(new File("C:\\gdit\\kkuh\\1126\\page5.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(ImagePan.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImagePan cv = new ImagePan();

        cv.addImage(img);
        //   cv.addImage(new ImageIcon(img.getScaledInstance(800, 600, Image.SCALE_FAST)));
        //   cv.resizeTrick(ImageGenerator.createBufferedImage(img), 400, 400);
        cv.repaint();
        frame.setLayout(new BorderLayout());


        frame.getContentPane().add(cv, BorderLayout.CENTER);
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static BufferedImage blurImage(BufferedImage image) {
        float ninth = 1.0f / 9.0f;
        float[] blurKernel = {
            ninth, ninth, ninth,
            ninth, ninth, ninth,
            ninth, ninth, ninth
        };

        Map map = new HashMap();

        map.put(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RenderingHints hints = new RenderingHints(map);
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
        
        return op.filter(image, null);
        
        
    }

    
    
    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public BufferedImage getBimg() {
        return bimg;
    }

    public void setBimg(BufferedImage bimg) {
        this.bimg = bimg;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    
}
