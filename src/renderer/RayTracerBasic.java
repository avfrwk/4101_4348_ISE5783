package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase{
    /** constructing the RayTracerBasic from {@link scene.Scene}.
     * @param scene the scene*/
    public RayTracerBasic(Scene scene){
        super(scene);
    }
    /** tracing ray
     * @param ray the ray to trace
     * @return the Color of the closet intersection*/
    @Override
    public Color traceRay(Ray ray){
        Point point=ray.findClosestPoint(this.scene.geometries.findIntersections(ray));
        if(point==null){
            return this.scene.background;
        }
        return this.calcColor(point);
    }
    /** calculate the color of point in the scene
     * @param point the point that calculates its value
     * @return the Color of the point*/
    public Color calcColor(Point point){
        return this.scene.ambientLight.getIntensity();
    }

}
