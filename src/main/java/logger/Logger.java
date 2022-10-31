package logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

// Класс для логирования
public class Logger {
    protected int num = 1;
    protected Date date = new Date();
    protected StringBuilder logs = new StringBuilder("Logs:\n");
    protected File logFile = new File("logs.txt");

    private static Logger logger;

    private Logger() {
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public Logger log(Object msg) {
        logs
                .append("[")
                .append(date.toString())
                .append(" ] [")
                .append(num++)
                .append("] ")
                .append(msg)
                .append("\n");
        return this;
    }

    public void printLogs() {
        String log = logs.toString();

        try (FileWriter fileWriter = new FileWriter(logFile)) {
            fileWriter.write(log);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException("IOException in Logger when trying to print logs");
        }
    }
}
