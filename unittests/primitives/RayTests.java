package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: the closet point in the middle of the list
        List<Point> list=List.of(new Point(5,5,5),new Point(2,2,2)
                ,new Point(4,4,4),new Point(3,3,3));
        assertEquals(new Point(2,2,2),
                new Ray(new Point(1,1,1),new Vector(1,1,1)).findClosestPoint(list),
                "Ray.findClosetPoint doesn't work properly when the closet point in the middle of the list");
        // =============== Boundary Values Tests ==================
        //TC02: the list is empty
        list=List.of();
        assertNull(new Ray(new Point(1,1,1),new Vector(1,1,1)).findClosestPoint(list),
                "Ray.findClosetPoint doesn't work properly when list is empty");
        list=List.of(new Point(2,2,2),new Point(5,5,5)
                ,new Point(4,4,4),new Point(3,3,3));

        //TC03: the closet point in the start of the list
        assertEquals(new Point(2,2,2),
                new Ray(new Point(1,1,1),new Vector(1,1,1)).findClosestPoint(list),
                "Ray.findClosetPoint doesn't work properly when the closet point in the start of the list");

        //TC04: the closet point in the end of the list
        list=List.of(new Point(5,5,5),new Point(4,4,4)
                ,new Point(3,3,3),new Point(2,2,2));
        assertEquals(new Point(2,2,2),
                new Ray(new Point(1,1,1),new Vector(1,1,1)).findClosestPoint(list),
                "Ray.findClosetPoint doesn't work properly when the closet point in the end of the list");
    }
}