package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests{

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}.
     */
    @Test
    void TestFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: some of the Intersectables intersects
        Geometries geos=new Geometries(
                new Plane(new Point(1,1,1),new Vector(0,0,1)),
                new Polygon(new Point(7,7,5),new Point(7,3,5),
                    new Point(5,1,5), new Point(2,3,5),new Point(3,7,5)),
                new Triangle(new Point(3,6,4),new Point(7,6,4),new Point(5,3,3)),
                new Tube(5,new Ray(new Point(10,10,10),new Vector(0,1,0))),
                new Cylinder(5,new Ray(new Point(11,1,6),new Vector(0,-1,0)),10),
                new Geometries(new Sphere(6,new Point(1,1,1)))
        );
        assertEquals(5,geos.findIntersections(new Ray(new Point(5,3.5,10),new Vector(0,0,-1))).size(),
    "Geometries's TestFindIntersections is not working properly for when some of the Intersectables are intersects");

        // =============== Boundary Values Tests ==================
        // TC02: empty Geometries object
        geos=new Geometries();
        assertNull(geos.findIntersections(new Ray(new Point(5,5,5),new Vector(1,1,1))),
                "Geometries's findIntsersections is not working properly for when empty");

        geos.add(
                new Plane(new Point(1,1,1),new Vector(0,0,1)),
                new Polygon(new Point(7,7,5),new Point(7,3,5),
                        new Point(5,1,5), new Point(2,3,5),new Point(3,7,5)),
                new Triangle(new Point(3,6,4),new Point(7,6,4),new Point(5,3,3)),
                new Tube(5,new Ray(new Point(3,1,1),new Vector(0,1,0))),
                new Cylinder(5,new Ray(new Point(2,2,1.7),new Vector(0,-1,0)),10),
                new Geometries(new Sphere(6,new Point(1,1,1)))
        );
        // TC03: none of the Intersectables are intersects
        assertNull(geos.findIntersections(new Ray(new Point(5,3.5,10),new Vector(0,1,0))),
     "Geometries's TestFindIntersections is not working properly for when none of the Intersectables are intersects");
        // TC04: one of the Intersectables is intersects
        assertEquals(1,geos.findIntersections(new Ray(new Point(15,15,10),new Vector(0,0,-1))).size(),
       "Geometries's TestFindIntersections is not working properly for when just one of the Intersectables is intersects");
        geos=new Geometries();
        geos.add(
                new Plane(new Point(1,1,1),new Vector(0,0,1)),
                new Polygon(new Point(7,7,5),new Point(7,3,5),
                        new Point(5,1,5), new Point(2,3,5),new Point(3,7,5)),
                new Triangle(new Point(3,6,4),new Point(7,6,4),new Point(5,3,3)),
                new Tube(5,new Ray(new Point(3,1,1),new Vector(0,1,0))),
                new Cylinder(5,new Ray(new Point(3,2,1.7),new Vector(0,0,-1)),10),
                new Geometries(new Sphere(6,new Point(1,1,1)))
        );
        // TC05: all the Intersectables are intersects
        assertEquals(9,geos.findIntersections(new Ray(new Point(5,3.5,10),new Vector(0,0,-1))).size(),
      "Geometries's TestFindIntersections is not working properly for when all the Intersectables are intersects");

    }
}