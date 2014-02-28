/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DownloadingImages {
    ImageDisplay displayPanel;
    String[] paths = {
        "http://antwrp.gsfc.nasa.gov/apod/image/0712/m74_hst_800c.jpg",
        "http://antwrp.gsfc.nasa.gov/apod/image/0711/pleiades_fs.jpg",
        "http://antwrp.gsfc.nasa.gov/apod/image/0711/crescent_bugnet.jpg",
        "http://antwrp.gsfc.nasa.gov/apod/image/0712/" +
                "HindsVariable_goldman800.jpg"
    };

    private class EventHandler implements ActionListener {
        BufferedImage image;
        int count = 0;

        public void actionPerformed(ActionEvent evt) {
            String ac = evt.getActionCommand();
            // images are download before display
            if (ac.equals("load image")) {
                count++;
                if (count == paths.length)
                    count=0;
                long start = System.currentTimeMillis();
                image = downloadImage(paths[count]);
                String name = getName(paths[count]);
                long end = System.currentTimeMillis();
                double time = (end - start)/1000.0;
                System.out.printf("load time for %s was: %.1f seconds%n",
                                   name, time);
            }

            if (ac.equals("show image")) {
                int imgwidth = 0, imgheight = 0;
                try {
                    imgwidth = image.getWidth();
                } catch (Exception e) {
                    System.out.println("can not get image width " + imgwidth);
                    System.out.println(e.getMessage());
                }
                try {
                    imgheight = image.getHeight();
                } catch (Exception e) {
                    System.out.println("can not get image height " + imgheight);
                    System.out.println(e.getMessage());
                }

                displayPanel.setImage(image);
            }

            if(ac.equals("load all"))
                loadAllImages();
        }

        private String getName(String s) {
            int lastSlash = s.lastIndexOf("/");
            if(lastSlash > -1 && lastSlash < s.length()-1)
                return s.substring(lastSlash+1);
            return null;
        }
    }

    private BufferedImage downloadImage(String path) {
        BufferedImage image = null;
        try {
            URL url = new URL(path);
            image = ImageIO.read(url);
        } catch (IOException ex) {
            System.out.println("can not read : " + path);
            System.out.println(ex.getMessage());
        }
        return image;
    }

    private class ImageDisplay extends JPanel {
        BufferedImage image;

        public ImageDisplay() {
            // this belongs here
            this.setBackground(Color.WHITE);
        }

        protected void paintComponent(Graphics g) {
            // This next line will fill component background
            // with background color specified in constructor
            // above.
            super.paintComponent(g);
            if(image != null) {
                int x = (getWidth() - image.getWidth())/2;
                int y = (getHeight() - image.getHeight())/2;
                g.drawImage(image, x, y, this);
            }
        }

        public void setImage(BufferedImage image) {
            this.image = image;
            Dimension d = new Dimension(image.getWidth(),
                                        image.getHeight());
            setPreferredSize(d);
            revalidate();
            repaint();
        }
    }

    private void loadAllImages() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                BufferedImage[] images =
                    new BufferedImage[paths.length];
                for(int i = 0; i < images.length; i++) {
                    try {
                        URL url = new URL(paths[i]);
                        images[i] = ImageIO.read(url);
                    } catch(IOException e) {
                        System.out.println("read error for: " +
                                            paths[i] + ": " +
                                            e.getMessage());
                    }
                }
                long end = System.currentTimeMillis();
                double time = (end - start)/1000.0;
                String s = "Images loaded in " + time + " seconds";
                JOptionPane.showMessageDialog(null, s,
                                              "images loaded", -1);
/*
                JPanel panel = new JPanel(new GridLayout(2,0));
                JScrollPane scrollPane = new JScrollPane(panel);
                scrollPane.setPreferredSize(new Dimension(500,500));
                for(int i = 0; i < images.length; i++) {
                    panel.add(new JLabel(new ImageIcon(images[i])));
                }
                JOptionPane.showMessageDialog(null, scrollPane,
                                              "images", -1);
*/
            }
        });
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }

    private JScrollPane getImagePanel() {
        displayPanel = new ImageDisplay();
        return new JScrollPane(displayPanel);
    }

    private JPanel getButtonPanel() {
        String[] ids = { "load image", "show image", "load all" };
        EventHandler handler = new EventHandler();
        JPanel panel = new JPanel();
        for(int j = 0; j < ids.length; j++) {
            JButton button = new JButton(ids[j]);
            button.setActionCommand(ids[j]);
            button.addActionListener(handler);
            panel.add(button);
        }
        return panel;
    }

    public static void main(String[] args) {
        DownloadingImages test = new DownloadingImages();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(test.getImagePanel());
        f.add(test.getButtonPanel(), "Last");
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}