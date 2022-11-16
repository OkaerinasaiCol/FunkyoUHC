package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.player.ScoreManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScoreBoard implements CommandExecutor {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "unused"})
    private FunkyUHC plugin;
    @SuppressWarnings("FieldMayBeFinal")
    private ScoreManager manager;

    public ScoreBoard(FunkyUHC plugin) {
        this.plugin = plugin;
        manager = new ScoreManager(plugin);
    }


    @SuppressWarnings("NullableProblems")
    public boolean onCommand(CommandSender sender, Command command, String label, String @NotNull [] args) {

        if (args.length == 0) {
            return false;
        }

        if (!(sender instanceof Player)) {
            if (args[0].equals("set")) {
                manager.SetScoreboard();
            }
        }
        return false;
    }
}
