package renderer;

import geometries.Geometry;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

public class RayTracerBasic extends RayTracerBase{
    /**the distance to move the point before checking if there are geometries
     *  between the geometry and a light source at specific point*/
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 MIN_CALC_COLOR_K = new Double3(0.001);
    private static final Double3 INITIAL_K = Double3.ONE;
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
        GeoPoint geoPoint = findClosestIntersection(ray);
        if(geoPoint==null){
            return this.scene.background;
        }
        return this.calcColor(geoPoint,ray);
    }
    /** finding the closet geopoint on a ray
     * @param ray the ray to search on
     * @return the closet geopoint on the ray*/
    private GeoPoint findClosestIntersection(Ray ray){
        return ray.findClosestGeoPoint(this.scene.geometries.findGeoIntersections(ray));
    }
    /** calculate the color of point in the scene
     * @param geoPoint the point that calculates its value
     * @param ray the ray from the camera to the point
     * @return the Color of the point*/
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray,MAX_CALC_COLOR_LEVEL,INITIAL_K)
                .add(this.scene.ambientLight.getIntensity());
    }
    /** calculate the color of point in the scene
     * @param geoPoint the point that calculates its value
     * @param ray the ray from the camera to the point
     * @param level current level for the recursion
     * @param k min calcColor
     * @return the Color of the point*/
    public Color calcColor(GeoPoint geoPoint,Ray ray,int level,Double3 k){
        Color color = this.calcLocalEffects(geoPoint,ray,k);
        return 1 == level ? color :color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }
    /** construct reflected ray at point in geometry
     * @param n normal to geometry at specific point
     * @param p point on geometry
     * @param vRay the direction of the coming ray
     * @return the constructed ray*/
    private Ray constructReflectedRay(Vector n,Point p,Vector vRay){
        double nl=Util.alignZero(n.dotProduct(vRay));
        Vector nVector = n.scale(nl < 0 ? 1 : -1);
        Vector retVRay=vRay.subtract(nVector.scale(vRay.dotProduct(nVector)*2));
        Point retPoint=p.add(retVRay.scale(DELTA));
        return new Ray(retPoint,retVRay);
    }
    /** construct Refracted ray at point in geometry
     * @param p point on geometry
     * @param vRay the direction of the coming ray
     * @return the constructed ray*/
    private Ray constructRefractedRay(Point p,Vector vRay){
        return new Ray(p.add(vRay.scale(DELTA)),vRay);
    }

    /** calculate the global  effects color of point in the scene
     * @param gp the point that calculates its value
     * @param ray the ray from the camera to the point
     * @param level current level for the recursion
     * @param k min calcColor
     * @return the global effects Color of the point*/
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Double3 kr = gp.geometry.getMaterial().kR, kkr =kr.product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)){
            Ray reflectedRay = constructReflectedRay(gp.geometry.getNormal(gp.point)
                    , gp.point, ray.getDir());
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint!=null)
                color =  color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
        }
        Double3 kt =  gp.geometry.getMaterial().kT, kkt = kt.product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)){
            Ray refractedRay = constructRefractedRay(gp.point, ray.getDir());
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint!=null)
                color =  color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
        }
        return color;

    }
    /**this method calc the ktr coefficient between point and light source
     * @param geoPoint the point
     * @param lightSource the light source
     * @param n the normal to the point
     * @param nv the dot product between n and the incoming ray
     * @return the ktr coefficient*/
    protected Double3 calcLocalShadowEffectsKtr(GeoPoint geoPoint,LightSource lightSource,Vector l,Vector n,double nv,double nl){
        //double nl = Util.alignZero(n.dotProduct(l));
       // if (nl * nv > 0){
            return transparency(geoPoint,l, n,nl,lightSource);
       // }
       // else return Double3.ZERO;
    }

    /** calculate the local effects color of point in the scene
     * @param geoPoint the point that calculates its value
     * @param ray the ray from the camera to the point
     * @return the local effects cColor of the point*/
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray,Double3 k){
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
            if (nl * nv > 0){
                Double3 ktr=calcLocalShadowEffectsKtr(geoPoint,lightSource,l,n,nv,nl);
                //Double3 ktr = transparency(geoPoint,l, n,nl,lightSource);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K))
                //if(unshaded(geoPoint,l,n,nl,lightSource))
                {
                    Color iL = lightSource.getIntensity(point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }
    /** calculates the opacity between point and light source
     @param gp the geopoint to check
     @param l vector from the light source to the geopoint
     @param n the normal to the geopoint
     @param nl the dot product between n and l
     @return the opacity*/
    protected Double3 transparency(GeoPoint gp, Vector l, Vector n, double nl, LightSource lightSource){
        Vector lightDirection = l.scale(-1);
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.
                findGeoIntersections(lightRay,lightSource.getDistance(gp.point));
        Double3 ktr = Double3.ONE;
        if(intersections==null)
            return ktr;
        for(GeoPoint intersection:intersections){
            ktr=ktr.product(intersection.geometry.getMaterial().kT);
        }
        return ktr;
    }
    private Double3 calcDiffusive(Material material,double nl){
        return material.kD.scale(Math.abs(nl));
    }
    private Double3 calcSpecular(Material material,Vector n,Vector l,double nl,Vector v){
        Vector r=l.subtract(n.scale(2*nl));
        return material.kS.scale(Math.pow(Math.max(0,v.scale(-1).dotProduct(r)),material.nShininess));
    }
}
