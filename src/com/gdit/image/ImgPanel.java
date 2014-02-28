/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

/**
 *
 * @author bahy
 */
public class ImgPanel extends JPanel {
    //Offset angle for rotation

    public static final int ROTATE_ANGLE_OFFSET = 90;
    private Image m_image;
    private int m_currentRotateAngle;
    private int m_imageWidth, m_imageHeight;
    private AffineTransform m_affineTransform;
    private boolean m_clockwise;
    int imageWidth;
    int imageHeight;

    public ImgPanel() {
    }

    /**
     * Constructor
     *
     * @param image
     * @param clockwise
     */



    public ImgPanel(Image image) {
        setSize(200,200);
        m_clockwise = true;
        image.getScaledInstance(400, 400,Image.SCALE_FAST);
        m_image = image;
        m_imageWidth = m_image.getWidth(this);
        m_imageHeight = m_image.getHeight(this);

//            if (!clockwise) {
//                m_currentRotateAngle = 360 + ROTATE_ANGLE_OFFSET;
//            }

        m_affineTransform = new AffineTransform();
    }


    /**
     * This method is overriden to draw and rotate
     * the image accordingly
     */
    public void paint(Graphics grp) {
        Rectangle rect = this.getBounds();
        Graphics2D g = (Graphics2D) grp;

        //set the background color to black
        g.setColor(Color.BLACK);

        //fill the rect
      //  g.fillRect(0, 0, getWidth(), getHeight());


        super.paint(g);
        int panelWidth = getWidth() - 6;
        int panelHeight = getHeight() - 6;
        double horizontalRatio = (double) panelWidth / imageWidth;
        double verticalRatio = (double) panelHeight / imageHeight;
        if (horizontalRatio > verticalRatio) {
            g.drawImage(m_image, (int) (panelWidth - imageWidth * verticalRatio) / 2 + 3, 3,
                    (int) (m_imageWidth * verticalRatio), (int) (imageHeight * verticalRatio), this);
        } else {
            g.drawImage(m_image, 3, 3, (int) (imageWidth * horizontalRatio), (int) (imageHeight * horizontalRatio), this);
        }

      //  Rectangle rect = new Rectangle((int) (imageWidth * horizontalRatio), (int) (imageHeight * horizontalRatio));
        g.fillRect(3, 3, getWidth(), getHeight());
        //set the translation to the mid of the component
        m_affineTransform.setToTranslation((rect.width - m_imageWidth) / 2, (rect.height - m_imageHeight) / 2);

        //rotate with the rotation point as the mid of the image
        m_affineTransform.rotate(Math.toRadians(m_currentRotateAngle), m_imageWidth / 2, m_imageHeight / 2);

        //draw the image using the AffineTransform
        g.drawImage(m_image, m_affineTransform, this);
    }
    /**
     * This method changes the rotation angle depending
     * upon the selected rotation direction and repaints
     * the ImagePanel
     */
    public void rotate(boolean m_clockwise) {
        if (m_clockwise) {
            //if clockwise then increment the rotation angle
            //by the offset till the angle is 360
            //then again set to 0
            m_currentRotateAngle += ROTATE_ANGLE_OFFSET;

            //mod takes care of setting the angle to 0 if
            //it is 360
            m_currentRotateAngle %= 360;
        } else {
            //if anti-clockwise then decrement the rotation angle
            //by the offset till the angle is equal to 0
            //then again set to 360
            m_currentRotateAngle -= ROTATE_ANGLE_OFFSET;

            if (m_currentRotateAngle <= 0) {
                m_currentRotateAngle = 360;
            }
        }

        //repaint the image panel
        repaint();
    }
}
