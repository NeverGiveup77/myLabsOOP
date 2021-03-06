import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class FractalExplorer {

    public FractalExplorer(int size) {

        FractalExplorer.size = size;
        generator.getInitialRange(range);
    }

    public static class FractalWorker extends SwingWorker<Object, Object> {

        private int x = 0;
        private int y = 0;
        int[] row = new int[size];

        public FractalWorker(int y){
            this.y = y;
        }

        @Override
        protected Object doInBackground() throws Exception {
            for(int x = 0; x < size; x++) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x+range.width, size,x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y+range.height, size,y);
                int iterations = generator.numIterations(xCoord, yCoord);

                float hue = 0.7f + (float) iterations / 200f;
                int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                if (iterations != -1) {
                    row[x] = rgbColor;
                } else {
                    row[x] = 0;
                }
            }

            return null;
        }

        @Override
        protected void done() {
            super.done();
            for (int x = 0; x < size; x++) {
                jImageDisplay.drawPixel(x, y, row[x]);
            }
            jImageDisplay.repaint(0,0,y,size,size);
            rowsRemaining--;
            if (rowsRemaining == 0)
                enableUI(true);
        }
    }

    public static class BtnResetListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            generator.getInitialRange(range);
            try {
                drawFractal();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            jImageDisplay.repaint();
        }
    }

    public static class JComboBoxListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            setGenerator((FractalGenerator) jComboBox.getSelectedItem());
            generator.getInitialRange(range);
            try {
                drawFractal();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            jImageDisplay.repaint();
        }
    }

    public static class BtnSaveImgListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JFileChooser jFileChooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            jFileChooser.setFileFilter(filter);
            jFileChooser.setAcceptAllFileFilterUsed(false);
            if (jFileChooser.showSaveDialog(jImageDisplay) == JFileChooser.APPROVE_OPTION){
                File file = jFileChooser.getSelectedFile();
                try {
                    ImageIO.write(jImageDisplay.image, "png", file);
                } catch (IOException ioException) {
                    JOptionPane jOptionPane = new JOptionPane();
                    JOptionPane.showMessageDialog(jImageDisplay, "Cannot save img","cant save img", JOptionPane.ERROR_MESSAGE);
                    ioException.printStackTrace();
                }
            }
        }
    }

    private static int rowsRemaining;

    private static int size = 800;

    private static final Rectangle2D.Double range = new Rectangle2D.Double(-2,-1.5,3,3);

    static FractalGenerator generator;

    public static void setGenerator(FractalGenerator generator) {
        FractalExplorer.generator = generator;
    }

    static MandelBrot mandelBrot = new MandelBrot();
    static Tricorn tricorn = new Tricorn();
    static BurningShip burningShip = new BurningShip();


    private static final JImageDisplay jImageDisplay = new JImageDisplay(800, 800);
    private static final JComboBox jComboBox = new JComboBox();
    private static final JButton btnReset = new JButton("Reset");
    private static final JLabel jLabel = new JLabel();
    private static final JButton btnSaveImg = new JButton();
    private static final JPanel jPanelNorth = new JPanel();
    private static final JPanel jPanelSouth = new JPanel();
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Fractal Explorer");

        btnReset.addActionListener(new BtnResetListener());

        jComboBox.addItem(mandelBrot);
        jComboBox.getSelectedItem();
        jComboBox.addItem(tricorn);
        jComboBox.addItem(burningShip);
        jComboBox.addActionListener(new JComboBoxListener());

        jLabel.setText("explanation");

        btnSaveImg.setText("Save image");
        btnSaveImg.addActionListener(new BtnSaveImgListener());

        jPanelNorth.add(jLabel);
        jPanelNorth.add(jComboBox);

        jPanelSouth.add(btnSaveImg);
        jPanelSouth.add(btnReset);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(jImageDisplay);

        jImageDisplay.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (rowsRemaining == 0) {
                    double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, size, e.getX());
                    double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, size, e.getY());
                    generator.recenterAndZoomRange(range, xCoord, yCoord, 0.7);
                    try {
                        drawFractal();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    jImageDisplay.repaint();
                }
            }
        });

        frame.getContentPane().add(jPanelNorth, BorderLayout.NORTH);
        frame.getContentPane().add(jImageDisplay, BorderLayout.CENTER);
        frame.getContentPane().add(jPanelSouth, BorderLayout.SOUTH);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setResizable (false);
    }

    private static void enableUI(boolean val){
       btnSaveImg.setEnabled(val);
       btnReset.setEnabled(val);
    }

    public static void drawFractal() throws InterruptedException {
        enableUI(false);
        rowsRemaining = size;
        for(int y = 0; y < size; y++) {
            (new FractalWorker(y)).execute();

        }

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setGenerator(new MandelBrot());
                new MandelBrot().getInitialRange(range);
                generator.getInitialRange(range);
                createAndShowGUI();
                try {
                    drawFractal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jImageDisplay.repaint();
            }
        });
    }

}