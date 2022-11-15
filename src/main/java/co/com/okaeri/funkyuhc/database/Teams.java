package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Teams {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public Teams(FunkyUHC plugin){
        this.plugin = plugin;
    }

    //TODO: agregar funciones para editar nombre, cambiar color y modificacion compañeros de equipo

    /**
     * Crear equipo para el uhc
     *
     * @param name: Nombre del equipo a crear, este nombre debe de no existir ya, en caso de que exista la función
     *            retornará false
     * @param capitan: El nombre del capitan del equipo
     * @param color: Color a usar por el equipo, este debe de no estar ya en uso o el equipo no podrá crearse
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean Create(String name, String capitan, String color) {
        // TODO: verificar que el color no esté en uso ya
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
                    "'id','name','color','capitan','kills','players') VALUES " +
                    "(" + row + ",'" + name + "','" + plugin.colors.colors.get(color) + "','" + capitan + "','" + 0 +
                    "','');");

            /* "CREATE TABLE IF NOT EXIST players(" +
            *        "'player' TEXT NOT NULL UNIQUE," +
            *        "'uuid' TEXT NOT NULL UNIQUE," +
            *        "'kills' INTEGER NOT NULL," +
            *        "'death' INTEGER NOT NULL," +
            *        "'team' TEXT NOT NULL," +
            *        "PRIMARY KEY('player'));"
            */

            //noinspection ConstantConditions
            statment.executeUpdate("INSERT INTO 'main'.'players'(" +
                    "'player','uuid','kills','death','team') VALUES " +
                    "('" + capitan + "','" +
                    plugin.getServer().getPlayer(capitan).getUniqueId() + "','" +
                    0 + "','" +
                    0 + "','" +
                    name + "');");

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

    @SuppressWarnings("UnusedReturnValue")
    public boolean addPlayer(String team, String player, CommandSender sender){

        try {

            Statement statment = plugin.db.statement();

            // verificar que el jugador no tenga un equipo
            ResultSet data = statment.executeQuery("SELECT * FROM players WHERE player =\"" + player + "\";");

            try {
                String player_team = data.getString("team");

                if (!(player_team.equals(""))){

                    if (!(sender instanceof Player)){
                        plugin.print("Solo se puede pertenecer a un equipo a la vez, " + player + " ya pertenece al " +
                                "equipo " + player_team);
                    } else {
                        Objects.requireNonNull(((Player) sender).getPlayer()).sendMessage("Solo se puede pertenecer a " +
                                "un equipo a la vez, " + player + " ya pertenece al " +
                                "equipo " + player_team);
                    }

                    return false;
                }
            } catch (SQLException e){

                //noinspection ConstantConditions
                statment.executeUpdate("INSERT INTO 'main'.'players'(" +
                    "'player','uuid','kills','death','team') VALUES " +
                    "('" + player + "','" +
                    plugin.getServer().getPlayer(player).getUniqueId() + "','" +
                    0 + "','" +
                    0 + "','" +
                    team + "');");
            }

            ResultSet equips = statment.executeQuery("SELECT * FROM equips WHERE name =\"" + team + "\";");

            // TODO: agregar verificador para que solo se ejecute en un equipo existente

            String players = equips.getString("players");

            plugin.print(" " + !(players.equals("")));

            if (!(players.equals(""))){
                String[] arr = players.split(",");

                ArrayList<String> team_players = new ArrayList<>(Arrays.asList(arr));
                if (!(team_players.contains(player))){
                    team_players.add(player);
                }

                String sql = "UPDATE equips SET players = ? WHERE name = ?";
                PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
                pstmt.setString(1, team_players.toString().replace("[", "").replace("]",""));
                pstmt.setString(2, team);

                pstmt.executeUpdate();

            } else {
                ArrayList<String> players_new = new ArrayList<>();
                players_new.add(player);

                String sql = "UPDATE equips SET players = ? WHERE name = ?";
                PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
                pstmt.setString(1, players_new.toString().replace("[", "").replace("]",""));
                pstmt.setString(2, team);

                pstmt.executeUpdate();
                // statment.executeUpdate("UPDATE 'main'.'equips' SET 'players'='"+ players_new.toString() + "' WHERE name = '" + team + "'");
            }


            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void removePlayer(String team, String player){
        try {
            Statement statment = plugin.db.statement();
            ResultSet equips = statment.executeQuery("SELECT * FROM equips WHERE name =\"" + team + "\";");

            String players = equips.getString("players");

            String[] arr = players.split(",");

            ArrayList<String> team_players = new ArrayList<>(Arrays.asList(arr));
            team_players.remove(player);

            String sql = "UPDATE equips SET players = ? WHERE name = ?";
            PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
            pstmt.setString(1, team_players.toString().replace("[", "").replace("]",""));
            pstmt.setString(2, team);

            pstmt.executeUpdate();

            String sql_player = "UPDATE player SET teams = ? WHERE player = ?";
            PreparedStatement pstmt_player = plugin.db.connection.prepareStatement(sql_player);
            pstmt_player.setString(1, player);
            pstmt_player.setString(2, "");

            pstmt.executeUpdate();

            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void removePlayer(String player, CommandSender sender){
        removePlayer(getTeam(sender.getName()), player);
    }

    public String getTeam(String user){

        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT * FROM players WHERE player =\"" +
                    user + "\";");

            return data.getString("team");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public String getTeamColor(String team){

        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT * FROM equips WHERE name =\"" +
                    team + "\";");

            return data.getString("color");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public String getTeamCapitan(String team){

        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT * FROM equips WHERE name =\"" +
                    team + "\";");

            return data.getString("capitan");
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String > getTeamPlayers(String team){
        try {
            Statement statment = plugin.db.statement();
            ResultSet equips = statment.executeQuery("SELECT * FROM equips WHERE name =\"" + team + "\";");

            String players = equips.getString("players");

            String[] arr = players.split(",");

            return new ArrayList<>(Arrays.asList(arr));

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unused")
    public ArrayList<String> getCapitans(){
        // TODO: verificar si funciona, si no se puede copiar de getTeams
        try {
            Statement statment = plugin.db.statement();
            ResultSet equips = statment.executeQuery("SELECT capitan FROM equips;");

            plugin.print(equips + " capitanes");
            ArrayList<String> arr = new ArrayList<>();
            arr.add(equips.toString());

            return arr;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public int getKills(String player){
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM players WHERE player =\"" +
                    player + "\";").getInt("kills");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTeamKills(String team){
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM equips WHERE name =\"" +
                    team + "\";").getInt("kills");

        } catch (SQLException ignored) {}

        return 0;
    }

    public void setKills(int kills, String player){
        try {
            String sql = "UPDATE players SET kills = ? WHERE player = ?";
            PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
            pstmt.setInt(1, kills);
            pstmt.setString(2, player);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public boolean getDeath(String player){
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM players WHERE player =\"" +
                    player + "\";").getBoolean("death");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setDeath(String player, Boolean death){
        try {
            String sql = "UPDATE players SET kills = ? WHERE player = ?";
            PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);

            if (death){
                pstmt.setInt(1, 0);
            } else {
                pstmt.setInt(1, 1);
            }

            pstmt.setString(2, player);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTeamKills(int kills, String team){
        try {
            String sql = "UPDATE equips SET kills = ? WHERE name = ?";
            PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
            pstmt.setInt(1, kills);
            pstmt.setString(2, team);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ReassignedVariable")
    public void addKill(String killer){
        if (plugin.UhcStarted){
            //noinspection unused
            Statement statment = plugin.db.statement();

            int kills = getKills(killer);

            kills++;

            setKills(kills, killer);

            String killer_team = this.getTeam(killer);

            String capitan = getTeamCapitan(killer_team);

            int kills_team = getTeamKills(killer_team);

            kills_team++;

            setTeamKills(kills_team, killer_team);

            ArrayList<String> teamPlayers = this.getTeamPlayers(killer_team);

            plugin.manager.UpdateKills(capitan);

            for (String p: teamPlayers){
                plugin.manager.UpdateKills(p);
            }
        }
    }

    public List<String> getTeams(){
        List<String> teams = new ArrayList<>();
        try {
            String sql = "SELECT name FROM equips";
            ResultSet rs = plugin.db.connection.prepareStatement(sql).executeQuery();
            while (rs.next()){
                teams.add(rs.getString("name"));
            }
            return teams;
        } catch (SQLException e){
            return teams;
        }
    }

    public List<String> getColors(){
        List<String> color = new ArrayList<>();
        try {
            String sql = "SELECT color FROM equips";
            ResultSet rs = plugin.db.connection.prepareStatement(sql).executeQuery();
            while (rs.next()){
                color.add(rs.getString("color"));
            }
            return color;
        } catch (SQLException e){
            return color;
        }
    }

    public void renameTeam(String old, String new_){
        try {
            List<String> teams = getTeams();

            if (!teams.contains(new_)) {
                String capitan = getTeamCapitan(old);
                String sql = "UPDATE equips SET name = ? WHERE capitan = ?";
                PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
                pstmt.setString(1, new_);
                pstmt.setString(2, capitan);

                pstmt.executeUpdate();
            } else {
                plugin.print("El nombre del equipo ya existe, por favor intente con otro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void renameTeam(String new_, Player sender){
        try {
            List<String> teams = getTeams();

            if (!teams.contains(new_)) {
                if (getTeamCapitan(getTeam(sender.getName())).equals(sender.getName())) {
                    String capitan = getTeamCapitan(getTeam(sender.getName()));
                    String sql = "UPDATE equips SET name = ? WHERE capitan = ?";
                    PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
                    pstmt.setString(1, new_);
                    pstmt.setString(2, capitan);

                    pstmt.executeUpdate();
                } else {
                    sender.sendMessage("Solo");
                }
            } else {
                plugin.print("El nombre del equipo ya existe, por favor intente con otro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeColor(String team, String color){
        try {
            List<String> colors = getColors();

            if (!colors.contains(color)) {
                String sql = "UPDATE equips SET color = ? WHERE name = ?";
                PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
                pstmt.setString(1, color);
                pstmt.setString(2, team);

                pstmt.executeUpdate();
            } else {
                plugin.print("El color seleccionado no está disponible");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeColor(String color, Player sender) {
        try {
            List<String> colors = getColors();

            if (!colors.contains(color)) {
                String sql = "UPDATE equips SET color = ? WHERE name = ?";
                PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
                pstmt.setString(1, color);
                pstmt.setString(2, getTeam(sender.getName()));

                pstmt.executeUpdate();
            } else {
                plugin.print("El color seleccionado no está disponible");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // TODO: agregar función para reiniciar todos los contadores

}
