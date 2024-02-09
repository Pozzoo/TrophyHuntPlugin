package com.github.pozzoo.treasurehunt.commands;

import com.github.pozzoo.treasurehunt.TreasureHunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearFoundCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Somente players podem usar este comando");
            return false;
        }
        Player player = (Player) sender;

        if (!(player.isOp())) {
            sender.sendMessage("Você não tem permissão para utilizar este comando");
            return false;
        }

        TreasureHunt.getInstance().getTreasureManager().clearPlayerCollected();

        return true;
    }
}
