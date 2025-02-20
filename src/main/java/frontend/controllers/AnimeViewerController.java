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
import main.java.backend.entertainment.Anime;

public class AnimeViewerController {

    private API api;

    @FXML
    private Label entertainment_type;

    @FXML
    private Label entertainment_franchise;

    @FXML
    private Label entertainment_title;

    @FXML
    private Label entertainment_info;

    @FXML
    private Label entertainment_primary_status;

    @FXML
    private Label entertainment_release_date;

    @FXML
    private Label entertainment_duration;

    @SuppressWarnings("rawtypes")
    @FXML
    private ListView entertainment_tag_grid;

    @FXML
    private Button entertainment_button_edit;

    @FXML
    private Button entertainment_button_copy;

    private Anime anime;

    @FXML
    public void view(Anime anime) {

        this.anime = anime;

        entertainment_franchise.setText(anime.getFranchise());
        entertainment_title.setText(anime.getTitle());
        entertainment_info.setText("Season " + anime.getSeason() + " Episode " + anime.getEpisode());

        switch (anime.getPrimaryStatus()) {
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

        // add duration
        if (anime.getRuntime() == 0) {
            entertainment_duration.setText("Duration: Unkown");
        } else {
            entertainment_duration.setText("Duration: " + anime.getRuntime() + " min");
        }

        // add date
        if (api.convert_LocalDate_to_string_date(anime.getDate()).equals("Jan 01, 3000")) {
            entertainment_release_date.setText("Release Date: Unknown");
        } else {
            entertainment_release_date
                    .setText("Release Date: " + api.convert_LocalDate_to_string_date(anime.getDate()));
        }

        // add tags
        try {

            for (String tag : anime.getTags()) {
                FXMLLoader center_label_loader = new FXMLLoader(
                        getClass().getResource("../../../res/fxml/CenterLabel.fxml"));
                Label label = center_label_loader.load();
                CenterLabelController clc = center_label_loader.getController();

                clc.setText(tag);
                entertainment_tag_grid.getItems().add(label);
            }

        } catch (IOException e) {
            // api.getLogger().error(this, e);
        }
    }

    @FXML
    public void copyEntertainmentName() {

        String copy_text = "";

        if (anime.getTitle().equals("NVR")) {
            copy_text = anime.getFranchise();
        } else if (anime.getFranchise().contains("**")) {

            anime.setFranchise(anime.getFranchise().replace("**", " "));
            copy_text = anime.getFranchise() + "" + anime.getTitle();

        } else {
            copy_text = anime.getFranchise() + ": " + anime.getTitle();
        }

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(copy_text);
        clipboard.setContent(content);
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }

}
