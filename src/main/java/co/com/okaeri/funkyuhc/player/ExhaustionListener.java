package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;

public class ExhaustionListener implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public ExhaustionListener(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHeal(EntityExhaustionEvent event) {

        if (plugin.UhcStarted && event.getExhaustionReason().equals(EntityExhaustionEvent.ExhaustionReason.REGEN)) {
            event.setCancelled(true);
        }
    }

}

