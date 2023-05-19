package de.suchterking.kinggame;
import de.suchterking.kinggame.commands.getGamestate;
import de.suchterking.kinggame.commands.setup;
import de.suchterking.kinggame.listener.Listeners;
import de.suchterking.kinggame.manager.GameManager;
import de.suchterking.kinggame.placeholders.PlayerSize;
import de.suchterking.kinggame.utils.Config;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class KingGame extends JavaPlugin {
    public static List<Player> players = new ArrayList<>();
    public static Config config;
    public static Config messages;
    public static String prefix;
    private static KingGame plugin;
    public static GameManager gameManager = new GameManager(KingGame.getPlugin());
    public static List<Player> finished = new ArrayList<>();

    @Override
    public void onEnable() {

        Boolean t = true;

        config = new Config("config.yml", getDataFolder());
        config.getFileConfiguration().addDefault("prefix", "[kg]");
        config.getFileConfiguration().addDefault("lobby.x", 190);
        config.getFileConfiguration().addDefault("lobby.y", 64);
        config.getFileConfiguration().addDefault("lobby.z", 230);
        config.getFileConfiguration().addDefault("spawn.x", 190);
        config.getFileConfiguration().addDefault("spawn.y", 64);
        config.getFileConfiguration().addDefault("spawn.z", 230);
        config.getFileConfiguration().options().copyDefaults(true);
        config.save();
        prefix = config.getFileConfiguration().getString("prefix");
        messages = new Config("messages.yml", getDataFolder());

        messages.getFileConfiguration().addDefault("join", "%kinggame_prefix% §e%player_name% §aist beigetreten §e[%kinggame_players% / 12]");
        messages.getFileConfiguration().addDefault("quit", "%kinggame_prefix% §e%player_name% §4ist gegangen §e[%kinggame_players% / 12]");
        messages.getFileConfiguration().addDefault("lobby", "%kinggame_prefix% §aDu bist zurück in der Lobby");
        messages.getFileConfiguration().addDefault("start", "%kinggame_prefix% §aDas Spiel startet in");
        messages.getFileConfiguration().addDefault("save", "%kinggame_prefix% §aDer Punkt wurde gespeichert");
        messages.getFileConfiguration().options().copyDefaults(true);
        messages.save();

        plugin = this;
        getCommand("setup").setExecutor(new setup());

        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);

        new PlayerSize(this).register();

    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isInsideVehicle()) {
                player.getVehicle().remove();
            }
        }
    }

    public static KingGame getPlugin() {
        return plugin;
    }

    public static String SendMessage(String path) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String msg1 = messages.getFileConfiguration().getString(path);
            String msg2 = PlaceholderAPI.setPlaceholders(player, msg1);

            return msg2;
        }
        return null;
    }
}
