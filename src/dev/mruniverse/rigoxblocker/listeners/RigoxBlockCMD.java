package dev.mruniverse.rigoxblocker.listeners;

import dev.mruniverse.rigoxblocker.enums.RigoxFile;
import dev.mruniverse.rigoxblocker.enums.RigoxSave;
import dev.mruniverse.rigoxblocker.RigoxBlocker;
import dev.mruniverse.rigoxblocker.utils.RigoxUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RigoxBlockCMD implements CommandExecutor {
    private final String cmd;
    public RigoxBlockCMD(String command) {
        this.cmd = command;
    }
    private boolean hasPermission(CommandSender sender, String permission) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            if (!player.hasPermission(permission)) {
                RigoxUtilities.sendColored(player, RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.MESSAGES).getString("messages.no-perms"));
            }
            return player.hasPermission(permission);
        }
        return true;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            if (hasPermission(sender,"rigoxblocker.cmd.main")) {
                RigoxUtilities.sendColored(sender, "&7------------- &aRigoxBlocker &7-------------");
                RigoxUtilities.sendColored(sender, "&e/" + this.cmd + " &8- &bMain Command");
                RigoxUtilities.sendColored(sender, "&e/" + this.cmd + " reload &8- &bReload plugin configuration");
                RigoxUtilities.sendColored(sender, "&eRigoxBlocker v" + RigoxBlocker.getInstance().getDescription().getVersion() + ".");
                RigoxUtilities.sendColored(sender, "&7------------- &aRigoxBlocker &7-------------");
            } else {
                RigoxUtilities.sendColored(sender, RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.MESSAGES).getString("messages.no-perms"));
            }
        } else {
            if(args[0].equalsIgnoreCase("reload")) {
                if (hasPermission(sender,"rigoxblocker.cmd.reload")) {
                    long timeFinished = System.currentTimeMillis();
                    RigoxBlocker.getInstance().getFiles().reloadFile(RigoxSave.ALL);
                    RigoxBlocker.getInstance().getFiles().initWriter();
                    RigoxUtilities.sendColored(sender, RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.MESSAGES).getString("messages.reload").replace("<ms>", "" + (System.currentTimeMillis() - timeFinished)));
                } else {
                    RigoxUtilities.sendColored(sender, RigoxBlocker.getInstance().getFiles().getControl(RigoxFile.MESSAGES).getString("messages.no-perms"));
                }
            }
        }
        return true;
    }
}
