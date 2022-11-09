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
        db = new co.com.okaeri.funkyuhc.database.Teams(plugin);
    }


    @SuppressWarnings("NullableProblems")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        // TODO: mejorar el manejo de argumentos
        switch (args[0].toLowerCase()) {
            case "create":
                if (args.length == 4) {
                    try {
                        db.Create(args[1], args[2], args[3]);
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
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
