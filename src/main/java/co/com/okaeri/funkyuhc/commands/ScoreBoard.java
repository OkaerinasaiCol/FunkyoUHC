package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.player.ScoreManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreBoard implements CommandExecutor {

    private FunkyUHC plugin;
    private ScoreManager manager;

    public ScoreBoard(FunkyUHC plugin){
        this.plugin = plugin;
        manager = new ScoreManager(plugin);
    }


    @SuppressWarnings("NullableProblems")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (args.length == 0){
            return false;
        }

        if (!(sender instanceof Player)){
            if (args[0].equals("set")){
                manager.SetScoreboard();
            }
        }
        return false;
    }
}
