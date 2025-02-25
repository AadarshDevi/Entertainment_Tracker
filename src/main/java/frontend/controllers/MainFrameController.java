package main.java.frontend.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.api.API;
import main.java.api.Logger.ConsoleLog;
import main.java.api.Logger.ConsoleLogFactory;
import main.java.backend.entertainment.Anime;
import main.java.backend.entertainment.Entertainment;
import main.java.backend.entertainment.Movie;

public class MainFrameController {

    public static final int ENTERTAINMENT_EDITOR = 3;
    public static final int ENTERTAINMENT_BULK_EDITOR = 4;

    @FXML
    private TabPane tabPane;

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

    // modules in completed, released and upcoming tabs
    private ArrayList<BorderPane> modules = new ArrayList<>();

    // modules in search tab
    private ArrayList<BorderPane> ogSearchModules = new ArrayList<>();

    // the ids of the modules in search
    private ArrayList<Integer> searchResults;

    private int list_completed_number = 0;
    private int list_released_number = 0;
    private int list_upcoming_number = 0;
    private int list_search_number = 0;

    private VBox movieViewer;
    private VBox animeViewer;

    private MovieViewerController mvController;
    private AnimeViewerController avController;

    // private int searchID;

    ConsoleLog logger = ConsoleLogFactory.getLogger();

    @FXML
    private void initialize() {

        search_field.setOnKeyPressed(event -> {
            // Get current text BEFORE processing event
            String currentText = search_field.getText();
            if (event.getCode() == KeyCode.ENTER) {
                // System.out.println("\n\nActual Text: '" + currentText + "'");
                // System.out.println(">>> Search Text: '" + currentText + "'");

                logger.log(this, "Search Text: '" + currentText + "'");

                searchResults = api.getSearchEngine().simpleSearch(currentText);

                searchController();
                System.out.println("Current Search List: " +
                        api.getSearchEngine().getCurrentSearchList().size());

            } else if (currentText.isEmpty()) {
                System.out.println("\n\n>>> Resetting Search Engine");
                api.getSearchEngine().resetEngine();
                System.out.println("Current Search List: " +
                        api.getSearchEngine().getCurrentSearchList().size());
            }
        });
    }

    public void connectAPI(API api) {
        this.api = api;
    }

    public void setModules(ArrayList<Entertainment> list) {
        for (int i = 0; i < list.size(); i++) {

            BorderPane module = createModule(list.get(i), (i + 1));
            modules.add(module);

            BorderPane duplicate = createModule(list.get(i), ++list_search_number);
            ogSearchModules.add(duplicate);
        }

        list_search.getItems().addAll(new ArrayList<>(ogSearchModules));
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEntertainment(int placeID, Entertainment entertainment) {
        switch (placeID) {
            // MovieViewer
            case 1:

                if (!api.isViewerDisabled()) {

                    information_viewer_stand_in.getChildren().clear();

                    mvController.setSize(
                            information_viewer_stand_in.getWidth(),
                            information_viewer_stand_in.getHeight());

                    information_viewer_stand_in.getChildren().add(movieViewer);
                    mvController.view((Movie) entertainment);
                } else {
                    logger.log(this, "Viewer Disabled");
                }
                break;

            // AnimeViewer
            case 2:
                if (!api.isViewerDisabled()) {
                    avController.view((Anime) entertainment);
                } else {
                    logger.log(this, "Viewer Disabled");
                }
                break;
            /*
             * // Entertainment Editor
             * case 3:
             * if (!api.isEditorDisabled()) {
             * logger.log(this, "Entered Editor");
             * // api.editEntertainment(moduleID);
             * } else {
             * logger.log(this, "Editor Disabled");
             * }
             * break;
             * 
             * // Entertainment Bulk Editor
             * case 4:
             * break;
             * 
             * // Entertainment Creator
             * case 5:
             * break;
             */
            default:
                logger.error(this, new Exception("placeID does not exist"));
        }
    }

    public void disableSearch() {
        search_tab.setDisable(true);
        search_field.setDisable(true);
        search_button.setDisable(true);

        logger.log(this, "Search Disabled");
    }

    @FXML
    private void searchController() {
        resetSearchListView();
        search();
    }

    /**
     * get the original list and duplicate it
     * get a list of the modules to remove
     */
    public void search() {

        // remove all modules in list_search
        list_search.getItems().clear();

        // the modules that will be removed from this list
        ArrayList<BorderPane> duplicateSearchList = new ArrayList<>(ogSearchModules);

        // this list contains modules to be removed
        ArrayList<BorderPane> removedModules = new ArrayList<>();

        // the id from the search results
        int moduleIdArray = 0;

        // going through the list of search modules
        for (BorderPane borderPane : duplicateSearchList) {

            // module controller of the module
            ModuleController mController = (ModuleController) (borderPane.getProperties().get("controller"));

            // id of module
            int moduleID = mController.getId();

            // id from search array
            int searchID = 0;
            if (moduleIdArray < searchResults.size()) {
                searchID = searchResults.get(moduleIdArray);
            }

            if (moduleID == searchID) {
                moduleIdArray++;
                logger.log(this,
                        "ID: " + moduleID + " : Franchise: " +
                                mController.getEntertainment().getFranchise());

            } else {
                removedModules.add(borderPane);
            }

        }

        duplicateSearchList.removeAll(removedModules);
        list_search.getItems().addAll(duplicateSearchList);

        // System.out.println(
        // "duplicateSearchList Length: " + duplicateSearchList.size() +
        // " :: searchResults Length: " + searchResults.size());

        if (duplicateSearchList.size() <= 0) {
            JOptionPane.showMessageDialog(null,
                    "Your search \"" + search_field.getText() + "\" was not found in the database",
                    "Search Not Found",
                    JOptionPane.ERROR_MESSAGE);
            resetSearchListView();
        }

        tabPane.getSelectionModel().select(3);

    }

    public void resetSearchListView() {
        list_search.getItems().clear(); // set listview to have nothing
        list_search.getItems().addAll(new ArrayList<>(ogSearchModules));

        // fixes the problem where modules disappear when searching
        list_search.refresh();
        list_completed.refresh();
        list_released.refresh();
        list_upcoming.refresh();
    }

    @FXML
    private void application_quit() {
        System.exit(0);
    }

    public void editEntertainment(Entertainment entertainment) {
        api.editEntertainment(entertainment);
    }
}
