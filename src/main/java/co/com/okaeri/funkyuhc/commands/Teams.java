package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


@SuppressWarnings("FieldMayBeFinal")
public class Teams implements CommandExecutor {

    private FunkyUHC plugin;
    private co.com.okaeri.funkyuhc.database.Teams db;

    public Teams(FunkyUHC plugin){
        this.plugin = plugin;
        db = plugin.TeamDB;
    }


    @SuppressWarnings("NullableProblems")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        // TODO: mejorar el manejo de argumentos
        // FIXME: arreglar el que no se puedan colocar nombres de equipo con espacios

        switch (args[0].toLowerCase()) {
            case "create":
                if (args.length == 4) {
                    db.Create(args[1], args[2], args[3]);
                    return true;
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
