package dev.mruniverse.rigoxblocker.utils;


import dev.mruniverse.rigoxblocker.RigoxBlocker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class RigoxUpdater {
    private final String currentVersion;
    private RigoxBlocker plugin;
    private String newestVersion;
    public RigoxUpdater(int projectID,RigoxBlocker main) {
        currentVersion = RigoxBlocker.getInstance().getDescription().getVersion();
        plugin = main;
        try {
            URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            newestVersion = sb.toString();
        } catch (IOException ignored) {
            RigoxBlocker.redIssue();
        }
    }
    public String getUpdateResult() {
        int using,latest;
        String update;
        String[] installed, spigot;
        //Version Verificator

        if(currentVersion == null || newestVersion == null) {
            return "RED_PROBLEM";
        }

        //Version Setup

        //* First Setup

        update= currentVersion.replace(".","");
        //if(currentVersion.contains(".")) update= currentVersion.replace(".","");
        installed= update.split("-");
        update= newestVersion.replace(".","");
        //if(newestVersion.contains(".")) update= newestVersion.replace(".","");
        spigot= update.split("-");

        //* Second Setup
        using= Integer.parseInt(installed[0].replace(" ",""));
        latest= Integer.parseInt(spigot[0].replace(" ",""));

        //Result Setup
        if(using == latest) {
            if(installed[1].equalsIgnoreCase(spigot[1])) {
                return "UPDATED";
            }
            return "NEW_VERSION";
        }
        if(using < latest) {
            return "NEW_VERSION";
        }
        if(installed[1].toLowerCase().contains("pre")) {
            if(installed[1].toLowerCase().contains("alpha")) {
                return "PRE_ALPHA_VERSION";
            }
            return "PRE_RELEASE";
        }
        if(installed[1].toLowerCase().contains("alpha")) {
            return "ALPHA_VERSION";
        }
        return "RELEASE";
    }
}