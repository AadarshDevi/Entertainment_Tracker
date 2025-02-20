package main.java.api;

import main.java.backend.Backend;
import main.java.frontend.controllers.MainFrameController;

public class API {

    private MainFrameController mfController;
    private Backend backend;
    private ConsoleLog logger;

    public API() {
        logger = new ConsoleLog();
        backend = new Backend(this);
    }

    public void setMainUIController(MainFrameController mfController) {
        this.mfController = mfController;
    }

    public void setUIModules() {
        mfController.setModules(backend.getModules());
    }

    public ConsoleLog getLogger() {
        return logger;
    }

}
