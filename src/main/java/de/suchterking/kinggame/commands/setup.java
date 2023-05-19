package de.suchterking.kinggame.commands;

import de.suchterking.kinggame.placeholders.PlayerSize;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static de.suchterking.kinggame.KingGame.SendMessage;
import static de.suchterking.kinggame.KingGame.config;
import static de.suchterking.kinggame.utils.CustomUtils.sendUsage;

public class setup implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) commandSender;

        if (args.length != 1) {
            player.sendMessage(sendUsage("/setup <spawn>"));
            return false;
        }

        String path = args[0];

        Location loc = player.getLocation();

        config.getFileConfiguration().set(path + ".x", loc.getX());
        config.getFileConfiguration().set(path + ".y", loc.getY());
        config.getFileConfiguration().set(path + ".z", loc.getZ());
        config.save();

        player.sendMessage(SendMessage("save"));
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        List<String> start = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            start.add("start" + i);
        }

        if (args.length == 1) {
            return start;
        }
        return null;
    }
}
