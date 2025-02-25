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

    // @Override

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

    // booleans

    public boolean equals(Entertainment inEntertainment) {

        return sameTags(inEntertainment.getTags()) &&
                inEntertainment.getType().equals(type) &&
                inEntertainment.getFranchise().equals(franchise) &&
                inEntertainment.getTitle().equals(title) &&
                inEntertainment.getPrimaryStatus() == primaryStatus &&
                inEntertainment.getSecondaryStatus() == secondaryStatus &&
                inEntertainment.getDate().equals(date);

    }

    private boolean sameTags(String[] inTags) {

        boolean isTagsSame = true;
        if (this.tags.length != inTags.length) {
            return false;
        }

        for (int i = 0; i < tags.length; i++) {
            if (!this.tags[i].equals(inTags[i])) {
                isTagsSame = false;
            }
        }
        return isTagsSame;
    }

    public boolean isDateEqual(Entertainment entertainment) {
        return this.date.isEqual(entertainment.getDate());
    }

    public boolean isFranchiseEqual(Entertainment entertainment) {
        return this.franchise.equals(entertainment.getFranchise());
    }

    public boolean isPrimaryStatusEqual(Entertainment entertainment) {
        return this.primaryStatus == entertainment.getPrimaryStatus();
    }

    public boolean isTitleEqual(Entertainment entertainment) {
        return this.title.equals(entertainment.getTitle());
    }

    public boolean isTypeEqual(Entertainment entertainment) {
        return this.type.equals(entertainment.getType());
    }

}
