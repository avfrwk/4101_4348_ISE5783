package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector dir;
    private double narrowBeam=1;
    /**construct the PointLight from intensity, position and direction
     * @param intensity the intensity of the SpotLight
     * @param position the position of the SpotLight
     * @param direction the direction of the SpotLight*/
    public SpotLight(Color intensity,Point position,Vector direction){
        super(intensity,position);
        this.dir=direction.normalize();
    }
    public Color getIntensity(Point p){
        double z=this.dir.dotProduct(this.getL(p));
        return super.getIntensity(p).scale(
                Math.max(0,Math.pow(z,narrowBeam)));
    }
    public SpotLight setNarrowBeam(double narrowBeam){
        this.narrowBeam=narrowBeam;
        return this;
    }
}
