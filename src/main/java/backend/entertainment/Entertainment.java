package main.java.backend.entertainment;

import java.time.LocalDate;

public class Entertainment {
    public static final String MOVIE = "Movie";
    public static final String ANIME = "Anime";

    private String type;
    private String franchise;
    private String title;
    private String[] statuses;
    private String[] tags;
    private LocalDate date;

    private int primaryStatus;
    private int secondaryStatus;

    public Entertainment(String type, String franchise, String title, String[] statuses, String[] tags,
            LocalDate date) {
        this.type = type;
        this.franchise = franchise;
        this.title = title;
        this.statuses = statuses;
        this.tags = tags;
        this.date = date;

        setStatus();
    }

    private void setStatus() {
        primaryStatus = Integer.parseInt(getStatuses()[0]);

        // if statement
        secondaryStatus = (getStatuses().length > 1) ? Integer.parseInt(getStatuses()[1]) : 0;
    }

    public String getFranchise() {
        return franchise;
    }

    public String[] getStatuses() {
        return statuses;
    }

    public String[] getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getPrimaryStatus() {
        return primaryStatus;
    }

    public int getSecondaryStatus() {
        return secondaryStatus;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public void setStatuses(String[] statuses) {
        this.statuses = statuses;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPrimaryStatus(int primaryStatus) {
        this.primaryStatus = primaryStatus;
    }

    public void setSecondaryStatus(int secondaryStatus) {
        this.secondaryStatus = secondaryStatus;
    }

}
