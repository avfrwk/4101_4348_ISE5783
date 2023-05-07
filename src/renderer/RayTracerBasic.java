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
     * @param ray the ray from the camera to the point
     * @return the Color of the point*/
    public Color calcColor(GeoPoint geoPoint,Ray ray){
        return this.scene.ambientLight.getIntensity()
                .add(this.calcLocalEffects(geoPoint,ray));
    }
    /** calculate the local effects color of point in the scene
     * @param geoPoint the point that calculates its value
     * @param ray the ray from the camera to the point
     * @return the Color of the point*/
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray){
        Geometry geometry=geoPoint.geometry;
        Point point=geoPoint.point;
        Color color=geometry.getEmission();
        Vector l,n=geometry.getNormal(point),v=ray.getDir();
        double nv = Util.alignZero((n.dotProduct(v)));
        Material material = geometry.getMaterial();
        if (nv == 0) return color;
        for (LightSource lightSource : scene.lights) {
            l = lightSource.getL(point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Color iL = lightSource.getIntensity(point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }
    private Double3 calcDiffusive(Material material,double nl){
        return material.kD.scale(Math.abs(nl));
    }
    private Double3 calcSpecular(Material material,Vector n,Vector l,double nl,Vector v){
        Vector r=l.subtract(n.scale(2*nl));
        return material.kS.scale(Math.pow(Math.max(0,v.scale(-1).dotProduct(r)),material.nShininess));
    }
}
