package co.com.okaeri.funkyuhc.commands.TabCompleter;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class teamsTab implements TabCompleter {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public teamsTab(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args) {

        List<String> list = new ArrayList<>();

        if (!(sender instanceof Player)) {
            completator(args, list, false);
        } else if (sender.isOp()) {
            completator(args, list, true);
        }

        return list;
    }

    private void completator (String[] args, List<String> list, boolean isplayer){
        if (args.length == 1){
            list.add("add_player");
            list.add("create");
            list.add("delete");
            list.add("help");
        } else if (args.length == 2) {

            if (args[0].equals("create") && isplayer){

                list.add("<Nombre del grupo>");

            } // TODO: hacer un else if para  delete que muestre el listado de grupos actuales y el de ayuda
        } else if (args.length == 3) {

            if (args[0].equals("create")){ // TODO: crear verificador que advierta si el grupo ya existe

                for (Player p: plugin.getServer().getOnlinePlayers()){

                    list.add(p.getName());

                }

            }
        } else if (args.length == 4) {

            if (args[0].equals("create")){

                list.add("Red");
                list.add("Blue");
                // for (Color c: Color.class.getEnumConstants()){

                //    list.add(c.toString());

                // }

            }

        }

    }

}
