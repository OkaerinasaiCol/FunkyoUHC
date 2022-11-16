package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagedListener implements Listener {

    private FunkyUHC plugin;

    public EntityDamagedListener(FunkyUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamageByEntiyEvent(EntityDamageByEntityEvent event){
        if (plugin.UhcStarted && event.getDamager().getType().equals(EntityType.PLAYER) &&
        plugin.round < 3){
            Player p = (Player) event.getDamager();
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent(plugin.colors.red + "El PVP está desactivado hasta la ronda 3"));
            event.setCancelled(true);
        }
    }
}
