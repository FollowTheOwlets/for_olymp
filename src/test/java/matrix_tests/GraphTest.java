package matrix_tests;

import matrix.Graph;
import matrix.Way;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GraphTest {
    Graph graph;

    @Test
    public void minDistanceTest() {
        System.out.println("minDistanceTest start!");
        int[][] testMatrix = new int[][]{
                new int[]{0, 1, 10},
                new int[]{1, 0, 2},
                new int[]{10, 2, 0}
        };
        graph = new Graph(testMatrix);

        Assertions.assertEquals(graph.searchMinDistance(0,2,new Way(0)).distance, 3);
        System.out.println("minDistanceTest complete!");
    }
}
