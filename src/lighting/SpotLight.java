package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector dir;
    /**construct the PointLight from intensity, position and direction
     * @param intensity the intensity of the SpotLight
     * @param position the position of the SpotLight
     * @param direction the direction of the SpotLight*/
    public SpotLight(Color intensity,Point position,Vector direction){
        super(intensity,position);
        this.dir=direction.normalize();
    }
    public Color getIntensity(Point p){
        return super.getIntensity(p).scale(Math.max(0,this.dir.dotProduct(this.getL(p))));
    }
    public Vector getL(Point p){
        return this.p0.subtract(p);
    }

}
