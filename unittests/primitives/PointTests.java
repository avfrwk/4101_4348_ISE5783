package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Point class
 */
class PointTests {
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void subtract() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(2, 3, 4).subtract(p1),new Vector(1, 1, 1),
                "ERROR: Point - Point does not work correctly");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class,()->p1.subtract(p1),
                "subtract point from herself does not handled properly");
    }
    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void add() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(0, 0, 0),p1.add(new Vector(-1, -2, -3)),
                "ERROR: Point + Vector does not work correctly");
        // =============== Boundary Values Tests ==================
    }
    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void distanceSquared() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(4,p1.distanceSquared(new Point(1, 2, 5)),
                "distanceSquared doesnt do well");
        // =============== Boundary Values Tests ==================
        assertEquals(0,p1.distanceSquared(p1),
                "the distanceSquared between the same point isnt 0");
    }
    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void distance() {
        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(1,p1.distance(new Point(1, 2, 4)),
                "distance doesnt do well");
        // =============== Boundary Values Tests ==================
        assertEquals(0,p1.distance(p1),
                "the distance between the same point isnt 0");

    }
}