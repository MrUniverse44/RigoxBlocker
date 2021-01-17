package dev.mruniverse.rigoxblocker.listeners;

import dev.mruniverse.rigoxblocker.enums.ConditionType;
import dev.mruniverse.rigoxblocker.enums.RigoxFile;
import dev.mruniverse.rigoxblocker.RigoxBlocker;
import dev.mruniverse.rigoxblocker.utils.RigoxMessages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandBlocker implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(event.isCancelled()) return;
        if(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.SETTINGS).getBoolean("modules.commandBlocker.toggle")) {
            List<String> lowerCaseCmds = new ArrayList<>();
            for (String cmds : RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getStringList("blocked-cmds")) {
                lowerCaseCmds.add("/" + cmds.toLowerCase());
            }
            for (String condition : RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getConfigurationSection("conditions").getKeys(false)) {
                if (lowerCaseCmds.contains(event.getMessage().toLowerCase())) {
                    RigoxMessages.sendBlock(event.getPlayer(), event.getMessage());
                    RigoxBlocker.SendConsoleMessage(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getString("notify-console").replace("%player%", event.getPlayer().getName()).replace("%command%", event.getMessage().toLowerCase()));
                    event.setCancelled(true);
                }
                if(event.isCancelled()) return;
                if (lowerCaseCmds.contains("[condition:" + condition + "]" + event.getMessage().toLowerCase().replace("/",""))) {
                    String value = getValue(condition);
                    if (getCondition(condition).equals(ConditionType.PASSWORD)) {
                        String passwordSetup = event.getMessage();

                        if (!passwordSetup.contains(value)) {
                            RigoxMessages.sendBlock(event.getPlayer(), event.getMessage());
                            RigoxMessages.sendConditionBlock(condition,event.getPlayer(),event.getMessage().replace(value,""),value);
                            RigoxBlocker.SendConsoleMessage(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getString("notify-console").replace("%player%", event.getPlayer().getName()).replace("%command%", event.getMessage().toLowerCase()));
                            event.setCancelled(true);
                            return;
                        }
                        event.getPlayer().performCommand(event.getMessage().replace(value,""));
                        return;
                    }
                    if(event.isCancelled()) return;
                    if(!event.getPlayer().hasPermission(value)) {
                        RigoxMessages.sendBlock(event.getPlayer(), event.getMessage());
                        RigoxMessages.sendConditionBlock(condition,event.getPlayer(),event.getMessage(),value);
                        RigoxBlocker.SendConsoleMessage(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getString("notify-console").replace("%player%", event.getPlayer().getName()).replace("%command%", event.getMessage().toLowerCase()));
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
    private String getValue(String condition) {
        return RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getString("conditions." + condition + ".value");
    }
    private ConditionType getCondition(String condition) {
        if(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getString("conditions." + condition + ".type").equalsIgnoreCase("PERMISSION")) {
            return ConditionType.PERMISSION;
        }
        return ConditionType.PASSWORD;
    }
}
