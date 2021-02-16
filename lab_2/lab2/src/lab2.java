import java.util.Scanner;

public class lab2 {
    public static void main(String[] args){
        Scanner myObj = new Scanner(System.in);
        // array of numbers for points
        int[] coord = new int[9];

        // asking for 9 numbers to create 3 points, one by one
        for (int i = 0; i < 9; i++) {
            System.out.println("Enter " + i + " coordinate");
            coord[i] = Integer.parseInt(myObj.nextLine());
        }
        Point3d point_1 = new Point3d(coord[0], coord[1], coord[2]);
        Point3d point_2 = new Point3d(coord[3], coord[4], coord[5]);
        Point3d point_3 = new Point3d(coord[6], coord[7], coord[8]);

        // computing area of triangle
        computeArea(point_1, point_2, point_3);
    }

    // computes area of triangle by 3 point in 3d
    public static double computeArea(Point3d point_1, Point3d point_2, Point3d point_3){

        // return 0 if either of 2 points are equal
        if (Point3d.isEqual(point_1, point_2) || Point3d.isEqual(point_1, point_3) || Point3d.isEqual(point_2, point_3)) {
            System.out.println("Two of the points are equal");
            return 0;
        }

        // a b c are sides of triangle
        double a = point_1.distanceTo(point_2);
        double b = point_2.distanceTo(point_3);
        double c = point_3.distanceTo(point_1);
        // s - half of perimeter
        double s = (a + b + c) / 2;
        // S - area by herons formula
        double S = Math.sqrt( s * (s - a) * (s - b) * (s - c) );
        System.out.println("Area = " + S);
        return S;
    }
}
