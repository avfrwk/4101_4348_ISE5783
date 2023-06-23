package renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;
import java.util.List;



import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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





   ///auxiliary method
   public Polygon[] box(Point p1,Point p2,Point p3,Point p4,Point p5,Point p6,Point p7,Point p8){
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
   public void buildCar() {
      Camera       camera1     = new Camera(new Point(1500, 1500, -2000), new Vector(-1.5, -1.5, 2), new Vector(-1.5, 1.5, 0))
              .setVPSize(200, 200).setVpDistance(1500)                                                                       //
              .setRayTracer(new RayTracerBasic(scene));
      scene.setAmbientLight(new AmbientLight(new Color(GREEN), new Double3(0.15)));
      scene.lights.add(
              new PointLight(new Color(RED),new Point(50,50,0))
      );
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
      Color PURPIL=new Color(127,0,255);
      Material metal=new Material().setKs(0.1).setShininess(60),
      rubber= new Material().setKs(0.3).setShininess(70),
      glass=new Material().setKs(0.9).setShininess(30),
      plastic=new Material().setKs(0.5).setShininess(40);
      for(int i=0;i<6;i++){
         scene.geometries.add(carBody[i].setMaterial(metal).setEmission(PURPIL));
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
         scene.geometries.add(carBody2[i].setMaterial(metal).setEmission(PURPIL));
      }

      Cylinder[] wheel=new Cylinder[4];
      wheel[0] = new Cylinder(17, new Ray(new Point(-40, 20, 62),new Vector(0,0,1)),30);
      wheel[1] = new Cylinder(17, new Ray(new Point(-40, 20, -42),new Vector(0,0,1)),30);
      wheel[2] = new Cylinder(17, new Ray(new Point(100, 20, 62),new Vector(0,0,1)),30);
      wheel[3] = new Cylinder(17, new Ray(new Point(100, 20, -42),new Vector(0,0,1)),30);
      for(int i=0;i<4;i++){
         scene.geometries.add(wheel[i].setMaterial(rubber).setEmission(new Color(RED)));
      }
      Cylinder[] wheel1=new Cylinder[4];
      wheel1[0] = new Cylinder(3, new Ray(new Point(-40, 20, 60),new Vector(0,0,1)),34);
      wheel1[1] = new Cylinder(3, new Ray(new Point(-40, 20, -46),new Vector(0,0,1)),34);
      wheel1[2] = new Cylinder(3, new Ray(new Point(100, 20, 60),new Vector(0,0,1)),34);
      wheel1[3] = new Cylinder(3, new Ray(new Point(100, 20, -46),new Vector(0,0,1)),34);
      for(int i=0;i<4;i++){
         scene.geometries.add(wheel1[i].setMaterial(plastic).setEmission(new Color(YELLOW)));
      }

      Sphere driver=new Sphere(10,new Point(36,42,30));
      scene.geometries.add(driver.setMaterial(glass).setEmission(new Color(ORANGE)));

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
         scene.geometries.add(front1[i].setMaterial(rubber).setEmission(new Color(GREEN)));
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
         scene.geometries.add(front2[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }
      for(int i=0;i<6;i++){
         scene.geometries.add(front3[i].setMaterial(rubber).setEmission(new Color(GREEN)));
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
         scene.geometries.add(taill1[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
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
         scene.geometries.add(taill2[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
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
         scene.geometries.add(taill3[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
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
         scene.geometries.add(tail1[i].setMaterial(rubber).setEmission(new Color(GREEN)));
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
         scene.geometries.add(tail2[i].setMaterial(rubber).setEmission(new Color(GREEN)));
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
         scene.geometries.add(tail3[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }


      Triangle[]add=new Triangle[9];
      add[0]=new Triangle(new Point(48,38,60),new Point(-20,20,60),new Point(48,20,70));
      add[1]=new Triangle(new Point(48,38,60),new Point(48,2,60),new Point(48,20,70));
      add[2]=new Triangle(new Point(48,2,60),new Point(-20,20,60),new Point(48,20,70));
      add[3]=new Triangle(new Point(48,38,0),new Point(-20,20,0),new Point(48,20,-10));
      add[4]=new Triangle(new Point(48,38,0),new Point(48,2,0),new Point(48,20,-10));
      add[5]=new Triangle(new Point(48,2,0),new Point(-20,20,0),new Point(48,20,-10));
      add[6]=new Triangle(new Point(22,40,5),new Point(-46,40,5),new Point(22,54,30));
      add[7]=new Triangle(new Point(22,40,55),new Point(-46,40,55),new Point(22,54,30));
      add[8]=new Triangle(new Point(22,40,5),new Point(22,40,55),new Point(22,54,30));

      for(int i=0;i<9;i++){
         scene.geometries.add(add[i].setMaterial(glass).setEmission(new Color(102,255,255)));
      }

      Plane plane=new Plane(new Point(0,0,0),new Point(5,0,2),new Point(1,0,2));
      scene.geometries.add(plane.setMaterial(new Material().setKs(1).setShininess(20)).setEmission(new Color(153,255,255)));




      scene.lights.add( //
                 new SpotLight(new Color(700, 400, 400), new Point(50, 20, 30), new Vector(-5, -2, -3)) //
                         .setKl(4E-4).setKq(2E-5));
      scene.lights.add(new PointLight(new Color(153,255,153),new Point(0,30,-40)));

         camera1.setImageWriter(new ImageWriter("carImageAliasing", 600, 600)) //
                 .renderImage()
                 .writeToImage();
                 ;
   }

   @Test
   public void boundrayTest() {
      Camera       camera1     = new Camera(new Point(1500, 1500, -2000), new Vector(-1.5, -1.5, 2), new Vector(-1.5, 1.5, 0))
              .setVPSize(200, 200).setVpDistance(1500)                                                                       //
              .setRayTracer(new RayTracerBasicSoftShadows(scene)).turnOnAntiAliasing(3);
      scene.setAmbientLight(new AmbientLight(new Color(GREEN), new Double3(0.15)));
      scene.lights.add(
              new PointLight(new Color(RED),new Point(50,50,0))
      );
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
      Material metal=new Material().setKs(0.1).setShininess(60),
              rubber= new Material().setKs(0.3).setShininess(70),
              glass=new Material().setKs(0.9).setShininess(30),
              plastic=new Material().setKs(0.5).setShininess(40);
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
      wheel[0] = new Cylinder(17, new Ray(new Point(-40, 20, 62),new Vector(0,0,1)),30);
      wheel[1] = new Cylinder(17, new Ray(new Point(-40, 20, -42),new Vector(0,0,1)),30);
      wheel[2] = new Cylinder(17, new Ray(new Point(100, 20, 62),new Vector(0,0,1)),30);
      wheel[3] = new Cylinder(17, new Ray(new Point(100, 20, -42),new Vector(0,0,1)),30);
      cbr3.add(wheel[0].setMaterial(rubber).setEmission(new Color(RED)),wheel[2].setMaterial(rubber).setEmission(new Color(RED)));
      cbr4.add(wheel[1].setMaterial(rubber).setEmission(new Color(RED)),wheel[3].setMaterial(rubber).setEmission(new Color(RED)));


      Cylinder[] wheel1=new Cylinder[4];
      wheel1[0] = new Cylinder(3, new Ray(new Point(-40, 20, 60),new Vector(0,0,1)),34);
      wheel1[1] = new Cylinder(3, new Ray(new Point(-40, 20, -46),new Vector(0,0,1)),34);
      wheel1[2] = new Cylinder(3, new Ray(new Point(100, 20, 60),new Vector(0,0,1)),34);
      wheel1[3] = new Cylinder(3, new Ray(new Point(100, 20, -46),new Vector(0,0,1)),34);
      cbr3.add(wheel1[0].setMaterial(rubber).setEmission(new Color(YELLOW)),wheel1[2].setMaterial(rubber).setEmission(new Color(YELLOW)));
      cbr4.add(wheel1[1].setMaterial(rubber).setEmission(new Color(YELLOW)),wheel1[3].setMaterial(rubber).setEmission(new Color(YELLOW)));

      Sphere driver=new Sphere(10,new Point(36,42,30));
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
      add[6]=new Triangle(new Point(22,40,5),new Point(-46,40,5),new Point(22,54,30));
      add[7]=new Triangle(new Point(22,40,55),new Point(-46,40,55),new Point(22,54,30));
      add[8]=new Triangle(new Point(22,40,5),new Point(22,40,55),new Point(22,54,30));

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
      scene.geometries.add(plane.setMaterial(new Material().setKs(1).setShininess(20)).setEmission(new Color(153,255,255)));
      cbr.add(cbr1,cbr2,cbr3,cbr4,cbr5,cbr6,cbr7);
      scene.geometries.add(cbr);


      scene.lights.add( //
              new SpotLight(new Color(700, 400, 400), new Point(50, 20, 30), new Vector(-5, -2, -3)) //
                      .setKl(4E-4).setKq(2E-5));
      scene.lights.add(new PointLight(new Color(153,255,153),new Point(0,30,-40)));

      camera1.setImageWriter(new ImageWriter("boundray", 600, 600)) //
              .renderImage()
              .writeToImage();
   }

   @Test
   public void boundrayAoutomatyTest() {
      Camera       camera1     = new Camera(new Point(1500, 1500, -2000), new Vector(-1.5, -1.5, 2), new Vector(-1.5, 1.5, 0))
              .setVPSize(200, 200).setVpDistance(1500)                                                                       //
              .setRayTracer(new RayTracerBasic(scene));
      scene.setAmbientLight(new AmbientLight(new Color(GREEN), new Double3(0.15)));
      scene.lights.add(
              new PointLight(new Color(RED),new Point(50,50,0))
      );
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
      Color PURPIL=new Color(127,0,255);
      Material metal=new Material().setKs(0.1).setShininess(60),
              rubber= new Material().setKs(0.3).setShininess(70),
              glass=new Material().setKs(0.9).setShininess(30),
              plastic=new Material().setKs(0.5).setShininess(40);
      for(int i=0;i<6;i++){
         cbr.add(carBody[i].setMaterial(metal).setEmission(PURPIL));
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
         cbr.add(carBody2[i].setMaterial(metal).setEmission(PURPIL));
      }

      Cylinder[] wheel=new Cylinder[4];
      wheel[0] = new Cylinder(17, new Ray(new Point(-40, 20, 62),new Vector(0,0,1)),30);
      wheel[1] = new Cylinder(17, new Ray(new Point(-40, 20, -42),new Vector(0,0,1)),30);
      wheel[2] = new Cylinder(17, new Ray(new Point(100, 20, 62),new Vector(0,0,1)),30);
      wheel[3] = new Cylinder(17, new Ray(new Point(100, 20, -42),new Vector(0,0,1)),30);
      for(int i=0;i<4;i++){
         cbr.add(wheel[i].setMaterial(rubber).setEmission(new Color(RED)));
      }
      Cylinder[] wheel1=new Cylinder[4];
      wheel1[0] = new Cylinder(3, new Ray(new Point(-40, 20, 60),new Vector(0,0,1)),34);
      wheel1[1] = new Cylinder(3, new Ray(new Point(-40, 20, -46),new Vector(0,0,1)),34);
      wheel1[2] = new Cylinder(3, new Ray(new Point(100, 20, 60),new Vector(0,0,1)),34);
      wheel1[3] = new Cylinder(3, new Ray(new Point(100, 20, -46),new Vector(0,0,1)),34);
      for(int i=0;i<4;i++){
         cbr.add(wheel1[i].setMaterial(plastic).setEmission(new Color(YELLOW)));
      }

      Sphere driver=new Sphere(10,new Point(36,42,30));
      cbr.add(driver.setMaterial(glass).setEmission(new Color(ORANGE)));

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
         cbr.add(front1[i].setMaterial(rubber).setEmission(new Color(GREEN)));
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
         cbr.add(front2[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }
      for(int i=0;i<6;i++){
         cbr.add(front3[i].setMaterial(rubber).setEmission(new Color(GREEN)));
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
         cbr.add(taill1[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
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
         cbr.add(taill2[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
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
         cbr.add(taill3[i].setMaterial(plastic).setEmission(new Color(0,100,0)));
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
         cbr.add(tail1[i].setMaterial(rubber).setEmission(new Color(GREEN)));
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
         cbr.add(tail2[i].setMaterial(rubber).setEmission(new Color(GREEN)));
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
         cbr.add(tail3[i].setMaterial(rubber).setEmission(new Color(GREEN)));
      }


      Triangle[]add=new Triangle[9];
      add[0]=new Triangle(new Point(48,38,60),new Point(-20,20,60),new Point(48,20,70));
      add[1]=new Triangle(new Point(48,38,60),new Point(48,2,60),new Point(48,20,70));
      add[2]=new Triangle(new Point(48,2,60),new Point(-20,20,60),new Point(48,20,70));
      add[3]=new Triangle(new Point(48,38,0),new Point(-20,20,0),new Point(48,20,-10));
      add[4]=new Triangle(new Point(48,38,0),new Point(48,2,0),new Point(48,20,-10));
      add[5]=new Triangle(new Point(48,2,0),new Point(-20,20,0),new Point(48,20,-10));
      add[6]=new Triangle(new Point(22,40,5),new Point(-46,40,5),new Point(22,54,30));
      add[7]=new Triangle(new Point(22,40,55),new Point(-46,40,55),new Point(22,54,30));
      add[8]=new Triangle(new Point(22,40,5),new Point(22,40,55),new Point(22,54,30));

      for(int i=0;i<9;i++){
         cbr.add(add[i].setMaterial(glass).setEmission(new Color(102,255,255)));
      }

      Plane plane=new Plane(new Point(0,0,0),new Point(5,0,2),new Point(1,0,2));
      scene.geometries.add(plane.setMaterial(new Material().setKs(1).setShininess(20)).setEmission(new Color(153,255,255)));
      cbr.partToBounds();;
      scene.geometries.add(cbr);


      scene.lights.add( //
              new SpotLight(new Color(700, 400, 400), new Point(50, 20, 30), new Vector(-5, -2, -3)) //
                      .setKl(4E-4).setKq(2E-5));
      scene.lights.add(new PointLight(new Color(153,255,153),new Point(0,30,-40)));

      camera1.setImageWriter(new ImageWriter("boundaryAuto", 600, 600)) //
              .renderImage()
              .writeToImage();
      ;
   }
}

