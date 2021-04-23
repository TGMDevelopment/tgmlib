package ga.matthewtgm.lib.util;

import ga.matthewtgm.lib.TGMLib;
import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHelper {

    /**
     * @param stringUrl The url to fetch from.
     * @return The json returned as a string.
     * @author MatthewTGM
     */
    public static String getJsonOnline(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setRequestProperty("User-Agent", TGMLib.NAME + "/" + TGMLib.VERSION);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                return IOUtils.toString(conn.getInputStream());
            return "{\"failed\": \"true\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"failed\": \"true\"}";
        }
    }

}