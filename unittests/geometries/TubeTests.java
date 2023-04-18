package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Tube class
 */
class TubeTests {
    /**
     * Test method for {@link geometries.Tube#findIntsersections(Ray)}.
     */
    @Test
    void TestFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Tube tub=new Tube(2,new Ray(new Point(0, 0, 0), new Vector(0,1 , 0)));
        System.out.println(tub.findIntsersections(new Ray(new Point(-2,0,0),new Vector(1,1,0))));

        // =============== Boundary Values Tests ==================

    }
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {

        Tube tb = new Tube(5,new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));

        // ============ Equivalence Partitions Tests ==============

        Vector norm = tb.getNormal(new Point(0, 5,2 ));
        assertEquals(new Vector(0, 1, 0), norm,
                "Tube's getNormal does not work well");

        // =============== Boundary Values Tests ==================
        norm = tb.getNormal(new Point(0, 5, 0));

        assertEquals(new Vector(0, 1, 0), norm,
                "Tube's getNormal does not work well when the the normal touch the start point");

    }
}