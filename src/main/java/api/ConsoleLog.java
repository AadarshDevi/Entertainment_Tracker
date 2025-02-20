package main.java.api;

public class ConsoleLog {

    public void log(Object object, String string) {
        System.out.println("[Log : " + object.getClass() + "] >>> " + string);
    }

    public void error(Object object, Exception e) {
        System.out.println("[ERROR : " + object.getClass() + "] >>> >>> " + e.getMessage());
    }

    public void error(Object object, String string) {
        System.out.println("[ERROR : " + object.getClass() + "] >>> >>> " + string);
    }

    public void debug(Object object, String string) {
        System.out.println("\n[DEBUG : " + object.getClass() + "] >>>>>> " + string + "\n");
    }

}
