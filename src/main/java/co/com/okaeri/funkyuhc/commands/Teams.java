package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;


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
        switch (args[0].toLowerCase()) {
            case "create":
                if (args.length == 4) {
                    db.Create(args[1], args[2], args[3]);
                    return true;
                }
                return false;
            case "delete":
                plugin.print("delete");
                return false;
            default:
                return false;

        }
    }

}
