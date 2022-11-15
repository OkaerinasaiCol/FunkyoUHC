package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlobalMessage implements CommandExecutor {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public GlobalMessage(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (plugin.UhcStarted) {

            String subFix;

            if (sender instanceof Player) {
                String team = plugin.TeamDB.getTeam(sender.getName());

                subFix = plugin.TeamDB.getTeamColor(team) + "<" + sender.getName() + "> [" + team + "]" + plugin.colors.reset;
            } else {
                subFix = plugin.colors.underline + plugin.colors.dark_red + "[Server]" + plugin.colors.reset;
            }

            //noinspection ReassignedVariable
            String message = "";

            for (String arg : args) {
                message = message.concat(" " + arg);
            }

            plugin.getServer().broadcastMessage(plugin.colors.bold + subFix + message);

        }

        return false;
    }
}
