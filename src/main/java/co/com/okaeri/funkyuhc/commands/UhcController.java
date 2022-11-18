package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.controller.StartUHC;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.Statement;

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

        if (args[0].equals("start") && !plugin.UhcTimerPaused && !plugin.UhcTimerStarted) {
            new StartUHC(plugin);
        } else if ((args[0].equals("time")) && plugin.UhcStarted) {
            plugin.print("Seconds: " + plugin.UhcTimerDuration);
            plugin.print("Tiempo transcurrido: " + plugin.timer.toString(plugin.UhcTimerDuration));
        } else if (args[0].equals("pause") && plugin.UhcTimerStarted) {
            plugin.print(plugin.colors.red + "Juego pausado ");
            plugin.print(plugin.colors.green + "Tiempo transcurrido: " +
                    plugin.colors.reset + plugin.UhcTimerDuration);
            plugin.print(plugin.colors.green + "Tiempo restante: " +
                    plugin.colors.reset + (((long) plugin.timePerRound * plugin.round) - plugin.UhcTimerDuration));
            plugin.print(plugin.colors.green + "Ronda: " +
                    plugin.colors.reset + plugin.round);
            plugin.print(plugin.colors.green + "Tamaño borde: " +
                    plugin.colors.reset + plugin.wb.getSize());

            plugin.UhcTimerPaused = true;
            plugin.PostPaused = false;

        } else if (args[0].equals("resume") && plugin.UhcTimerPaused) {

            if (!(sender instanceof Player)){
                resume();
            } else if(sender.isOp()){
                resume();
            }

        } else if (args[0].equals("stop") && plugin.UhcTimerStarted) {

            long time = plugin.UhcTimerDuration;
            long restante = (((long) plugin.timePerRound * plugin.round) - plugin.UhcTimerDuration);
            int round = plugin.round;
            double size = plugin.wb.getSize();

            stop();

            plugin.print(plugin.colors.red + "Juego detenido ");
            plugin.print(plugin.colors.green + "Tiempo transcurrido: " +
                    plugin.colors.reset + time);
            plugin.print(plugin.colors.green + "Tiempo restante: " +
                    plugin.colors.reset + restante);
            plugin.print(plugin.colors.green + "Ronda: " +
                    plugin.colors.reset + round);
            plugin.print(plugin.colors.green + "Tamaño borde: " +
                    plugin.colors.reset + size);

        }
    }

    public void stop(){
        plugin.UhcStarted = false;
        plugin.UhcTimerStarted = false;
        plugin.UhcTimerPaused = false;
        plugin.UhcTimeRestarted = false;
        plugin.PostPaused = false;
        plugin.round = 1;

        for (FastBoard board: plugin.boards.values()){
            board.delete();
        }

        plugin.boards.clear();
        plugin.changeSize(plugin.maxSize, 1);
        plugin.timeBar.removeAll();

        for (String team: plugin.TeamDB.getTeams()){
            plugin.TeamDB.setTeamKills(0, team);
            try {
                Statement statment = plugin.db.statement();
                statment.executeUpdate("UPDATE players SET kills = 0");
                statment.executeUpdate("UPDATE players SET death = 0");
                statment.close();

            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        for (int i = 1; i <= plugin.roundsStarted.size(); i++){
            plugin.roundsStarted.replace(i, false);
            plugin.worldBorderBefore.replace(i, false);
            plugin.worldBorderReduceStart.replace(i, false);
        }

        plugin.heads.clearHeads();
        plugin.UhcDatabaseManager.clear();
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
