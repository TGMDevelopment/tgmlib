/*
 * Copyright (C) MatthewTGM
 * This file is part of TGMLib <https://github.com/TGMDevelopment/TGMLib>.
 *
 * TGMLib is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TGMLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TGMLib. If not, see <http://www.gnu.org/licenses/>.
 */

package ga.matthewtgm.lib.util.https;

import lombok.Getter;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public class HttpsPost {

    @Getter private String url;
    @Getter private Map<String, String> parameters;

    @Getter private String response;
    @Getter private int responseCode;
    @Getter private boolean successful;

    public HttpsPost(String url) {
        this.url = url;
    }

    public HttpsPost(String url, Map<String, String> parameters) {
        this.url = url;
        this.parameters = parameters;
    }

    public void send(String agentName, String agentVersion, String input) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            if (parameters != null) {
                StringJoiner sj = new StringJoiner("&");
                for(Map.Entry<String, String> entry : parameters.entrySet()) {
                    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
            }
            byte[] out = input.getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            conn.setFixedLengthStreamingMode(length);
            conn.setRequestProperty("User-Agent", agentName + "/" + agentVersion);
            conn.setConnectTimeout(5000);
            conn.connect();
            try(OutputStream os = conn.getOutputStream()) {
                os.write(out);
            }
            if(conn.getResponseMessage() != null && !conn.getResponseMessage().isEmpty())
                this.response = conn.getResponseMessage();
            if(conn.getResponseCode() != 0)
                this.responseCode = conn.getResponseCode();
            successful = true;
        } catch (Exception e) {
            e.printStackTrace();
            successful = false;
        }
    }

}