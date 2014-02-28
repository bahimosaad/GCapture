/*
 * $Id: Capabilities.java,v 1.3 2009/11/06 18:28:15 motovsky Exp $
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
import java.awt.Frame;
import java.awt.event.ActionListener;
 
import java.util.ArrayList;
import java.util.Properties;


import SK.gnome.morena.MorenaImage;

public abstract class Capabilities implements ActionListener
{
  protected ArrayList<Capability> allCapabilities;
  protected Properties properties;
  protected String sourceName;
  protected String sourceValidFileName;
  protected boolean restartAfterPreview;
  protected int sleepTimeBeforeRestart;
  protected Component centerPanel;
  protected Frame frame;
  protected CapabilityDialogs dialogWindow;
  public static boolean debug=Boolean.valueOf(System.getProperty("morena.capabilities.debug","false"));
  
  public abstract MorenaImage getPreviewImage();
  public abstract String getSourceName();
  
  public ArrayList<Capability> getCapabilities()
  {
    return allCapabilities;
  }

  public String getSourceValidFileName()
  {
    return sourceValidFileName;
  }
  
  public Capability getCapability(String name)
  {
    String temp;
    for (Capability capability : allCapabilities)
    {
      temp=capability.getName();
//      System.out.println("Capabilities.getCapability()temp="+temp);
      if((null!=temp)&&(temp.equals(name)))
        return capability;
    }
    return null;
  }
  
  public boolean isRestartAfterPreview()
  {
    return restartAfterPreview;
  }
  
  public void setRestartAfterPreview(boolean restartAfterPreview)
  {
    this.restartAfterPreview = restartAfterPreview;
  }
  
  public int getSleepTimeBeforeRestart()
  {
    return sleepTimeBeforeRestart;
  }
  
  public void setSleepTimeBeforeRestart(int sleepTimeAfterRestart)
  {
    this.sleepTimeBeforeRestart = sleepTimeAfterRestart;
  }
  
  public String makeFileNameValid(String string)
  {
    StringBuffer s=new StringBuffer();
    for (int i = 0; i < string.length(); i++)
    {
      Character ch=string.charAt(i);
      if (Character.isJavaIdentifierPart(ch))
        s.append(ch);
    }
    return s.toString();
  }
}
