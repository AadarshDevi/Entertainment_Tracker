package main.java.frontend.controllers.vierer;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import main.java.api.Logger.ConsoleLog;
import main.java.api.Logger.ConsoleLogFactory;
import main.java.api.apiloader.API;
import main.java.api.apiloader.APIFactory;
import main.java.backend.entertainment.Entertainment;
import main.java.frontend.controllers.ParentController;

public class SeasonModuleController extends ParentController {

    @SuppressWarnings("unused") private final ConsoleLog logger = ConsoleLogFactory.getLogger();

    private Entertainment entertainment;
    private int id;

    private API api;

    @FXML private BorderPane module_container;
    @FXML private BorderPane module_info;
    @FXML private Label module_name;
    @FXML private Label module_season_info;
    @FXML private Button module_colapser_expander;
    @FXML private Label module_id;
    @FXML private ListView<BorderPane> module_list;

    private boolean isExpanded = false;

    @FXML
    public void initialize() {

    }

    @SuppressWarnings("unused")
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

        module_id.setVisible(true);
        module_name.setVisible(true);
    }

    public void setApi() {
        this.api = APIFactory.getApi();
    }

    public void setEntertainment(Entertainment entertainment) {
        this.entertainment = entertainment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entertainment getEntertainment() {
        return entertainment;
    }

    public int getId() {
        return id;
    }

    public void addEpisode() {
    }

    public void removeEpisode() {
    }

    public void getEpisode(int index) {
    }

    public void addEpisode(int index) {
    }

    public void clearEpisodes() {
    }

    public void removeEpisode(ArrayList<BorderPane> removeItems) {
    }

    // Toggle between expanded and collapsed states
    @FXML
    public void toggleExpandCollapse() {
        if (isExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    // Expand method to show ListView
    public void expand() {
        isExpanded = true;
        module_list.setVisible(true); // Make ListView visible
        module_list.setManaged(true); // Include it in layout calculations
        module_colapser_expander.setText("Collapse"); // Update button text
        module_container.setPrefHeight(340.0); // Only header height

        api.getMfController().getSearchList().refresh();
    }

    // Collapse method to hide ListView
    public void collapse() {
        isExpanded = false;
        module_list.setVisible(false); // Hide ListView
        module_list.setManaged(false); // Exclude it from layout calculations
        module_colapser_expander.setText("Expand"); // Update button text
        module_container.setPrefHeight(34.0); // Only header height

        api.getMfController().getSearchList().refresh();
    }

    public void testData() {
        module_name.setText("Miraculous: Tales of Ladybug and Cat Noir");
        module_season_info.setText("Season 6");
        module_id.setText("1.");

        String[][] test_data = {
                { "2.", "The Illustrhater", "22 min", "Jan 24, 2025" },
                { "3.", "Sublimation", "22 min", "Jan 31, 2025" },
                { "4.", "Daddycop", "22 min", "Feb 8, 2025" },
                { "5.", "Werepapas", "22 min", "Feb 15, 2025" },
                { "11.", "Revelator", "22 min", "April 19, 2025" },
                { "1.", "Climatiqueen", "22 min", "April 26, 2025" }
        };

        try {

            BorderPane[] modules = new BorderPane[6];
            int i = 0;

            for (String[] strings : test_data) {

                // create module
                FXMLLoader moduleLoader = new FXMLLoader(
                        getClass().getResource("../../../../res/fxml/viewer/Module.fxml"));

                System.out.println("Location: " + moduleLoader.getLocation());

                BorderPane module = moduleLoader.load();
                ModuleController mController = (ModuleController) moduleLoader.getController();

                module.getProperties().put("controller", mController);

                mController.setApi();
                mController.setId(id);
                mController.setTestData(strings[0], strings[1], strings[2], strings[3]);
                modules[i++] = module;

            }

            module_list.getItems().addAll(modules);

            collapse();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
