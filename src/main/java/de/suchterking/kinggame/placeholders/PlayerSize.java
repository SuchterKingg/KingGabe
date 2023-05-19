package de.suchterking.kinggame.placeholders;

import de.suchterking.kinggame.KingGame;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import static de.suchterking.kinggame.KingGame.config;
import static de.suchterking.kinggame.KingGame.players;
import static de.suchterking.kinggame.listener.Listeners.getJoinedName;

public class PlayerSize extends PlaceholderExpansion {

    private KingGame plugin;

    public PlayerSize(KingGame plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "SuchterKing";
    }

    @Override
    public String getIdentifier() {
        return "kinggame";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("players")) {
            return String.valueOf(players.size());
        }
        else if (params.equalsIgnoreCase("joined")) {
            return getJoinedName();
        }
        else if (params.equalsIgnoreCase("prefix")) {
            return config.getFileConfiguration().getString("prefix");
        }

        return null;
    }


}
