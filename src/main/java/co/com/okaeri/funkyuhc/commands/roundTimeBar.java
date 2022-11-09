package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


public class roundTimeBar implements CommandExecutor {

    private FunkyUHC plugin;
    public BossBar bossBar;

    public roundTimeBar(FunkyUHC plugin, BossBar bossBar){
        this.plugin = plugin;
        this.bossBar = bossBar;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        // players = plugin.getServer().getOnlinePlayers();

        for (Player p: plugin.getServer().getOnlinePlayers()){
            try {
                if (!bossBar.getPlayers().contains(p)){
                    bossBar.addPlayer(p);
                }
            } catch (Exception e){
                bossBar.addPlayer(p);
            }
        }
        return true;
    }
}
