package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.controller.StartUHC;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sun.management.Sensor;

public class UhcController implements CommandExecutor {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public UhcController(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             String @NotNull [] args) {

        if (args.length == 0) {
            plugin.print("&4[&rUhcfunkyo Plugin&4]&r Version:" + plugin.pluginVersion + " Teams: " +
                    (plugin.teams.toArray().length - 1));
        } else if (!(sender instanceof Player)) { // inicializador de clase
            command(args, sender);
        }

        return false;
    }

    private void command(String[] args, CommandSender sender) {
        if (args[0].equals("start")) {
            new StartUHC(plugin);
        } else if ((args[0].equals("time")) && plugin.UhcStarted) {
            plugin.print("Seconds: " + plugin.UhcTimerDuration);
            plugin.print("Tiempo transcurrido: " + plugin.timer.toString(plugin.UhcTimerDuration));
        } else if (args[0].equals("resume")) {

            // TODO: verificador de que se tenga un argumento y que el argumento este en la lista de equipos de la base de datos
            if (!(sender instanceof Player)){
                resume();
            } else if(sender.isOp()){
                resume();
            }

        }
    }

    public void resume(){
        plugin.UhcTimerPaused = false;
        plugin.UhcTimeRestarted = true;
        plugin.UhcTimerStarted = true;
        plugin.manager.SetScoreboard();

        for (Player p: plugin.getServer().getOnlinePlayers()) {
            if (plugin.TeamDB.getTeam(p.getName()) != null) {
                if (!plugin.TeamDB.getDeath(p.getName())) {
                    plugin.timeBar.addPlayer(p);
                }
            }
        }
    }
}
