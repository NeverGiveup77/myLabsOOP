public class Point3d {
    // ** координата X **/
    private double xCoord;
    // ** координата Y **/
    private double yCoord;
    // ** координата Z **/
    private double zCoord;
    // ** Конструктор инициализации **/
    public Point3d ( double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    // ** Конструктор по умолчанию. **/
    public Point3d () {
    //Вызовите конструктор с двумя параметрами и определите источник.
        this(0, 0, 0);
    }

    // ** Возвращение координаты X **/
    public double getX () {
        return xCoord;
    }
    // ** Возвращение координаты Y **/
    public double getY () {
        return yCoord;
    }
    // ** Возвращение координаты Z **/
    public double getZ () {
        return zCoord;
    }

    // ** Установка значения координаты X. **/
    public void setX ( double val) {
        xCoord = val;
    }
    // ** Установка значения координаты Y. **/
    public void setY ( double val) {
        yCoord = val;
    }
    // ** Установка значения координаты Y. **/
    public void setZ ( double val) {
        zCoord = val;
    }

    public static boolean isEqual(Point3d point3d_1, Point3d point3d_2){
        return point3d_1.getX() == point3d_2.getX() &&
                point3d_1.getY() == point3d_2.getY() &&
                point3d_1.getZ() == point3d_2.getZ();
    }
    public double distanceTo(Point3d point3d_1){
        double distance = Math.sqrt( Math.pow((point3d_1.getX() - this.getX()), 2) +
                    Math.pow((point3d_1.getY() - this.getY()), 2) +
                    Math.pow((point3d_1.getZ() - this.getZ()), 2));
        return distance;
    }
}