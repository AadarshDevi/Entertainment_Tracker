package main.java.api.Logger;

public class ConsoleLog implements LoggingInterface {

    @Override
    public void log(Object object, String string) {
        System.out.println("[Log : " + object.getClass() + "] >>> " + string);
    }

    @Override
    public void error(Object object, Exception e) {
        System.out.println("\n\n[EXCEPTION : " + object.getClass() + "] >>> >>> " + e.getMessage() + "\n");
    }

    @Override
    public void debug(Object object, String string) {
        System.out.println("[DEBUG : " + object.getClass() + "] >>> " + string);
    }

}
