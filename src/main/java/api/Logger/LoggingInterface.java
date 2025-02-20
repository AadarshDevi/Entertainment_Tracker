package main.java.api.Logger;

public interface LoggingInterface {
    public void log(Object object, String string);

    public void error(Object object, Exception e);

    public void debug(Object object, String string);
}
