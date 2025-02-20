package main.java.backend.entertainment;

public class Anime extends Entertainment {

    private int runtime;
    private String date;
    private int season;
    private int episode;

    public Anime(String type, String franchise, String title, String[] statuses, String[] tags,
            int season, int episode, String date, int runtime) {

        super(type, franchise, title, statuses, tags);

        this.season = season;
        this.episode = episode;
        this.runtime = runtime;
        this.date = date;
    }

    public String getDate() {
        return date;
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

    public void setDate(String date) {
        this.date = date;
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
