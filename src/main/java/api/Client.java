package main.java.api;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.frontend.controllers.MainFrameController;

public class Client extends Application {

    // private final double DISPLAY_WIDTH =
    // Screen.getPrimary().getBounds().getWidth() - 0.6666;
    // private final double DISPLAY_HEIGHT =
    // Screen.getPrimary().getBounds().getHeight() - 23.34;

    // private final double APP_WIDTH = (DISPLAY_WIDTH * 0.9);
    // private final double APP_HEIGHT = (DISPLAY_HEIGHT * 0.9);

    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage stage) throws Exception {

        API api = new API();
        api.getLogger().log(this, "Application starting");
        api.getLogger().log(this, "API Creation Finished");

        FXMLLoader mainframeLoader = new FXMLLoader(getClass().getResource("../../res/fxml/Mainframe.fxml"));

        VBox mainFrame = mainframeLoader.load();
        MainFrameController mfController = mainframeLoader.getController();
        mfController.setApi(api);

        Scene scene = new Scene(mainFrame);
        api.getLogger().log(this, "MainFrame FXML Created");

        api.setMainUIController(mfController);
        api.getLogger().log(this, "MainFrame Controller sent");

        api.setUIModules();
        api.getLogger().log(this, "Modules setup");

        stage.setScene(scene);
        stage.setTitle("Entertainment Tracker");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        api.getLogger().log(this, "Application is ready to be used");
    }

}