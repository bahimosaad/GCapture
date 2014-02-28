/*
 * $Id: MorenaCapabilities.java,v 1.1 2009/09/23 14:03:21 motovsky Exp $
 *
 * Copyright (c) 2009 Gnome spol. s r.o. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Gnome spol. s r.o. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Gnome.
 */

package SK.gnome.capabilities;

import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SK.gnome.capabilities.sane.SaneActivity;
import SK.gnome.capabilities.twain.TwainActivity;
import SK.gnome.morena.MorenaSource;
import SK.gnome.sane.SaneConnection;
import SK.gnome.twain.TwainException;
import SK.gnome.twain.TwainManager;

public class MorenaCapabilities
{
  static final String TWAIN_ = "TWAIN_";
  static final String SANE_ = "SANE_";
  static Properties properties;
  static String selectedSource = "";
  
  private TwainActivity twainActivity=null;
  private SaneActivity saneActivity=null;
  
  static public void selectSource(JPanel centerPanel)
  {
    String host="localhost";
    int port=6566;
    String userName="";
    
    final int TWAIN=0;
    final int SANE=1;
    final String INTERFACE[]= { "TWAIN", "SANE", "Cancel" };
    final String os=System.getProperty("os.name").toLowerCase();
    int api=SANE;
    
    if (os.indexOf("windows")>=0 || os.indexOf("os x")>=0)
      api=JOptionPane.showOptionDialog(centerPanel, "Select image acquisition interface", "Morena source selector", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, INTERFACE, INTERFACE[TWAIN]);
    
    String sourceName=null;
    MorenaSource source=null;
    switch (api)
    { case TWAIN:
        source= TwainManager.selectSource(centerPanel, null);
        if(null!=source)
        {
          sourceName = TWAIN_ + source.toString();
        }
          
        try
        {
          TwainManager.close();
        } catch (TwainException e2)
        {
          e2.printStackTrace();
        }
        break;
      case SANE:
        
        class SaneConnectionOptionPane extends JPanel
        {
          private JTextField hostTextField;
          private JTextField portTextField;
          private JTextField userNameTextField;
          
          public SaneConnectionOptionPane(Component centerPanel, String host, int port, String userName)
          {
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JPanel hostPanel = new JPanel();
            hostPanel.setLayout(new BoxLayout(hostPanel, BoxLayout.X_AXIS));
            hostPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            hostPanel.add(new JLabel("Host: "));
            hostPanel.add(hostTextField=new JTextField(host));
            add(hostPanel);
            
            JPanel portPanel = new JPanel();
            portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));
            portPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            portPanel.add(new JLabel("Port:  "));
            portPanel.add(portTextField=new JTextField(String.valueOf(port)));
            add(portPanel);
            
            JPanel userNamePanel = new JPanel();
            userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
            userNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            userNamePanel.add(new JLabel("User name: "));
            userNamePanel.add(userNameTextField=new JTextField(userName,10));
            add(userNamePanel);
            
          }
          
          public String getHost()
          {
            return hostTextField.getText();
          }

          public int getPort()
          {
            return Integer.parseInt(portTextField.getText());
          }

          public String getUserName()
          {
            return userNameTextField.getText();
          }

        }
        
        SaneConnectionOptionPane saneConnectionOptionPane=new SaneConnectionOptionPane(centerPanel, host, port, userName);
        JOptionPane.showMessageDialog(centerPanel,saneConnectionOptionPane);
        
        host=saneConnectionOptionPane.getHost();
        port=saneConnectionOptionPane.getPort();
        userName=saneConnectionOptionPane.getUserName();
        
        SaneConnection sc=null;
        Cursor previousCursor=centerPanel.getCursor();
        try
        {
          centerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          sc = SaneConnection.connect(host, port, userName);
        } catch (Exception e1)
        {
          System.out.println("SelectSourceAction.actionPerformed()"+e1.getMessage());
        } 
        finally
        {
          centerPanel.setCursor(previousCursor);
        }
        source = sc.selectSource(centerPanel, null);
        if (null != source)
        {
          sourceName = SANE_ + source.toString()+";"+host+";"+port+";"+userName;
        }
          
        try
        {
          sc.close();
        } catch (Exception e1)
        {
          e1.printStackTrace();
        }
        break;
    }
    
    if (null != sourceName)
    {
      try
      {
        properties = new Properties();
        properties.setProperty("selectedSource", sourceName);
        
        File file = new File(System.getProperty("user.home") + "/.morena/" + "MorenaCapabilities.properties");
        if (!file.exists())
          file.getParentFile().mkdirs();
        properties.store(new FileOutputStream(file), null);
        selectedSource=sourceName;
      } catch (Exception ex)
      {
        JOptionPane.showMessageDialog(centerPanel, ex.getMessage(), "Exception during storing the properties file", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
      }
    }
  }
  
  public MorenaCapabilities(JPanel centerPanel)
  {
    try
    {
      properties = new Properties();
      properties.load(new FileInputStream(System.getProperty("user.home") + "/.morena/" + "MorenaCapabilities.properties"));
      selectedSource = properties.getProperty("selectedSource", "");
    } catch (Exception e)
    {
      JOptionPane.showMessageDialog(centerPanel, e.getMessage(), "Exception during loading the properties file", JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
    }
    
    String sourceName=null;
    if (selectedSource.startsWith(TWAIN_))
    {
      sourceName=selectedSource.substring(TWAIN_.length());
      twainActivity=new TwainActivity(centerPanel,sourceName);
    }
    else if (selectedSource.startsWith(SANE_))
    {
      sourceName=selectedSource.substring(SANE_.length());
      saneActivity=new SaneActivity(centerPanel,sourceName);
    }
    else
      JOptionPane.showMessageDialog(centerPanel, "No source selected. Expected source name:"+sourceName,"" , JOptionPane.ERROR_MESSAGE);
  }

  public int showDialogWindow()
  {
    if(null!=twainActivity)
      return twainActivity.showDialogWindow();
    else if (null!=saneActivity)
      return saneActivity.showDialogWindow();
    else return 0;
  }
  
  public MorenaSource getSource()
  {
    if(null!=twainActivity)
      return twainActivity.getSource();
    else if (null!=saneActivity)
      return saneActivity.getSource();
    else return null;
  }
  
  public void close()
  {
    if(null!=twainActivity)
      twainActivity.close();
    else if (null!=saneActivity)
      saneActivity.close();
  }
}
