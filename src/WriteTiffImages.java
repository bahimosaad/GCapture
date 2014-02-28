
import com.asprise.util.tiff.TIFFWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
public class WriteTiffImages implements Runnable {

    /**
     * @param args the command line arguments
     */

    private BufferedImage bimg;
    private String path;
    private int dpi;




    @Override
    public void run() {
          try {
            TIFFWriter.preferredResolution = dpi;
            TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg}, new File(path));
            // TODO code application logic here
        } catch (IOException ex) {
            Logger.getLogger(WriteTiffImages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage getBimg() {
        return bimg;
    }

    public void setBimg(BufferedImage bimg) {
        this.bimg = bimg;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



}
