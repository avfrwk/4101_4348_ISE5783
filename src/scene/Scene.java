package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;


public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
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
    /** build the scene from XML file
     * @param imageName the name of the XML file
     * @param floderPath the path to the folder of the XML file
     * @return this Scene*/
    public Scene setSceneByXML(String imageName,String floderPath){

        return this;
    }
    /** build the scene from XML file inside the current directory
     * @param imageName the name of the XML file
     * @return this Scene*/
    public Scene setSceneByXML(String imageName){
        return this.setSceneByXML(imageName,System.getProperty("user.dir"));
    }

}
