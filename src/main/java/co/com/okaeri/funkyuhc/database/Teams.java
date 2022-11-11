package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Teams {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public Teams(FunkyUHC plugin){
        this.plugin = plugin;
    }

    //TODO: agregar funciones para editar nombre, cambiar color, creacion y modificacion compañeros de equipo

    @SuppressWarnings("UnusedReturnValue")
    public boolean Create(String name, String capitan, String color) {

        // Obtener lista de equipos existentes
        List<List<String>> teams = plugin.teams;
        int row = 1;

        for (int x = 1; x < 100; x++){
            if (teams == null){
                break;
            } else {
                List<String> team = teams.get(teams.size() - 1);
                plugin.print("Rows used " + team);
                if (!team.contains(Integer.toString(x))){
                    row = x;
                    break;
                    }
                }
            }

        if (teams != null) {
            for (List<String> team : teams){
                if (team.contains(name)){
                    plugin.print("El equipo " + name + " ya existe");
                    return false;
                }

                if (team.contains(capitan)){
                    plugin.print("Solo se puede ser capitán de un equipo a la vez");
                    return false;
                }
            }
        }

        try {
            Statement statment = plugin.db.statement();

            statment.executeUpdate("INSERT INTO 'main'.'equips'(" +
                    "'id','name','color','capitan','players') VALUES " +
                    "(" + row + ",'" + name + "','" + color + "','" + capitan +
                    "','');");

            //regenerar la lista de equipos
            plugin.teams = plugin.db.getTeams();

            return true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean Delete(String name) {

        // Obtener lista de equipos existentes
        List<List<String>> teams = plugin.teams;

        if (teams != null) {
            for (List<String> team : teams) {
                if (team.contains(name)) {
                    try {
                        plugin.print("Borrando el equipo: " + name);

                        Statement statment = plugin.db.statement();

                        statment.executeUpdate("DELETE FROM 'main'.'equips' WHERE name IN ('" + name + "');");

                        plugin.print("Equipo borrado con exito" + name);

                        //regenerar la lista de equipos
                        plugin.teams = plugin.db.getTeams();

                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
