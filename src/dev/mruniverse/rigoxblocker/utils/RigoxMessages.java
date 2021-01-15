package dev.mruniverse.rigoxblocker.utils;

import dev.mruniverse.rigoxblocker.files.RigoxFiles;
import dev.mruniverse.rigoxblocker.RigoxBlocker;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RigoxMessages {
    public static void sendBlock(Player player, String command) {
        for(String message : RigoxUtilities.manageList(player, RigoxFiles.getConfig().getStringList("modules.commandBlocker.onError"))) {
            String cmd = "MSG";
            if(message.contains("[msg]")) {
                cmd = "MSG";
            }
            if(message.contains("[ConsoleCMD]")) {
                cmd = "CON";
            }
            if(message.contains("[playerCMD]")) {
                cmd = "CMD";
            }
            if(message.contains("[sound]")) {
                cmd = "SND";
            }
            if(message.contains("[actionbar]")) {
                cmd = "ABA";
            }
            execute(player,cmd,message,command);
        }
    }

    private static void execute(Player player,String type,String command,String usedCommand) {
        if(command.contains("<command>")) { command = command.replace("<command>", usedCommand); }
        if(command.contains("[prefix]")) { command = command.replace("[prefix]",RigoxUtilities.manageString(player,RigoxFiles.getLang().getString("messages.prefix"))); }
        if(type.equalsIgnoreCase("CMD")) {
            String cmd = command.replace("[playerCMD] ","").replace("[playerCMD]","");
            player.performCommand(cmd);
        } else if (type.equalsIgnoreCase("ABA")) {
            RigoxBlocker.SendConsoleMessage("The [actionbar] variable isn't working now, sorry. :(");
        } else if (type.equalsIgnoreCase("MSG")) {
            String cmd = command.replace("[msg] ","").replace("[msg]","");
            player.sendMessage(cmd);
        } else if (type.equalsIgnoreCase("SND")) {
            String cmd = command.replace("[sound] ","").replace("[sound]","");
            if(Sound.valueOf(cmd) != null) {
                player.getWorld().playSound(player.getLocation(), Sound.valueOf(cmd), 1f, 1f);
            } else {
                RigoxBlocker.SendConsoleMessage("&cSound " + cmd + "&c doesn't exist!");
            }
        } else if (type.equalsIgnoreCase("CON")) {
            String cmd = command.replace("[ConsoleCMD] ","").replace("[ConsoleCMD]","");
            ConsoleCommandSender consoleSender = RigoxBlocker.getInstance().getServer().getConsoleSender();
            Bukkit.dispatchCommand(consoleSender,cmd);
        }
    }

}
