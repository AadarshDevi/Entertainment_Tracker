package main.java.api.Logger;

public class ConsoleLogFactory {
    private static final ConsoleLog logger = new ConsoleLog();

    public static ConsoleLog getLogger() {
        return logger;
    }

    private ConsoleLogFactory() {
    }
}
