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
import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpsGet {

    @Getter private final String url;

    @Getter private String response;
    @Getter private int responseCode;
    @Getter private boolean successful;
    @Getter private Exception error;

    public HttpsGet(String url) {
        this.url = url;
    }

    public void send(String agentName, String agentVersion) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(true);
            conn.setRequestProperty("User-Agent", agentName + "/" + agentVersion);
            this.responseCode = conn.getResponseCode();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                this.response = IOUtils.toString(conn.getInputStream(), Charset.defaultCharset());
                this.successful = true;
                this.error = null;
            } else {
                this.successful = false;
                this.error = new Exception("Response code wasn't 200. Response code: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.successful = false;
            this.error = e;
        }
    }

}