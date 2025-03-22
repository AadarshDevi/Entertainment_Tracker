package main.java.api;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.api.Logger.ConsoleLog;
import main.java.api.apiloader.API;
import main.java.api.apiloader.APIFactory;
import main.java.backend.fxmlLoader.FXMLManager;
import main.java.backend.fxmlLoader.FXMLManagerFactory;
import main.java.frontend.controllers.MainFrameController;

public class Client extends Application {

    public static String currentScene = "";

    private static VBox mainFrame;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        API api = APIFactory.getApi(); // create api

        ConsoleLog logger = new ConsoleLog();
        logger.log(this, "API Created");

        // FXMLManager manager = FXMLManagerFactory.getFxmlManager();
        // mainFrame = manager.getMainframe().getPane();
        // MainFrameController mfController = manager.getMainframe().getController();

        logger.log(this, "Mainframe Created");

        // create mainframe ui and controller
        FXMLLoader mainframeLoader = new FXMLLoader(getClass().getResource("../../res/fxml/Mainframe.fxml"));
        mainFrame = mainframeLoader.load();
        MainFrameController mfController = mainframeLoader.getController();

        { // is api null
            boolean isNull = (api == null) ? true : false;
            logger.debug(this, "Is api null (#1) : " + isNull);
        }

        // connect api and mainframe
        api.connectMainFrameController(mfController);
        mfController.connectAPI();

        logger.log(this, "Connected API and Mainframe");

        // api creates backend, logger and search engine
        api.createBackend();
        api.setBackend();
        api.setFrontend(); // sets the frontend

        logger.log(this, "Backend and Frontend are Ready");

        // start app
        Scene scene = new Scene(mainFrame);
        stage.setScene(scene);
        stage.setTitle("Entertainment Tracker");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        logger.log(this, "App is Ready");

    }

    public static void disable() {
        mainFrame.setDisable(true);
    }

    public static void enable() {
        mainFrame.setDisable(false);
    }
}