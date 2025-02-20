package main.java.frontend.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import main.java.api.API;
import main.java.backend.entertainment.Movie;

public class MovieViewerController {

    private API api;

    @SuppressWarnings("rawtypes")
    @FXML
    private ListView movie_tags;

    @SuppressWarnings("rawtypes")
    @FXML
    private ListView movie_production_companies;

    @FXML
    private Button entertainment_button_edit;

    @FXML
    private Button entertainment_button_copy;

    @FXML
    private Label entertainment_franchise;

    @FXML
    private Label entertainment_title;

    @FXML
    private Label entertainment_primary_status;

    @FXML
    private Label entertainment_secondary_status;

    @FXML
    private Label entertainment_release_date;

    @FXML
    private Label entertainment_duration;

    private Movie movie;

    @FXML
    public void view(Movie movie) {

        this.movie = movie;

        if (!movie.getTitle().equals("NVR")) {
            entertainment_title.setText("");
            entertainment_title.setVisible(true);
            entertainment_title.setManaged(true);

            if (movie.getFranchise().contains("**")) {
                if (movie.getTitle().contains(": ")) {
                    String additional_text = movie.getTitle().substring(0,
                            movie.getTitle().indexOf(": "));
                    entertainment_franchise.setText(movie.getFranchise().replace("**", " ") +
                            additional_text);
                    entertainment_title.setText(movie.getTitle().substring(movie.getTitle().indexOf(": ") + 2));
                } else {
                    entertainment_franchise.setText(movie.getFranchise().replace("**", " ") +
                            movie.getTitle());
                    entertainment_title.setText("");
                    entertainment_title.setVisible(false);
                    entertainment_title.setManaged(false);
                }

            } else {
                entertainment_franchise.setText(movie.getFranchise());
                entertainment_title.setText(movie.getTitle());
            }

        } else {
            entertainment_title.setText("");
            entertainment_title.setVisible(false);
            entertainment_title.setManaged(false);

            entertainment_franchise.setText(movie.getFranchise());
        }

        switch (movie.getPrimaryStatus()) {
            case 1:
                entertainment_primary_status.setText("Completed");
                break;
            case 2:
                entertainment_primary_status.setText("Released");
                break;
            case 3:
                entertainment_primary_status.setText("Upcoming");
                break;
        }

        if (movie.getSecondaryStatus() == 0) {

            entertainment_secondary_status.setText("");
            entertainment_secondary_status.setVisible(false);
            entertainment_secondary_status.setManaged(false);

        } else {

            entertainment_secondary_status.setVisible(true);
            entertainment_secondary_status.setManaged(true);

            switch (movie.getSecondaryStatus()) {
                case 4:
                    entertainment_secondary_status.setText("Special");
                    break;
                case 5:
                    entertainment_secondary_status.setText("Pilot");
                    break;
            }

        }

        if (movie.getDuration() == 0) {
            entertainment_duration.setText("Duration: Unkown");
        } else {
            entertainment_duration.setText("Duration: " + movie.getDuration() + " min");
        }

        if (api.convert_LocalDate_to_string_date(movie.getDate()).equals("Jan 01, 3000")) {
            entertainment_release_date.setText("Release Date: Unknown");
        } else if (api.convert_LocalDate_to_string_date(movie.getDate()).equals("Jan02,3000"))

        {
            entertainment_release_date.setText("Release Date: Completed");
        } else {
            entertainment_release_date
                    .setText("Release Date: " + api.convert_LocalDate_to_string_date(movie.getDate()));
        }

        // loads CenterLabel
        FXMLLoader center_label_loader = new FXMLLoader(
                getClass().getResource("../../../res/fxml/CenterLabel.fxml"));

        // add production companies
        try {
            for (String production_company : movie.getAnimationCompanies()) {
                // FXMLLoader center_label_loader = new FXMLLoader(
                // getClass().getResource("../../../res/fxml/CenterLabel.fxml"));
                Label label = center_label_loader.load();
                CenterLabelController clc = center_label_loader.getController();

                clc.setText(production_company);
                movie_production_companies.getItems().add(label);
            }

            // add tags
            for (String tag : movie.getTags()) {
                // FXMLLoader center_label_loader = new FXMLLoader(
                // getClass().getResource("../../../res/fxml/CenterLabel.fxml"));
                Label label = center_label_loader.load();
                CenterLabelController clc = center_label_loader.getController();

                clc.setText(tag);
                movie_tags.getItems().add(label);
            }

        } catch (IOException e) {
        }
    }

    @FXML
    public void copyEntertainmentName() {

        String copy_text = "";

        // api.getLogger().debug(this, "\"" + movie.getFranchise() + "\"");

        if (movie.getTitle().equals("NVR")) {
            copy_text = movie.getFranchise();
        } else if (movie.getFranchise().contains("**")) {

            movie.setFranchise(movie.getFranchise().replace("**", " "));
            copy_text = movie.getFranchise() + movie.getTitle();

        } else {
            copy_text = movie.getFranchise() + ": " + movie.getTitle();
        }

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(copy_text);
        clipboard.setContent(content);
    }

    public void setApi(API api) {
        this.api = api;
    }

}