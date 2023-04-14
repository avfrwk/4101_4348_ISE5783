package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTests {
    /** Test method for {@link primitives.Vector#Vector(primitives.Double3)},
     * {@link primitives.Vector#Vector(double,double,double)}.
     * */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (IllegalArgumentException ignore) {} catch (Exception ignore) {
            fail("ERROR: zero vector throws wrong exception");
        }
        try {
            new Vector(new Double3(0, 0, 0));
            fail("ERROR: zero vector does not throw an exception");
        } catch (IllegalArgumentException ignore) {} catch (Exception ignore) {
            fail("ERROR: zero vector throws wrong exception");
        }
    }
    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void add() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(-1, -2, -3),v1.add(v2)
            ,"ERROR: Point - Point does not work correctly");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,()->v1.add(new Vector(-1, -2, -3)),
                "ERROR: Vector + -itself does not throw an exception");
    }
    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        //
        assertEquals(new Vector(2,4,6),v1.scale(2),"doesnt scaling good");
        //
        assertEquals(new Vector(-1,-2,-3),v1.scale(-1),"doesnt scaling good negative scalar");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,()->v1.scale(0),"scale by 0 enabled");
    }
    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        assertTrue(isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        assertTrue(isZero(v1.dotProduct(v3)),"ERROR: dotProduct() for orthogonal vectors is not zero");
    }
    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()),
        "ERROR: crossProduct() wrong result length");
        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v3)),
            "ERROR: crossProduct() result is not orthogonal to its operands");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, ()->v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }
    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(isZero(new Vector(1, 2, 3).lengthSquared() - 14),"ERROR: lengthSquared() wrong value");
        // =============== Boundary Values Tests ==================
    }
    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5),"ERROR: length() wrong value");

        // =============== Boundary Values Tests ==================
    }
    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        assertTrue (isZero(u.length() - 1),"ERROR: the normalized vector is not a unit vector");

        assertThrows(IllegalArgumentException.class,()->v.crossProduct(u),
            "ERROR: the normalized vector is not parallel to the original one");

        assertFalse (v.dotProduct(u) < 0,
                "ERROR: the normalized vector is opposite to the original one");
        // =============== Boundary Values Tests ==================

    }
}