package dev.mruniverse.rigoxblocker.listeners;

import dev.mruniverse.rigoxblocker.files.RigoxFiles;
import dev.mruniverse.rigoxblocker.RigoxBlocker;
import dev.mruniverse.rigoxblocker.utils.RigoxUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RigoxBlockCMD implements CommandExecutor {
    private String cmd;
    public RigoxBlockCMD(String command) {
        this.cmd = command;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                if (player.hasPermission("rigoxblocker.cmd.main")) {
                    RigoxUtilities.sendColored(player, "&7------------- &aRigoxBlocker &7-------------");
                    RigoxUtilities.sendColored(player, "&e/" + this.cmd + " &8- &bMain Command");
                    RigoxUtilities.sendColored(player, "&e/" + this.cmd + " reload &8- &bReload plugin configuration");
                    RigoxUtilities.sendColored(player, "&eRigoxBlocker v" + RigoxBlocker.getInstance().getDescription().getVersion() + ".");
                    RigoxUtilities.sendColored(player, "&7------------- &aRigoxBlocker &7-------------");
                } else {
                    RigoxUtilities.sendColored(player, RigoxFiles.getLang().getString("messages.no-perms"));
                }
            } else {
                if(args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("rigoxblocker.cmd.reload")) {
                        long timeFinished = System.currentTimeMillis();
                        RigoxFiles.reloadFiles();
                        RigoxFiles.initWriter();
                        RigoxUtilities.sendColored(player,RigoxFiles.getLang().getString("messages.reload").replace("<ms>", "" + (System.currentTimeMillis() - timeFinished)));
                    } else {
                        RigoxUtilities.sendColored(player, RigoxFiles.getLang().getString("messages.no-perms"));
                    }
                }
            }
        } else {
            if(args.length == 0) {
                RigoxUtilities.sendColored(sender, "&7------------- &aRigoxBlocker &7-------------");
                RigoxUtilities.sendColored(sender, "&e/" + this.cmd + " &8- &bMain Command");
                RigoxUtilities.sendColored(sender, "&e/" + this.cmd + " reload &8- &bReload plugin configuration");
                RigoxUtilities.sendColored(sender, "&eRigoxBlocker v" + RigoxBlocker.getInstance().getDescription().getVersion() + ".");
                RigoxUtilities.sendColored(sender, "&7------------- &aRigoxBlocker &7-------------");

            } else {
                if(args[0].equalsIgnoreCase("reload")) {
                    long timeFinished = System.currentTimeMillis();
                    RigoxFiles.reloadFiles();
                    RigoxFiles.initWriter();
                    RigoxUtilities.sendColored(sender,RigoxFiles.getLang().getString("messages.reload").replace("<ms>", "" + (System.currentTimeMillis() - timeFinished)));

                }
            }
        }
        return true;
    }
}
