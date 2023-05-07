package geometries;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**This interface will serve as the basis for all the geometric shapes*/
public abstract class Geometry implements Intersectable{
    /** get normal to specific point on the Geometry
     * @param point the point on the Geometry
     * @return the normal to the point
     * */
    abstract public Vector getNormal(Point point);
    protected Color emission=Color.BLACK;

    /** get the emission on the Geometry
     * @return the emission
     * */
    public Color getEmission() {
        return emission;
    }

    /** set the emission on the Geometry
     * @param color to change the emission
     * */
    public void setEmission(Color emission) {
        this.emission = emission;
    }
}
