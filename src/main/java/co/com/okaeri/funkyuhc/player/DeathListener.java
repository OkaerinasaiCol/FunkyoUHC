package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.commands.UhcController;
import co.com.okaeri.funkyuhc.util.Head;
import co.com.okaeri.funkyuhc.util.SendToBot;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class DeathListener implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public DeathListener(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void Muertes(PlayerDeathEvent e) {
        if (plugin.UhcStarted) {
            Player p = e.getEntity();

            Head h = new Head();

            ItemStack drop = h.getPlayerHead(p);
            p.getWorld().dropItem(p.getLocation(), drop);

            plugin.print(e.getEntity() + " death " + e.getEntity().getLastDamageCause() + e.getEntity().getKiller());
            plugin.print("death id" + e.getEntity().getUniqueId());

            if (e.getEntity().getKiller() != null) {
                plugin.TeamDB.addKill(e.getEntity().getKiller().getName());
            }

            plugin.TeamDB.setDeath(p.getName(), true);

            p.setGameMode(GameMode.SPECTATOR);

            //"PLAYER\tDEATH\tPLAYER"
            new SendToBot("PLAYER","DEATH",
                    new String[]{p.getName(),});

            List<Player> alive_players = new ArrayList<>();

            for (Player p_: plugin.getServer().getOnlinePlayers()){
                if (!p_.isDead() &&
                        !plugin.TeamDB.getTeam(p_.getName()).equals(
                                plugin.TeamDB.getTeam(e.getEntity().getKiller().getName()))){

                    alive_players.add(p_);

                }
            }

            if (alive_players.size() == 0){
                plugin.tittle.setTittle(plugin.colors.green + "El equipo " + plugin.TeamDB.getTeam(
                        e.getEntity().getKiller().getName()), "Ha ganado el Uhc");

                // TODO: hacer todos los avisos y celebraciones
                UhcController controller = new UhcController(plugin);
                controller.stop();
            }

        }
    }
}
