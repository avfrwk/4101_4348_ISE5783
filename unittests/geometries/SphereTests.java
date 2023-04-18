package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {
    /**
     * Test method for {@link geometries.Sphere#findIntsersections(Ray)}.
     */
    @Test
    void TestFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp=new Sphere(5,new Point(1,1,1));
        assertEquals(List.of(new Point(1,1,-4),new Point(1,1,6)),
                sp.findIntsersections(new Ray(new Point(1,1,7),new Vector(0,0,-1))),
                "sphere's FindIntersections is not working properly");

        assertEquals(List.of(new Point(1,1,-4)),
                sp.findIntsersections(new Ray(new Point(1,1,6),new Vector(0,0,-1))),
                "do not return intersections on the bounding");
        // =============== Boundary Values Tests ==================

        assertNull(sp.findIntsersections(new Ray(new Point(6,1,6),new Vector(0,0,-1))),
                "if there are just one intersection, with with the bounding, return null is needed");
    }
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp=new Sphere(5,new Point(0,0,0));
        assertEquals(new Vector(0,0,1),sp.getNormal(new Point(0,0,5)),
                "Sphere's getNormal does not work as excepted");
        // =============== Boundary Values Tests ==================

    }
}