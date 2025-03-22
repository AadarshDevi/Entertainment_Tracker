package main.java.backend.fxmlLoader;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.frontend.controllers.MainFrameController;

public class FXMLManager {

    public <T extends Pane, C> FXMLPackage<T, C> getEntertainmentEditor() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/EntertainmentEditor.fxml"));

        try {
            T pane = fxmlLoader.load();
            C controller = fxmlLoader.getController();
            return new FXMLPackage<>(pane, controller);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FXMLPackage<VBox, MainFrameController> getMainframe() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/Mainframe.fxml"));

        try {
            VBox pane = fxmlLoader.load();
            MainFrameController controller = (MainFrameController) fxmlLoader.getController();
            return new FXMLPackage<>(pane, controller);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
