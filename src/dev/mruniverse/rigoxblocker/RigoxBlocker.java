package dev.mruniverse.rigoxblocker;

import dev.mruniverse.rigoxblocker.enums.RigoxFile;
import dev.mruniverse.rigoxblocker.listeners.CommandBlocker;
import dev.mruniverse.rigoxblocker.listeners.RigoxBlockCMD;
import dev.mruniverse.rigoxblocker.files.RigoxFiles;
import dev.mruniverse.rigoxblocker.utils.Metrics;
import dev.mruniverse.rigoxblocker.utils.RigoxUpdater;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class RigoxBlocker extends JavaPlugin {
    public static RigoxBlocker instance;
    public static RigoxBlocker getInstance() { return instance; }
    public RigoxFiles rigoxFiles;
    private boolean placeholdersAPI = false;
    @Override
    public void onLoad() {
        long msToLoad = System.currentTimeMillis();
        instance = this;
        rigoxFiles = new RigoxFiles();
        SendConsoleMessage("The plugin is loading...");
        rigoxFiles.initConfig();
        rigoxFiles.initWriter();
        Metrics metrics = new Metrics(this, 9516);
        rigoxFiles.save();
        if(getFiles().getControl(RigoxFile.SETTINGS).getBoolean("settings.check-update")) {
            RigoxUpdater updateChecker = new RigoxUpdater(72359,this);
            String UpdateResult = updateChecker.getUpdateResult();
            if (UpdateResult.equalsIgnoreCase("UPDATED")) {
                SendConsoleMessage("&aYou're using latest version of RigoxBlocker, You're Awesome!");
            } else if (UpdateResult.equalsIgnoreCase("NEW_VERSION")) {
                SendConsoleMessage("&aA new update is available: &bhttps://www.spigotmc.org/resources/72359");
            } else if (UpdateResult.equalsIgnoreCase("PRE_RELEASE")) {
                SendConsoleMessage("&aYou are Running a Pre-Release version, please report bugs ;)");
            } else if (UpdateResult.equalsIgnoreCase("RED_PROBLEM")) {
                SendConsoleMessage("&aRigoxBlocker can't connect to WiFi to check plugin version.");
            } else if (UpdateResult.equalsIgnoreCase("ALPHA_VERSION")) {
                SendConsoleMessage("&bYou are Running a &aAlpha version&b, it is normal to find several errors, please report these errors so that they can be solved.");
            } else if (UpdateResult.equalsIgnoreCase("PRE_ALPHA_VERSION")) {
                SendConsoleMessage("&cYou are Running a &aPre Alpha version&c, it is normal to find several errors, please report these errors so that they can be solved. &eWARNING: &cI (MrUniverse) recommend a Stable version, PreAlpha aren't stable versions!");
            } else if (UpdateResult.equalsIgnoreCase("RELEASE")) {
                SendConsoleMessage("&aYou are Running a Release version, maybe you're the first running this version, is only for my discord users.");
            }
        }
        SendConsoleMessage("Plugin config loaded in &b" + (System.currentTimeMillis() - msToLoad) + "&fms.");
    }
    @Override
    public void onEnable() {
        long msToLoad = System.currentTimeMillis();
        SendConsoleMessage("Loading plugin commands and Events..");
        getCommand("rigoxblocker").setExecutor(new RigoxBlockCMD("rigoxblocker"));
        getCommand("rblocker").setExecutor(new RigoxBlockCMD("rblocker"));
        getCommand("rb").setExecutor(new RigoxBlockCMD("rb"));
        getServer().getPluginManager().registerEvents(new CommandBlocker(),this);
        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            placeholdersAPI = true;
        }
        SendConsoleMessage("The plugin was loaded in &b" + (System.currentTimeMillis() - msToLoad) + "&fms.");
    }
    public RigoxFiles getFiles() {
        if(rigoxFiles == null) rigoxFiles = new RigoxFiles();
        return rigoxFiles;
    }
    public boolean hasPAPI() {
        return placeholdersAPI;
    }
    public static void SendConsoleMessage(String message) {
        instance.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&b[Rigox Blocker] &f" + message));
    }
    public static void redIssue() {
        instance.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&b[Rigox Blocker] &fRigox Blocker can't connect to Wi-Fi"));
    }


}
