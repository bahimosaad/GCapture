/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.image;

/**
 *
 * @author admin
 */
/*  
* This example is from javareference.com  
* for more information visit,  
* http://www.javareference.com  
*/  

import java.awt.*; 
import java.awt.geom.*; 
import java.awt.event.*; 
import javax.swing.*; 

/** 
 * This class creates a Image Rotator 
 * @author Rahul Sapkal(rahul@javareference.com) 
 */ 
public class ImageRotatorFrame extends JFrame implements ActionListener 
{ 
    private ImagePanel m_imagePanel; 
    private int m_rotateSpeed; 
    private Timer m_rotateTimer; 
    private JPanel m_imageContainer; 
     
    /** 
     * Constructor 
     *   
     * @param image 
     * @param speed 
     * @param imageName 
     * @param clockwise 
     */     
    public ImageRotatorFrame(Image image, int speed, String imageName, boolean clockwise) 
    { 
        super("Image Rotator [" + imageName + "]"); 
         
        m_rotateSpeed = speed; 
         
        if(image == null) 
        { 
            add(new JLabel("Image " + imageName + " not Found")); 
        } 
        else 
        { 
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
             
            String mess = "Rotation Speed : " + m_rotateSpeed +    " milli-seconds"; 
             
            if(clockwise) 
            { 
                mess += " in Clockwise direction"; 
            } 
            else 
            { 
                mess += " in Anti-Clockwise direction"; 
            } 
             
            topPanel.add(new JLabel(mess));  
                                     
            m_imagePanel = new ImagePanel(image, clockwise); 
                     
            getContentPane().add(BorderLayout.NORTH, topPanel); 
            getContentPane().add(BorderLayout.CENTER, m_imagePanel); 
                         
            m_imagePanel.repaint(); 
        } 
         
        setSize(1000, 800); 
        setVisible(true); 
         
        //create the rotate timer 
        m_rotateTimer = new Timer(m_rotateSpeed, this); 
         
        //start the rotate timer 
        m_rotateTimer.start(); 
    } 
     
    /** 
     * Action Listener method taking care of  
     * actions on the buttons 
     */ 
    public void actionPerformed(ActionEvent ae) 
    { 
        if(ae.getSource().equals(m_rotateTimer)) 
        { 
            //give call to rotate the image 
            m_imagePanel.rotate(); 
        } 
    } 
     
    /** 
     * This class is the Image Panel where the image 
     * is drawn and rotated 
     *  
     * @author Rahul Sapkal(rahul@javareference.com) 
     */ 
    public class ImagePanel extends JPanel 
    { 
        //Offset angle for rotation 
        public static final int ROTATE_ANGLE_OFFSET = 5;  
          
        private Image m_image; 
        private int m_currentRotateAngle; 
        private int m_imageWidth, m_imageHeight; 
        private AffineTransform m_affineTransform; 
        private boolean m_clockwise; 
                 
        /** 
         * Constructor 
         *  
         * @param image 
         * @param clockwise 
         */                                          
        public ImagePanel(Image image, boolean clockwise) 
        { 
            m_clockwise = clockwise; 
            m_image = image; 
            m_imageWidth = m_image.getWidth(this); 
            m_imageHeight = m_image.getHeight(this); 
             
            if(!clockwise) 
            { 
                m_currentRotateAngle = 360 + ROTATE_ANGLE_OFFSET; 
            } 
             
            m_affineTransform = new AffineTransform(); 
        } 
         
        /** 
         * This method is overriden to draw and rotate 
         * the image accordingly 
         */ 
        public void paintComponent(Graphics grp)  
        {  
            Rectangle rect = this.getBounds(); 
             
            Graphics2D g2d = (Graphics2D)grp; 
             
            //set the background color to black 
            g2d.setColor(Color.BLACK); 
             
            //fill the rect 
            g2d.fillRect(0, 0, getWidth(), getHeight()); 
             
            //set the translation to the mid of the component 
              m_affineTransform.setToTranslation((rect.width-m_imageWidth)/2,(rect.height-m_imageHeight)/2); 
               
              //rotate with the rotation point as the mid of the image 
            m_affineTransform.rotate(Math.toRadians(m_currentRotateAngle), m_imageWidth/2, m_imageHeight/2); 
             
            //draw the image using the AffineTransform 
            g2d.drawImage(m_image, m_affineTransform, this); 
        } 

        /** 
         * This method changes the rotation angle depending  
         * upon the selected rotation direction and repaints  
         * the ImagePanel  
         */         
        public void rotate() 
        { 
            if(m_clockwise) 
            { 
                //if clockwise then increment the rotation angle 
                //by the offset till the angle is 360 
                //then again set to 0 
                m_currentRotateAngle += ROTATE_ANGLE_OFFSET; 
                 
                //mod takes care of setting the angle to 0 if 
                //it is 360 
                m_currentRotateAngle %= 360; 
            } 
            else 
            { 
                //if anti-clockwise then decrement the rotation angle 
                //by the offset till the angle is equal to 0 
                //then again set to 360 
                m_currentRotateAngle -= ROTATE_ANGLE_OFFSET; 
                 
                if(m_currentRotateAngle <= 0) 
                { 
                    m_currentRotateAngle = 360; 
                } 
            } 
             
            //repaint the image panel 
            repaint(); 
        } 
    } 
} 
 
//package 

//import statements 
 