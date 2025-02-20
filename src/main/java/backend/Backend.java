package main.java.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import main.java.api.API;
import main.java.backend.entertainment.Anime;
import main.java.backend.entertainment.Entertainment;
import main.java.backend.entertainment.Movie;
import main.java.frontend.controllers.ModuleController;

public class Backend {
    API api;

    private String[] raw_data;
    private String[][] data;

    private BorderPane[] modules;
    private Entertainment[] entertainments;

    public Backend(API api) {

        this.api = api;
        api.getLogger().log(this, "processes started");
        this.startProccess();
    }

    public void startProccess() {
        readData();
        setEntertainment();
        setModules();
        api.getLogger().log(this, "processes ended");
    }

    private void setModules() {
        api.getLogger().log(this, "Creating Modules");
        try {
            FXMLLoader moduleLoader = new FXMLLoader(getClass().getResource("../../res/fxml/Module.fxml"));
            BorderPane module = moduleLoader.load();
            ModuleController moduleController = moduleLoader.getController();
            moduleController.setApi(api);
            module.getProperties().put("controller", moduleController);

            int id = 0;
            for (Entertainment entertainment : entertainments) {
                moduleController.setData(++id, entertainment);
                modules[(id - 1)] = module;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        api.getLogger().log(this, "Modules Created");
    }

    public BorderPane[] getModules() {
        return modules;
    }

    private void setEntertainment() {
        api.getLogger().log(this, "Creating Entertainments");
        for (int i = 0; i < data.length; i++) {
            String[] dataline = data[i];
            switch (dataline[0]) {

                case Entertainment.MOVIE:
                    Movie movie = new Movie(dataline[0], dataline[1], dataline[2],
                            parseDataL2(dataline[3]), parseDataL2(dataline[4]), Integer.parseInt(dataline[5]),
                            dataline[6]);
                    entertainments[i] = movie;
                    break;

                case Entertainment.ANIME:
                    Anime anime = new Anime(dataline[0], dataline[1], dataline[2], parseDataL2(dataline[3]),
                            parseDataL2(dataline[4]), Integer.parseInt(dataline[5]), Integer.parseInt(dataline[6]),
                            dataline[7], Integer.parseInt(dataline[8]));

                    entertainments[i] = anime;
                    break;

                default:
                    api.getLogger().error(this, dataline[0] + " Not Found");
            }
        }
        // System.out.println();
        api.getLogger().log(this, "Loaded " + entertainments.length + " entertainment items");
        api.getLogger().log(this, "Entertainments Created");
    }

    public Entertainment[] getEntertainments() {
        return entertainments;
    }

    // the method gets and reads data in data file
    private void readData() {
        api.getLogger().log(this, "Reading Data");
        // filepath for data.txt
        String datafilepath = "D:/Programming/Java/Projects/Entertainment_Tracker/Group_3/data.txt";

        int lines = 0; // no of lines in data.txt

        // gets the number of lines in data.txt and set the values in raw_data
        try {

            File file = new File(datafilepath); // create file
            Scanner filereader = new Scanner(file); // create filereader

            // get line count from file
            while (filereader.hasNextLine()) {
                filereader.nextLine();
                lines++;
            }
            filereader.close();

            raw_data = new String[lines]; // create the raw_data array with the lines

            // put the raw data into the array
            filereader = new Scanner(file);
            for (int i = 0; i < lines; i++) {
                raw_data[i] = filereader.nextLine();
            }
            filereader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            api.getLogger().error(this, e);
        }

        data = new String[lines][]; // creating the array with the same lines
        parseDataL1(raw_data); // passing in the raw_data for level 1 parsing

        modules = new BorderPane[lines];
        entertainments = new Entertainment[lines];
        api.getLogger().log(this, "Data finished reading");
    }

    // level 1 data parsing
    private void parseDataL1(String[] raw_data) {
        for (int i = 0; i < raw_data.length; i++) {
            data[i] = raw_data[i].split("<##>");
        }
    }

    // level 2 data parsing
    private String[] parseDataL2(String l2_unparsed_data) {
        return l2_unparsed_data.split("<<>>");
    }
}
