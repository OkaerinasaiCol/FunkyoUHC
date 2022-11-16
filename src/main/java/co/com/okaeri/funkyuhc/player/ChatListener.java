package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public ChatListener(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {

        if (plugin.UhcStarted && (event.getPlayer().getGameMode() != GameMode.SPECTATOR)) {
            Player p = event.getPlayer();
            String message = event.getMessage();
            event.setCancelled(true);

            String team = plugin.TeamDB.getTeam(p.getName());
            String capitan = plugin.TeamDB.getTeamCapitan(team);

            try {

                if (!(p.getName().equals(capitan))) {
                    //noinspection ConstantConditions
                    plugin.getServer().getPlayer(capitan).sendMessage("<" + p.getName() + "> " + message);
                }

            } catch (NullPointerException ignored) {
            }

            //noinspection ConstantConditions
            plugin.print(p.getPlayer().getName() + " m: " + message);

            for (String team_players : plugin.TeamDB.getTeamPlayers(team)) {
                Player team_player = plugin.getServer().getPlayer(team_players);

                try {
                    if (!(team_players.equals(p.getName()))) {
                        //noinspection ConstantConditions
                        team_player.sendMessage("<" + p.getName() + "> " + message);
                    }
                } catch (NullPointerException ignored) {
                }

            }

        }
    }
}
