package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTests {
    /** Test method for {@link geometries.Plane#Plane(Point,Point,Point)},
     * */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(2, 0, 0)),
                "plane with 3 points on the same line constructed");
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 0, 1)),
                "Constructed a plane with two points");
    }
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Plane pla = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pla.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pla.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges (I can test the triangle in the plane)
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Plane's normal is not orthogonal to one of the edges");
    }
}