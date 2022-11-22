package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings("FieldMayBeFinal")
public class Teams implements CommandExecutor {

    private FunkyUHC plugin;
    private co.com.okaeri.funkyuhc.database.Teams db;

    public Teams(@NotNull FunkyUHC plugin) {
        this.plugin = plugin;
        db = plugin.TeamDB;
    }


    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             String @NotNull [] args) {

        // TODO: mejorar el manejo de argumentos
        // FIXME: arreglar el que no se puedan colocar nombres de equipo con espacios

        switch (args[0].toLowerCase()) {
            case "create":
                if (args.length == 4) {
                    db.Create(args[1], args[2], args[3]);
                    return true;
                }
                return false;
            case "add_player":
                if (args.length == 3) {
                    db.addPlayer(args[1], args[2], sender);
                    return true;
                }
                return false;
            case "remove_player":
                if ((args.length == 3) && !(sender instanceof Player)) {
                    db.removePlayer(args[1], args[2]);
                    return true;
                } else if (args.length == 2) {
                    db.removePlayer(args[1], sender);
                }
                return false;
            case "rename":
                if ((args.length == 3) && !(sender instanceof Player)) {
                    db.renameTeam(args[1], args[2]);
                    return true;
                } else if (args.length == 2) {
                    db.renameTeam(args[1], (Player) sender);
                }
                return false;
            case "color":
                // FIXME: guarda los colores como el nombre en vez del código
                if ((args.length == 3) && !(sender instanceof Player)) {
                    db.changeColor(args[1], args[2]);
                    return true;
                } else if (args.length == 2) {
                    db.changeColor(args[1], (Player) sender);
                }
                return false;
            case "delete":
                // TODO: verificador de que se tenga un argumento y que el argumento este en la lista de equipos de la base de datos
                plugin.print("delete");
                db.Delete(args[1]);
                return true;
            default:
                return false;

        }
    }

}
