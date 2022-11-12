package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;


public class ScoreManager {

    private FunkyUHC plugin;

    public ScoreManager(FunkyUHC plugin){
        this.plugin = plugin;
    }

    public void SetScoreboard(){
        for (Player p: plugin.getServer().getOnlinePlayers()){

            FastBoard board = new FastBoard(p);
            board.updateTitle("§5§l§n UHC TESTNAME §r");
            board.updateLines("",
                              " §nNombre del equipo§r           §c█§r ",
                              "",
                              "§n§9 Capitan:§r xxxxxxxxxxxxxxxx",
                              "",
                              "§n§b Integrantes:§r xxxxxxxxxxxxxxxx",
                              "                 xxxxxxxxxxxxxxxx",
                              "                 xxxxxxxxxxxxxxxx",
                              "                 xxxxxxxxxxxxxxxx",
                              "",
                              "§n§c Kills:§r 28  §n§cPing:§r 112",
                              "============================",
                              "                  §d@FunkyoEnma2022");

            plugin.boards.put(p, board);
        }
    }

    public void UpdateBoard(){
        for (FastBoard board: plugin.boards.values()){
            board.updateLine(13, plugin.timer.toString(plugin.UhcTimerDuration));
        }
    }

    // TODO: hacer funcion que borre la scoreboard al salir el jugador y guarde info importante en la bbdd

}
