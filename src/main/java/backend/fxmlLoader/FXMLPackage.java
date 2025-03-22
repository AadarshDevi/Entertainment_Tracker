package main.java.backend.fxmlLoader;

import javafx.scene.layout.Pane;

public class FXMLPackage<T extends Pane, C> {

    private T pane;
    private C controller;

    public FXMLPackage(T pane, C controller) {
        this.pane = pane;
        this.controller = controller;
    }

    public T getPane() {
        return pane;
    }

    public C getController() {
        return controller;
    }
}
