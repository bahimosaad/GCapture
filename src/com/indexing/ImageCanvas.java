package com.indexing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class ImageCanvas extends JPanel
{
  private Image img;
  private double zoom = 1.0D;
//  private String message = "Please wait! Loading image files.";
  private String message = "";
  private Image offScreen;
  private Graphics g;

    public ImageCanvas() {
        try {
//             UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setBackground(new Color(236,233,216));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    
    @Override
  public void paint(Graphics paramGraphics)
  {
    paramGraphics.drawImage(paintOffScreen(), 0, 0, this);
  }

  public void setMassage(String paramString)
  {
    this.message = paramString;
    repaint();
  }

  public void zoom(int paramInt)
  {
    this.zoom = (paramInt / 100.0D);
    repaint();
  }

    @Override
  public void update(Graphics paramGraphics)
  {
    paint(paramGraphics);
  }

  private Image paintOffScreen()
  {
    int i = size().width;
    int j = size().height;
    this.offScreen = createImage(i, j);
    this.g = this.offScreen.getGraphics();
    if (this.img != null)
    {
      int k = (int)(this.img.getWidth(this) * this.zoom);
      int m = (int)(this.img.getHeight(this) * this.zoom);
      int n = (i - k) / 2;
      int i1 = (j - m) / 2;
      this.g.drawImage(this.img, n, i1, k, m, this);
      setSize(k, m);
      setPreferredSize(new Dimension(k, m));
    }
    else
    {
//      this.g.setColor(Color.white);
      this.g.drawString(this.message, 30, 40);
    }
    return this.offScreen;
  }

  public void setImage(Image paramImage)
  {
    this.img = paramImage;
    this.zoom = 1.0D;
  }

}

/* Location:           C:\Documents and Settings\ehab\Desktop\imgViewer\New Folder (2)\viewer_x.zip
 * Qualified Name:     viewer.cards.ImageCanvas
 * JD-Core Version:    0.6.0
 */