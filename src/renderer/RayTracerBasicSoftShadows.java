package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import lighting.PointLight;
import primitives.*;
import scene.Scene;

public class RayTracerBasicSoftShadows extends RayTracerBasic{

    int shadowRayesAmountSq=3;
    /** constructing the RayTracerBasicSoftShadows from {@link scene.Scene}.
     * @param scene the scene*/
    public RayTracerBasicSoftShadows(Scene scene){
        super(scene);
    }
    /**function to set the square root amount of shadow rays*/
    public RayTracerBasicSoftShadows setsShadowRayesAmountSq(int nSq){
        if((Math.log(nSq-1)/Math.log(2))%1!=0){
            throw new IllegalArgumentException("the amount square root is not 2^k+1 number");
        }
        this.shadowRayesAmountSq=nSq;
        return this;
    }
    /**this method calc the average ktr coefficient between point and light source by using ray beam
     * @param geoPoint the point
     * @param lightSource the light source
     * @param n the normal to the point
     * @param nv the dot product between n and the incoming ray
     * @return the ktr coefficient*/
    @Override
    protected Double3 calcLocalShadowEffectsKtr(Intersectable.GeoPoint geoPoint, LightSource lightSource,Vector l, Vector n, double nv,double nl){
        if(lightSource instanceof PointLight){
            RayBeam rayBeam =new RayBeam(new Ray(geoPoint.point,l.scale(-1)),shadowRayesAmountSq
                    ,((PointLight)lightSource).getCenter(),
                    2,2);
            Ray[][] rays=rayBeam.getRays();
            Double3 ktr=Double3.ZERO;
            for (Ray[] i:rays){
                for (Ray j:i){
                    ktr=ktr.add(super.calcLocalShadowEffectsKtr(geoPoint,lightSource,j.getDir().scale(-1), n,nv,nl));
                }
            }
            return ktr.reduce(shadowRayesAmountSq*shadowRayesAmountSq);
        }else{
            return super.calcLocalShadowEffectsKtr(geoPoint, lightSource, l, n, nv,nl);
        }
    }

}
