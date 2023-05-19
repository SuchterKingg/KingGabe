package de.suchterking.kinggame.utils;

import de.suchterking.kinggame.placeholders.PlayerSize;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static de.suchterking.kinggame.KingGame.*;

public class CustomUtils {

    private static int spawns = 1;

    public static boolean checkStart() {
        if (players.size() >= 1) {
            return true;
        }
        return false;
    }

    public static void spawn() {

        int spawns = 1;

        for (int i = 0; i < players.size(); i ++){
            Player player = players.get(i);
            Bukkit.broadcastMessage(player.getName());

            int s = spawns;
            Bukkit.broadcastMessage(String.valueOf(s));

            double x = config.getFileConfiguration().getDouble("start"+s+".x");
            double y = config.getFileConfiguration().getDouble("start"+s+".y");
            double z = config.getFileConfiguration().getDouble("start"+s+".z");

            Bukkit.broadcastMessage(x +"x" + y +"y" +z+"z");

            Location location = new Location(player.getWorld(), x, y, z);

            Horse horse = (Horse) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(location, EntityType.HORSE);
            horse.setTamed(true);
            horse.setOwner(player);
            horse.addPassenger(player);
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            spawns ++;
        }
    }

    public static void Lobby() {
        for (Player player : players) {
            player.sendMessage(SendMessage("lobby"));
            double x = config.getFileConfiguration().getDouble("lobby.x");
            double y = config.getFileConfiguration().getDouble("lobby.y");
            double z = config.getFileConfiguration().getDouble("lobby.z");

            if (player.isInsideVehicle()) {
                player.getVehicle().remove();
            }
            player.teleport(new Location(player.getWorld(), x,y, z));
        }
    }
    public static void Lobby1(Player player) {
        player.sendMessage(SendMessage("lobby"));
        double x = config.getFileConfiguration().getDouble("lobby.x");
        double y = config.getFileConfiguration().getDouble("lobby.y");
        double z = config.getFileConfiguration().getDouble("lobby.z");

        if (player.isInsideVehicle()) {
            player.getVehicle().remove();
        }
        player.teleport(new Location(player.getWorld(), x,y, z));
    }


    public static String sendUsage(String s) {
        String usage = "§aBitte nutze §e"+s;
        return usage;
    }

}
