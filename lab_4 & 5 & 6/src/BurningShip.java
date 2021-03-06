import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator{

    public static final int MAX_ITERATIONS = 2000;


    public void getInitialRange(Rectangle2D.Double range) {

        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }


    public void recenterAndZoomRange(Rectangle2D.Double range,
                                     double centerX, double centerY, double scale) {

        double newWidth = range.width * scale;
        double newHeight = range.height * scale;

        range.x = centerX - (newWidth / 2);
        range.y = centerY - (newHeight / 2);
        range.width = newWidth;
        range.height = newHeight;
    }

    public int numIterations(double cx, double cy) {
        double t;
        double x = 0;
        double y = 0;

        for (int k = 0; k < MAX_ITERATIONS; k++) {

            t = x * x - y * y + cx;
            y = Math.abs(2 * x * y) + cy;
            x = t;
            if (x * x + y * y > 4) return k;

        }
        return -1;
    }

    @Override
    public String toString() {
        return "Burning Ship";
    }
}
