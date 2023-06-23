import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.WHITE;

public class carScene {


   /*@Test
   public void raceCar(){
      Point a=new Point(0,0.5,0.2);
      Point b=new Point(0,-0.5,0.2);
      Point c=new Point(3,0.5,1);
      Point d=new Point(3,-0.5,1);
      Point a2=new Point(0,0.5,-0.2);
      Point b2=new Point(0,-0.5,-0.2);
      Point c2=new Point(3,0.5,-1);
      Point d2=new Point(3,-0.5,-1);
      Point e=new Point(3,1.5,1);
      Point e2=new Point(3,1.5,-1);
      Point f=new Point(3,-1.5,1);
      Point f2=new Point(3,-1.5,-1);
      Point g1=new Point(3,1.5,0.2);
      Point g2=new Point(3,-1.5,0.2);
      Point g3=new Point(3,1.5,-0.2);
      Point g4=new Point(3,-1.5,-0.2);
      Point h1=new Point(6,1.5,1);
      Point h2=new Point(6,-1.5,1);
      Point h3=new Point(6,1.5,-1);
      Point h4=new Point(6,-1.5,-1);


      //סגול אטום
      Polygon frontRectangle1= new Polygon(a, b, c, d);
      Polygon frontRectangle2= new Polygon(a2, b2, c2, d2);
      Triangle front1= new Triangle(a,c,e);
      Triangle front2= new Triangle(a2,c2,e2);
      Triangle front3= new Triangle(b,d,f);
      Triangle front4= new Triangle(b2,d2,f2);
      Triangle frontSide1= new Triangle(a,g1,e);
      Triangle frontSide2= new Triangle(b,g2,e2);
      Triangle frontSide3= new Triangle(a2,g3,f);
      Triangle frontSide4= new Triangle(b2,g4,f2);
      Polygon frontSRectangle1= new Polygon(a, g1, g3, a2);
      Polygon frontSRectangle2= new Polygon(b, g2, g4, b2);
      Polygon behinde1= new Polygon(e, h1, h3, e2);
      Polygon behinde2= new Polygon(e2, h3, h4, f2);
      Polygon behinde3= new Polygon(f2, h4, h2, f);
      Polygon behinde4= new Polygon(f, h2, h1, e);
      Polygon behind =new Polygon(h3, h2, h1, h4);

   }*/
    @Test
    public void buildCar() {
        Scene carScene= new Scene("car")
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
        Camera carCamera=new Camera(new Point(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(1, 0, 0))
                .setVPSize(200, 200).setVpDistance(1000)                                                                  //
                .setRayTracer(new RayTracerBasic(carScene));
        // Car Body (Polygon)
        Polygon carBody = new Polygon(
                new Point(0, 0, 0),
                new Point(0, 2, 0),
                new Point(2, 2, 0),
                new Point(3, 1, 0),
                new Point(3, 0, 0)
        );

        // Car Roof (Polygon)
        Polygon carRoof = new Polygon(
                new Point(0, 2, 0),
                new Point(0, 2, 1),
                new Point(2, 2, 1),
                new Point(2, 2, 0)
        );

        // Car Front Windshield (Triangle)
        Triangle carWindshield = new Triangle(
                new Point(2, 2, 0),
                new Point(2, 2, 1),
                new Point(3, 1, 0)
        );

        // Car Rear Windshield (Triangle)
        Triangle carRearWindshield = new Triangle(
                new Point(0, 0, 0),
                new Point(0, 0, 1),
                new Point(3, 0, 0)
        );

        // Car Front Wheel (Sphere)
        Sphere carFrontWheel = new Sphere(0.5, new Point(1, 0, 0));

        // Car Rear Wheel (Sphere)
        Sphere carRearWheel = new Sphere(0.5, new Point(1, 2, 0));

        // Car Axle (Cylinder)
        Cylinder carAxle = new Cylinder(0.1, new Ray(new Point(1, 0, -0.5), new Vector(0, 0, 1)), 1);
        // Add the car components to the scene
        Material material=new Material().setKs(0.8).setShininess(60);
        Color color=new Color(11,61,41);
        carScene.geometries.add(
                carBody.setMaterial(material).setEmission(color),
                carRoof.setMaterial(material).setEmission(color),
                carWindshield.setMaterial(material).setEmission(color),
                carRearWindshield.setMaterial(material).setEmission(color),
                carFrontWheel.setMaterial(material).setEmission(color),
                carRearWheel.setMaterial(material).setEmission(color),
                carAxle.setMaterial(material).setEmission(color)
        );

        // Set up lights and camera
        carScene.lights.add(
                // new DirectionalLight(new Color(700, 400, 400)
                //,new Vector(0,0,-1))
                new SpotLight(new Color(700, 400, 400),
                        new Point(40, 40, 115),
                        new Vector(-1, -1, -4))
                        .setKl(4E-4).setKq(2E-5)
        );
        carCamera.setImageWriter(new ImageWriter("carImage", 600, 600))
                // Render the image
                .renderImage()
                .writeToImage();//
    }
}
