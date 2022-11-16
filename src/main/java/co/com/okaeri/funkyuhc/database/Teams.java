package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Teams {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public Teams(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    //TODO: agregar funciones para editar nombre, cambiar color y modificacion compañeros de equipo

    /**
     * Crear equipo para el UHc
     *
     * @param name: {@link String} Nombre del equipo a crear, este nombre debe de no existir ya, en caso de que exista la función
     *                 retornará false
     * @param capitan: {@link String} El nombre del capitan del equipo
     * @param color: {@link String} Color a usar por el equipo, este debe de no estar ya en uso o el equipo no podrá crearse
     */
    public void Create(String name, String capitan, String color) {
        // TODO: verificar que el color no esté en uso ya
        // Obtener lista de equipos existentes
        List<List<String>> teams = plugin.teams;
        int row = 1;

        for (int x = 1; x < 100; x++) {
            if (teams == null) {
                break;
            } else {
                List<String> team = teams.get(teams.size() - 1);
                plugin.print("Rows used " + team);
                if (!team.contains(Integer.toString(x))) {
                    row = x;
                    break;
                }
            }
        }

        if (teams != null) {
            for (List<String> team : teams) {
                if (team.contains(name)) {
                    plugin.print("El equipo " + name + " ya existe");
                    return;
                }

                if (team.contains(capitan)) {
                    plugin.print("Solo se puede ser capitán de un equipo a la vez");
                    return;
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
            //noinspection deprecation
            plugin.teams = plugin.db.getTeams();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Eliminar un equipo del UHC
     * @param name: {@link String} nombre del equipo a crear
     */
    public void Delete(String name) {

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
                        //noinspection deprecation
                        plugin.teams = plugin.db.getTeams();

                        break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Añadir un jugador a un equipo
     * @param team {@link String} Equipo en el cual se desea agregar al jugador
     * @param player {@link String} Jugador que se desea agregar al equipo, este debe de estar conectado
     * @param sender {@link CommandSender} Entidad que genera el comando
     */
    public void addPlayer(String team, String player, CommandSender sender) {

        try {

            Statement statment = plugin.db.statement();

            // verificar que el jugador no tenga un equipo
            ResultSet data = statment.executeQuery("SELECT * FROM players WHERE player =\"" + player + "\";");

            try {
                String player_team = data.getString("team");

                if (!(player_team.equals(""))) {

                    if (!(sender instanceof Player)) {
                        plugin.print("Solo se puede pertenecer a un equipo a la vez, " + player + " ya pertenece al " +
                                "equipo " + player_team);
                    } else {
                        Objects.requireNonNull(((Player) sender).getPlayer()).sendMessage("Solo se puede pertenecer a " +
                                "un equipo a la vez, " + player + " ya pertenece al " +
                                "equipo " + player_team);
                    }

                    return;
                }
            } catch (SQLException e) {

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

            if (!(players.equals(""))) {
                String[] arr = players.split(",");

                ArrayList<String> team_players = new ArrayList<>(Arrays.asList(arr));
                if (!(team_players.contains(player))) {
                    team_players.add(player);
                }

                String sql = "UPDATE equips SET players = ? WHERE name = ?";
                PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
                pstmt.setString(1, team_players.toString().replace("[", "").replace("]", ""));
                pstmt.setString(2, team);

                pstmt.executeUpdate();

            } else {
                ArrayList<String> players_new = new ArrayList<>();
                players_new.add(player);

                String sql = "UPDATE equips SET players = ? WHERE name = ?";
                PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
                pstmt.setString(1, players_new.toString().replace("[", "").replace("]", ""));
                pstmt.setString(2, team);

                pstmt.executeUpdate();
                // statment.executeUpdate("UPDATE 'main'.'equips' SET 'players'='"+ players_new.toString() + "' WHERE name = '" + team + "'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remover un jugador de un equipo con dos argumentos, este es para cuando se ejecuta desde la consola
     * TODO: agregar que este se pueda correr por jugador mientras tenga los permisos (Opcional)
     * @param team {@link String} Equipo del que se quiere substraer al jugador
     * @param player {@link String} Jugador a eliminar del equipo
     */
    public void removePlayer(String team, String player) {
        try {
            Statement statment = plugin.db.statement();
            ResultSet equips = statment.executeQuery("SELECT * FROM equips WHERE name =\"" + team + "\";");

            String players = equips.getString("players");

            String[] arr = players.split(",");

            ArrayList<String> team_players = new ArrayList<>(Arrays.asList(arr));
            team_players.remove(player);

            String sql = "UPDATE equips SET players = ? WHERE name = ?";
            PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);
            pstmt.setString(1, team_players.toString().replace("[", "").replace("]", ""));
            pstmt.setString(2, team);

            pstmt.executeUpdate();

            String sql_player = "UPDATE player SET teams = ? WHERE player = ?";
            PreparedStatement pstmt_player = plugin.db.connection.prepareStatement(sql_player);
            pstmt_player.setString(1, player);
            pstmt_player.setString(2, "");

            pstmt.executeUpdate();

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Remover un jugador de un equipo con dos argumentos, este es para cuando se ejecuta desde un comando en
     * el juego
     * @param player {@link String} Jugador al que se desea agregar al equipo de la persona que envía el comando
     * @param sender {@link String} Entidad que ejecuta el comando
     */
    public void removePlayer(String player, @NotNull CommandSender sender) {
        removePlayer(getTeam(sender.getName()), player);
    }

    /**
     * Obtener el equipo del usuario proporcionado como argumento
     * @param user: {@link String} Nombre del jugador
     * @return {@link String} Nombre del equipo al que pertenece el jugador
     */
    public String getTeam(String user) {

        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT * FROM players WHERE player =\"" +
                    user + "\";");

            return data.getString("team");
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Obtener color del team del jugador
     * @param team: {@link String} Nombre del jugador
     * @return String Color del equipo
     */
    public String getTeamColor(String team) {

        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT * FROM equips WHERE name =\"" +
                    team + "\";");

            return data.getString("color");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtener capitan del team del jugador
     * @param team: {@link String} Nombre del equipo
     * @return String capitan del equipo del jugador
     */
    public String getTeamCapitan(String team) {

        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT * FROM equips WHERE name =\"" +
                    team + "\";");

            return data.getString("capitan");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtener jugadores del equipo
     * @param team: {@link String} Nombre del equipo
     * @return {@link ArrayList}[{@link String}] Arraylist de nombres de los integrantes del equipo
     */
    public ArrayList<String> getTeamPlayers(String team) {
        try {
            Statement statment = plugin.db.statement();
            ResultSet equips = statment.executeQuery("SELECT * FROM equips WHERE name =\"" + team + "\";");

            String players = equips.getString("players");

            String[] arr = players.split(",");

            return new ArrayList<>(Arrays.asList(arr));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtener lista de todos los capitanes de equipos
     * @return {@link ArrayList}[{@link String}] Arraylist de nombres de los capitanes de equipos
     */
    @SuppressWarnings("unused")
    public ArrayList<String> getCapitans() {
        // TODO: verificar si funciona, si no se puede copiar de getTeams
        try {
            Statement statment = plugin.db.statement();
            ResultSet equips = statment.executeQuery("SELECT capitan FROM equips;");

            plugin.print(equips + " capitanes");
            ArrayList<String> arr = new ArrayList<>();
            arr.add(equips.toString());

            return arr;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtener cantidad de kills del jugador dado como parametro
     * @param player: {@link String} Nombre del jugador
     * @return int Número de kills del jugador
     */
    public int getKills(String player) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM players WHERE player =\"" +
                    player + "\";").getInt("kills");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Obtener cantidad de kills del equipo dado como parametro
     * @param team: {@link String} Nombre del equipo
     * @return int Número de kills del equipo
     */
    public int getTeamKills(String team) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM equips WHERE name =\"" +
                    team + "\";").getInt("kills");

        } catch (SQLException ignored) {
        }

        return 0;
    }

    /**
     * Establecer cantidad de kills a un jugador
     * @param kills: int Cantidad de kills a establecer
     * @param player: {@link String} Jugador al que se debe establecer las kills
     */
    public void setKills(int kills, String player) {
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

    /**
     * Obtener de la base de datos si el jugador está muerto o no
     * @param player: {@link String} Nombre del jugador
     * @return boolean Estado del jugador
     */
    @SuppressWarnings("unused")
    public boolean getDeath(String player) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM players WHERE player =\"" +
                    player + "\";").getBoolean("death");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Establecer estado de muerte del jugador
     * @param player: {@link String} Nombre del jugador
     * @param death: boolean Estado del jugador
     */
    public void setDeath(String player, @NotNull Boolean death) {
        try {
            String sql = "UPDATE players SET kills = ? WHERE player = ?";
            PreparedStatement pstmt = plugin.db.connection.prepareStatement(sql);

            if (death) {
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

    /**
     * Establecer cantidad de kills a un equipo
     * @param kills: int Cantidad de kills a establecer
     * @param team: {@link String} Equipo al que se debe establecer las kills
     */
    public void setTeamKills(int kills, String team) {
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

    /**
     * Agregar kill al jugador
     * @param killer: {@link String} Nombre del jugador que hizo la kill
     */
    public void addKill(String killer) {
        if (plugin.UhcStarted) {
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

            for (String p : teamPlayers) {
                plugin.manager.UpdateKills(p);
            }
        }
    }

    /**
     * Obtener listado de las kills
     * @return {@link List}[{@link String}] Listado de nombres de los equipos
     */
    public List<String> getTeams() {
        List<String> teams = new ArrayList<>();
        try {
            String sql = "SELECT name FROM equips";
            ResultSet rs = plugin.db.connection.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                teams.add(rs.getString("name"));
            }
            return teams;
        } catch (SQLException e) {
            return teams;
        }
    }

    /**
     * Obtener listado de los colores en uso
     * @return {@link List}[{@link String}] Listado los colores en uso
     */
    public List<String> getColors() {
        List<String> color = new ArrayList<>();
        try {
            String sql = "SELECT color FROM equips";
            ResultSet rs = plugin.db.connection.prepareStatement(sql).executeQuery();
            while (rs.next()) {
                color.add(rs.getString("color"));
            }
            return color;
        } catch (SQLException e) {
            return color;
        }
    }

    /**
     * Renombrar equipo desde la consola
     * @param old: {@link String} Nombre del equipo
     * @param new_: {@link String} Nuevo nombre del equipo
     */
    public void renameTeam(String old, String new_) {
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

    /**
     * Renombrar equipo desde comando in-game
     * @param new_: {@link String} Nombre del equipo
     * @param sender: {@link Player} Jugador que ejecuta el comando
     */
    public void renameTeam(String new_, Player sender) {
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

    /**
     * Cambiar color del equipo desde la consola
     * @param team: {@link String} Nombre del equipo
     * @param color: {@link String} Color del equipo que se desea colocar
     */
    public void changeColor(String team, String color) {
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

    /**
     * Cambiar color del equipo desde comando in-game
     * @param color: {@link String} Color del equipo que se desea colocar
     * @param sender: {@link Player} Jugador que ejecuta el comando
     */
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
