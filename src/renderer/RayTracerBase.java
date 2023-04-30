package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
/**abstract class is a base of ray tracer*/
public abstract class RayTracerBase {
    protected Scene scene;
    /** constructing the RayTracerBase from {@link scene.Scene}.
     * @param scene the scene*/
    RayTracerBase(Scene scene){
        this.scene=scene;
    }
    /** tracing ray
     * @param ray the ray to trace
     * @return the Color of the closet intersection*/
    abstract public Color traceRay(Ray ray);
}
