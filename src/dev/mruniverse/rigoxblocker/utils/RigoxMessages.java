package dev.mruniverse.rigoxblocker.utils;

import dev.mruniverse.rigoxblocker.enums.Actions;
import dev.mruniverse.rigoxblocker.enums.RigoxFile;
import dev.mruniverse.rigoxblocker.RigoxBlocker;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RigoxMessages {
    public static void sendBlock(Player player, String command) {
        for(String message : RigoxUtilities.manageList(player, RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.SETTINGS).getStringList("modules.commandBlocker.onError"))) {
            Actions action = Actions.NONE;
            if(message.contains("[msg]")) {
                action = Actions.MESSAGE;
            }
            if(message.contains("[ConsoleCMD]")) {
                action = Actions.CONSOLE_COMMAND;
            }
            if(message.contains("[playerCMD]")) {
                action = Actions.COMMAND;
            }
            if(message.contains("[sound]")) {
                action = Actions.SOUND;
            }
            if(message.contains("[actionbar]")) {
                action = Actions.ACTIONBAR;
            }
            execute(player,action,message,command);
        }
    }
    public static void sendConditionBlock(String condition,Player player, String command,String value) {
        if(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getBoolean("conditions." + condition + ".error-result.toggle")) {
            for (String message : RigoxUtilities.manageList(player, RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getStringList("conditions" + condition + ".error-result.values"))) {
                Actions action = Actions.NONE;
                if (message.contains("[msg]")) {
                    action = Actions.MESSAGE;
                }
                if (message.contains("[ConsoleCMD]")) {
                    action = Actions.CONSOLE_COMMAND;
                }
                if (message.contains("[playerCMD]")) {
                    action = Actions.COMMAND;
                }
                if (message.contains("[sound]")) {
                    action = Actions.SOUND;
                }
                if (message.contains("[actionbar]")) {
                    action = Actions.ACTIONBAR;
                }
                execute(player, action, message.replace("%value%",value), command);
            }
        }
    }
    @SuppressWarnings("ConstantConditions")
    private static void execute(Player player, Actions action, String command, String usedCommand) {
        if(command.contains("<command>")) { command = command.replace("<command>", usedCommand); }
        if(command.contains("[prefix]")) { command = command.replace("[prefix]",RigoxUtilities.manageString(player,RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.MESSAGES).getString("messages.prefix"))); }
        if(action.equals(Actions.COMMAND)) {
            String cmd = command.replace("[playerCMD] ","").replace("[playerCMD]","");
            player.performCommand(cmd);
            return;
        }
        if (action.equals(Actions.ACTIONBAR)) {
            RigoxBlocker.SendConsoleMessage("The [actionbar] variable isn't working now, sorry. :(");
            return;
        }
        if (action.equals(Actions.MESSAGE) || action.equals(Actions.NONE)) {
            String cmd = command.replace("[msg] ","").replace("[msg]","");
            player.sendMessage(cmd);
            return;
        }
        if (action.equals(Actions.SOUND)) {
            String cmd = command.replace("[sound] ","").replace("[sound]","");
            if(Sound.valueOf(cmd) != null) {
                player.getWorld().playSound(player.getLocation(), Sound.valueOf(cmd), 1f, 1f);
                return;
            }
            RigoxBlocker.SendConsoleMessage("&cSound " + cmd + "&c doesn't exist!");
            return;
        }
        if (action.equals(Actions.CONSOLE_COMMAND)) {
            String cmd = command.replace("[ConsoleCMD] ","").replace("[ConsoleCMD]","");
            ConsoleCommandSender consoleSender = RigoxBlocker.getInstance().getServer().getConsoleSender();
            Bukkit.dispatchCommand(consoleSender,cmd);
        }

    }

}
