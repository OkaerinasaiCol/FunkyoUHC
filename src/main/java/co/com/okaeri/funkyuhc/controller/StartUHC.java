package co.com.okaeri.funkyuhc.controller;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.util.SendToBot;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class StartUHC {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "unused"})
    private FunkyUHC plugin;

    /**
     * Funcion encargada de iniciar los parametros necesarios para el UHC
     * @param plugin: Clase principal del plugin ({@link FunkyUHC})
     */
    public StartUHC(FunkyUHC plugin) {
        this.plugin = plugin;
        // TODO: Inhabilitar los comandos que no se puedan ejecutar dentro del uhc


        for (String team: plugin.TeamDB.getTeams()){
            plugin.TeamDB.setTeamKills(0, team);
            plugin.TeamDB.setKills(0, plugin.TeamDB.getTeamCapitan(team));
            plugin.TeamDB.setDeath(plugin.TeamDB.getTeamCapitan(team), false);

            for (String player: plugin.TeamDB.getTeamPlayers(team)){
                plugin.TeamDB.setKills(0, player);
                plugin.TeamDB.setDeath(player, false);
            }
        }

        plugin.heads.clearHeads();
        plugin.UhcDatabaseManager.clear();

        if (Verify()) {

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                try {
                    if (!plugin.timeBar.getPlayers().contains(p)) {
                        plugin.timeBar.addPlayer(p);
                    }
                } catch (Exception e) {
                    plugin.timeBar.addPlayer(p);
                }
                plugin.manager.setScoreBoardToPlayer(p);
            }

            for (World world: plugin.getServer().getWorlds()){
                world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            }
            plugin.manager.SetScoreboard();
            plugin.startTime = LocalDateTime.now();
            plugin.UhcStarted = true;
            plugin.UhcTimerStarted = true;

            // "UHC START"
            new SendToBot("UHC START"," ",
                    new String[]{" ",});
        }
    }

    /**
     * Funcion encargada de verificar que todos los parametros esten correctos para ejecutar el UHC
     * @return true: si todo_ se encuentra bien <p>false: si se encuentra algún error
     */
    private boolean Verify() {
        // Verificar que todo_ este correcto antes de inicial el uhc
        return true; // cambiar en cuanto se cree la clase
    }
}
