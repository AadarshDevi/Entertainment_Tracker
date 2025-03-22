package main.java.backend.entertainment;

public class Season extends Entertainment {

    private int seasonNum;

    public Season(
            String type, // season
            String franchise, // Miraculous
            String title, // Tales of Ladybug and Cat Noir
            String[] statuses, // status of the list
            String[] tags, // miraculous ladybug
            int seasonNum // season 6
    ) {
        super(type, franchise, title, statuses, tags, null);
        this.seasonNum = seasonNum;
    }

}
