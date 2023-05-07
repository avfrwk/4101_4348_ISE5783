package lighting;

import primitives.Color;
import primitives.Double3;

/**this class represent the ambient light in the scene*/
public class AmbientLight extends Light{
    public static AmbientLight NONE=new AmbientLight(Color.BLACK,Double3.ZERO);
    /**construct from IA and KA
     * @param IA IA
     * @param KA KA*/
    public AmbientLight(Color IA,Double3 KA){
        super(IA.scale(KA));
    }
    /**construct from KA
     * @param KA KA*/
    public AmbientLight(double KA){
        super(Color.BLACK.scale(KA));
    }

}
