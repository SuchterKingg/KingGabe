package de.suchterking.kinggame.listener;

import de.suchterking.kinggame.manager.GameState;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

import static de.suchterking.kinggame.KingGame.*;
import static de.suchterking.kinggame.utils.CustomUtils.*;
import static org.bukkit.entity.EntityType.HORSE;

public class Listeners implements Listener {

    private static String JoinedName;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setGameMode(GameMode.SURVIVAL);
        if (player.isInsideVehicle()) {
            player.getVehicle().remove();
        }

        if (gameManager.getGameState() == GameState.LOBBY) {
            Lobby1(player);
        }

        if (gameManager.getGameState() != GameState.LOBBY && players.size() >= 1) {
            player.setGameMode(GameMode.SPECTATOR);
            this.JoinedName = player.getName();

            return;
        }

        if (players.size() == 12) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(""); //todo send full -> from cfg

            return;
        }

        players.add(player);
        e.setJoinMessage(SendMessage("join"));
        if (checkStart() == true) {
            gameManager.setGameState(GameState.LOADING);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (players.contains(player)) {
            players.remove(player);
            e.setQuitMessage(SendMessage("quit"));
        }

        if (checkStart() == false) {
            gameManager.setGameState(GameState.LOBBY);
        }

    }


    @EventHandler
    public void onVehicleLeave(VehicleExitEvent e) {
        if (e.getExited() instanceof Player) {
            e.setCancelled(true);
        }
    }

    public static String getJoinedName() {
        return JoinedName;
    }

    @EventHandler
    public void onEntityMove(EntityMoveEvent e) {
        if (e.getEntityType() != HORSE) return;
        if (e.getEntity().getPassengers().isEmpty()) return;
        if (e.getEntity().getLocation().subtract(0, 1, 0).getBlock().getType() != Material.GOLD_BLOCK) return;

        Player player = (Player) e.getEntity().getPassenger();

        e.getEntity().remove();
        finished.add(player);

        if (players.size() == finished.size()) {
            Bukkit.broadcastMessage("finished");
            gameManager.setGameState(GameState.LOBBY);
        }
    }
}
