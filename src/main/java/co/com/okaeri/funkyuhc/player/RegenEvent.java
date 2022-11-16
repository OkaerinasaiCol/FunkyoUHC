package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RegenEvent implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public RegenEvent (FunkyUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent event){
        if (plugin.UhcStarted && event.getEntity() instanceof Player){

            if (event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.SATIATED)){
                event.setCancelled(true);
            }

        }
    }

}
