package lighting;

import primitives.Color;

/**this class represents light*/
abstract class Light {
    private Color intensity;
    /**construct from intensity
     * @param intensity the intensity*/
    protected Light(Color intensity){
        this.intensity=intensity;
    }
    /**get the intensity
     * @return the intensity*/
    public Color getIntensity() {
        return intensity;
    }
}
