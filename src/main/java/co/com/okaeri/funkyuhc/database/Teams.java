package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class Teams {

    private FunkyUHC plugin;

    public Teams(FunkyUHC plugin){
        this.plugin = plugin;
    }

    public void Create(String name, String capitan, String color) throws SQLException {
        List<List<String>> teams = plugin.teams;
        int row = 1;

        for (int x = 1; x < 100; x++){
            if (teams == null){
                break;
            } else {
                List<String> team = teams.get(teams.size() - 1);
                plugin.print("ids ocupados: " + team);
                if (!team.contains(Integer.toString(x))){
                    plugin.print("El número " + x + " ya está ocupado " + !team.contains(Integer.toString(x)));
                    row = x;
                    break;
                    }
                }

            }

        plugin.print("Creating row: " + row +
                "\nName: " + name +
                "\nCapitan: " + name +
                "\nColor: " + color);

        }

}
