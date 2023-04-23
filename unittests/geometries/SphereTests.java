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
     * Test method for {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test

    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        Point p31 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result3 = sphere.findIntersections(new Ray(new Point(0.5, 0.5, 0),
                new Vector(3, 1, 0)));
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(List.of(p31), result3, "Ray starts inside the sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2,1,0), new Vector(3, 1, 0))),
                "Ray starts after the sphere");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        Point p11=new Point(1.8,0.6,0);
        List<Point> result11 = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(0.8, -0.4, 0)));
        assertEquals(1, result11.size(), "Wrong number of points");
        assertEquals(List.of(p11), result11, "Ray starts at sphere and goes inside");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,1,0), new Vector(3, 1, 0))),
                "Ray starts at sphere and goes outside");
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Point p131=new Point(1,-1,0);
        Point p132=new Point(1,1,0);
        List<Point> result13 = sphere.findIntersections(new Ray(new Point(1, 4, 0),
                new Vector(0, -1, 0)));
        if (result13.get(0).getY() > result13.get(1).getY())
            result13 = List.of(result13.get(1), result13.get(0));
        assertEquals(2, result13.size(), "Wrong number of points");
        assertEquals(List.of(p131,p132), result13, "Ray starts before the sphere");
        // TC14: Ray starts at sphere and goes inside (1 points)
        Point p14=new Point(1,-1,0);
        List<Point> result14 = sphere.findIntersections(new Ray(new Point(1, 1, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result14.size(), "Wrong number of points");
        assertEquals(List.of(p14), result14, "Ray starts at sphere and goes inside");
        // TC15: Ray starts inside (1 points)
        Point p15=new Point(1,-1,0);
        List<Point> result15 = sphere.findIntersections(new Ray(new Point(1, 0.5, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result15.size(), "Wrong number of points");
        assertEquals(List.of(p15), result15, "Ray starts inside");
        // TC16: Ray starts at the center (1 points)
        Point p16=new Point(1,-1,0);
        List<Point> result16 = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(0, -1, 0)));
        assertEquals(1, result16.size(), "Wrong number of points");
        assertEquals(List.of(p16), result16, "Ray starts at the center");
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,1,0), new Vector(0, 1, 0))),
                "Ray starts at sphere and goes outside");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3,1,0), new Vector(1, 1, 0))),
                "Ray starts after sphere");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,1), new Vector(1, 0, 0))),
                "Ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1,0,1), new Vector(1, 0, 0))),
                "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,1), new Vector(1, 0, 0))),
                "Ray starts at the tangent point");
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(0, 1, 0))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
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