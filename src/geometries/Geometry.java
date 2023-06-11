package geometries;
//import org.jocl.cl_context;
//import org.jocl.cl_device_id;
//import org.jocl.cl_kernel;
//import org.jocl.cl_program;
import primitives.*;

import java.lang.invoke.MethodHandles;

//import static org.jocl.CL.*;

/**This interface will serve as the basis for all the geometric shapes*/
public abstract class Geometry extends Intersectable{
    /** get normal to specific point on the Geometry
     * @param point the point on the Geometry
     * @return the normal to the point
     * */
    abstract public Vector getNormal(Point point);
    protected Color emission=Color.BLACK;
    private Material material=new Material();
    /** get the emission on the Geometry
     * @return the emission*/
    public Color getEmission() {
        return emission;
    }
    /** get the Material of the Geometry
     * @return the material*/
    public Material getMaterial() {
        return material;
    }
    /** set the emission on the Geometry
     * @param emission the emission to set
     * @return the caller Geometry*/
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    /** set the Material of the Geometry
     * @param material the material to set
     * @return the caller Geometry*/
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

}
