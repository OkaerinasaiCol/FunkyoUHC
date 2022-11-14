package co.com.okaeri.funkyuhc.controller;

import co.com.okaeri.funkyuhc.FunkyUHC;

import java.time.LocalDateTime;

public class StartUHC {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private FunkyUHC plugin;
    // TODO: agregar base de datos para guardar información del uhc en caso de que se cierre

    public StartUHC(FunkyUHC plugin){
        this.plugin = plugin;
        // TODO: Inhabilitar los comandos que no se puedan ejecutar dentro del uhc

        if (Verify()){
            plugin.startTime = LocalDateTime.now();
            plugin.UhcStarted = true;
            plugin.UhcTimerStarted = true;
            plugin.manager.SetScoreboard();
        }
    }

    private boolean Verify(){
        // Verificar que todo_ este correcto antes de inicial el uhc
        return true; // cambiar en cuanto se cree la clase
    }
}
