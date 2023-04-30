package lighting;

import primitives.Color;
import primitives.Double3;

/**this class represent the ambient light in the scene*/
public class AmbientLight {
    private Color intensity;
    public static AmbientLight NONE=new AmbientLight(Color.BLACK,Double3.ZERO);

    public AmbientLight(Color IA,Double3 KA){
        this.intensity=new Color(IA.scale(KA));
    }

    public AmbientLight(double KA){
        this.intensity=new Color().scale(KA);
    }

    public Color getIntensity() {
        return intensity;
    }
}
