import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class JImageDisplay extends javax.swing.JComponent {

    private Dimension dim = new Dimension();
    public java.awt.image.BufferedImage image;

    public JImageDisplay(int width, int height) {

        dim.setSize(width, height);
        setPreferredSize(dim);

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    // for drawing image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0,0,image.getWidth(), image.getHeight(), null);
    }

    public void clearImage() {
        int[] blackRGB = new int[image.getWidth()*image.getHeight()];
        for(int pixel: blackRGB){
            pixel = 0;
        }
        image.setRGB(0,
                0,
                image.getWidth(),
                image.getHeight(),
                blackRGB,
                0,
                image.getWidth());

        repaint();
    }

    public void drawPixel(int x, int y, int rgbColor) {
        image.setRGB(x,y,rgbColor);
    }
}
