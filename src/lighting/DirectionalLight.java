package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector dir;
    /**construct the PointLight from intensity and direction
     * @param intensity the intensity of the DirectionalLight
     * @param direction the direction of the DirectionalLight*/
    public DirectionalLight(Color intensity,Vector direction){
        super(intensity);
        this.dir=direction.normalize();
    }
    public Color getIntensity(Point p){
        return super.getIntensity();
    }
    public Vector getL(Point p){
        return this.dir;
    }

}
