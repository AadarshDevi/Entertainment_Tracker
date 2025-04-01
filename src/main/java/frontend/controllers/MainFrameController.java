package main.java.frontend.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.java.api.Logger.ConsoleLog;
import main.java.api.Logger.ConsoleLogFactory;
import main.java.api.apiloader.API;
import main.java.api.apiloader.APIFactory;
import main.java.backend.SearchEngine.IncrementalSearch;
import main.java.backend.entertainment.Anime;
import main.java.backend.entertainment.Entertainment;
import main.java.backend.entertainment.Movie;
import main.java.frontend.controllers.vierer.AnimeViewerController;
import main.java.frontend.controllers.vierer.ModuleController;
import main.java.frontend.controllers.vierer.MovieViewerController;
import main.java.frontend.controllers.vierer.SeasonModuleController;

public class MainFrameController extends ParentController {

    public static final int ENTERTAINMENT_EDITOR = 3;
    public static final int ENTERTAINMENT_BULK_EDITOR = 4;

    @FXML private AnchorPane information_viewer_stand_in;

    @FXML private TabPane tabPane;
    @FXML private MenuItem app_quit;

    @FXML private ListView<BorderPane> list_completed;
    @FXML private ListView<BorderPane> list_released;
    @FXML private ListView<BorderPane> list_upcoming;
    @FXML private ListView<BorderPane> list_search;

    @FXML private MenuItem edit_entertainment_add;
    @FXML private MenuItem edit_entertainment_edit;

    @FXML private Tab search_tab;
    @FXML private TextField search_field;
    @FXML private Button search_button;

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

    private ArrayList<BorderPane> duplicateSearchList;

    ConsoleLog logger = ConsoleLogFactory.getLogger();

    private SeasonModuleController smController;
    private BorderPane seasonModule;

    private IncrementalSearch searchEngine;

    @FXML
    private void initialize() {

        { // is api null
            boolean isNull = (api == null) ? true : false;
            logger.debug(this, "Is api null (in mfController - before): " + isNull);
        }

        // if (api == null)
        connectAPI();

        System.out.println("Search Engine (check 1):" + searchEngine);

        // { // is api null
        // boolean isNull = (api == null) ? true : false;
        // logger.debug(this, "Is api null (in mfController - after): " + isNull);
        // }

        search_field.setOnKeyPressed(event -> {
            // Get current text BEFORE processing event
            String currentText = search_field.getText();

            if (event.getCode() == KeyCode.ENTER) {
                // System.out.println("\n\nActual Text: '" + currentText + "'");
                // System.out.println(">>> Search Text: '" + currentText + "'");

                logger.log(this, "Search Text: '" + currentText + "'");

                searchResults = searchEngine.simpleSearch(currentText);

                searchController();
                logger.log(this, "Current Search List: " +
                        searchEngine.getCurrentSearchList().size());

            } else if (currentText.isEmpty()) {
                System.out.println("\n\n>>> Resetting Search Engine");
                System.out.println("Search Engine(check 2):" + searchEngine);
                searchEngine.resetEngine();
                logger.log(this, "Current Search List: " +
                        searchEngine.getCurrentSearchList().size());
            }
        });
    }

    public void connectAPI() {
        this.api = APIFactory.getApi();

        { // is api null
            boolean isNull = (api == null) ? true : false;
            logger.debug(this, "Is api null (in mfController - at connection): " + isNull);
        }
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
            FXMLLoader moduleLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/viewer/Module.fxml"));
            BorderPane module = moduleLoader.load();
            ModuleController mController = (ModuleController) moduleLoader.getController();

            module.getProperties().put("controller", mController);

            mController.setApi();
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/viewer/MovieViewer.fxml"));
            movieViewer = fxmlLoader.load();
            mvController = fxmlLoader.getController();
            movieViewer.getProperties().put("controller", mvController);
            mvController.setApi();

            fxmlLoader = new FXMLLoader(getClass().getResource("../../../res/fxml/viewer/AnimeViewer.fxml"));
            animeViewer = fxmlLoader.load();
            avController = fxmlLoader.getController();
            animeViewer.getProperties().put("controller", avController);
            avController.setApi();

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

        list_search.getItems().clear(); // remove all modules in list_search

        // the modules that will be removed from this list
        duplicateSearchList = new ArrayList<>(ogSearchModules);

        // this list contains modules to be removed
        ArrayList<BorderPane> removedModules = new ArrayList<>();

        int moduleIdArray = 0; // the id from the search results

        // going through the list of search modules
        for (BorderPane borderPane : duplicateSearchList) {

            // module controller of the module
            ModuleController mController = (ModuleController) (borderPane.getProperties().get("controller"));

            int moduleID = mController.getId(); // id of module

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

    public ListView<BorderPane> getSearchList() {
        return list_search;
    }

    public ObservableList<BorderPane> getCompletedListModules() {
        return list_completed.getItems();
    }

    public ArrayList<BorderPane> getSearchArrayList() {
        return ogSearchModules;
    }

    public void resetViewer(int searchID) {

        information_viewer_stand_in.getChildren().clear();

        mvController.setSize(
                information_viewer_stand_in.getWidth(),
                information_viewer_stand_in.getHeight());

        information_viewer_stand_in.getChildren().add(movieViewer);

        BorderPane module = ogSearchModules.get(searchID);
        ModuleController controller = (ModuleController) module.getProperties().get("controller");
        mvController.view((Movie) controller.getEntertainment());

        logger.log(this, "Movie viewer reseted");
    }

    public ArrayList<BorderPane> getSearchResultModules() {
        if (duplicateSearchList != null)
            return duplicateSearchList;
        return null;
    }

    public void resetList() {
        ArrayList<BorderPane> tempList;
        // switch (list_) {
        // case 1:
        tempList = new ArrayList<>(list_completed.getItems());

        for (BorderPane module : tempList) {
            ModuleController mController = (ModuleController) module.getProperties().get("controller");
            mController.refresh();
        }

        list_completed.getItems().clear();
        list_completed.getItems().addAll(tempList);
        // break;

        // case 2:
        tempList = new ArrayList<>(list_released.getItems());

        for (BorderPane module : tempList) {
            ModuleController mController = (ModuleController) module.getProperties().get("controller");
            mController.refresh();
        }

        list_released.getItems().clear();
        list_released.getItems().addAll(tempList);
        // break;

        // case 3:
        tempList = new ArrayList<>(list_upcoming.getItems());

        for (BorderPane module : tempList) {
            ModuleController mController = (ModuleController) module.getProperties().get("controller");
            mController.refresh();
        }

        list_upcoming.getItems().clear();
        list_upcoming.getItems().addAll(tempList);
        // break;

        // default:
        tempList = null;
        logger.log(this, "Primary status does not exist");
        // }

        tempList = new ArrayList<>(ogSearchModules);

        for (BorderPane module : tempList) {
            ModuleController mController = (ModuleController) module.getProperties().get("controller");
            mController.refresh();
        }

        list_search.getItems().clear();
        list_search.getItems().addAll(tempList);

        list_completed.refresh();
        list_released.refresh();
        list_upcoming.refresh();
        list_search.refresh();
    }

    @FXML
    public void addSeasonModule() {

        try {
            FXMLLoader seasonModuleLoader = new FXMLLoader(
                    getClass().getResource("../../../res/fxml/viewer/SeasonModule_v2.fxml"));

            seasonModule = seasonModuleLoader.load();
            smController = seasonModuleLoader.getController();

            seasonModule.getProperties().put("controller", smController);
            smController.setApi();
            smController.testData();

            ogSearchModules.add(seasonModule);

            list_search.getItems().clear();
            list_search.getItems().addAll(new ArrayList<>(ogSearchModules));

            logger.debug(this, "Season Module Added");
            list_search.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeSeasonModule() {

        ogSearchModules.remove(seasonModule);

        list_search.getItems().clear();
        list_search.getItems().addAll(new ArrayList<>(ogSearchModules));

        logger.debug(this, "Season Module Removed");
        list_search.refresh();

    }

    @FXML
    public void add_entertainment() {
        api.addEntertainment();
    }

    public void setSearchEngine(IncrementalSearch engine) {
        this.searchEngine = api.getSearchEngine();
    }
}
