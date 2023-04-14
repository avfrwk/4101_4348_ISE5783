package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp=new Sphere(5,new Point(0,0,0));
        assertEquals(new Vector(0,0,1),sp.getNormal(new Point(0,0,5)),
                "Sphere's getNormal does not work as excepted");
        // =============== Boundary Values Tests ==================

    }
}