package matrix_tests;

import matrix.Way;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WayTest {
    Way way;
    @Test
    public void visitTest(){
        System.out.println("visitTest start!");
        way = new Way(0);
        way.visit(1);
        Assertions.assertEquals(way.toString(), "1->\n");
        System.out.println("visitTest complete!");
    }
    @Test
    public void containsTest(){
        System.out.println("containsTest start!");
        way = new Way(0);
        way.visit(1);
        Assertions.assertTrue(way.contains(1));
        System.out.println("containsTest complete!");
    }
    @Test
    public void newWayTest(){
        System.out.println("newWayTest start!");
        way = new Way(2);
        Way newWay = new Way(way, 0);

        Assertions.assertEquals(newWay.toString(), way.toString());
        System.out.println("newWayTest complete!");
    }
}
