package main.java.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.java.api.API;
import main.java.backend.entertainment.Anime;
import main.java.backend.entertainment.Entertainment;
import main.java.backend.entertainment.Movie;

public class ModuleController {

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

    public void initialize() {
        // module_id = new Label();
        // module_name = new Label();
        // module_info_left = new Label();
        // module_info_right = new Label();
    }

    public void setData(int id, Entertainment entertainment) {
        this.entertainment = entertainment;
        // this.id = id;
        viewData();
    }

    public void viewData() {
        // module_id.setText(id + ".");
        module_name.setText(entertainment.getFranchise() + ": " + entertainment.getTitle());
        // api.getLogger().debug(this, entertainment.getFranchise());
        switch (entertainment.getType()) {
            case Entertainment.MOVIE:
                Movie movie = (Movie) entertainment;
                module_info_left.setText(movie.getDate());
                module_info_right.setText(Integer.toString(movie.getRuntime()));
                // api.getLogger().debug(this, module_info_left.getText());
                break;
            case Entertainment.ANIME:
                Anime anime = (Anime) entertainment;
                module_info_left.setText("S" + anime.getSeason());
                module_info_right.setText("E" + anime.getEpisode());
                // api.getLogger().debug(this, module_info_left.getText());
                break;
            default:
                api.getLogger().error(this, " --> Temp Text <--");

        }
        api.getLogger().debug(this, module_info_left.getText());
    }

    public Entertainment getEntertainment() {
        return entertainment;
    }

    public void setApi(API api) {
        this.api = api;
    }

    public void setID(int id) {
        this.id = id;
        module_id.setText(this.id + ".");
    }
}
