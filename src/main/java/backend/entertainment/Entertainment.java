package main.java.backend.entertainment;

public class Entertainment {
    public static final String MOVIE = "Movie";
    public static final String ANIME = "Anime";

    private String type;
    private String franchise;
    private String title;
    private String[] statuses;
    private String[] tags;

    public Entertainment(String type, String franchise, String title, String[] statuses, String[] tags) {
        this.type = type;
        this.franchise = franchise;
        this.title = title;
        this.statuses = statuses;
        this.tags = tags;
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

}
