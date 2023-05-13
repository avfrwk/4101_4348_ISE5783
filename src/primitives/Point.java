package primitives;

/** This class represents a point*/
public class Point {
    /** the point coordinates*/
    final Double3 xyz;
    public static final Point ZERO=new Point(Double3.ZERO);
    /** Constructor to initialize Point based on Double3 object
     * @param b the Double3 object that containing the point coordinates*/
    Point(Double3 b){
        this.xyz=b;
    }
    /** Constructor to initialize Vector based on three number values
     * @param x first coordinate value
     * @param y second coordinate value
     * @param z third coordinate value */
    public Point(double x,double y,double z){
        this.xyz=new Double3(x,y,z);
    }
    /** Constructor to initialize Point based on String of 3 double, separated by spaces
     * @param str String of 3 double, separated by spaces*/
    public Point (String str){
        this.xyz=new Double3(str);
    }
    public double getX(){
        return xyz.d1;
    }
    public double getY(){
        return xyz.d2;
    }
    public double getZ(){
        return xyz.d3;
    }
    /** subtract two points
     * @param b the another point
     * @return   the vector between to points */
    public Vector subtract(Point b){
        return new Vector(this.xyz.subtract(b.xyz));
    }
    /** add vector to point
     * @param b the vector to add
     * @return   the point at the tail of the vector */
    public Point add(Vector b){
        return new Point(this.xyz.add(b.xyz));
    }
    /** calculate the distance between two points, Squared
     * @param b the another point
     * @return   the distance between the points, Squared */
    public double distanceSquared(Point b){
        return (this.xyz.d1-b.xyz.d1)*(this.xyz.d1-b.xyz.d1)+(this.xyz.d2-b.xyz.d2)*(this.xyz.d2-b.xyz.d2)+(this.xyz.d3-b.xyz.d3)*(this.xyz.d3-b.xyz.d3);
    }
    /** calculate the distance between two points
     * @param b the another point
     * @return   the distance between the points */
    public double distance(Point b){
        return Math.sqrt(this.distanceSquared(b));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point other)
            return this.xyz.equals(other.xyz);
        return false;
    }
    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}

