package co.com.okaeri.funkyuhc.commands.TabCompleter;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class mapSizeTab implements TabCompleter {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public mapSizeTab(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args) {

        List<String> list = new ArrayList<>();

        if (!(sender instanceof Player)) {
            completator(args, list);
        } else if (sender.isOp()) {
            completator(args, list);
        }

        return list;
    }

    private void completator (String[] args, List<String> list){
        if (args.length == 1){
            plugin.print(args[0]);
            list.add("setmax");
            list.add("set");
            list.add("help");
        }
    }
}
