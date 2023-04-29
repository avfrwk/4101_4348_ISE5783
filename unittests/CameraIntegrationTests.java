
import static org.junit.jupiter.api.Assertions.*;

import geometries.Sphere;
import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.Camera;

public class CameraIntegrationTests {
    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)} and {@link geometries.Sphere}.
     */
    @Test
    void sphereConstructRayTest() {
        Camera camera=new Camera(new Point(11,1,1),new Vector(-1,0,0),new Vector(0,1,0))
                .setVPSize(3,3).setVPDistance(4);
        Sphere sphere=new Sphere(3,new Point(1,1,1));

        int amount=0;
        for(int i=0;i<3;++i)
            for(int j=0;j<3;++j){
                if(sphere.findIntersections(camera.constructRay(3,3,i,j))!=null)
                    ++amount;
            }
        assertEquals(5,amount,"ConstructRay from camera to sphere failed");
    }
    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)} and {@link geometries.Triangle}.
     */
    @Test
    void triangleConstructRayTest() {

    }
    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)} and {@link geometries.Plane}.
     */
    @Test
    void planeConstructRayTest() {

    }

}
