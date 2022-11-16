package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.controller.StartUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
            command(args);
        }

        return false;
    }

    private void command(String[] args) {
        if (args[0].equals("start")) {
            new StartUHC(plugin);
        } else if ((args[0].equals("time")) && plugin.UhcStarted) {
            plugin.print("Seconds: " + plugin.UhcTimerDuration.getSeconds());
            plugin.print("Tiempo transcurrido: " + plugin.timer.toString(plugin.UhcTimerDuration));
        }
    }
}
