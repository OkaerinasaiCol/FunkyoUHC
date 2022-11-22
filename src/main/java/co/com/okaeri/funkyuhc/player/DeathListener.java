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
            plugin.print("location: " + e.getEntity().getLocation());

            if (e.getEntity().getKiller() != null) {
                plugin.TeamDB.addKill(e.getEntity().getKiller().getName());
            }

            plugin.TeamDB.setDeath(p.getName(), true);

            p.setGameMode(GameMode.SPECTATOR);

            //"PLAYER\tDEATH\tPLAYER"
            new SendToBot("PLAYER", "DEATH",
                    new String[]{p.getName(),});

            List<String> alive_teams = new ArrayList<>();

            for (Player p_ : plugin.getServer().getOnlinePlayers()) {
                if (!p_.isDead() && !alive_teams.contains(plugin.TeamDB.getTeam(p_.getName()))) {

                    alive_teams.add(plugin.TeamDB.getTeam(p_.getName()));
                    System.out.println();
                }
            }

            if (alive_teams.size() == 1) {


                plugin.tittle.setTittle(plugin.colors.green + "El equipo " + alive_teams.get(0), "Ha ganado el Uhc");
                // TODO: solucionar error que falla cuando no es una pesona quien hace la kill
                // TODO: hacer todos los avisos y celebraciones
                UhcController controller = new UhcController(plugin);
                controller.stop();
            }

        }
    }
}
