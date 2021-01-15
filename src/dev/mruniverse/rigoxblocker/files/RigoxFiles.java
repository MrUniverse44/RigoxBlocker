package dev.mruniverse.rigoxblocker.files;

import dev.mruniverse.rigoxblocker.RigoxBlocker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class RigoxFiles {
    public static File config, msg,cmds;
    public static FileConfiguration rConfig, rMsg,rCmds;

    public static void initConfig() {
        boolean created;
        if(!RigoxBlocker.getInstance().getDataFolder().exists()) {
            created = RigoxBlocker.getInstance().getDataFolder().mkdir();
            if(created) {
                RigoxBlocker.SendConsoleMessage("&aPlugin folder created.");
            }
        }
        config = new File(RigoxBlocker.getInstance().getDataFolder(), "settings.yml");
        msg = new File(RigoxBlocker.getInstance().getDataFolder(), "messages.yml");
        cmds = new File(RigoxBlocker.getInstance().getDataFolder(),"commands.yml");
        try {
            if (!config.exists()) {
                created = config.createNewFile();
                if(created) {
                    RigoxBlocker.SendConsoleMessage("&fFile &bconfig.yml &fcreated.");
                }
            }
            if (!msg.exists()) {
                created = msg.createNewFile();
                if(created) {
                    RigoxBlocker.SendConsoleMessage("&fFile &bmessages.yml &fcreated.");
                }
            }
            if (!cmds.exists()) {
                created = cmds.createNewFile();
                if(created) {
                    RigoxBlocker.SendConsoleMessage("&fFile &bitems.yml &fcreated.");
                }
            }
            RigoxBlocker.SendConsoleMessage("All files has been &bchecked&f.");
        } catch(IOException e) {
            RigoxBlocker.SendConsoleMessage("Can't create &bsettings.yml &fand &bmessages.yml &fand &bcommands.yml &fthe plugin don't have permissions to create files.");
        }
    }



    private static void addConfig(boolean isConfig,String path,Object object) {
        if(isConfig) {
            if(!getConfig().contains(path)) {
                getConfig().set(path,object);
            }
        } else {
            if(!getLang().contains(path)) {
                getLang().set(path, object);
            }
        }
    }

    private static void addExampleCommand(String path,Object object) {
        if(!getCommands().contains(path)) {
            getCommands().set(path, object);
        }
    }

    public static void initWriter() {
        List<String> pluginList = new ArrayList<>();
        pluginList.add("[msg][prefix]&8¿What are you trying to do?");
        pluginList.add("[msg][prefix]&7You can't use &c<command>&7!");
        pluginList.add("[sound]VILLAGER_NO");
        addConfig(true,"settings.check-update", true);
        addConfig(true,"settings.PlaceholderAPI-Support", true);
        addConfig(true,"modules.commandBlocker.toggle",true);
        addConfig(true,"modules.commandBlocker.onError",pluginList);
        addConfig(true,"modules.Block-TabCompleter.toggle", false);
        addConfig(true,"modules.Block-TabCompleter.fakeList.toggle",false);
        pluginList = new ArrayList<>();
        pluginList.add("NetworkAPI");
        pluginList.add("PixelMOTD");
        addConfig(true,"modules.Block-TabCompleter.fakeList.list",pluginList);
        addConfig(false,"messages.prefix", "&b[Rigox Blocker] &7");
        addConfig(false,"messages.reload","&aThe plugin was reloaded in <ms>ms");
        addConfig(false,"messages.no-perms","&7You don't have permissions for this command.");
        pluginList = new ArrayList<>();
        pluginList.add("bukkit:plugins");
        pluginList.add("bukkit:pl");
        pluginList.add("bukkit:?");
        pluginList.add("bukkit:help");
        pluginList.add("minecraft:help");
        pluginList.add("bukkit:ver");
        pluginList.add("bukkit:version");
        pluginList.add("bukkit:about");
        pluginList.add("bukkit:msg");
        pluginList.add("bukkit:tell");
        pluginList.add("bukkit:kill");
        pluginList.add("minecraft:kill");
        pluginList.add("bukkit:me");
        pluginList.add("bukkit:icanhasbukkit");
        pluginList.add("minecraft:me");
        pluginList.add("minecraft:tell");
        pluginList.add("minecraft:msg");
        pluginList.add("icanhasbukkit");
        pluginList.add("plugins");
        pluginList.add("pl");
        pluginList.add("version");
        pluginList.add("ver");
        pluginList.add("bukkit:*");
        pluginList.add("plugin");
        pluginList.add("?");
        pluginList.add("info");
        pluginList.add("minecraft:plugin");
        pluginList.add("minecraft:ban");
        pluginList.add("minecraft:execute");
        pluginList.add("whois");
        pluginList.add("ip");
        pluginList.add("bukkit:execute");
        pluginList.add("bukkit:ban");
        pluginList.add("bukkit:plugin");
        pluginList.add("bukkit:restart");
        pluginList.add("minecraft:restart");
        pluginList.add("restart");
        pluginList.add("op");
        pluginList.add("deop");
        pluginList.add("minecraft:op");
        pluginList.add("minecraft:deop");
        pluginList.add("bukkit:op");
        pluginList.add("bukkit:deop");
        pluginList.add("server");
        pluginList.add("/calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
        pluginList.add("calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}");
        pluginList.add("calc");
        pluginList.add("/calc");
        pluginList.add("calculate");
        pluginList.add("/calculate");
        pluginList.add("/solve");
        pluginList.add("/eval");
        pluginList.add("solve");
        pluginList.add("eval");
        pluginList.add("worldedit:calc");
        pluginList.add("worldedit:/calc");
        pluginList.add("worldedit:calculate");
        pluginList.add("worldedit:/calculate");
        pluginList.add("worldedit:/solve");
        pluginList.add("worldedit:/eval");
        pluginList.add("worldedit:solve");
        pluginList.add("worldedit:eval");
        pluginList.add("executioner");
        addExampleCommand("notify-console","&7(Notify of &c%player%&7) tried to execute &c/%command%&7!");
        addExampleCommand("blocked-cmds",pluginList);
        save();
        reloadFiles();
    }

    public static void reloadFiles() {
        initConfig();
        rConfig = YamlConfiguration.loadConfiguration(config);
        rMsg = YamlConfiguration.loadConfiguration(msg);
        rCmds = YamlConfiguration.loadConfiguration(cmds);
        InputStream defCConfig = RigoxBlocker.getInstance().getResource("settings.yml");
        InputStream defCLang = RigoxBlocker.getInstance().getResource("messages.yml");
        InputStream defCCmds = RigoxBlocker.getInstance().getResource("commands.yml");
        if(defCConfig != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defCConfig));
            rConfig.setDefaults(defConfig);
        }
        if(defCCmds != null) {
            YamlConfiguration defCmds = YamlConfiguration.loadConfiguration(new InputStreamReader(defCCmds));
            rCmds.setDefaults(defCmds);
        }
        if(defCLang != null) {
            YamlConfiguration defLang = YamlConfiguration.loadConfiguration(new InputStreamReader(defCLang));
            rMsg.setDefaults(defLang);
        }
    }

    public static FileConfiguration getConfig() {
        if(rConfig == null) { reloadFiles(); }
        return rConfig;
    }

    public static FileConfiguration getLang() {
        if(rMsg == null) { reloadFiles(); }
        return rMsg;
    }

    public static FileConfiguration getCommands() {
        if(rCmds == null) { reloadFiles(); }
        return rCmds;
    }

    public static void save() {
        if (config == null || rConfig == null || rMsg == null || msg == null || rCmds == null || cmds == null) {
            return;
        }
        try {
            getConfig().save(config);
            getLang().save(msg);
            getCommands().save(cmds);
        } catch (IOException ex) {
            RigoxBlocker.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " +
                    config + ", " + msg, ex);
        }
    }

}
