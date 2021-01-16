package dev.mruniverse.rigoxblocker.files;

import dev.mruniverse.rigoxblocker.RigoxBlocker;
import dev.mruniverse.rigoxblocker.enums.RigoxFile;
import dev.mruniverse.rigoxblocker.enums.RigoxSave;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RigoxFiles {
    public static File config, msg,cmds;
    public static FileConfiguration rConfig, rMsg,rCmds;

    public void initConfig() {
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



    private void addConfig(RigoxFile rigoxFile,String path,Object object) {
        if(rigoxFile.equals(RigoxFile.MESSAGES)) {
            if(!getControl(RigoxFile.MESSAGES).contains(path)) {
                getControl(RigoxFile.MESSAGES).set(path,object);
            }
            return;
        }
        if(rigoxFile.equals(RigoxFile.SETTINGS)) {
            if(!getControl(RigoxFile.SETTINGS).contains(path)) {
                getControl(RigoxFile.SETTINGS).set(path,object);
            }
            return;
        }
        if(rigoxFile.equals(RigoxFile.COMMANDS)) {
            if(!getControl(RigoxFile.COMMANDS).contains(path)) {
                getControl(RigoxFile.COMMANDS).set(path,object);
            }
        }
    }

    public void initWriter() {
        List<String> pluginList = new ArrayList<>();
        pluginList.add("[msg][prefix]&8¿What are you trying to do?");
        pluginList.add("[msg][prefix]&7You can't use &c<command>&7!");
        pluginList.add("[sound]VILLAGER_NO");
        if(getControl(RigoxFile.COMMANDS).get("conditions") == null) {
            addConfig(RigoxFile.COMMANDS,"conditions.hasPermission.value","rigoxblocker.permission.blockedCmd");
            addConfig(RigoxFile.COMMANDS,"conditions.hasPermission.type","PERMISSION");
            addConfig(RigoxFile.COMMANDS,"conditions.hasPermission.error-result.toggle",true);
            addConfig(RigoxFile.COMMANDS,"conditions.hasPermission.error-result.values",pluginList);
        }
        addConfig(RigoxFile.SETTINGS,"settings.check-update", true);
        addConfig(RigoxFile.SETTINGS,"settings.PlaceholderAPI-Support", true);
        addConfig(RigoxFile.SETTINGS,"modules.commandBlocker.toggle",true);
        addConfig(RigoxFile.SETTINGS,"modules.commandBlocker.onError",pluginList);
        addConfig(RigoxFile.SETTINGS,"modules.Block-TabCompleter.toggle", false);
        addConfig(RigoxFile.SETTINGS,"modules.Block-TabCompleter.fakeList.toggle",false);
        pluginList = new ArrayList<>();
        pluginList.add("RigoxBlocker");
        pluginList.add("RigoxSkyBlock");
        pluginList.add("RigoxPractice");
        pluginList.add("PixelMOTD");
        addConfig(RigoxFile.SETTINGS,"modules.Block-TabCompleter.fakeList.list",pluginList);
        addConfig(RigoxFile.MESSAGES,"messages.prefix", "&b[Rigox Blocker] &7");
        addConfig(RigoxFile.MESSAGES,"messages.reload","&aThe plugin was reloaded in <ms>ms");
        addConfig(RigoxFile.MESSAGES,"messages.no-perms","&7You don't have permissions for this command.");
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
        addConfig(RigoxFile.COMMANDS,"notify-console","&7(Notify of &c%player%&7) tried to execute &c/%command%&7!");
        addConfig(RigoxFile.COMMANDS,"blocked-cmds",pluginList);
        save();
        reloadFile(RigoxSave.ALL);
    }
    public void reloadFile(RigoxSave rigoxSave) {
        initConfig();
        if(rigoxSave.equals(RigoxSave.MESSAGES) || rigoxSave.equals(RigoxSave.ALL)) {
            rMsg = YamlConfiguration.loadConfiguration(msg);
            InputStream defCLang = RigoxBlocker.getInstance().getResource("messages.yml");
            if(defCLang != null) {
                YamlConfiguration defLang = YamlConfiguration.loadConfiguration(new InputStreamReader(defCLang));
                rMsg.setDefaults(defLang);
            }
        }
        if(rigoxSave.equals(RigoxSave.SETTINGS) || rigoxSave.equals(RigoxSave.ALL)) {
            rConfig = YamlConfiguration.loadConfiguration(config);
            InputStream defCConfig = RigoxBlocker.getInstance().getResource("settings.yml");
            if(defCConfig != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defCConfig));
                rConfig.setDefaults(defConfig);
            }
        }
        if(rigoxSave.equals(RigoxSave.COMMANDS) || rigoxSave.equals(RigoxSave.ALL)) {
            rCmds = YamlConfiguration.loadConfiguration(cmds);
            InputStream defCCmds = RigoxBlocker.getInstance().getResource("commands.yml");
            if(defCCmds != null) {
                YamlConfiguration defCmds = YamlConfiguration.loadConfiguration(new InputStreamReader(defCCmds));
                rCmds.setDefaults(defCmds);
            }
        }
    }
    public FileConfiguration getControl(RigoxFile rigoxFile) {
        if(rigoxFile.equals(RigoxFile.MESSAGES)) {
            if(rMsg == null) { reloadFile(RigoxSave.MESSAGES); }
            return rMsg;
        }
        if(rigoxFile.equals(RigoxFile.COMMANDS)) {
            if(rCmds == null) { reloadFile(RigoxSave.COMMANDS); }
            return rCmds;
        }
        if(rConfig == null) { reloadFile(RigoxSave.SETTINGS); }
        return rConfig;
    }
    public void save() {
        if (config == null || rConfig == null || rMsg == null || msg == null || rCmds == null || cmds == null) {
            return;
        }
        try {
            getControl(RigoxFile.COMMANDS).save(cmds);
            getControl(RigoxFile.MESSAGES).save(msg);
            getControl(RigoxFile.SETTINGS).save(config);
        }catch(Throwable throwable) {
            RigoxBlocker.SendConsoleMessage("&aUps");
        }
    }

}
