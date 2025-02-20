package main.java.frontend.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.java.api.API;
import main.java.backend.entertainment.Anime;
import main.java.backend.entertainment.Entertainment;
import main.java.backend.entertainment.Movie;

public class MainFrameController {

    @FXML
    private ListView<BorderPane> list_completed;

    @FXML
    private ListView<BorderPane> list_released;

    @FXML
    private ListView<BorderPane> list_upcoming;

    @FXML
    private ListView<BorderPane> list_search;

    @FXML
    private MenuItem app_quit;

    @FXML
    private MenuItem edit_entertainment_add;

    @FXML
    private MenuItem edit_entertainment_edit;

    @FXML
    private TextField search_field;

    @FXML
    private AnchorPane information_viewer_stand_in;

    @FXML
    private Tab search_tab;

    @FXML
    private Button search_button;

    private API api;
    private ArrayList<BorderPane> modules = new ArrayList<>();
    private ArrayList<BorderPane> searchModules = new ArrayList<>();
    private ArrayList<Integer> searchResults = new ArrayList<>();

    private int list_completed_number = 0;
    private int list_released_number = 0;
    private int list_upcoming_number = 0;
    private int list_search_number = 0;

    private VBox movieViewer;
    private VBox animeViewer;
    private AnchorPane editor;

    private MovieViewerController mvController;
    private AnimeViewerController avController;
    private EditorController eController;

    @FXML
    private void initialize() {

        // search_field.setOnKeyPressed(event -> {
        // // Get current text BEFORE processing event
        // String currentText = search_field.getText();
        // if (event.getCode() == KeyCode.ENTER) {
        // System.out.println("\n\nActual Text: '" + currentText + "'");
        // System.out.println(">>> Search Text: '" + currentText + "'");
        // ArrayList<Integer> searchResults = api.getSearchEngine().search(currentText);
        // for (Integer integer : searchResults) {
        // integer += 1000;
        // }
        // searchController();
        // System.out.println("Current Search List: " +
        // api.getSearchEngine().getCurrentSearchList().size());
        // } else if (currentText.isEmpty()) {
        // System.out.println("\n\n>>> Resetting Search Engine");
        // api.getSearchEngine().resetEngine();
        // System.out.println("Current Search List: " +
        // api.getSearchEngine().getCurrentSearchList().size());
        // }
        // });
    }

    public void connectAPI(API api) {
        this.api = api;
    }

    public void setModules(ArrayList<Entertainment> list) {
        for (int i = 0; i < list.size(); i++) {
            BorderPane module = createModule(list.get(i), (i + 1));
            modules.add(module);
            BorderPane duplicate = createModule(list.get(i), ++list_search_number);

            searchModules.add(duplicate);
        }

        list_search.getItems().addAll(searchModules);
    }

    /*
     * the method creates a module using the entertainment adn id. after creating
     * it, it will create a duplicate module with the same information and put it in
     * the search tab for searching
     */
    public BorderPane createModule(Entertainment entertainment, int id) {
        try {

            // create module
            FXMLLoader moduleLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/Module.fxml"));
            BorderPane module = moduleLoader.load();
            ModuleController mController = (ModuleController) moduleLoader.getController();

            module.getProperties().put("controller", mController);

            mController.setApi(api);
            mController.setId(id);
            mController.setEntertainment(entertainment);

            return module;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new BorderPane();
    }

    public void populateModules() {

        // Reset counters before populating
        list_completed_number = 0;
        list_released_number = 0;
        list_upcoming_number = 0;

        // Clear existing lists before repopulating
        list_completed.getItems().clear();
        list_released.getItems().clear();
        list_upcoming.getItems().clear();

        for (BorderPane module : modules) {
            populateModule(module);
        }
    }

    public void populateModule(BorderPane module) {
        ModuleController mController = (ModuleController) module.getProperties().get("controller");
        Entertainment entertainment = mController.getEntertainment();
        int primaryStatus = entertainment.getPrimaryStatus();
        LocalDate entertainmentDate = entertainment.getDate();

        // completed
        if (primaryStatus == 1) {
            mController.setId(++list_completed_number);
            list_completed.getItems().add(module);
        } else {

            LocalDate todaysDate = LocalDate.now();

            // upcoming
            if (entertainmentDate.isAfter(todaysDate)) {
                mController.setId(++list_upcoming_number);
                list_upcoming.getItems().add(module);
                entertainment.setPrimaryStatus(3);

                // released
            } else {
                mController.setId(++list_released_number);
                list_released.getItems().add(module);
                entertainment.setPrimaryStatus(2);
            }
        }
    }

    public void setFxmlsAndControllers() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/MovieViewer.fxml"));
            movieViewer = fxmlLoader.load();
            mvController = fxmlLoader.getController();
            movieViewer.getProperties().put("controller", mvController);
            mvController.setApi(api);

            fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/AnimeViewer.fxml"));
            animeViewer = fxmlLoader.load();
            avController = fxmlLoader.getController();
            animeViewer.getProperties().put("controller", avController);
            avController.setApi(api);

            fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/EntertainmentEditor.fxml"));
            editor = fxmlLoader.load();
            eController = fxmlLoader.getController();
            editor.getProperties().put("controller", eController);
            eController.setApi(api);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEntertainment(int placeID, Entertainment entertainment) {
        switch (placeID) {
            // MovieViewer
            case 1:
                mvController.view((Movie) entertainment);
                break;

            // AnimeViewer
            case 2:
                avController.view((Anime) entertainment);
                break;

            // Entertainment Editor
            case 3:
                break;

            // Entertainment Bulk Editor
            case 4:
                break;

            // Entertainment Creator
            case 5:
                break;

            default:
        }
    }

    public void disableSearch() {
        search_tab.setDisable(true);
        search_field.setDisable(true);
        search_button.setDisable(true);
    }

    @FXML
    private void searchController() {
        // resetSearchListView();
        // search();
    }

    // public void search() {
    // // Create a list to hold modules to remove
    // List<BorderPane> modulesToRemove = new ArrayList<>();

    // for (BorderPane module : list_search.getItems()) {
    // ModuleController mController = (ModuleController)
    // module.getProperties().get("controller");

    // // Check if the current module's ID is not in searchResults
    // if (!searchResults.contains(mController.getId())) {
    // modulesToRemove.add(module); // Mark for removal
    // System.out.println(
    // ">>> Removed ID: " + mController.getId() +
    // " >>> Primary Status: " + mController.getEntertainment().getPrimaryStatus() +
    // " >>> Franchise: " + mController.getEntertainment().getFranchise());
    // }
    // }

    // // Remove all marked modules from the ListView
    // list_search.getItems().removeAll(modulesToRemove);
    // list_search.refresh();
    // }

    // public void resetSearchListView() {
    // list_search.getItems().clear(); // set listview to have nothing
    // list_search.getItems().addAll(new ArrayList<>(modules));
    // list_search.refresh();
    // }

    @FXML
    private void application_quit() {
    }
}