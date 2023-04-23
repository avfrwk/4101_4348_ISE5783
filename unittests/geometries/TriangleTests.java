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
        // TC01: The point inside the triangle (1 points)
        Point p1 = new Point(0.5,1, 0);
        List<Point> result = t.findIntersections(new Ray(new Point(-0.5, 0, 1),
                new Vector(1, 1, -1)));
        שssertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1),result,"The point inside the triangle");
        // TC02: The point outside the triangle opposite a side (0 points)
        assertNull(t.findIntersections(new Ray(new Point(2,4,5), new Vector(-1,-5,-5))),
                "The point outside the triangle opposite a side");
        // TC03: The point outside the triangle opposite a vertex (0 points)
        assertNull(t.findIntersections(new Ray(new Point(2,4,5), new Vector(-3,-4,-13))),
                "The point outside the triangle opposite a side");


        // =============== Boundary Values Tests ==================
        // TC04: The point on a side (0 points)
        assertNull(t.findIntersections(new Ray(new Point(2,4,5), new Vector(-2,-3,-5))),
                "The point outside the triangle opposite a vertex");
        // TC05: The point on the continuation of the rib (0 points)
        assertNull(t.findIntersections(new Ray(new Point(2,4,5), new Vector(-2,0,-5))),
                "The point on the continuation of the rib");
        // TC05: The point is on a vertex (0 points)
        assertNull(t.findIntersections(new Ray(new Point(2,4,5), new Vector(-2,-6,-5))),
                "The point is on a vertex");

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