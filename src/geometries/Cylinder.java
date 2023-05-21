package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/** This class represent a cylinder*/

public class Cylinder extends Tube{
    final private double height;
    /** Constructor to initialize Cylinder based on radius, the center axis and height
     * @param Radius first number value
     * @param ray second number value
     * @param Height third number value */
    public Cylinder(double Radius, Ray ray, double Height){
        super(Radius, ray);
        this.height=Height;
    }

    /** get the height of the cylinder
     * @return the height of the cylinder*/
    public double getHeight() {
        return height;
    }

    /** get the normal to the cylinder at specific point
     *  @param point the point of normal's head
     * @return normal to the cylinder at specific point*/
    @Override
    public Vector getNormal(Point point){
        Vector dir=this.ray.getDir();
        Point P00= this.ray.getP0();
        /*if(P00.distanceSquared(point)<this.radius*this.radius){
            return dir.scale(-1);

        }if (P00.add(dir.scale(this.height)).distanceSquared(point)<this.radius*this.radius) {
            return dir;
        }*/
        Point heightP0=this.ray.getPoint(this.height);
        //if(point.equals(this.ray.getP0())||point.subtract(P00).dotProduct(dir)==0){
        //    return dir.scale(-1);
        //}
        if(point.equals(heightP0)||point.equals(P00)||point.subtract(P00).dotProduct(dir)==0||point.subtract(heightP0).dotProduct(dir)==0){
            return dir;
        }
        return super.getNormal(point);
    }

    private Point findIntersectionsHelper(Ray ray,Point p0,Vector normal,double radius, double maxDistance){
        double t=normal.dotProduct(ray.getDir());
        if(!Util.isZero(t)&&!ray.getP0().equals(p0)){
            t=normal.dotProduct(p0.subtract(ray.getP0()))/t;
            if(Util.alignZero(t)>0&&Util.alignZero(t-maxDistance)<=0){
                Point ret=ray.getPoint(t);
                if(Util.alignZero(ret.distanceSquared(p0)-radius*radius)<=0){
                    return ret;
                }
            }
        }
        return null;
    }
    private Point findIntersectionsHelper(Ray ray,Point p0,Vector normal,double radius){
        double t=normal.dotProduct(ray.getDir());
        if(!Util.isZero(t)&&!ray.getP0().equals(p0)){
            t=normal.dotProduct(p0.subtract(ray.getP0()))/t;
            if(Util.alignZero(t)>0){
                Point ret=ray.getPoint(t);
                if(Util.alignZero(ret.distanceSquared(p0)-radius*radius)<=0){
                    return ret;
                }
            }
        }
        return null;
    }
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        List<GeoPoint> infinityPoints=super.findGeoIntersectionsHelper(ray);
        Point heightP0=this.ray.getPoint(this.height);
        Point P00=this.ray.getP0();
        Vector dir=this.ray.getDir();
        boolean flag1=false,flag2=false;
        if(infinityPoints!=null){
            int size=infinityPoints.size();
            Point pt;
            if(size==2){
                if (Util.alignZero(dir.dotProduct(infinityPoints.get(1).point.subtract(P00))) > 0 && Util.alignZero(dir.dotProduct(infinityPoints.get(1).point.subtract(heightP0))) < 0) {
                    flag2=true;
                }
            }
            if(size>=1) {
                if (Util.alignZero(dir.dotProduct(infinityPoints.get(0).point.subtract(P00))) > 0 && Util.alignZero(dir.dotProduct(infinityPoints.get(0).point.subtract(heightP0))) < 0) {
                    flag1 = true;
                }
            }
        } if(flag1&&flag2){
            return infinityPoints;
        }
        Point insect3=this.findIntersectionsHelper(ray,P00,dir,this.radius,maxDistance);
        Point insect4=this.findIntersectionsHelper(ray,heightP0,dir,this.radius,maxDistance);
        if (flag1){
            if(insect3!=null){
                return List.of(new GeoPoint(this,infinityPoints.get(0).point),
                        new GeoPoint(this,insect3));
            }
            if(insect4!=null){
                return List.of(new GeoPoint(this,infinityPoints.get(0).point),
                        new GeoPoint(this,insect4));
            }
            return List.of(new GeoPoint(this,infinityPoints.get(0).point));
        }if(flag2){
            if(insect3!=null){
                return List.of(new GeoPoint(this,infinityPoints.get(1).point),
                        new GeoPoint(this,insect3));
            }
            if(insect4!=null){
                return List.of(new GeoPoint(this,infinityPoints.get(1).point)
                        ,new GeoPoint(this,insect4));
            }
            return List.of(new GeoPoint(this,infinityPoints.get(1).point));
        }if(insect3!=null&&insect4!=null){
            return List.of(new GeoPoint(this,insect4),
                    new GeoPoint(this,insect3));
        }if(insect3!=null){
            return List.of(new GeoPoint(this,insect3));
        }if(insect4!=null){
            return List.of(new GeoPoint(this,insect4));
        }
        return null;
    }


    /** get list of intersection between ray and Cylinder
     * @param ray the ray
     * @return list of intersections
     * */
    @Override
    public List<GeoPoint>findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> infinityPoints=super.findGeoIntersectionsHelper(ray);
        Point heightP0=this.ray.getPoint(this.height);
        Point P00=this.ray.getP0();
        Vector dir=this.ray.getDir();
        boolean flag1=false,flag2=false;
        if(infinityPoints!=null){
            int size=infinityPoints.size();
            Point pt;
            if(size==2){
                if (Util.alignZero(dir.dotProduct(infinityPoints.get(1).point.subtract(P00))) > 0 && Util.alignZero(dir.dotProduct(infinityPoints.get(1).point.subtract(heightP0))) < 0) {
                    flag2=true;
                }
            }
            if(size>=1) {
                if (Util.alignZero(dir.dotProduct(infinityPoints.get(0).point.subtract(P00))) > 0 && Util.alignZero(dir.dotProduct(infinityPoints.get(0).point.subtract(heightP0))) < 0) {
                    flag1 = true;
                }
            }
        } if(flag1&&flag2){
            return infinityPoints;
        }
        Point insect3=this.findIntersectionsHelper(ray,P00,dir,this.radius);
        Point insect4=this.findIntersectionsHelper(ray,heightP0,dir,this.radius);
        if (flag1){
            if(insect3!=null){
                return List.of(new GeoPoint(this,infinityPoints.get(0).point),
                        new GeoPoint(this,insect3));
            }
            if(insect4!=null){
                return List.of(new GeoPoint(this,infinityPoints.get(0).point),
                        new GeoPoint(this,insect4));
            }
            return List.of(new GeoPoint(this,infinityPoints.get(0).point));
        }if(flag2){
            if(insect3!=null){
                return List.of(new GeoPoint(this,infinityPoints.get(1).point),
                        new GeoPoint(this,insect3));
            }
            if(insect4!=null){
                return List.of(new GeoPoint(this,infinityPoints.get(1).point)
                        ,new GeoPoint(this,insect4));
            }
            return List.of(new GeoPoint(this,infinityPoints.get(1).point));
        }if(insect3!=null&&insect4!=null){
            return List.of(new GeoPoint(this,insect4),
                    new GeoPoint(this,insect3));
        }if(insect3!=null){
            return List.of(new GeoPoint(this,insect3));
        }if(insect4!=null){
            return List.of(new GeoPoint(this,insect4));
        }
        return null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder.height, height) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
}