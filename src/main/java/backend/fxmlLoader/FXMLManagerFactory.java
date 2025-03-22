package main.java.backend.fxmlLoader;

public class FXMLManagerFactory {

    private static FXMLManager fxmlManager;

    public static FXMLManager getFxmlManager() {
        if (fxmlManager == null)
            fxmlManager = new FXMLManager();
        return fxmlManager;
    }

}
