package main.java.frontend.controllers;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.java.api.API;
import main.java.backend.entertainment.Anime;
import main.java.backend.entertainment.Entertainment;
import main.java.backend.entertainment.Movie;

public class EditorController {

    @FXML
    private TextField module_id;
    @FXML
    private TextField entertainment_type;
    @FXML
    private TextField entertainment_franchise;
    @FXML
    private TextField entertainment_title;
    @FXML
    private TextField entertainment_release_date;
    @FXML
    private TextField entertainment_duration;
    @FXML
    private TextField entertainment_season_number;
    @FXML
    private TextField entertainment_episode_number;
    @FXML
    private CheckBox entertainment_status_1;
    @FXML
    private CheckBox entertainment_status_2;
    @FXML
    private CheckBox entertainment_status_3;
    @FXML
    private CheckBox entertainment_status_4;
    @FXML
    private CheckBox entertainment_status_5;

    @FXML
    private Button change_entertainment;
    @FXML
    private Button next_entertainment;
    @FXML
    private Button previous_entertainment;

    @FXML
    private TextArea entertainment_production_companies;
    @FXML
    private TextArea entertainment_tags;

    private API api;

    public void setApi(API api) {
        this.api = api;
    }

    public Entertainment addEntertainment() {

        String type = entertainment_type.getText();
        String franchise = entertainment_franchise.getText();
        String title = entertainment_title.getText();

        String primary_status = "";
        if (entertainment_status_1.isSelected()) {
            primary_status = "1";
        } else if (entertainment_status_2.isSelected()) {
            primary_status = "2";
        } else if (entertainment_status_3.isSelected()) {
            primary_status = "3";
        }

        String secondary_status = "";
        if (entertainment_status_4.isSelected()) {
            primary_status = "4";
        } else if (entertainment_status_5.isSelected()) {
            primary_status = "5";
        }

        String[] statuses;
        if (!secondary_status.equals(""))
            statuses = (primary_status + " " + secondary_status).split(" ");
        else
            statuses = primary_status.split(" ");

        String[] tags = entertainment_tags.getText().split("\n");
        int duration = Integer.parseInt(entertainment_duration.getText());
        LocalDate date = api.convert_string_date_to_LocalDate(entertainment_release_date.getText());
        String[] production_companies = entertainment_production_companies.getText().split("\n");

        int season_num = Integer.parseInt(entertainment_season_number.getText());
        int episode_num = Integer.parseInt(entertainment_episode_number.getText());

        switch (type) {
            case "Movie":
                return new Movie(type, franchise, title, statuses, tags, duration, date, production_companies);
            case "Anime":
                return new Anime(type, franchise, title, statuses, tags, season_num, episode_num, date, duration);
            default:
                return new Entertainment(type, franchise, title, statuses, tags, date);
        }
    }

    public void bulkAddEntertainment() {

    }

    public void editEntertainment(Entertainment entertainment) {

    }

    public void bulkEditEntertainment(Entertainment[] entertainments) {

        Entertainment[] bulkList = new Entertainment[entertainments.length];

    }
}
