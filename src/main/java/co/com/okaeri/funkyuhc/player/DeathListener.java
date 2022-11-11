package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.util.Head;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener{

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public  DeathListener(FunkyUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void Muertes(PlayerDeathEvent e){
        Player p = e.getEntity();

        Head h = new Head();

        ItemStack drop = h.getPlayerHead(p);
        p.getWorld().dropItem(p.getLocation(), drop);

        plugin.print(e.getEntity() + " death " + e.getEntity().getLastDamageCause() + e.getEntity().getKiller());
        plugin.print("death id" + e.getEntity().getUniqueId());

        // TODO: Poner al jugador en modo espectador

    }



}
