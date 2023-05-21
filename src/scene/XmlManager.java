package scene;

import geometries.*;
import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static java.lang.Double.parseDouble;

public class XmlManager {
    /** return Geometries inside Geometries Element
     * @param geometriesElement the Element that contains the geometries
     * @return the Geometries inside the Geometries Element*/
    private static Geometries GeometriesFromXmlElement(Element geometriesElement){
        NodeList geometries=geometriesElement.getChildNodes();
        Geometries geos=new Geometries();
        int length=geometries.getLength();
        Element IElement;
        Intersectable intersectable;
        for(int i=0;i<length;++i){
            if(geometries.item(i).getNodeType() == Node.ELEMENT_NODE){
                IElement=(Element) geometries.item(i);
                switch (IElement.getTagName()){
                    case "Geometries":
                        intersectable=GeometriesFromXmlElement(IElement);
                        break;
                    case "Sphere":
                        intersectable=new Sphere(
                                parseDouble(IElement.getAttribute("radius")),
                                new Point(IElement.getAttribute("center"))
                        );
                        break;
                    case "Triangle":
                        intersectable=new Triangle(
                                new Point(IElement.getAttribute("p0")),
                                new Point(IElement.getAttribute("p1")),
                                new Point(IElement.getAttribute("p2"))
                        );
                        break;
                    case "Polygon":
                        String[] sd= IElement.getAttribute("vertices").split(",");
                        Point[] points=new Point[sd.length];
                        for (int j=0;j<sd.length;++j){
                            points[j]=new Point(sd[j]);
                        }
                        intersectable=new Polygon(points);
                        break;
                    case "Plane":
                        if(IElement.hasAttribute("vector")){
                            intersectable=new Plane(
                                    new Point(IElement.getAttribute("p0")),
                                    new Vector(IElement.getAttribute("vector"))
                            );
                        }else{
                            intersectable=new Plane(
                                    new Point(IElement.getAttribute("p0")),
                                    new Point(IElement.getAttribute("p1")),
                                    new Point(IElement.getAttribute("p2"))
                            );
                        }
                        break;
                    case "Tube":
                        intersectable=new Tube(
                                parseDouble(IElement.getAttribute("radius")),
                                new Ray(new Point(IElement.getAttribute("point")),
                                        new Vector(IElement.getAttribute("vector")))
                        );
                        break;
                    case "Cylinder":
                        intersectable=new Cylinder(
                                parseDouble(IElement.getAttribute("radius")),
                                new Ray(new Point(IElement.getAttribute("point")),
                                        new Vector(IElement.getAttribute("vector"))),
                                parseDouble(IElement.getAttribute("height")));
                        break;
                    default:
                        continue;
                }
                geos.add(intersectable);
            }
        }
        return geos;
    }
    /** build the scene from XML file
     * @param fileName the name of the XML file
     * @param floderPath the path to the folder of the XML file
     * @return this Scene*/
    public static Scene setFromXML(String sceneName,String fileName,String floderPath){
        Scene scene=new Scene(sceneName);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(floderPath+'/'+fileName));

            Element sceneElement = (Element) document.getElementsByTagName("Scene").item(0);
            Element IElement;
            scene.background=new Color(Util.parseDouble3(sceneElement.getAttribute("background")));
            IElement=(Element) sceneElement.getElementsByTagName("AmbientLight").item(0);
            scene.ambientLight=new AmbientLight(
                    new Color(Util.parseDouble3(IElement.getAttribute("color"))),
                    Util.parseDouble3(IElement.getAttribute("KA")));

            IElement=(Element)sceneElement.getElementsByTagName("Geometries").item(0);
            scene.geometries=XmlManager.GeometriesFromXmlElement(IElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scene;
    }
    /** build the scene from XML file inside the current directory
     * @param fileName the name of the XML file
     * @return this Scene*/
    public static Scene setFromXML(String sceneName,String fileName){
        return XmlManager.setFromXML(sceneName,fileName,System.getProperty("user.dir"));
    }
}
