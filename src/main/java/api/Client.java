package main.java.api;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.frontend.controllers.MainFrameController;

public class Client extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage stage) throws Exception {

        // create api
        API api = new API();

        // create mainframe ui and controller
        FXMLLoader mainframeLoader = new FXMLLoader(getClass().getResource("../../res/fxml/Mainframe.fxml"));
        VBox mainFrame = mainframeLoader.load();
        MainFrameController mfController = mainframeLoader.getController();

        // api creates backend, logger and search engine
        api.createBackend();
        api.setBackend();

        // connect api to mainframe controller
        api.connectMainFrameController(mfController);

        // connect api to mainframe controller
        mfController.connectAPI(api);

        // sets the frontend
        api.setFrontend();

        // start app
        Scene scene = new Scene(mainFrame);
        stage.setScene(scene);
        stage.setTitle("Entertainment Tracker");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

    }

}