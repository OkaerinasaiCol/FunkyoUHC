package co.com.okaeri.funkyuhc.controller;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class StartUHC {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "unused"})
    private FunkyUHC plugin;
    // TODO: agregar base de datos para guardar información del uhc en caso de que se cierre

    /**
     * Funcion encargada de iniciar los parametros necesarios para el UHC
     * @param plugin: Clase principal del plugin ({@link FunkyUHC})
     */
    public StartUHC(FunkyUHC plugin) {
        this.plugin = plugin;
        // TODO: Inhabilitar los comandos que no se puedan ejecutar dentro del uhc

        if (Verify()) {

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                try {
                    if (!plugin.timeBar.getPlayers().contains(p)) {
                        plugin.timeBar.addPlayer(p);
                    }
                } catch (Exception e) {
                    plugin.timeBar.addPlayer(p);
                }
            }

            plugin.startTime = LocalDateTime.now();
            plugin.UhcStarted = true;
            plugin.UhcTimerStarted = true;
            plugin.manager.SetScoreboard();
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
