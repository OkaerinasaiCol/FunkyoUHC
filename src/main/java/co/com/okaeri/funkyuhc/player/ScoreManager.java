package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.util.Colors;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ScoreManager {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;
    private String team_kills_txt;

    public ScoreManager(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    public void SetScoreboard() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            setScoreBoardToPlayer(p);
        }
    }

    public void setScoreBoardToPlayer(@NotNull Player player) {

        if (plugin.TeamDB.getTeam(player.getName()) != null) {
            Colors colors = plugin.colors;
            String team = plugin.TeamDB.getTeam(player.getName());
            String color = plugin.TeamDB.getTeamColor(team);
            String capitan = plugin.TeamDB.getTeamCapitan(team);
            int kills = plugin.TeamDB.getKills(player.getName());
            int teamKills = plugin.TeamDB.getTeamKills(player.getName());

            team_kills_txt = "§n§c Kills:§r " + kills + " §n§cKills Equipo:§r " + teamKills + " §n§cPing:§r ";

            ArrayList<String> teamPlayers = plugin.TeamDB.getTeamPlayers(team);
            String player2 = "----------------";
            String player3 = "----------------";
            String player4 = "----------------";

            try {
                player2 = teamPlayers.get(0).replaceAll("\\s+", "");
                player3 = teamPlayers.get(1).replaceAll("\\s+", "");
                player4 = teamPlayers.get(2).replaceAll("\\s+", "");
            } catch (IndexOutOfBoundsException ignored) {
            }

            FastBoard board = new FastBoard(player);
            board.updateTitle("§5§l§n UHC TESTNAME §r"); // <- Inmutable durante el tiempo de ejecución
            board.updateLines("", // <- Inmutable durante el tiempo de ejecución
                    "§5§l          00:00:00§r",
                    "", // <- Inmutable durante el tiempo de ejecución
                    " " + colors.red + "Equipo: " + colors.reset + team + "  " + color + "█§r ", // <- Inmutable durante el tiempo de ejecución
                    "", // <- Inmutable durante el tiempo de ejecución
                    "§n§9 Capitan:§r " + capitan,
                    "", // <- Inmutable durante el tiempo de ejecución
                    "§n§b Integrantes:§r ▼▼▼▼▼▼▼▼▼",
                    "§n§b                 §r" + player2,
                    "§n§b                 §r" + player3,
                    "§n§b                 §r" + player4,
                    "", // <- Inmutable durante el tiempo de ejecución
                    "§n§c Kills:§r " + kills + " §n§cKills Equipo:§r " + teamKills + " §n§cPing:§r " + player.getPing(),
                    "============================", // <- Inmutable durante el tiempo de ejecución
                    "                   §d@FunkyoEnma2022"); // <- Inmutable durante el tiempo de ejecución
            plugin.print(player.getName() + board);
            plugin.boards.put(player, board);
        }
    }

    @SuppressWarnings("ReassignedVariable")
    public void UpdateBoard() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {

            FastBoard board = plugin.boards.get(p);
            try {
                board.updateLine(0, "");

            } catch (NullPointerException e) {

                setScoreBoardToPlayer(p);
                board = plugin.boards.get(p);

            }

            try {
                board.updateLine(1, "§5§l             " + plugin.timer.toString(plugin.UhcTimerDuration) + "§r          ");
                board.updateLine(12, team_kills_txt + p.getPing());
            } catch (NullPointerException e) {
                setScoreBoardToPlayer(p);
                board.updateLine(1, "§5§l             " + plugin.timer.toString(plugin.UhcTimerDuration) + "§r          ");
                board.updateLine(12, team_kills_txt + p.getPing());
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void UpdateKills(String player) {

        Player p = plugin.getServer().getPlayer(player);

        FastBoard board = plugin.boards.get(p);
        int kills = plugin.TeamDB.getKills(p.getName());
        int teamKills = plugin.TeamDB.getTeamKills(plugin.TeamDB.getTeam(p.getName()));

        team_kills_txt = "§n§c Kills:§r " + kills + " §n§cKills Equipo:§r " + teamKills + " §n§cPing:§r ";

        board.updateLine(12, team_kills_txt + p.getPing());
    }

}
