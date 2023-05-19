package de.suchterking.kinggame.manager;

import de.suchterking.kinggame.KingGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static de.suchterking.kinggame.KingGame.SendMessage;
import static de.suchterking.kinggame.KingGame.gameManager;
import static de.suchterking.kinggame.utils.CustomUtils.Lobby;
import static de.suchterking.kinggame.utils.CustomUtils.spawn;

public class GameManager {

    private final KingGame plugin;
    private GameState gameState = GameState.LOBBY;
    private int time;
    private int time2;
    private static boolean LeaveVehicle = true;

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        switch (gameState) {
            case LOBBY:
                Lobby();
                break;
            case LOADING:

                this.time = 10;
                this.time2 = 10;

                BukkitRunnable runnable = new BukkitRunnable() {

                    @Override
                    public void run() {
                        switch (time) {
                            case 30:
                                sendTime();
                                break;
                            case 15:
                                sendTime();
                                break;
                            case 10:
                                sendTime();
                                break;
                            case 5:
                                sendTime();
                                break;
                            case 4:
                                sendTime();
                                break;
                            case 3:
                                sendTime();
                                break;
                            case 2:
                                sendTime();
                                break;
                            case 1:
                                sendTime();
                                break;
                            case 0:
                                gameManager.setGameState(GameState.STARTING);
                                break;
                            default:
                        }

                        if (time == 10 || time == 30 || time <= 5 && time >= 1) {
                            if (time <= 3) {
                                sd(1);
                            }else {
                                sd(2);
                            }
                        }

                        time -- ;
                    }
                };

                runnable.runTaskTimer(KingGame.getPlugin(),  0, 20);

                if (time == 0) {
                    runnable.cancel();
                }

                break;
            case STARTING:
                spawn();
                BukkitRunnable rb = new BukkitRunnable() {

                    @Override
                    public void run() {
                        switch (time2) {
                            case 4:
                                Bukkit.broadcastMessage(SendMessage("start"));
                                break;
                            case 3:
                                sendTime2();
                                break;
                            case 2:
                                sendTime2();
                                break;
                            case 1:
                                sendTime2();
                                break;
                            case 0:
                                gameManager.setGameState(GameState.ACTIVE);
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendTitle(ChatColor.GREEN + "LOS", "");
                                }
                                break;
                            default:
                        }

                        if (time2 <= 3 && time2 >= 1) {
                            if (time <= 3 && time2 != 1) {
                                sd(2);
                            }if (time2 == 1) {
                                sd(1);
                            }
                        }

                        time2 -- ;
                    }
                };

                rb.runTaskTimer(KingGame.getPlugin(),  0, 20);

                if (time2 == 0) {
                    rb.cancel();
                }

                break;
            case ACTIVE: break;
        }
    }

    private void sendTime() {

        Bukkit.broadcastMessage("§a" +String.valueOf(time));

    }private void sendTime2() {

        Bukkit.broadcastMessage("§a" +String.valueOf(time2));

    }

    public void cleanup() {

    }

    public GameState getGameState() {
        return gameState;
    }

    public GameManager(KingGame plugin) {
        this.plugin = plugin;
    }

    public static boolean getVehicleLeft() {
        return LeaveVehicle;
    };

    public static void sd(int i) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL , 100, i );
        }
    }

}
