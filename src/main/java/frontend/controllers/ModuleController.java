package main.java.frontend.controllers;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.java.api.API;
import main.java.api.Logger.ConsoleLog;
import main.java.api.Logger.ConsoleLogFactory;
import main.java.backend.entertainment.Anime;
import main.java.backend.entertainment.Entertainment;
import main.java.backend.entertainment.Movie;

public class ModuleController {

    private final ConsoleLog logger = ConsoleLogFactory.getLogger();

    private Entertainment entertainment;
    private int id;

    private API api;

    @FXML
    private Label module_id;

    @FXML
    private Label module_name;

    @FXML
    private Label module_info_left;

    @FXML
    private Label module_info_right;

    public void setEntertainment(Entertainment entertainment) {
        this.entertainment = entertainment;
        viewEntertainment();
    }

    private void viewEntertainment() {

        // set module name
        if (entertainment.getTitle().equals("NVR")) {
            module_name.setText(entertainment.getFranchise());
        } else if (entertainment.getFranchise().contains("**")) {
            module_name.setText(entertainment.getFranchise().replace("**", "") +
                    " " + entertainment.getTitle());
        } else {
            module_name.setText(entertainment.getFranchise() + ": " +
                    entertainment.getTitle());
        }

        LocalDate unknownDate = LocalDate.of(3000, 01, 01);
        LocalDate completedDate = LocalDate.of(3000, 01, 02);

        if (entertainment.getDate().isEqual(unknownDate)) {
            module_info_right.setText("Unknown");
        } else if (entertainment.getDate().isEqual(completedDate)) {
            module_info_right.setText("Completed");
        } else {
            module_info_right.setText(api.convert_LocalDate_to_string_date(entertainment.getDate()));
        }

        switch (entertainment.getType()) {
            case Entertainment.MOVIE:

                // convert entertainment to movie
                Movie movie = (Movie) entertainment;

                // set viewing duration
                String left_info = (movie.getDuration() == 0) ? "Unknown" : (movie.getDuration() + " min");
                module_info_left.setText(left_info);
                break;

            case Entertainment.ANIME:

                // convert entertainment to anime
                Anime anime = (Anime) entertainment;

                // checking of the series is over
                left_info = (anime.getEpisode() == 947) ? "No Info"
                        : "S" + anime.getSeason() + " E" + anime.getEpisode();
                module_info_left.setText(left_info);
                break;

            default:
                break;
        }

        module_id.setVisible(true);
        module_name.setVisible(true);
        module_info_left.setVisible(true);
        module_info_right.setVisible(true);

        // Force layout update
        // list_completed.getParent().layout();
    }

    public void setApi(API api) {
        this.api = api;
    }

    public void setId(int id) {
        this.id = id;
        module_id.setText(id + ".");
    }

    public Entertainment getEntertainment() {
        return entertainment;
    }

    public int getId() {
        return id;
    }

    /*
     * when the module is clicked, it will send the entertainment information to the
     * api. the api will then take the data and give it to the information viewer.
     * based on the type of the entertainment, the viewer will change the template
     * before adding it.
     */
    @FXML
    public void onClicked() {

        logger.log(this, ("Module clicked: " + entertainment.getFranchise()));

        int placeID = (entertainment.getType().equals("Movie")) ? 1 : 2;
        api.getMfController().sendEntertainment(placeID, entertainment);
    }

}
