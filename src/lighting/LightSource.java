package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**this class represents light source*/
public interface LightSource{
    /**get the intensity from the light source at specific point
     * @param p the point
     * @return the intensity from the light source*/
    public Color getIntensity(Point p);
    /**get vector from the light source to point
     * @param p the point
     * @return the vector from the light source*/
    public Vector getL(Point p);
}
