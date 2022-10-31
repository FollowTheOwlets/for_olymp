package matrix_tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MatrixTest {
    Matrix matrix;

    @Test
    public void buildWayMatrixTest() {
        System.out.println("buildWayMatrixTest start!");
        char[][] testMatrix = new char[][]{
                new char[]{'X', 'X', 'X'},
                new char[]{'X', 'O', 'X'},
                new char[]{'X', 'O', 'X'},
                new char[]{'X', 'O', 'X'},
                new char[]{'X', 'X', 'X'},
        };
        matrix = new Matrix(testMatrix, 3, 4);

        int[][] checkMatrix = matrix.buildWayMatrix().getWayMatrix();

        Assertions.assertEquals(checkMatrix.length, 12);
        System.out.println("buildWayMatrixTest complete!");
    }
}
