package main.java.api;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.api.Logger.ConsoleLog;
import main.java.api.Logger.ConsoleLogFactory;
import main.java.backend.Backend;
import main.java.backend.SearchEngine.IncrementalSearch;
import main.java.backend.entertainment.Entertainment;
import main.java.frontend.controllers.EditorController;
import main.java.frontend.controllers.MainFrameController;
import main.java.frontend.controllers.ModuleController;

public class API {

    private Backend backend;
    private IncrementalSearch engine;

    private MainFrameController mfController;

    private AnchorPane editor;
    private EditorController eController;

    private Stage entertainmentEditor;
    private Scene editorScene;

    private boolean editorDisabled;
    private boolean viewerDisabled;

    private ConsoleLog logger;

    // Basic Constructor
    public API() {
        logger = ConsoleLogFactory.getLogger();
    }

    // creates the backend and search engine
    public void createBackend() {
        backend = new Backend(this);
        engine = new IncrementalSearch(50);
    }

    // connects api to mainframe controller
    public void connectMainFrameController(MainFrameController mfController) {
        this.mfController = mfController;
    }

    // the entertainment passed will be edited in the editer
    public void editEntertainment(Entertainment entertainment) {

        try {
            eController.editEntertainment(entertainment);
            entertainmentEditor.setTitle("Entertainment Editor");
            entertainmentEditor.show();
            Client.disable();
        } catch (Exception e) {
            entertainmentEditor.hide();
            logger.log(this, "Editor not opened");
        }

    }

    // starts the backend processes
    public void setBackend() {
        backend.start(); // backend setup
        engine.setOriginalList(backend.getRawData()); // SearchEngine setup
    }

    // starts the frontend processes
    public void setFrontend() {

        // put information into modules
        mfController.setModules(backend.getEntertainmentList());

        // disablers
        {
            // disabling the viewer feature
            // disableViewer();

            // disabling the search feature
            // mfController.disableSearch();

            // disabling the editing feature
            // disableEditor();
        }

        // put modules in the ui
        mfController.populateModules();

        /*
         * the method sets up the viewers and editor
         */
        mfController.setFxmlsAndControllers();

        if (!editorDisabled) {

            logger.log(this, "Editor Ready");

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(
                        getClass().getResource("../../res/fxml/EntertainmentEditor.fxml"));
                editor = fxmlLoader.load();
                eController = fxmlLoader.getController();
                editor.getProperties().put("controller", eController);
                eController.setApi(this);
                eController.setEntertainmentList(getBackend().getEntertainmentList());
                eController.setEditor();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // logger.debug(this, "before error");

            editorScene = new Scene(editor);
            entertainmentEditor = new Stage();
            entertainmentEditor.setScene(editorScene);
        } else {
            logger.log(this, "Editor Disabled");
        }

    }

    @SuppressWarnings("unused")
    private void disableViewer() {
        viewerDisabled = true;
    }

    // disables the editor
    @SuppressWarnings("unused")
    private void disableEditor() {
        editorDisabled = true;
    }

    // gets the mainframe controller
    public MainFrameController getMfController() {
        return mfController;
    }

    // Date Format Conversions

    /*
     * converts: MMM dd, yyyy
     * to: yyyy-mm-dd
     */
    public String convert_format_1_to_format_2(String stringed_date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringed_date, inputFormatter);
        return date.format(outputFormatter);
    }

    /*
     * converts: yyyy-mm-dd
     * to: MMM dd, yyyy
     */
    public String convert_format_2_to_format_1(String stringed_date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        LocalDate date = LocalDate.parse(stringed_date, inputFormatter);
        return date.format(outputFormatter);
    }

    // LocalDate conversion

    /*
     * converts string: Jan 24, 2025
     * to LocalDate: Jan 24, 2025
     */
    public LocalDate convert_string_date_to_LocalDate(String stringed_date) {
        DateTimeFormatter format_1 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        LocalDate date = LocalDate.parse(stringed_date, format_1);
        return date;
    }

    /*
     * converts LocalDate: MMM dd, yyyy
     * to string: yyyy-mm-dd
     * to string: MMM dd, yyyy
     */
    public String convert_LocalDate_to_string_date(LocalDate localDate) {
        return convert_format_2_to_format_1(localDate.toString());
    }

    // Getters and Setters

    // backend
    public Backend getBackend() {
        return backend;
    }

    // search engine
    public IncrementalSearch getSearchEngine() {
        return engine;
    }

    public boolean isEditorDisabled() {
        return editorDisabled;
    }

    public boolean isViewerDisabled() {
        return viewerDisabled;
    }

    public void getEditorSize() {
        // logger.debug(this, editor.getWidth() + " :: " +
        // editor.getHeight());
    }

    public void sendRefreshModule(int searchID) {
        logger.log(this, "Search ID: " + searchID);
        // logger.log(this, "Information reset method activated");

        BorderPane duplicateModule = mfController.getSearchArrayList().get(searchID);
        ModuleController mController = (ModuleController) duplicateModule.getProperties().get("controller");
        mController.refresh();
        mfController.getSearchList().getItems().clear();

        if (mfController.getSearchResultModules() != null) {
            mfController.getSearchList().getItems().addAll(mfController.getSearchResultModules());
            mfController.resetViewer(searchID);
        }

        // logger.log(this, "Information reset");

    }

    public void closeEditor() {
        logger.log(this, "Editor closed");
        entertainmentEditor.hide();
        Client.enable();
        logger.log(this, "Client enabled");
    }

    // new method
}
