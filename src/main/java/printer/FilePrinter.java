package printer;

import logger.Logger;
import matrix.Matrix;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// Класс для записи в файл
public class FilePrinter implements PrinterInterface {
    private final Matrix matrix;
    private final String fileName;

    public FilePrinter(Matrix matrix, String fileName) {
        this.matrix = matrix;
        this.fileName = fileName;
    }

    public void print() {
        try (FileOutputStream fout = new FileOutputStream(fileName + "_resolve.txt")) {
            fout.write(matrix.toString().getBytes(StandardCharsets.UTF_8));
            fout.flush();
            Logger.getInstance().log("Записали полученное решение в файл");
        } catch (IOException e) {
            System.out.println(matrix);
            throw new RuntimeException("IOException in Printer when trying to print matrix in file. Print in Console");
        }
    }
}
