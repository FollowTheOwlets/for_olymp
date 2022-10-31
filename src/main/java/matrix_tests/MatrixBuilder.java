package matrix_tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

// Шаблон Builder
public class MatrixBuilder {

    public static Matrix build(String fileName) {
        int xSize;
        int ySize;
        Scanner scan;
        try (FileInputStream fis = new FileInputStream(fileName + ".txt")) {
            scan = new Scanner(fis);
            xSize = scan.nextInt();
            ySize = scan.nextInt();
            char[][] map = new char[ySize][xSize];
            for (int i = 0; i < ySize; i++) {
                for (int j = 0; j < xSize; j++) {
                    map[i][j] = scan.next().charAt(0);
                }
            }
            return new Matrix(map, xSize, ySize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
