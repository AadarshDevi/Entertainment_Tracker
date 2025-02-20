package main.java.backend.entertainment;

import java.time.LocalDate;

public class Anime extends Entertainment {

    private int runtime;
    private int season;
    private int episode;

    public Anime(String type, String franchise, String title, String[] statuses, String[] tags,
            int season, int episode, LocalDate date, int runtime) {

        super(type, franchise, title, statuses, tags, date);

        this.season = season;
        this.episode = episode;
        this.runtime = runtime;

    }

    public int getRuntime() {
        return runtime;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

}
