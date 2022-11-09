package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mapSize implements CommandExecutor{

    private FunkyUHC plugin;

    public mapSize(FunkyUHC main){
        this.plugin = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            if (args.length == 0) {
                size();
                return true;
            } else if (args.length == 2) { // Todo: verificar que el argumento [1] sea un entero
                switch (args[0].toLowerCase()) {
                    case "setmax":
                        setMax(Integer.parseInt(args[1]));
                        return true;
                    case "set":
                        setSize(Integer.parseInt(args[1]));
                        return true;
                    default:
                        return false;
                }

            } else {
                return false;
            }
        }else {
            if (sender.isOp()){
                if (args.length == 0) {
                    size();
                    return true;
                } else if (args.length == 2) { // Todo: verificar que el argumento [1] sea un entero
                    switch (args[0].toLowerCase()) {
                        case "setmax":
                            setMax(Integer.parseInt(args[1]));
                            return true;
                        case "set":
                            setSize(Integer.parseInt(args[1]));
                            return true;
                        default:
                            return false;
                    }

                } else {
                    return false;
                }
            }
            return false;
        }
    }

    private void setMax(int value){
        plugin.changeMaxSize(value);
        plugin.print("Tamaño maximo del mapa cambiado a: " + value);
    }

    public void setSize(int value){
        // Todo: hacer que se vaya achicando poco a poco
        plugin.changeSize(value);
        plugin.db.updateSize(value);
        plugin.print("Tamaño del mapa cambiado a: " + value);
    }

    private int size(){
        plugin.print(String.valueOf(plugin.size));
        return plugin.size;
    }
}
