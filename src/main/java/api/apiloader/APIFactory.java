package main.java.api.apiloader;

public class APIFactory {
    private static API api;

    public static API getApi() {
        if (api == null)
            api = new API();
        return api;
    }
}
