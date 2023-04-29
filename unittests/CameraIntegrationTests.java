
import static org.junit.jupiter.api.Assertions.*;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.Camera;
import java.util.List;
public class CameraIntegrationTests {

    private int getAmountIntersections(Geometry geometry,Camera camera,int Nx,int Ny){
        int sum=0;
        for(int i=0;i<Nx;i++){
            for(int j=0;j<Ny;j++){
                List list=geometry.findIntersections(camera.constructRay(Nx,Ny,i,j));
                if(list!=null){
                    sum+=list.size();
                }
            }
        }
        return sum;
    }
    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)} and {@link geometries.Sphere}.
     */
    @Test
    void sphereConstructRayTest() {
        //case 1:
        Camera camera=new Camera(new Point(0,0,0),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere=new Sphere(1,new Point(0,0,-3));
        assertEquals(2,getAmountIntersections(sphere,camera,3,3),"ConstructRay from camera to sphere failed");

        //case 2:
        Camera camera2=new Camera(new Point(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere2=new Sphere(2.5,new Point(0,0,-2.5));
        assertEquals(18,getAmountIntersections(sphere2,camera2,3,3),"ConstructRay from camera to sphere failed");

        //case 3:
        Camera camera3=new Camera(new Point(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere3=new Sphere(2,new Point(0,0,-2));
        assertEquals(10,getAmountIntersections(sphere3,camera3,3,3),"ConstructRay from camera to sphere failed");

        //case 4:
        Camera camera4=new Camera(new Point(4,4,4),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere4=new Sphere(16,new Point(3,3,3));
        assertEquals(9,getAmountIntersections(sphere4,camera4,3,3),"ConstructRay from camera to sphere failed");

        //case 5:
        Camera camera5=new Camera(new Point(0,0,0),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Sphere sphere5=new Sphere(0.5,new Point(0,0,1));
        assertEquals(0,getAmountIntersections(sphere5,camera5,3,3),"ConstructRay from camera to sphere failed");

    }
    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)} and {@link geometries.Triangle}.
     */
    @Test
    void triangleConstructRayTest() {
        //case 1:
        Camera camera=new Camera(new Point(0,0,0),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Triangle triangle=new Triangle(new Point(0,1,-2),new Point(-1,-1,-2),new Point(1,-1,-2));
        assertEquals(1,getAmountIntersections(triangle,camera,3,3),"ConstructRay from camera to triangle failed");

        //case 2:
        Camera camera2=new Camera(new Point(0,0,0),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Triangle triangle2=new Triangle(new Point(0,20,-2),new Point(-1,-1,-2),new Point(1,-1,-2));
        assertEquals(2,getAmountIntersections(triangle2,camera2,3,3),"ConstructRay from camera to triangle failed");

    }
    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)} and {@link geometries.Plane}.
     */
    @Test
    void planeConstructRayTest() {
        // TC01: camera's Vto is orthogonal to the plane
        Camera camera=new Camera(new Point(1,1,2),new Vector(0,0,-1),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(1);
        Plane plane =new Plane(new Point(1,1,1),new Vector(0,0,1));
        assertEquals(9,getAmountIntersections(plane,camera,3,3),
                "ConstructRay from Camera to Plane failed when the camera's Vto is orthogonal to the plane");
        // TC02: the angle between camera and plane is small
        plane =new Plane(new Point(1,1,1),new Vector(0,-3,6));
        assertEquals(9,getAmountIntersections(plane,camera,3,3),
                "ConstructRay from Camera to Plane failed when the angle between camera and plane is small");
        // TC03: the angle between camera and plane is large
        plane =new Plane(new Point(1,1,1),new Vector(0,-4,1));
        assertEquals(6,getAmountIntersections(plane,camera,3,3),
                "ConstructRay from Camera to Plane failed when the angle between camera and plane is large");

    }

}
