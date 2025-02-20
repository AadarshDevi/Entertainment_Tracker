package main.java.backend.entertainment;

import java.time.LocalDate;

public class Movie extends Entertainment {

    private int duration;
    private String[] animation_companies;

    public Movie(String type, String franchise, String title, String[] statuses, String[] tags,
            int duration, LocalDate date, String[] animation_companies) {
        super(type, franchise, title, statuses, tags, date);

        this.duration = duration;
        this.animation_companies = animation_companies;
    }

    public int getDuration() {
        return duration;
    }

    public String[] getAnimationCompanies() {
        return animation_companies;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
