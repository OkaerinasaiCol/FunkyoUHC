package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerMove implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public PlayerMove(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {

        ItemStack main_hand = event.getPlayer().getInventory().getItemInMainHand();
        ItemStack off_hand = event.getPlayer().getInventory().getItemInOffHand();
        Location default_location = new Location(event.getPlayer().getWorld(), 0.0d, 0.0d, 0.0d);

        if (main_hand.getType().equals(Material.COMPASS)) {
            try {
                //noinspection ConstantConditions
                if (main_hand.getItemMeta().getLore().get(0).equals("Brújula para buscar jugadores cercanos")) {
                    String team = plugin.TeamDB.getTeam(event.getPlayer().getName());

                    List<String> team_players = plugin.TeamDB.getTeamPlayers(team);
                    team_players.add(plugin.TeamDB.getTeamCapitan(team));

                    Map<Double, Player> locations = new HashMap<>();

                    for (Player p : plugin.getServer().getOnlinePlayers()) {

                        if (!team_players.contains(p.getName()) && !p.isDead() && p.getGameMode().equals(GameMode.SURVIVAL)) {
                            locations.put(p.getLocation().distance(event.getPlayer().getLocation()), p);
                        }
                    }

                    Map.Entry<Double, Player> min = null;
                    for (Map.Entry<Double, Player> entry : locations.entrySet()) {
                        if (min == null || min.getKey() > entry.getKey()) {
                            min = entry;
                        }
                    }

                    assert min != null;
                    event.getPlayer().setCompassTarget(min.getValue().getLocation());

                } else {
                    event.getPlayer().setCompassTarget(default_location);
                }
            } catch (NullPointerException e) {
                event.getPlayer().setCompassTarget(default_location);
            }

        } else if (off_hand.getType().equals(Material.COMPASS)) {

            try {
                //noinspection ConstantConditions
                if (main_hand.getItemMeta().getLore().get(0).equals("Brújula para buscar jugadores cercanos")) {
                    String team = plugin.TeamDB.getTeam(event.getPlayer().getName());

                    List<String> team_players = plugin.TeamDB.getTeamPlayers(team);
                    team_players.add(plugin.TeamDB.getTeamCapitan(team));

                    Map<Double, Player> locations = new HashMap<>();

                    for (Player p : plugin.getServer().getOnlinePlayers()) {

                        if (!team_players.contains(p.getName()) && !p.isDead() && p.getGameMode().equals(GameMode.SURVIVAL)) {
                            locations.put(p.getLocation().distance(event.getPlayer().getLocation()), p);
                        }
                    }

                    Map.Entry<Double, Player> min = null;
                    for (Map.Entry<Double, Player> entry : locations.entrySet()) {
                        if (min == null || min.getKey() > entry.getKey()) {
                            min = entry;
                        }
                    }

                    assert min != null;
                    event.getPlayer().setCompassTarget(min.getValue().getLocation());

                } else {
                    event.getPlayer().setCompassTarget(default_location);
                }
            } catch (NullPointerException e) {
                event.getPlayer().setCompassTarget(default_location);
            }

        }
    }
}
