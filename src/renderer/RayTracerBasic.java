package renderer;

import geometries.Geometry;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

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
        GeoPoint geoPoint=ray.findClosestGeoPoint(this.scene.geometries.findGeoIntersections(ray));
        if(geoPoint==null){
            return this.scene.background;
        }
        return this.calcColor(geoPoint,ray);
    }
    /** calculate the color of point in the scene
     * @param geoPoint the point that calculates its value
     * @return the Color of the point*/
    public Color calcColor(GeoPoint geoPoint,Ray ray){
        Geometry geometry=geoPoint.geometry;
        Point point=geoPoint.point;
        Double3 kD=geometry.getMaterial().kD;
        Double3 kS=geometry.getMaterial().kS;
        int Nsh=geometry.getMaterial().nShininess;
        Color color=this.scene.ambientLight.getIntensity().add(geometry.getEmission());
        for (LightSource i:this.scene.lights){
            Vector l=i.getL(point);
            Vector n=geometry.getNormal(point);
            Vector r=l.subtract(n.scale(l.dotProduct(n)*2));

            color=color.add(
                kD.scale(Math.abs(
                   l.dotProduct(n)))
                .add(
                        kS.scale(Math.max(0, ray.getDir().scale(-1).dotProduct(r))))
            );
        }
        return color;
    }

}
