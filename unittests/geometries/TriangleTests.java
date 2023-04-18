package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTests {
    /**
     * Test method for {@link geometries.Triangle#findIntsersections(Ray)}.
     */
    @Test
    void TestFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Triangle t=new Triangle(new Point(0,2,0),new Point(2,0,0),new Point(0,-2,0));
        assertEquals(List.of(new Point(1,0,0)),
                t.findIntsersections(new Ray(new Point(1,0,1),new Vector(0,0,-1))),
                "ray's vector is orthogonal to the triangle");


        // =============== Boundary Values Tests ==================
        assertNotEquals(List.of(new Point(1,0,0)),
                t.findIntsersections(new Ray(new Point(1,0,1),new Vector(0,0,1))),
                "ray's vector is in the opposite direction to the triangle");

    }

    /** Test method for {@link geometries.Triangle#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
        Triangle tri = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = tri.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Triangle's normal is not orthogonal to one of the edges");
    }
}