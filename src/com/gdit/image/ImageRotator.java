/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.image;

/**
 *
 * @author admin
 */
import java.awt.*;  
import java.awt.event.*; 
import javax.swing.*;  

/** 
 * This class creates the Image Rotator applet 
 * @author Rahul Sapkal(rahul@javareference.com) 
 */ 
public class ImageRotator extends JApplet implements ActionListener 
{  
    //static variables 
    public static final int DEFAULT_ROTATION_SPEED = 50; //in milli-seconds 
          
    //public variables 
    private String images[] = {"puppy.jpg", 
                               "ash.jpg", 
                               "jr.gif", 
                               "cliff.jpg", 
                               "bridge.jpg"};  
     
    private JButton m_launch; 
    private JList m_imageList; 
    private JTextField m_rotateSpeed; 
    private JCheckBox m_clockwise; 
      
    public void init()  
    {  
        getContentPane().setLayout(new BorderLayout()); 
         
        m_imageList = new JList(images); 
        m_imageList.setSelectedIndex(0); 
                 
        m_launch = new JButton("Launch Image Rotator"); 
        m_launch.addActionListener(this);             
         
        Panel input = new Panel(new FlowLayout()); 
        input.add(m_launch); 
         
        Panel input1 = new Panel(new FlowLayout()); 
        input1.add(new Label("Enter image rotation speed in milli-secs and rotation direction :")); 
         
        m_rotateSpeed = new JTextField(5); 
        m_rotateSpeed.setText(String.valueOf(DEFAULT_ROTATION_SPEED)); 
         
        m_clockwise = new JCheckBox("Rotate Clockwise", true); 
         
        input1.add(m_rotateSpeed); 
        input1.add(m_clockwise); 
          
        getContentPane().add(BorderLayout.NORTH, input1);  
        getContentPane().add(BorderLayout.CENTER, m_imageList); 
        getContentPane().add(BorderLayout.SOUTH, input); 
    }  

    public void actionPerformed(ActionEvent ae) 
    { 
        if(ae.getSource().equals(m_launch)) 
        { 
            //getting the image 
            String imageName = ((String)(m_imageList.getSelectedValue())).trim(); 
            Image image = getImage(getCodeBase(), imageName); 
             
            MediaTracker mt = new MediaTracker(this); 
            mt.addImage(image, 1); 
             
            try  
            {  
                mt.waitForAll();  
            }  
            catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            } 
              
            int speed = DEFAULT_ROTATION_SPEED; 
             
            try 
            { 
                speed = Integer.valueOf(m_rotateSpeed.getText().trim()).intValue(); 
            } 
            catch(NumberFormatException numExp) 
            { 
            }                 
             
            //create the Image Rotator frame 
            ImageRotatorFrame imageRotator = new ImageRotatorFrame(image, speed, imageName, m_clockwise.isSelected()); 
        }     
    }         
}