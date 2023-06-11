package renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/** Testing basic shadows
 * @author Dan */
public class ShadowTests {
   private Intersectable sphere     = new Sphere(60d,new Point(0, 0, -200))                                         //
      .setEmission(new Color(BLUE))                                                                                  //
      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
   private Material      trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

   private Scene         scene      = new Scene("Test scene");
   private Camera        camera     = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
      .setVPSize(200, 200).setVpDistance(1000)                                                                       //
      .setRayTracer(new RayTracerBasic(scene));

   /** Helper function for the tests in this module */
   void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
      scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
      scene.lights.add( //
                       new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                          .setKl(1E-5).setKq(1.5E-7));
      camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere and triangle with point light and shade */
   @Test
   public void sphereTriangleInitial() {
      sphereTriangleHelper("shadowSphereTriangleInitial", //
                           new Triangle(
                                   new Point(-70, -40, 0),
                                   new Point(-40, -70, 0),
                                   new Point(-68, -68, -4)
                           ), //
                           new Point(-100, -100, 200));
   }


   /**
    * Sphere-Triangle shading - move triangle up-right
    */
   @Test
   public void sphereTriangleMove1() {
      sphereTriangleHelper("shadowSphereTriangleMove1", //
              new Triangle(
                      new Point(-60, -30, 0),
                      new Point(-30, -60, 0),
                      new Point(-60, -60, -5)
              ), //
              new Point(-100, -100, 200));
   }

   /**
    * Sphere-Triangle shading - move triangle upper-righter
    */
   @Test
   public void sphereTriangleMove2() {
      sphereTriangleHelper("shadowSphereTriangleMove2", //
              new Triangle(new Point(-50, -15, 0), new Point(-15, -50, 0), new Point(-45, -45, -5)), //
              new Point(-100, -100, 200));
   }
   @Test
   public void sphereTriangleSpot1() {
      sphereTriangleHelper("shadowSphereTriangleSpot1", //
              new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
              new Point(-80, -80, 100));
   }

   /**
    * Sphere-Triangle shading - move spot even more close
    */
   @Test
   public void sphereTriangleSpot2() {
      sphereTriangleHelper("shadowSphereTriangleSpot2", //
              new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
              new Point(-100, -100, 90));
   }



   /** Produce a picture of two triangles lighted by a spot light with a Sphere
    * producing a shading */
   @Test
   public void trianglesSphere() {
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

      scene.geometries.add( //
              new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                      new Point(75, 75, -150)) //
                      .setMaterial(new Material().setKs(0.8).setShininess(60)), //
              new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                      .setMaterial(new Material().setKs(0.8).setShininess(60)), //
              new Sphere(30d,new Point(0, 0, -11)) //
                      .setEmission(new Color(BLUE)) //
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
      );
      scene.lights.add( //
              new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                      .setKl(4E-4).setKq(2E-5));

      camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
              .renderImage() //
              .writeToImage();
   }

   /** Produce a picture of ten shapes lighted by many variations of light sources
    * producing a shading */
   /*@Test
   public void raceCar(){
      // TODO: implement test
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
      scene.setAmbientLight(new AmbientLight(new Color(GREEN), new Double3(0.15)));
      scene.lights.add(
              new PointLight(new Color(RED),new Point(50,50,0))
      );
// Car Body (Polygon)
         Polygon carBody1 = new Polygon(
                 new Point(50, 40, 0),
                 new Point(50, 0, 0),
                 new Point(80, 18, 30),
                 new Point(80, 22, 30)
         );
         Polygon carBody2 = new Polygon(
                 new Point(50, 40, 80),
                 new Point(50, 0, 80),
                 new Point(80, 18, 50),
                 new Point(80, 22, 50)
         );

         Polygon carBody3 = new Polygon(
                 new Point(50, 40, 0),
                 new Point(50, 0, 0),
                 new Point(50, 0, 80),
                 new Point(50, 40, 80)
         );
         Polygon carBody4 = new Polygon(
                 new Point(50, 0, 0),
                 new Point(80, 18, 30),
                 new Point(80, 18, 50),
                 new Point(50, 0, 80)
         );

         Polygon carBody5 = new Polygon(
                 new Point(80, 18, 30),
                 new Point(80, 22, 30),
                 new Point(80, 22, 50),
                 new Point(80, 18, 50)
         );
         Polygon carBody6 = new Polygon(
                 new Point(80, 22, 30),
                 new Point(50, 40, 0),
                 new Point(50, 40, 80),
                 new Point(80, 22, 50)

         );


         // Car Body (Polygon)
         Polygon car1 = new Polygon(
                 new Point(-80, 0, 0),
                 new Point(-80, 40, 0),
                 new Point(50, 40, 0),
                 new Point(50, 0, 0)
         );
         Polygon car2 = new Polygon(
                 new Point(-80, 0, 80),
                 new Point(-80, 40, 80),
                 new Point(50, 40, 80),
                 new Point(50, 0, 80)
         );

         Polygon car3 = new Polygon(
                 new Point(-80, 0, 0),
                 new Point(-80, 40, 0),
                 new Point(-80, 40, 80),
                 new Point(-80, 0, 80)
         );
         Polygon car4 = new Polygon(
                 new Point(-80, 40, 0),
                 new Point(50, 40, 0),
                 new Point(50, 40, 80),
                 new Point(-80, 40, 80)
         );

         Polygon car5 = new Polygon(
                 new Point(50, 40, 0),
                 new Point(50, 0, 0),
                 new Point(50, 0, 80),
                 new Point(50, 40, 80)
         );
         Polygon car6 = new Polygon(
                 new Point(-80, 0, 80),
                 new Point(50, 0, 80),
                 new Point(50, 0, 0),
                 new Point(-80, 0, 0)

         );




         Polygon window = new Polygon(
                 new Point(0, 15, 1),
                 new Point(0, 30, 1),
                 new Point(30, 30, 1),
                 new Point(30, 15, 1)
         );

         // Car Roof (Polygon)
         Polygon carRoof = new Polygon(
                 new Point(-70, 40, 30),
                 new Point(-70, 40, 70),
                 new Point(50, 40, 70),
                 new Point(50, 40, 30)
         );

         // Car Front Windshield (Triangle)
         Triangle carWindshield = new Triangle(
                 new Point(50, 40, 0),
                 new Point(50, 40, 30),
                 new Point(80, 0, 0)
         );

         // Car Rear Windshield (Triangle)
         Triangle carRearWindshield = new Triangle(
                 new Point(-80, 0, 0),
                 new Point(-80, 0, 30),
                 new Point(-70, 40, 0)
         );

         // Car Front Wheels (Spheres)
         Cylinder carFrontWheel1 = new Cylinder(10, new Ray(new Point(-40, -10, -15),new Vector(0,0,1)),5);
         Cylinder carFrontWheel2 = new Cylinder(10, new Ray(new Point(-40, -10, 15),new Vector(0,0,1)),5);

         // Car Rear Wheels (Spheres)
         Cylinder carRearWheel1 = new Cylinder(10, new Ray(new Point(30, -10, -15),new Vector(0,0,1)),5);
         Cylinder carRearWheel2 = new Cylinder(10, new Ray(new Point(30, -10, 15),new Vector(0,0,1)),5);


         // Car Axle (Cylinder)
         //Cylinder carAxle = new Cylinder(4, new Ray(new Point(-40, -20, -17), new Vector(0, 0, 1)), 30);

         // Racing Car Tail (Polygon)
         Polygon carTail1 = new Polygon(
                 new Point(-70, 45, 0),
                 new Point(-60, 45, 0),
                 new Point(-60, 45, 30),
                 new Point(-70, 45, 30)
         );
         Polygon carTail2 = new Polygon(
                 new Point(-70, 40, 0),
                 new Point(-60, 40, 0),
                 new Point(-60, 55, 0),
                 new Point(-70, 55, 0)
         );
         Polygon carTail3 = new Polygon(
                 new Point(-70, 40, 30),
                 new Point(-60, 40, 30),
                 new Point(-60, 55, 30),
                 new Point(-70, 55, 30)
         );



         // Add the car components to the scene
         scene.geometries.add(
                 carBody1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(BLUE)),
                 carBody2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(BLUE)),
                 carBody3.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
                 carBody4.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
                 carBody5.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
                 carBody6.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),

                 car1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(BLUE)),
                 car2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(BLUE)),
                 car3.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
                 car4.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
                 car5.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
                 car6.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),


                 carRoof.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(GREEN)),
                 carWindshield.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(YELLOW)),
                 carRearWindshield.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(RED)),
                 carFrontWheel1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(PINK)),
                 carFrontWheel2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(WHITE)),
                 carRearWheel1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(PINK)),
                 carRearWheel2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(GRAY)),
                 //carAxle.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(200, 255, 0)),
                 carTail1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
                 window.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(BLACK)),
                 carTail2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
                 carTail3.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE))

         );
      camera.rotateCameraAroundPointVright(new Point(0,0,0),30);

      scene.lights.add( //
                 new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                         .setKl(4E-4).setKq(2E-5));

         camera.setImageWriter(new ImageWriter("carImage", 600, 600)) //
                 .renderImage() //
                 .writeToImage();
   }
}


/*

// Car Body (Polygon)
      Polygon carBody1 = new Polygon(
              new Point(-80, 0, 0),
              new Point(-80, 40, 30),
              new Point(50, 40, 30),
              new Point(80, 0, 0)
      );

      Polygon carBody2 = new Polygon(
              new Point(-80, 0, 80),
              new Point(-80, 40, 50),
              new Point(50, 40, 50),
              new Point(80, 0, 80)
      );

      Polygon carBody3 = new Polygon(
              new Point(-80, 0, 50),
              new Point(-80, 0, 30),
              new Point(-80, 40, 30),
              new Point(-80, 0, 70)
      );
      Polygon carBody4 = new Polygon(
              new Point(50, 0, 80),
              new Point(50, 0, 50),
              new Point(80, 0, 70),
              new Point(80, 0, 80)
      );

      Polygon window = new Polygon(
              new Point(0, 15, 1),
              new Point(0, 30, 1),
              new Point(30, 30, 1),
              new Point(30, 15, 1)
      );

      // Car Roof (Polygon)
      Polygon carRoof = new Polygon(
              new Point(-70, 40, 30),
              new Point(-70, 40, 70),
              new Point(50, 40, 70),
              new Point(50, 40, 30)
      );

      // Car Front Windshield (Triangle)
      Triangle carWindshield = new Triangle(
              new Point(50, 40, 0),
              new Point(50, 40, 30),
              new Point(80, 0, 0)
      );

      // Car Rear Windshield (Triangle)
      Triangle carRearWindshield = new Triangle(
              new Point(-80, 0, 0),
              new Point(-80, 0, 30),
              new Point(-70, 40, 0)
      );

      // Car Front Wheels (Spheres)
      Cylinder carFrontWheel1 = new Cylinder(10, new Ray(new Point(-40, -10, -15),new Vector(0,0,1)),5);
      Cylinder carFrontWheel2 = new Cylinder(10, new Ray(new Point(-40, -10, 15),new Vector(0,0,1)),5);

      // Car Rear Wheels (Spheres)
      Cylinder carRearWheel1 = new Cylinder(10, new Ray(new Point(30, -10, -15),new Vector(0,0,1)),5);
      Cylinder carRearWheel2 = new Cylinder(10, new Ray(new Point(30, -10, 15),new Vector(0,0,1)),5);


      // Car Axle (Cylinder)
      //Cylinder carAxle = new Cylinder(4, new Ray(new Point(-40, -20, -17), new Vector(0, 0, 1)), 30);

      // Racing Car Tail (Polygon)
      Polygon carTail1 = new Polygon(
              new Point(-70, 45, 0),
              new Point(-60, 45, 0),
              new Point(-60, 45, 30),
              new Point(-70, 45, 30)
      );
      Polygon carTail2 = new Polygon(
              new Point(-70, 40, 0),
              new Point(-60, 40, 0),
              new Point(-60, 55, 0),
              new Point(-70, 55, 0)
      );
      Polygon carTail3 = new Polygon(
              new Point(-70, 40, 30),
              new Point(-60, 40, 30),
              new Point(-60, 55, 30),
              new Point(-70, 55, 30)
      );



      // Add the car components to the scene
      scene.geometries.add(
              carBody1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(BLUE)),
              carBody2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(BLUE)),
              carBody3.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
              carBody4.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),


              carRoof.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(GREEN)),
              carWindshield.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(YELLOW)),
              carRearWindshield.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(RED)),
              carFrontWheel1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(PINK)),
              carFrontWheel2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(WHITE)),
              carRearWheel1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(PINK)),
              carRearWheel2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(GRAY)),
              //carAxle.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(200, 255, 0)),
              carTail1.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
              window.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(BLACK)),
              carTail2.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE)),
              carTail3.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(ORANGE))

              );

 */