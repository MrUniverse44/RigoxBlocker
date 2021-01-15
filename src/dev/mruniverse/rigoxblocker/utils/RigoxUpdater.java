package dev.mruniverse.rigoxblocker.utils;


import dev.mruniverse.rigoxblocker.RigoxBlocker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class RigoxUpdater {
    private static String currentVersion;
    private static String newestVersion;
    public RigoxUpdater(int projectID) {
        currentVersion = RigoxBlocker.getInstance().getDescription().getVersion();
        try {
            URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            newestVersion = sb.toString(); } catch (IOException ignored) {}
    }

    public static String getUpdateResult() {
        String result = "RED_PROBLEM";
        int current,newest;
        String[] currentV,newestV;
        if(currentVersion.contains("-") && newestVersion.contains("-")) {
            currentV = currentVersion.replace(".", "").replace(" ", "").split("-");
            newestV = newestVersion.replace(".", "").replace(" ", "").split("-");
            current = Integer.parseInt(currentV[0]);
            newest = Integer.parseInt(newestV[0]);
            if (current == newest) {
                if (currentV[1].equals(newestV[1])) {
                    result = "UPDATED";
                } else {
                    result = "NEW_VERSION";
                }
            } else if (current < newest) {
                result = "NEW_VERSION";
            } else {
                if (currentV[1].toLowerCase().contains("pre")) {
                    result = "BETA_VERSION";
                    if(currentV[1].toLowerCase().contains("alpha")) {
                        result = "PRE_ALPHA_VERSION";
                    }
                } else if (currentV[1].toLowerCase().contains("alpha")) {
                    result = "ALPHA_VERSION";
                }
            }
        }
        return result;
    }
}