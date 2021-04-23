package ga.matthewtgm.lib.other;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class StandardVersionChecker {

    private final Gson gson = new Gson();

    private String versJsonString;
    private JsonObject versJsonObject;

    public StandardVersionChecker(String url) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            StringBuilder builder = new StringBuilder();
            reader.lines().forEach(builder::append);
            versJsonString = builder.toString();
            versJsonObject = gson.fromJson(versJsonString, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLatestVersion() {
        try {
            return versJsonObject.get("version").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getDownloadUrl() {
        try {
            return versJsonObject.get("download").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}