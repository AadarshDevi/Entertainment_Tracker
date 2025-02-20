package main.java.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import main.java.api.API;

public class MainFrameController {

    private API api;
    @FXML
    private ListView<BorderPane> list_completed;
    private int list_completed_number = 0;

    @FXML
    private ListView<BorderPane> list_released;
    private int list_released_number = 0;

    @FXML
    private ListView<BorderPane> list_upcoming;
    private int list_upcoming_number = 0;

    @FXML
    private ListView<BorderPane> list_search;
    private int list_search_number = 0;

    public void setModules(BorderPane[] modules) {
        for (BorderPane module : modules) {
            ModuleController mc = (ModuleController) module.getProperties().get("controller");

            api.getLogger().debug(this, mc.getEntertainment().getType());

            for (String status : mc.getEntertainment().getStatuses()) {
                if (status.equals("1")) {
                    mc.setID(++list_completed_number);
                    list_completed.getItems().add(module);
                    list_completed.layout();
                } else if (status.equals("2")) {
                    mc.setID(++list_released_number);
                    list_released.getItems().add(module);
                } else if (status.equals("3")) {
                    mc.setID(++list_upcoming_number);
                    list_upcoming.getItems().add(module);
                }
            }
        }
    }

    public API getApi() {
        return api;
    }

    public void setApi(API api) {
        this.api = api;
    }

}
