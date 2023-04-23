package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
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
    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane pl = new Plane(new Point (1, 1, 0),new Point (0, 0, 7),new Point (3, 3, 9) );
        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray cuts the plane (1 points)
        Point p1 = new Point(-1, -1, 2);
        List<Point> result = pl.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(0, -1, 2)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "The ray cuts the plane");
        // TC02: The ray does not cut the plane (0 points)
        assertNull(pl.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(-1, 1, 0))),
                "The ray does not cut the plane");

        // =============== Boundary Values Tests ==================
        // TC03: The ray is parallel to the plane (0 points)
        assertNull(pl.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                " The ray is parallel to the plane");
        // TC04: The ray is merge with the plane (0 points)
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 1, 0))),
                " The ray is parallel to the plane");

        // TC05: The ray is perpendicular and starts before the plane (1 points)
        Point p5 = new Point(2, 2, 8);
        List<Point> result5 = pl.findIntersections(new Ray(new Point(0, 4, 4),
                new Vector(1, -1, 2)));
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(List.of(p5), result5, "The ray is perpendicular and starts before the plane");

        // TC06: The ray is perpendicular and starts in the plane (0 points)
        assertNull(pl.findIntersections(new Ray(new Point(2, 2, 0), new Vector(1, -1, 0))),
                " The ray is perpendicular and starts in the plane");
        // TC07: The ray is perpendicular and starts after the plane (0 points)
        assertNull(pl.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, -1, 0))),
                "The ray is perpendicular and starts after the plane");
        // TC08: The ray isn't perpendicular and starts in the plane (0 points)
        assertNull(pl.findIntersections(new Ray(new Point(2, 2, 0), new Vector(1, 0, 0))),
                " The ray isn't perpendicular and starts in the plane");
        // TC09: The ray starts at a reference point
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
                "The ray starts at a reference point");
    }
}