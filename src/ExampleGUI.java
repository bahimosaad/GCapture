/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bahy
 */
import SK.gnome.capabilities.MorenaCapabilities;
import SK.gnome.morena.MorenaImage;
import SK.gnome.twain.TwainException;
import SK.gnome.twain.TwainManager;
import SK.gnome.twain.TwainSource;
import com.asprise.util.tiff.TIFFWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ExampleGUI implements ActionListener {

    private JFrame frame;
    private JDialog toolboxOne;
    private JButton buttonOne,  buttonTwo,  buttonThree;
    private JLabel textLabel;
    private JTextField field;

    public ExampleGUI() {
        frame = new JFrame("ExampleGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        toolboxOne = new JDialog(frame, "Tool Box");
        Dimension b = new Dimension(150, 40);
        buttonOne = new JButton("Button one");
        buttonOne.setActionCommand("one");
        buttonOne.addActionListener(this);
        buttonOne.setPreferredSize(b);
        buttonOne.setMinimumSize(b);
        buttonOne.setMaximumSize(b);
        buttonTwo = new JButton("Button two");
        buttonTwo.setActionCommand("two");
        buttonTwo.addActionListener(this);
        buttonTwo.setPreferredSize(b);
        buttonTwo.setMinimumSize(b);
        buttonTwo.setMaximumSize(b);
        buttonThree = new JButton("Button three");
        buttonThree.setActionCommand("three");
        buttonThree.addActionListener(this);
        buttonThree.setPreferredSize(b);
        buttonThree.setMinimumSize(b);
        buttonThree.setMaximumSize(b);
        textLabel = new JLabel("Button clicked: ");
        field = new JTextField(10);
        field.setText("none");
        field.setEditable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if ("one".equals(e.getActionCommand())) {
            try {
                TwainSource source = TwainManager.selectSource(null);
                int count = 0;
               if (source != null) {
                source.setFeederEnabled(true);
                source.setAutoFeed(true);
                source.setIndicators(false);
                source.setVisible(false);
                //  source.setTransferCount(5);

                do {

                    MorenaImage image = new MorenaImage(source);
                    Image img = Toolkit.getDefaultToolkit().createImage(image);

                    BufferedImage bimg = ImageGenerator.createBufferedImage(img);
                    TIFFWriter.createTIFFFromImages(new BufferedImage[]{bimg}, new File("C:/test/test" + count + ".tif"));


                     field.setText("Button one "+count++);



                }while(source.hasMoreImages());
               }
            } catch ( Exception ex) {
                Logger.getLogger(ExampleGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if ("two".equals(e.getActionCommand())) {
            field.setText("Button two");
        } else if ("three".equals(e.getActionCommand())) {
            field.setText("Button three");
        }
    }

    public void createAndShowGUI() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        labelPanel.add(textLabel);
        labelPanel.add(field);
        frame.getContentPane().add(labelPanel);
        frame.pack();
        frame.setVisible(true);
        buttonPanel.add(buttonOne);
        buttonPanel.add(buttonTwo);
        buttonPanel.add(buttonThree);
        toolboxOne.getContentPane().add(buttonPanel);
        toolboxOne.pack();
        toolboxOne.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new ExampleGUI().createAndShowGUI();
            }
        });
    }
}
