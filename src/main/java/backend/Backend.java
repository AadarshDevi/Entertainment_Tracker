package main.java.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import main.java.api.apiloader.API;
import main.java.backend.entertainment.Anime;
import main.java.backend.entertainment.Entertainment;
import main.java.backend.entertainment.Episode;
import main.java.backend.entertainment.Movie;
import main.java.backend.entertainment.Season;

public class Backend {

    // private API api;

    private ArrayList<String> rawData = new ArrayList<>();
    private ArrayList<Entertainment> entertainmentList = new ArrayList<>();
    private ArrayList<Entertainment> seasonList = new ArrayList<>();

    public Backend(API api) {
        // this.api = api;
    }

    public void start() {
        ArrayList<String[]> parsedData = readData();
        setEntertainments(parsedData);
    }

    public ArrayList<String[]> readData() {

        ArrayList<String[]> parsedData = new ArrayList<>();
        try {

            // create filereader that reads from data.txt
            BufferedReader fileReader = new BufferedReader(
                    new FileReader(new File("D:/Projects/Entertainment_Tracker/data/data.txt")));
            // BufferedReader fileReader = new BufferedReader(new FileReader(new
            // File("src/main/res/data_2.txt")));
            String line;

            while ((line = fileReader.readLine()) != null) {

                if (!(line.contains("//") || line.contains("Anime"))) {
                    rawData.add(line); // raw data
                    parsedData.add(line.split("<##>")); // level 1 parsed data
                }
            }
            fileReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parsedData;
    }

    public Entertainment createEntertainment(String[] list) {
        switch (list[0]) {
            case "Movie":
                return new Movie(
                        list[0], // type
                        list[1], // franchise
                        list[2], // title
                        list[3].split("<<>>"), // status
                        list[4].split("<<>>"), // tags
                        Integer.parseInt(list[5]), // duration
                        LocalDate.parse(list[6]), // date
                        list[7].split("<<>>")); // production companies
            case "Anime":
                return new Anime(
                        list[0], // type
                        list[1], // franchise
                        list[2], // title
                        list[3].split("<<>>"), // status
                        list[4].split("<<>>"), // tags
                        Integer.parseInt(list[5]), // season num
                        Integer.parseInt(list[6]), // episode num
                        LocalDate.parse(list[7]), // date
                        Integer.parseInt(list[8])); // duration
            case "Season": // TODO: add season to entertainment and season list
                return new Season("Season", "Miraculous", "Tales of Ladybug and Cat Noir", new String[2], new String[2],
                        6);
            case "Episode": // TODO: add to corresponging season
                return new Episode(null, null, null, 0, 0, null, null, null, list, list);

            default:
                return new Entertainment(
                        list[0], // type
                        list[1], // franchise
                        list[2], // title
                        list[3].split("<<>>"), // status
                        list[4].split("<<>>"), // tags
                        LocalDate.parse(list[7]) // date
                );
        }
    }

    public void setEntertainments(ArrayList<String[]> parsedData) {
        for (String[] parsed : parsedData) {
            entertainmentList.add(createEntertainment(parsed));
        }
    }

    public ArrayList<String> getRawData() {
        return rawData;
    }

    public ArrayList<Entertainment> getEntertainmentList() {
        return entertainmentList;
    }

}
