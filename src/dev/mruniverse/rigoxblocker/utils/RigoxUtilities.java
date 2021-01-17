package dev.mruniverse.rigoxblocker.utils;

import dev.mruniverse.rigoxblocker.RigoxBlocker;
import dev.mruniverse.rigoxblocker.enums.RigoxFile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RigoxUtilities {
    public static String manageString(Player player, String message) {
        if(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.SETTINGS).getBoolean("settings.PlaceholderAPI-Support")) {
            if(RigoxBlocker.getInstance().hasPAPI()) {
                return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, message));
            } else {
                return ChatColor.translateAlternateColorCodes('&', message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static List<String> manageList(Player player, List<String> loreToRecolor) {
        List<String> recolored = new ArrayList<>();
        if(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.SETTINGS).getBoolean("settings.PlaceholderAPI-Support")) {
            for (String color : loreToRecolor) {
                if(RigoxBlocker.getInstance().hasPAPI()) {
                    recolored.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, color)));
                } else {
                    recolored.add(ChatColor.translateAlternateColorCodes('&', color));
                }
            }
            return recolored;
        }
        for (String color : loreToRecolor) {
            recolored.add(ChatColor.translateAlternateColorCodes('&', color));
        }
        return recolored;
    }

    public static void sendColored(Player player,String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
    }
    public static void sendColored(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
    }
}
