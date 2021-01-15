package dev.mruniverse.rigoxblocker.listeners;

import dev.mruniverse.rigoxblocker.enums.RigoxFile;
import dev.mruniverse.rigoxblocker.files.RigoxFiles;
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
        if(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.SETTINGS).getBoolean("modules.commandBlocker.toggle")) {
            List<String> lowerCaseCmds = new ArrayList<>();
            for(String cmds : RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getStringList("blocked-cmds")) {
                lowerCaseCmds.add("/" + cmds.toLowerCase());
            }
            if(lowerCaseCmds.contains(event.getMessage().toLowerCase())) {
                RigoxMessages.sendBlock(event.getPlayer(),event.getMessage());
                RigoxBlocker.SendConsoleMessage(RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.COMMANDS).getString("notify-console").replace("%player%",event.getPlayer().getName()).replace("%command%",event.getMessage().toLowerCase()));
                event.setCancelled(true);
            }
        }
    }
}
