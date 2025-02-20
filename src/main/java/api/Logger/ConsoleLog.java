package main.java.api.Logger;

public class ConsoleLog implements LoggingInterface {

    @Override
    public void log(Object object, String string) {
        System.out.println("[Log : " + object.getClass() + "] >>> " + string);
    }

    @Override
    public void error(Object object, Exception e) {
        System.out.println("[ERROR : " + object.getClass() + "] >>> >>> " + e.getMessage());
    }

    @Override
    public void debug(Object object, String string) {
        System.out.println("\n[DEBUG : " + object.getClass() + "] >>>>>> " + string + "\n");
    }

}
