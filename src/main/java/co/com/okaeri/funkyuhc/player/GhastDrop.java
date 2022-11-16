package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class GhastDrop implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public GhastDrop(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void DisableGhastDrops(EntityDeathEvent event) {
        if (plugin.UhcStarted && event.getEntity().getType().equals(EntityType.GHAST)) {
            event.getDrops().removeIf(drop -> drop.getType().equals(Material.GHAST_TEAR));
        }
    }
}
