package printer;

import logger.Logger;
import matrix_tests.Matrix;

public class ConsolePrinter implements PrinterInterface {
    private final Matrix matrix;

    public ConsolePrinter(Matrix matrix) {
        this.matrix = matrix;
        Logger.getInstance().log("Вывели решение в консоль");
    }

    public void print() {
        System.out.println(matrix);
    }
}
