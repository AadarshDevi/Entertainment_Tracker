package main.java.frontend.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import main.java.api.API;
import main.java.api.Logger.ConsoleLog;
import main.java.api.Logger.ConsoleLogFactory;
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
    private ConsoleLog logger;

    @FXML
    private Button to_first_button;
    @FXML
    private Button previous_button;
    @FXML
    private Button search_button;
    @FXML
    private Button next_button;
    @FXML
    private Button to_last_button;

    private Entertainment entertainment;

    private int searchID;

    public void setApi(API api) {

        this.api = api;
        logger = ConsoleLogFactory.getLogger();
    }

    private void disbaleSlider() {
        to_first_button.setDisable(true);
        previous_button.setDisable(true);
        search_button.setDisable(true);
        next_button.setDisable(true);
        to_last_button.setDisable(true);
        module_id.setDisable(true);
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

    public void editEntertainment(Entertainment entertainment) throws Exception {

        disbaleSlider();

        this.entertainment = entertainment;

        searchID = findEntertainment(entertainment);

        logger.debug(this, searchID + "");

        if (searchID >= 0) {
            logger.log(this, "Entertainment found");

            if (entertainment.getType().equals(Entertainment.MOVIE)) {

                module_id.setText((searchID + 1) + "");
                setMovie((Movie) entertainment);

                // TODO: Add anime
            } else {
                logger.error(this, new Exception("Entertainment Type not found"));
            }
            entertainment_franchise.requestFocus();

        } else {
            logger.error(this, new Exception("Entertainment not found: " + entertainment.getFranchise()));
            JOptionPane.showMessageDialog(null,
                    "Entertainment not found: " + entertainment.getFranchise(),
                    "Entertainment Not Found",
                    JOptionPane.ERROR_MESSAGE);
            throw new Exception("Entertainment Not Found");
        }

        // disabledSlider();

    }

    private int findEntertainment(Entertainment searchEntertainment) {

        int finalI = -1;
        for (int i = 0; i < entertainmentList.size(); i++) {
            if (searchEntertainment.equals(entertainmentList.get(i))) {
                finalI = i;
            }
        }
        return finalI;
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
        switch (movie.getPrimaryStatus()) {
            case 1:
                entertainment_status_1.setSelected(true);
                entertainment_status_2.setSelected(false);
                entertainment_status_3.setSelected(false);
                break;

            case 2:
                entertainment_status_2.setSelected(true);
                entertainment_status_3.setSelected(false);
                entertainment_status_1.setSelected(false);
                break;

            case 3:
                entertainment_status_3.setSelected(true);
                entertainment_status_1.setSelected(false);
                entertainment_status_2.setSelected(false);
                break;

            default:
                entertainment_status_3.setSelected(false);
                entertainment_status_1.setSelected(false);
                entertainment_status_2.setSelected(false);
        }

        // check secondary status
        switch (movie.getPrimaryStatus()) {
            case 4:
                entertainment_status_4.setSelected(true);
                entertainment_status_5.setSelected(false);
                break;

            case 5:
                entertainment_status_4.setSelected(false);
                entertainment_status_5.setSelected(true);
                break;

            default:
                entertainment_status_4.setSelected(false);
                entertainment_status_5.setSelected(false);
        }

        // disables unwanted checkboxes
        checkPrimaryStatus();
        checkSecondaryStatus();

        // production companies
        String op_prod_comp = "";
        for (String string : movie.getAnimationCompanies()) {
            op_prod_comp += string + "\n";
        }
        // logger.debug(this, op_prod_comp);
        entertainment_production_companies.setText(op_prod_comp);

        // tags
        String op_tags = "";
        for (String string : movie.getTags()) {
            op_tags += string + "\n";
        }
        entertainment_tags.setText(op_tags);

        // duration and release date
        entertainment_duration.setText(movie.getDuration() + "");
        entertainment_release_date.setText(movie.getDate() + "");

    }

    // TODO: add animes/tv shows
    @SuppressWarnings("unused")
    private void setAnime(Anime anime) {
    }

    @FXML
    public void checkPrimaryStatus() {
        // logger.debug(this, "Checking Primary Status Checkboxes");

        if (entertainment_status_1.isSelected()) {
            entertainment_status_2.setDisable(true);
            entertainment_status_3.setDisable(true);

            entertainment_status_2.setSelected(false);
            entertainment_status_3.setSelected(false);

        } else if (entertainment_status_2.isSelected()) {
            entertainment_status_1.setDisable(true);
            entertainment_status_3.setDisable(true);

            entertainment_status_1.setSelected(false);
            entertainment_status_3.setSelected(false);

        } else if (entertainment_status_3.isSelected()) {
            entertainment_status_1.setDisable(true);
            entertainment_status_2.setDisable(true);

            entertainment_status_1.setSelected(false);
            entertainment_status_2.setSelected(false);
        } else {
            entertainment_status_1.setDisable(false);
            entertainment_status_2.setDisable(false);
            entertainment_status_3.setDisable(false);

            entertainment_status_1.setSelected(false);
            entertainment_status_2.setSelected(false);
            entertainment_status_3.setSelected(false);
        }

    }

    @FXML
    public void checkSecondaryStatus() {
        // logger.debug(this, "Checking Secondary Status Checkboxes");

        if (entertainment_status_4.isSelected()) {
            entertainment_status_5.setDisable(true);
            entertainment_status_5.setSelected(false);

        } else if (entertainment_status_5.isSelected()) {
            entertainment_status_4.setDisable(true);
            entertainment_status_4.setSelected(false);

        } else {
            entertainment_status_4.setDisable(false);
            entertainment_status_5.setDisable(false);

            entertainment_status_4.setSelected(false);
            entertainment_status_5.setSelected(false);
        }

    }

    public void setEntertainmentList(ArrayList<Entertainment> entertainmentList) {
        this.entertainmentList = entertainmentList;
    }

    public void getEditorSize() {
        api.getEditorSize();
    }

    public void setEditor() {
        String filepath = "../../../res/img/search_icon.png";
        // String filepath =
        // "D:\\Programming\\Java\\Projects\\Entertainment_Tracker\\Group_3\\Build_1\\src\\main\\res\\img\\search_icon.png";

        Image image = new Image(getClass().getResourceAsStream(filepath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(search_button.getPrefHeight() - 12);
        imageView.setFitWidth(search_button.getPrefWidth() - 12);
        imageView.setPreserveRatio(true);
        search_button.setText("");
        search_button.setGraphic(imageView);
        // search_button.setContentDisplay(ContentDisplay.CENTER);

    }

    @FXML
    public void save_current_entertainment() {

        switch (entertainment.getType()) {
            case Entertainment.MOVIE:
                Movie movie = (Movie) entertainment;

                if (!movie.getType().equals(Entertainment.MOVIE))
                    movie.setType(Entertainment.MOVIE);
                if (!movie.getFranchise().equals(entertainment_franchise.getText())) {
                    movie.setFranchise(entertainment_franchise.getText());
                    logger.debug(this, "Saving: " + movie.getFranchise());
                }
                if (!movie.getTitle().equals(entertainment_title.getText()))
                    movie.setFranchise(entertainment_title.getText());
                // TODO: DO Date
                // if(!movie.getDate().equals(Entertainment.MOVIE)) movie.;
                if (movie.getDuration() != Integer.parseInt(entertainment_duration.getText()))
                    movie.setDuration(Integer.parseInt(entertainment_duration.getText()));

                // TODO: primary status and secondary status

                // TODO: tags and production companies

                break;

            default:
                break;
        }

        api.sendRefreshModule(searchID);
        api.closeEditor();

    }

    public void refreshModules() {

    }

    public int getSearchID() {
        return searchID;
    }
}
