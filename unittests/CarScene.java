import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.GREEN;

public class CarScene {
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
    Scene scene=new Scene("car scene");
    double darkScale=1;
    Color PURPIL=new Color(127,0,255).scale(darkScale),
        wheelsColor=new Color(RED).scale(darkScale),
        wheelsAxisColor=new Color(255,128,0).scale(darkScale),
        driverCanopyColor=new Color(ORANGE).scale(darkScale),
        wingsColor=new Color(GREEN).scale(darkScale),
        sidesTrianglesColor=new Color(102,255,255).scale(darkScale),
        asphaltYellow=new Color(238,219,50).scale(darkScale),
        asphaltWhite=new Color(242,242,242).scale(darkScale),
        roadSidesColor=new Color(185,185,185).scale(darkScale),
        roadColor=new Color(32,32,32).scale(darkScale),
     trafficLightsColor=new Color(2, 2, 2).scale(darkScale);

    Material trafficLightsBodyMaterial=new Material().setKr(0.0).setShininess(0),
    trafficLightsMaterial=new Material().setKs(0.9).setKd(0).setKt(0.4).setKr(0.1).setShininess(100),
    asphalt=new Material().setKs(0.5).setKd(1).setShininess(5),
            metal=new Material().setKs(0.1).setShininess(60).setKd(0.8),
            rubber= new Material().setKs(0.3).setShininess(70).setKd(0.01),
            GLASS1=new Material().setKs(0.9).setShininess(30).setKd(0),
            GLASS=new Material().setKs(0.9).setKd(0).setKt(0.9).setKr(0.1).setShininess(100),
    plastic=new Material().setKs(0.5).setShininess(40).setKd(0.7);
    @Test
    public void buildCar() {
        double fixDownFront=-17;
// Car Body (Polygon)
        Polygon[]carBody = box(
                new Point(160, 22+fixDownFront, 25),
                new Point(50, 40, 0),
                new Point(50, 0, 0),
                new Point(160, 18+fixDownFront, 25),
                new Point(160, 22+fixDownFront, 35),
                new Point(50, 40, 60),
                new Point(50, 0, 60),
                new Point(160, 18+fixDownFront, 35));
        CBR cbr=new CBR();
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
        Cylinder[] wheel=new Cylinder[4];//+fixDownFront
        double wheelsHight=20+fixDownFront/2;
        wheel[0] = new Cylinder(20, new Ray(new Point(-40, wheelsHight, 72),new Vector(0,0,1)),30);
        wheel[1] = new Cylinder(20, new Ray(new Point(-40, wheelsHight, -42),new Vector(0,0,1)),30);
        wheel[2] = new Cylinder(20, new Ray(new Point(100, wheelsHight, 72),new Vector(0,0,1)),30);
        wheel[3] = new Cylinder(20, new Ray(new Point(100, wheelsHight, -42),new Vector(0,0,1)),30);
        for(int i=0;i<4;i++){
            cbr.add(wheel[i].setMaterial(rubber).setEmission(wheelsColor));
        }
        Cylinder[] wheel1=new Cylinder[4];
        wheel1[0] = new Cylinder(4, new Ray(new Point(-40, wheelsHight, 46),new Vector(0,0,1)),60);
        wheel1[1] = new Cylinder(4, new Ray(new Point(-40, wheelsHight, -46),new Vector(0,0,1)),60);
        wheel1[2] = new Cylinder(4, new Ray(new Point(100, wheelsHight, 46),new Vector(0,0,1)),60);
        wheel1[3] = new Cylinder(4, new Ray(new Point(100, wheelsHight, -46),new Vector(0,0,1)),60);
        for(int i=0;i<4;i++){
            cbr.add(wheel1[i].setMaterial(plastic).setEmission(wheelsAxisColor));
        }

        Sphere driver=new Sphere(20,new Point(26,33,30));
        cbr.add(driver.setMaterial(GLASS).setEmission(driverCanopyColor));

        Polygon[]front1 = box(
                new Point(130, 22+fixDownFront, -20),
                new Point(130, 18+fixDownFront, -20),
                new Point(150, 18+fixDownFront, -20),
                new Point(150, 22+fixDownFront, -20),
                new Point(130, 22+fixDownFront, 80),
                new Point(130, 18+fixDownFront, 80),
                new Point(150, 18+fixDownFront, 80),
                new Point(150, 22+fixDownFront, 80));
        for(int i=0;i<6;i++){
            cbr.add(front1[i].setMaterial(rubber).setEmission(wingsColor));
        }
        Polygon[]front2 = box(
                new Point(150, 35+fixDownFront, -20),
                new Point(130, 35+fixDownFront, -20),
                new Point(130, 18+fixDownFront, -20),
                new Point(150, 18+fixDownFront, -20),
                new Point(150, 35+fixDownFront, -22),
                new Point(130, 35+fixDownFront, -22),
                new Point(130, 18+fixDownFront, -22),
                new Point(150, 18+fixDownFront, -22));
        Polygon[]front3 = box(
                new Point(150, 35+fixDownFront, 80),
                new Point(130, 35+fixDownFront, 80),
                new Point(130, 18+fixDownFront, 80),
                new Point(150, 18+fixDownFront, 80),
                new Point(150, 35+fixDownFront, 82),
                new Point(130, 35+fixDownFront, 82),
                new Point(130, 18+fixDownFront, 82),
                new Point(150, 18+fixDownFront, 82));
        for(int i=0;i<6;i++){
            cbr.add(front2[i].setMaterial(rubber).setEmission(wingsColor));
        }
        for(int i=0;i<6;i++){
            cbr.add(front3[i].setMaterial(rubber).setEmission(wingsColor));
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
            cbr.add(taill1[i].setMaterial(plastic).setEmission(wingsColor));
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
            cbr.add(taill2[i].setMaterial(plastic).setEmission(wingsColor));
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
            cbr.add(taill3[i].setMaterial(plastic).setEmission(wingsColor));
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
            cbr.add(tail1[i].setMaterial(rubber).setEmission(wingsColor));
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
            cbr.add(tail2[i].setMaterial(rubber).setEmission(wingsColor));
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
            cbr.add(tail3[i].setMaterial(rubber).setEmission(wingsColor));
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

        for(int i=0;i<9;i++){
            cbr.add(add[i].setMaterial(GLASS1).setEmission(sidesTrianglesColor));
        }
        //road
        double RoadHight=wheelsHight-20;
        Plane plane=new Plane(new Point(0,RoadHight,0),new Point(5,RoadHight,2),new Point(1,RoadHight,2));
        scene.geometries.add(plane.setMaterial(asphalt).setEmission(roadSidesColor));
        scene.geometries.add(
                new Polygon(new Point(1000,RoadHight+0.000005,-90),
                        new Point(1000,RoadHight+0.000005,150),
                        new Point(-1000,RoadHight+0.000005,150),
                        new Point(-1000,RoadHight+0.000005,-90))
                .setMaterial(asphalt).setEmission(roadColor));

        double stripeWidth=4;
        //road stripes
        for(int i=-1000;i<1000;i+=80){
            cbr.add(new Polygon(new Point(i+30,RoadHight+0.00001,30-stripeWidth),
                    new Point(i+30,RoadHight+0.00001,30+stripeWidth),
                    new Point(i,RoadHight+0.00001,30+stripeWidth),
                    new Point(i,RoadHight+0.00001,30-stripeWidth))
                   .setMaterial(asphalt).setEmission(asphaltWhite));
        }
        cbr.add(new Polygon(new Point(1000,RoadHight+0.00001,130-stripeWidth),
                new Point(1000,RoadHight+0.00001,130+stripeWidth),
                new Point(-1000,RoadHight+0.00001,130+stripeWidth),
                new Point(-1000,RoadHight+0.00001,130-stripeWidth))
                .setMaterial(asphalt)
                .setEmission(asphaltYellow));
        cbr.add(new Polygon(new Point(1000,RoadHight+0.00001,-70-stripeWidth),
                new Point(1000,RoadHight+0.00001,-70+stripeWidth),
                new Point(-1000,RoadHight+0.00001,-70+stripeWidth),
                new Point(-1000,RoadHight+0.00001,-70-stripeWidth))
                .setMaterial(asphalt).setEmission(asphaltYellow));
        Point ligherStart=new Point(350,-17,170);
        double trafficLightsBoxHight=50;
        double trafficLightsBoxwidth=15;
        double ligtherCylinderHeight=120;
        CBR LighterCbr=new CBR();
        Polygon[] lightBox=box(
                ligherStart.add(new Vector(trafficLightsBoxwidth,ligtherCylinderHeight+trafficLightsBoxHight,-trafficLightsBoxwidth)),
                ligherStart.add(new Vector(-trafficLightsBoxwidth,ligtherCylinderHeight+trafficLightsBoxHight,-trafficLightsBoxwidth)),
                ligherStart.add(new Vector(-trafficLightsBoxwidth,ligtherCylinderHeight,-trafficLightsBoxwidth)),
                ligherStart.add(new Vector(trafficLightsBoxwidth,ligtherCylinderHeight,-trafficLightsBoxwidth)),
                ligherStart.add(new Vector(trafficLightsBoxwidth,ligtherCylinderHeight+trafficLightsBoxHight,trafficLightsBoxwidth)),
                ligherStart.add(new Vector(-trafficLightsBoxwidth,ligtherCylinderHeight+trafficLightsBoxHight,trafficLightsBoxwidth)),
                ligherStart.add(new Vector(-trafficLightsBoxwidth,ligtherCylinderHeight,trafficLightsBoxwidth)),
                ligherStart.add(new Vector(trafficLightsBoxwidth,ligtherCylinderHeight,trafficLightsBoxwidth))
        );
        for (Polygon i:lightBox){i.setEmission(trafficLightsColor).setMaterial(trafficLightsBodyMaterial);}
        LighterCbr.add(lightBox);
        LighterCbr.add(
                new Sphere(20,ligherStart).setEmission(trafficLightsColor),
                new Cylinder(10,new Ray(ligherStart,new Vector(0,1,0)),ligtherCylinderHeight).setMaterial(trafficLightsBodyMaterial)
                        .setEmission(trafficLightsColor).setMaterial(trafficLightsBodyMaterial),
                new Cylinder(6,new Ray(ligherStart.add(new Vector(-trafficLightsBoxwidth,ligtherCylinderHeight+16/3+5,0))
                    ,new Vector(-1,0,0)),1).setMaterial(trafficLightsMaterial)
                        .setEmission(new Color(0,255,0)
                        .scale(darkScale).scale(0.5)),
                new Cylinder(6,new Ray(ligherStart.add(new Vector(-trafficLightsBoxwidth,ligtherCylinderHeight+15+32/3,0))
                        ,new Vector(-1,0,0)),1).setMaterial(trafficLightsMaterial)
                        .setEmission(new Color(255,255,0)
                        .scale(darkScale).scale(0.5)),
                new Cylinder(6,new Ray(ligherStart.add(new Vector(-trafficLightsBoxwidth,ligtherCylinderHeight+24+48/3,0))
                ,new Vector(-1,0,0)),1).setMaterial(trafficLightsMaterial)
                        .setEmission(new Color(255,0,0)
                        .scale(darkScale).scale(0.5))
        );
        // scene.lights.add(new SpotLight(
       //         new Color(256,256,256).scale(0.1),
       //         ligherStart.add(new Vector(0,ligtherCylinderHeight+16/3+5,0))
       //         new Vector(-1,0,0)
       // ));
        LighterCbr.partToBounds();
        cbr.partToBounds();
        cbr.add(LighterCbr);
        scene.geometries.add(cbr);
        scene.setAmbientLight(new AmbientLight(new Color(GREEN), new Double3(0.01)));

        scene.lights.add(  new SpotLight(new Color(10, 50, 80), new Point(200, 100, 30), new Vector(-20, -10, -3))
               .setKl(4E-4).setKq(2E-5));
        scene.lights.add(new PointLight(new Color(153,255,153),new Point(0,200,40)).setKq(0.0001));
        scene.lights.add(new DirectionalLight(new Color(107,51,83),new Vector(-1,-1,-1).scale(0.2)));
        scene.lights.add(new PointLight(new Color(200,50,50),new Point(-100,50,40)).setKq(0.0001));

        //scene.setBackground(new Color(255,204,255));
        Point CarCenterPoint=new Point(26,30,30);
        Camera camera1=new Camera(CarCenterPoint.add(new Vector(3000,0,0))
                , new Vector(-1, 0, 0), new Vector(0,1,0))
                .setVPSize(200, 200).setVpDistance(900);
               // .rotateCameraAroundPointVright(CarCenterPoint,-5)
        camera1
                .rotateCameraAroundPointVup(CarCenterPoint,135)
                .rotateCameraAroundPointVright(CarCenterPoint,-35)
        .setRayTracer(new RayTracerBasic(scene))
                //.setRayTracer(new RayTracerBasicSoftShadows(scene))
                //.turnOnAntiAliasing(3)
         .setImageWriter(new ImageWriter("carImage", 600, 600))
               // .setVideoWriter(new VideoWriter("finalVideo").setFps(36))
                .renderImage().writeToImage()
        ;

        //for(int i=0;i<29;++i){
       //          camera1.rotateCamera(-(13)*Math.sin(35*2*Math.PI/360))
       //                 .rotateCameraAroundPointVup(CarCenterPoint,10).renderImage().writeToFrame()
        //        ;
       // }camera1.writeToVideo();
      //  for(int i=0;i<29*3;++i){
       //     camera1.rotateCamera(-(13/3)*Math.sin(35*2*Math.PI/360))
       //             .rotateCameraAroundPointVup(CarCenterPoint,10/3).renderImage().writeToFrame()
       //     ;
       // }camera1.writeToVideo();
       // for(int i=0;i<29;++i){
          //  camera1
          //          .rotateCamera(-(13)*Math.sin(35*2*Math.PI/360))
          //          .rotateCameraAroundPointVup(CarCenterPoint,10)
          //          .setImageWriter(new ImageWriter("finalVideo"+String.valueOf(i), 600, 600)
          //                  .setPath("C:\\Users\\user\\Documents\\קורסים\\יב\\סמס ב\\מלת פרוייקט\\targil_1\\images\\mov\\final mov"))
          //          .renderImage().writeToImage();//.writeToFrame()
            ;
        //    System.out.println(((double)i)/(29));
       // }
       // new VideoWriter("finalVideo").setFps(12).loadFromeImages("C:\\Users\\user\\Documents\\קורסים\\יב\\סמס ב\\מלת פרוייקט\\targil_1\\images\\mov\\final mov","finalVideo").writeVideo();

    }
}
