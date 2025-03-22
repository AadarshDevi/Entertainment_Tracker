package main.java.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.api.Client;
import main.java.api.apiloader.API;
import main.java.api.apiloader.APIFactory;

public class ClientController extends ParentController {

    public static final String TVSHOW = "tvshow";
    public static final String MOVIE = "movie";

    @FXML private Button client_button_tv_tracker;
    @FXML private Button client_button_movie_tracker;
    @FXML private Label client_label_logo;

    @SuppressWarnings("unused") private API api;

    public void setImgs() {

        ImageView movieView = new ImageView(new Image("main/res/img/movie_2.png"));
        movieView.setFitWidth(100);
        movieView.setFitHeight(100);
        client_button_movie_tracker.setText("");
        client_button_movie_tracker.setGraphic(movieView);
        client_button_movie_tracker.setContentDisplay(ContentDisplay.CENTER);

        ImageView tvShowView = new ImageView(new Image("main/res/img/tv_1.png"));
        tvShowView.setFitWidth(100);
        tvShowView.setFitHeight(100);
        client_button_tv_tracker.setText("");
        client_button_tv_tracker.setGraphic(tvShowView);
        client_button_tv_tracker.setContentDisplay(ContentDisplay.CENTER);

        ImageView ticketView = new ImageView(new Image("main/res/img/ticket_1.png"));
        ticketView.setFitWidth(150);
        ticketView.setFitHeight(150);
        client_label_logo.setText("");
        client_label_logo.setGraphic(ticketView);
        client_label_logo.setContentDisplay(ContentDisplay.CENTER);
    }

    @FXML
    public void setMovieTracker() {
        Client.currentScene = new String(MOVIE);
        // Client.setScene(MOVIE);
    }

    @FXML
    public void setTVTracker() {
        Client.currentScene = new String(TVSHOW);
        // Client.setScene(TVSHOW);
    }

    public void setApi() {
        this.api = APIFactory.getApi();
    }
}
