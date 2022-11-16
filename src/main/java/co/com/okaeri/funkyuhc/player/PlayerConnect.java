package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerConnect implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public PlayerConnect(FunkyUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerConnect(@NotNull PlayerJoinEvent event){
        if (plugin.UhcStarted){
            if (plugin.TeamDB.getTeam(event.getPlayer().getName()) != null){
                if (!plugin.TeamDB.getDeath(event.getPlayer().getName())){
                    plugin.timeBar.addPlayer(event.getPlayer());
                    event.getPlayer().setGameMode(GameMode.SURVIVAL);
                }
            } else {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}
