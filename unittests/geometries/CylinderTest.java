package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Cylinder class
 */
class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder#findIntsersections(Ray)}.
     */
    @Test
    void TestFindIntersections() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Tube tb = new Cylinder(5,new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)),5);
        // ============ Equivalence Partitions Tests ==============
        Vector norm = tb.getNormal(new Point(0, 5,2 ));
        assertEquals(new Vector(0, 1, 0), norm,
                "Tube's getNormal does not work well");

        norm = tb.getNormal(new Point(1, 1,0 ));
        assertEquals(new Vector(0,0, -1), norm,
                "Tube's getNormal does not work well on base 1");

        norm = tb.getNormal(new Point(1, 1,5 ));
        assertEquals(new Vector(0,0 , 1), norm,
                "Tube's getNormal does not work well on base 2");


        // =============== Boundary Values Tests ==================
        norm = tb.getNormal(new Point(0, 0,0 ));
        assertEquals(new Vector(0,0, -1), norm,
                "Tube's getNormal does not work well on the center of base 1");

        norm = tb.getNormal(new Point(0, 0,5 ));
        assertEquals(new Vector(0,0 , 1), norm,
                "Tube's getNormal does not work well on the center of base 2");
    }
}