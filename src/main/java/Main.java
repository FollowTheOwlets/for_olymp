import logger.Logger;
import matrix.Matrix;
import matrix.MatrixBuilder;
import printer.ConsolePrinter;
import printer.FilePrinter;
import printer.PrinterInterface;

import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static String fileName;
    static Logger logger;

    public static void main(String[] args) {
        // Из условия не совсем понятно каким образом будет вводиться название файла
        if (args.length == 0) {
            System.out.println("Input file name:");
            fileName = scan.nextLine().replaceAll(".txt", "");
        } else {
            fileName = args[0].replaceAll(".txt", "");
        }
        logger = Logger.getInstance().log("Начинаем работу с файлом " + fileName + ".txt");
        Matrix matrix = MatrixBuilder.build(fileName);
        //Сначала строим матрицу путей
        assert matrix != null;
        matrix.buildWayMatrix()
                //Потом создаем по ней граф
                .createGraph()
                // Находим все пути и выбираем набор с минимальным кол-вом неповторяющихся элементов
                .changeMap();

        PrinterInterface printer = new ConsolePrinter(matrix);
        printer.print();
        printer = new FilePrinter(matrix, fileName);
        printer.print();
        logger.printLogs();
    }
}
