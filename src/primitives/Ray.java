package primitives;

/** This class represents a ray*/
public class Ray {
    final private Point p0;
    final private Vector dir;
    /** Constructor to initialize Ray base on start point and direction
     * @param p the start point
     * @param v the direction*/
    public Ray(Point p, Vector v) {
        this.p0 = p;
        this.dir = v.normalize();
    }
    /** get the start point
     * @return  the start point of the ray*/
    public Point getP0() {
        return p0;
    }
    /** get the direction
     * @return the direction of the ray*/
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.p0.equals(other.p0) && this.dir.equals(other.dir);
        return false;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}

