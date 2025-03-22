package main.java.backend.entertainment;

import java.time.LocalDate;

public class Episode extends Entertainment {

    public Episode(
            String type, // Episode
            String franchise, String title, // Miraculous: Tales of Ladybug and Cat Noir
            int seasonNum, // Season -> 6
            int episodeNum, String episodeTitle, // 2, The Illustrhater
            String duration, // min -> 22
            LocalDate date, // Jan 24, 2025
            String[] statuses, // episode -> 1
            String[] tags // miraculous deputy, alya
    ) {
        super(type, franchise, episodeTitle, statuses, tags, date);
    }

}
