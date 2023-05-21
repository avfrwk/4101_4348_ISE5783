package scene;

import geometries.*;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.*;

import java.util.LinkedList;
import java.util.List;


public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights=new LinkedList<>();
    /** Constructor to initialize empty scene
     * @param name the name of the scene */
    public Scene(String name){
        this.name=name;
        this.background=Color.BLACK;
        this.ambientLight=AmbientLight.NONE;
        this.geometries=new Geometries();
    }
    /** set the Background color of the scene
     * @param background the color to set
    * @return this Scene*/
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    /** set the ambientLight of the scene
     * @param ambientLight the AmbientLight to set
     * @return this Scene*/
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    /** set the geometries of the scene
     * @param geometries the geometries to set
     * @return this Scene*/
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    /** set the lights of the scene
     * @param lights the lights to set
     * @return this Scene*/
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
