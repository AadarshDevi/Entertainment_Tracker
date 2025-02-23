package main.java.frontend.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

    @FXML
    private HBox entertainment_season_hbox_module;
    @FXML
    private HBox entertainment_episode_hbox_module;

    private ArrayList<Entertainment> entertainmentList;

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

        if (entertainment.getType().equals(Entertainment.MOVIE)) {
            setMovie((Movie) entertainment);
        }

    }

    public void bulkEditEntertainment(Entertainment[] entertainments) {

        // Entertainment[] bulkList = new Entertainment[entertainments.length];

    }

    private void setMovie(Movie movie) {

        // disable season and episode fields
        entertainment_season_hbox_module.setDisable(true);
        entertainment_season_hbox_module.setVisible(false);

        entertainment_episode_hbox_module.setDisable(true);
        entertainment_episode_hbox_module.setVisible(false);

        // basic info
        entertainment_type.setText("Movie");
        entertainment_franchise.setText(movie.getFranchise());
        entertainment_title.setText(movie.getTitle());

        switch (movie.getPrimaryStatus()) {
            case 1:
                entertainment_status_1.setSelected(true);
                break;

            case 2:
                entertainment_status_2.setSelected(true);
                break;

            case 3:
                entertainment_status_3.setSelected(true);
                break;

            default:
                break;
        }

        // status (primary and secondary)
        viewStatus(movie);

        // production companies
        String[] production_companies = entertainment_production_companies.getText().split("\n");
        String op_prod_comp = "";
        for (String string : production_companies) {
            op_prod_comp += string + "\n";
        }
        entertainment_production_companies.setText(op_prod_comp);

        // tags
        String[] tags = entertainment_tags.getText().split("\n");
        String op_tags = "";
        for (String string : tags) {
            op_tags += string + "\n";
        }
        entertainment_tags.setText(op_tags);

        // duration and release date
        entertainment_duration.setText(movie.getDuration() + "");
        entertainment_release_date.setText(movie.getDate() + "");
    }

    // TODO: add animes/tv shows
    // private void setAnime(Anime anime) {
    // }

    @FXML
    private void viewStatus(Entertainment entertainment) {

        // completed
        if (entertainment_status_1.isSelected()) {
            entertainment_status_2.setSelected(false);
            entertainment_status_3.setSelected(false);
            entertainment_status_2.setDisable(true);
            entertainment_status_3.setDisable(true);
        } else {
            entertainment_status_2.setSelected(false);
            entertainment_status_3.setSelected(false);
            entertainment_status_2.setDisable(false);
            entertainment_status_3.setDisable(false);
        }

        // released
        if (entertainment_status_2.isSelected()) {
            entertainment_status_1.setSelected(false);
            entertainment_status_3.setSelected(false);
            entertainment_status_1.setDisable(true);
            entertainment_status_3.setDisable(true);
        } else {
            entertainment_status_1.setSelected(false);
            entertainment_status_3.setSelected(false);
            entertainment_status_1.setDisable(false);
            entertainment_status_3.setDisable(false);
        }

        // upcoming
        if (entertainment_status_3.isSelected()) {
            entertainment_status_2.setSelected(false);
            entertainment_status_1.setSelected(false);
            entertainment_status_2.setDisable(true);
            entertainment_status_1.setDisable(true);
        } else {
            entertainment_status_2.setSelected(false);
            entertainment_status_1.setSelected(false);
            entertainment_status_2.setDisable(false);
            entertainment_status_1.setDisable(false);
        }

        // special
        // pilot
    }

    public void setEntertainmentList(ArrayList<Entertainment> entertainmentList) {
        this.entertainmentList = entertainmentList;
    }

}
