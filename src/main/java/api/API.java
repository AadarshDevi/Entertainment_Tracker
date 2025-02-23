package main.java.api;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.backend.Backend;
import main.java.backend.SearchEngine.IncrementalSearch;
import main.java.backend.entertainment.Entertainment;
import main.java.frontend.controllers.EditorController;
import main.java.frontend.controllers.MainFrameController;

public class API {

    // private MainFrameController mfController;
    // private Backend backend;
    // private ConsoleLog logger;
    // private SearchEngine searchEngine;

    public API() {
        // logger = new ConsoleLog();
        // backend = new Backend(this);
        // searchEngine = new SearchEngine(this, backend.getRawData());
    }

    // public void setMainUIController(MainFrameController mfController) {
    // this.mfController = mfController;
    // }

    // public void setUIModules() {
    // mfController.loadModules(backend.getModules());
    // mfController.searchSetup();
    // }

    // public ConsoleLog getLogger() {
    // return logger;
    // }

    // public void application_close() {
    // backend.app_quit();
    // }

    // public void viewModuleData(Entertainment entertainment) {
    // mfController.loadViewers();
    // mfController.view(entertainment);
    // }

    // public ArrayList<Integer> searchPhrase(String string) {

    // logger.debug(this, "Search String: " + string);

    // ArrayList<Integer> result;

    // if (string.equals(""))
    // result = searchEngine.cmd(string);
    // else if (string.charAt(0) == '#')
    // result = new ArrayList<>();
    // else
    // result = searchEngine.search(string);

    // return result;
    // }

    // // TODO: get the entertainmnets from the search modules and send them
    // public Entertainment[] getDataFromApp() {
    // return new Entertainment[20];
    // }

    private Backend backend;
    private IncrementalSearch engine;

    private MainFrameController mfController;

    private AnchorPane editor;
    private EditorController eController;

    private Stage entertainmentEditor;
    private Scene editorScene;

    public void createBackend() {
        backend = new Backend(this);
        engine = new IncrementalSearch(50);
    }

    public Backend getBackend() {
        return backend;
    }

    public IncrementalSearch getSearchEngine() {
        return engine;
    }

    public void connectMainFrameController(MainFrameController mfController) {
        this.mfController = mfController;
    }

    public void setBackend() {
        backend.start(); // backend setup
        engine.setOriginalList(backend.getRawData()); // SearchEngine setup
    }

    public void setFrontend() {
        mfController.setModules(backend.getEntertainmentList());
        // mfController.disableSearch();
        mfController.populateModules();
        mfController.setFxmlsAndControllers();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("../../res/fxml/EntertainmentEditor.fxml"));
            editor = fxmlLoader.load();
            eController = fxmlLoader.getController();
            editor.getProperties().put("controller", eController);
            eController.setApi(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        editorScene = new Scene(editor);
        entertainmentEditor = new Stage();
        entertainmentEditor.setScene(editorScene);

    }

    public MainFrameController getMfController() {
        return mfController;
    }

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

    public void editEntertainment(Entertainment entertainment) {

        eController.editEntertainment(entertainment);

        entertainmentEditor.setTitle("Entertainment Editer");
        entertainmentEditor.show();
    }

}
