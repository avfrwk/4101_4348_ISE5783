package primitives;

public class Point {
    final Double3 xyz;
    Point(Double3 b){
        this.xyz=b;
    }
    public Point(double x,double y,double z){
        this.xyz=new Double3(x,y,z);
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

    public Vector subtract(Point b){
        return new Vector(this.xyz.subtract(b.xyz));
    }
    public Point add(Vector b){
        return new Point(this.xyz.add(b.xyz));
    }
    public double distanceSquared(Point b){
        return (this.xyz.d1-b.xyz.d1)*(this.xyz.d1-b.xyz.d1)+(this.xyz.d2-b.xyz.d2)*(this.xyz.d2-b.xyz.d2)+(this.xyz.d3-b.xyz.d3)*(this.xyz.d3-b.xyz.d3);
    }
    public double distance(Point b){
        return Math.sqrt(this.distanceSquared(b));
    }
}

