package de.suchterking.kinggame.commands;
import de.suchterking.kinggame.manager.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.suchterking.kinggame.KingGame.gameManager;

public class getGamestate implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        gameManager.setGameState(GameState.LOBBY);
        player.sendMessage(String.valueOf(gameManager.getGameState()));


        return false;
    }
}
