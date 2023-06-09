/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private Scene scene = new Scene("Test scene");

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVpDistance(1000);

      scene.geometries.add( //
                           new Sphere(50d,new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                           new Sphere(25d,new Point(0, 0, -50)).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVpDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

      scene.geometries.add( //
                           new Sphere(400d,new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere(200d,new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a two triangles lighted by a spotlight with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(200, 200).setVpDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

      scene.geometries.add( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Sphere(30d,new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKl(4E-5).setKq(2E-7));

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }
   public Polygon[] box(Point p1, Point p2, Point p3, Point p4, Point p5, Point p6, Point p7, Point p8){
      Polygon[] polygons=new Polygon[6];
      Polygon r1 = new Polygon(p1,p2,p3,p4);
      Polygon r2 = new Polygon(p1,p2,p6,p5);
      Polygon r3 = new Polygon(p5,p6,p7,p8);
      Polygon r4 = new Polygon(p8,p7,p3,p4);
      Polygon r5 = new Polygon(p2,p3,p7,p6);
      Polygon r6 = new Polygon(p1,p4,p8,p5);
      polygons[0]=r1;
      polygons[1]=r2;
      polygons[2]=r3;
      polygons[3]=r4;
      polygons[4]=r5;
      polygons[5]=r6;
      return polygons;
   }
   @Test
   public void reflectionCar() {
      Camera       camera1     = new Camera(new Point(1500, 1500, -2000), new Vector(-1.5, -1.5, 2), new Vector(-1.5, 1.5, 0))
              .setVPSize(200, 200).setVpDistance(1500)                                                                       //
              .setRayTracer(new RayTracerBasic(scene)).rotateCameraAroundPointVup(Point.ZERO,-10)
              .rotateCameraAroundPointVright(Point.ZERO,15)
              .rotateCamera(-20);

// Car Body (Polygon)
      Polygon[]carBody = box(
              new Point(160, 22, 25),
              new Point(50, 40, 0),
              new Point(50, 0, 0),
              new Point(160, 18, 25),
              new Point(160, 22, 35),
              new Point(50, 40, 60),
              new Point(50, 0, 60),
              new Point(160, 18, 35));
      CBR cbr=new CBR();
      CBR cbr1=new CBR(),cbr2=new CBR(),cbr3=new CBR(),cbr4=new CBR(),cbr5=new CBR(),cbr6=new CBR(),cbr7=new CBR();
      Color PURPIL=new Color(127,0,255);
      Material metal=new Material().setKs(0.2).setShininess(60).setKd(0.8),
              rubber= new Material().setKs(0.4).setShininess(70).setKd(0.01),
              glass=new Material().setKs(0.9).setShininess(30).setKd(0.5),
              plastic=new Material().setKs(0.5).setShininess(40).setKd(0.7);
      for(int i=0;i<6;i++){
         cbr1.add(carBody[i].setMaterial(metal).setEmission(PURPIL));
      }

      Polygon[]carBody2 = box(
              new Point(-80, 0, 0),
              new Point(-80, 40, 0),
              new Point(50, 40, 0),
              new Point(50, 0, 0),
              new Point(-80, 0, 60),
              new Point(-80, 40, 60),
              new Point(50, 40, 60),
              new Point(50, 0, 60));
      for(int i=0;i<6;i++){
         cbr2.add(carBody2[i].setMaterial(metal).setEmission(PURPIL));
      }

      Cylinder[] wheel=new Cylinder[4];
      wheel[0] = new Cylinder(17, new Ray(new Point(-40, 20, 72),new Vector(0,0,1)),30);
      wheel[1] = new Cylinder(17, new Ray(new Point(-40, 20, -42),new Vector(0,0,1)),30);
      wheel[2] = new Cylinder(17, new Ray(new Point(100, 20, 72),new Vector(0,0,1)),30);
      wheel[3] = new Cylinder(17, new Ray(new Point(100, 20, -42),new Vector(0,0,1)),30);
      cbr3.add(wheel[0].setMaterial(rubber).setEmission(new Color(RED)),wheel[2].setMaterial(rubber).setEmission(new Color(RED)));
      cbr4.add(wheel[1].setMaterial(rubber).setEmission(new Color(RED)),wheel[3].setMaterial(rubber).setEmission(new Color(RED)));


      Cylinder[] wheel1=new Cylinder[4];
      wheel1[0] = new Cylinder(3, new Ray(new Point(-40, 20, 76),new Vector(0,0,-1)),60);
      wheel1[1] = new Cylinder(3, new Ray(new Point(-40, 20, -46),new Vector(0,0,1)),60);
      wheel1[2] = new Cylinder(3, new Ray(new Point(100, 20, 76),new Vector(0,0,-1)),60);
      wheel1[3] = new Cylinder(3, new Ray(new Point(100, 20, -46),new Vector(0,0,1)),60);
      cbr3.add(wheel1[0].setMaterial(rubber).setEmission(new Color(YELLOW)),wheel1[2].setMaterial(rubber).setEmission(new Color(YELLOW)));
      cbr4.add(wheel1[1].setMaterial(rubber).setEmission(new Color(YELLOW)),wheel1[3].setMaterial(rubber).setEmission(new Color(YELLOW)));

      Sphere driver=new Sphere(20,new Point(26,33,30));
      cbr5.add(driver.setMaterial(glass).setEmission(new Color(ORANGE)));

      Polygon[]front1 = box(
              new Point(130, 22, -20),
              new Point(130, 18, -20),
              new Point(150, 18, -20),
              new Point(150, 22, -20),
              new Point(130, 22, 80),
              new Point(130, 18, 80),
              new Point(150, 18, 80),
              new Point(150, 22, 80));
      for(int i=0;i<6;i++){
         cbr6.add(front1[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }
      Polygon[]front2 = box(
              new Point(150, 35, -20),
              new Point(130, 35, -20),
              new Point(130, 18, -20),
              new Point(150, 18, -20),
              new Point(150, 35, -22),
              new Point(130, 35, -22),
              new Point(130, 18, -22),
              new Point(150, 18, -22));
      Polygon[]front3 = box(
              new Point(150, 35, 80),
              new Point(130, 35, 80),
              new Point(130, 18, 80),
              new Point(150, 18, 80),
              new Point(150, 35, 82),
              new Point(130, 35, 82),
              new Point(130, 18, 82),
              new Point(150, 18, 82));
      for(int i=0;i<6;i++){
         cbr6.add(front2[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }
      for(int i=0;i<6;i++){
         cbr6.add(front3[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }

      Polygon[]taill1 = box(
              new Point(-78, 53, 2),
              new Point(-60, 53, 2),
              new Point(-60, 55, 2),
              new Point(-78, 55, 2),
              new Point(-78, 53, 58),
              new Point(-60, 53, 58),
              new Point(-60, 55, 58),
              new Point(-78, 55, 58));
      for(int i=0;i<6;i++){
         cbr7.add(taill1[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
      }
      Polygon[]taill2 = box(
              new Point(-78, 40, 2),
              new Point(-60, 40, 2),
              new Point(-60, 55, 2),
              new Point(-78, 55, 2),
              new Point(-78, 40, 4),
              new Point(-60, 40, 4),
              new Point(-60, 55, 4),
              new Point(-78, 55, 4));
      for(int i=0;i<6;i++){
         cbr7.add(taill2[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
      }
      Polygon[]taill3 = box(
              new Point(-78, 40, 58),
              new Point(-60, 40, 58),
              new Point(-60, 55, 58),
              new Point(-78, 55, 58),
              new Point(-78, 40, 56),
              new Point(-60, 40, 56),
              new Point(-60, 55, 56),
              new Point(-78, 55, 56));
      for(int i=0;i<6;i++){
         cbr7.add(taill3[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
      }

      Polygon[]tail1 = box(
              new Point(-78, 55, -5),
              new Point(-60, 55, -5),
              new Point(-60, 60, -5),
              new Point(-78, 60, -5),
              new Point(-78, 55, 65),
              new Point(-60, 55, 65),
              new Point(-60, 60, 65),
              new Point(-78, 60, 65));
      for(int i=0;i<6;i++){
         cbr7.add(tail1[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }
      Polygon[]tail2 = box(
              new Point(-60, 75, 65),
              new Point(-78, 75, 65),
              new Point(-78, 55, 65),
              new Point(-60, 55, 65),
              new Point(-60, 75, 68),
              new Point(-78, 75, 68),
              new Point(-78, 55, 68),
              new Point(-60, 55, 68));
      for(int i=0;i<6;i++){
         cbr7.add(tail2[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }
      Polygon[]tail3 = box(
              new Point(-60, 75, -5),
              new Point(-78, 75, -5),
              new Point(-78, 55, -5),
              new Point(-60, 55, -5),
              new Point(-60, 75, -8),
              new Point(-78, 75, -8),
              new Point(-78, 55, -8),
              new Point(-60, 55, -8));
      for(int i=0;i<6;i++){
         cbr7.add(tail3[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }


      Triangle[]add=new Triangle[9];
      add[0]=new Triangle(new Point(48,38,60),new Point(-20,20,60),new Point(48,20,70));
      add[1]=new Triangle(new Point(48,38,60),new Point(48,2,60),new Point(48,20,70));
      add[2]=new Triangle(new Point(48,2,60),new Point(-20,20,60),new Point(48,20,70));
      add[3]=new Triangle(new Point(48,38,0),new Point(-20,20,0),new Point(48,20,-10));
      add[4]=new Triangle(new Point(48,38,0),new Point(48,2,0),new Point(48,20,-10));
      add[5]=new Triangle(new Point(48,2,0),new Point(-20,20,0),new Point(48,20,-10));
      add[6]=new Triangle(new Point(22,40,11),new Point(-46,40,30),new Point(22,54,30));
      add[7]=new Triangle(new Point(22,40,49),new Point(-46,40,30),new Point(22,54,30));
      add[8]=new Triangle(new Point(22,40,11),new Point(22,40,49),new Point(22,54,30));

      for(int i=0;i<3;i++){
         cbr3.add(add[i].setMaterial(glass).setEmission(new Color(102,255,255)));
      }
      for(int i=3;i<6;i++){
         cbr4.add(add[i].setMaterial(glass).setEmission(new Color(102,255,255)));
      }
      for(int i=6;i<9;i++){
         cbr6.add(add[i].setMaterial(glass).setEmission(new Color(102,255,255)));
      }

      Plane plane=new Plane(new Point(0,0,0),new Point(5,0,2),new Point(1,0,2));
      scene.geometries.add(plane.setMaterial(new Material().setKs(0.99).setShininess(20).setKd(0.002).setKr(0.3)).setEmission(new Color(107,103,133)));
      cbr.add(cbr1,cbr2,cbr3,cbr4,cbr5,cbr6,cbr7);
      scene.geometries.add(cbr);

      scene.setAmbientLight(new AmbientLight(new Color(GREEN), new Double3(0.15)));
      scene.lights.add(
              new PointLight(new Color(RED),new Point(50,50,-50))
      );
      scene.lights.add( //
              new SpotLight(new Color(700, 400, 400), new Point(100, 50, 60), new Vector(-5, -2, -3)) //
                      .setKl(4E-4).setKq(2E-5));
      //scene.lights.add(new PointLight(new Color(153,255,153),new Point(0,100,40)));
      scene.lights.add(new PointLight(new Color(GREEN),new Point(0,100,40)));
      //scene.lights.add(new DirectionalLight(new Color(0,200,0),new Vector(-1,-1,-1)));

      camera1.setImageWriter(new ImageWriter("reflectionCar", 600, 600)) //
              .renderImage()
              .writeToImage();
   }
}
