package main.java.backend.entertainment;

public class Movie extends Entertainment {

    private int runtime;
    private String date;

    public Movie(String type, String franchise, String title, String[] statuses, String[] tags,
            int runtime, String date) {
        super(type, franchise, title, statuses, tags);

        this.runtime = runtime;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

}
