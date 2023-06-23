package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    Point p0;
    double kC=1,kL=0,kQ=0;
    /**construct the PointLight from intensity and position
     * @param intensity the intensity of the PointLight
     * @param position the position of the PointLight*/
    public PointLight(Color intensity,Point position){
        super(intensity);
        this.p0=position;
    }
    /**set the kC of this PointLight
     * @param kC kC
     * @return this PointLight*/
    public PointLight setKc(double kC){
        this.kC=kC;
        return this;
    }
    /**set the kL of this PointLight
     * @param kL kL
     * @return this PointLight*/
    public PointLight setKl(double kL){
        this.kL=kL;
        return this;
    }
    /**set the kQ of this PointLight
     * @param kQ kQ
     * @return this PointLight*/
    public PointLight setKq(double kQ){
        this.kQ=kQ;
        return this;
    }
    public Color getIntensity(Point p){
        double distanceSquared=this.p0.distanceSquared(p);
        return super.getIntensity().reduce(kC+kL*Math.sqrt(distanceSquared)+kQ*distanceSquared);
    }
    public Vector getL(Point p){
        return p.subtract(this.p0).normalize();
    }
    @Override
    public double getDistance(Point point){
        return this.p0.distance(point);
    }
    public Point getCenter() {
        return p0;
    }
}
