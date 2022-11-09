package co.com.okaeri.funkyuhc.database;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import co.com.okaeri.funkyuhc.database.Database; // import the database class.
import co.com.okaeri.funkyuhc.FunkyUHC; // import your main class


public class SQLite extends Database{
    String dbname;
    public SQLite(FunkyUHC main){
        super(main);
        dbname = plugin.getName(); // Set the table name here e.g player_kills
    }

    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS players (" + // make sure to put your table name in here too.
            "`player` TEXT NOT NULL," + // This creates the different colums you will save data too. varchar(32) Is a string, int = integer
            "`name` TEXT NOT NULL," +
            "`total` INTEGER NOT NULL," +
            "PRIMARY KEY (`player`)" +  // This is creating 3 colums Player, Kills, Total. Primary key is what you are going to use as your indexer. Here we want to use player so
            ");"; // we can search by player, and get kills and total. If you some how were searching kills it would provide total and player.


    public String CreateEquips = "CREATE TABLE IF NOT EXISTS equips (" +
            "'id' INTEGER NOT NULL UNIQUE," +
            "'name' TEXT NOT NULL UNIQUE," +
            "'color' TEXT NOT NULL UNIQUE," +
            "'capitan' TEXT NOT NULL UNIQUE," +
            "'players' TEXT UNIQUE," +
            "PRIMARY KEY('id')" +
            ");";

    public String CreateMapSizes = "CREATE TABLE IF NOT EXISTS mapSizes(" +
            "'id' TEXT NOT NULL UNIQUE," +
            "'size' INTEGER NOT NULL," +
            "PRIMARY KEY('id'));";

    public void updateSize(int size){
        try {
            Statement s = connection.createStatement();
            String sql = "UPDATE 'main'.'mapSizes' SET 'size'="+ size + " WHERE id = 'size'";
            s.executeUpdate(sql);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // SQL creation stuff, You can leave the blow stuff untouched.
    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        if (!dataFolder.exists()){
            try {
                //noinspection ResultOfMethodCallIgnored
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
            }
        }
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
            // TODO: Arreglar error de que la subcarpeta FunkyoUHC no existe dentro de la carpeta plugins

        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTokensTable);
            s.executeUpdate(CreateEquips);
            s.executeUpdate(CreateMapSizes);
            s.executeUpdate("insert or ignore into mapSizes values('size'," + plugin.size + ");");
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }

    public Statement statement(){

        try {
            connection = getSQLConnection();
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<List<String>> getTeams(){
        connection = getSQLConnection();
        List<List<String>> teams = new ArrayList<>();
        List<String> busy = new ArrayList<>();

        try {
            Statement s = connection.createStatement();

            ResultSet len = s.executeQuery("SELECT COUNT(*) AS total FROM \"main\".\"equips\"");
            len.next();
            if (len.getInt("total") == 0){
                return null;
            }

            ResultSet r = s.executeQuery("SELECT * FROM \"main\".\"equips\"");
            while (r.next()){
                List<String> team = new ArrayList<>();

                team.add(r.getString("id"));
                busy.add(r.getString("id"));

                team.add(r.getString("name"));

                team.add(r.getString("color"));

                team.add(r.getString("capitan"));

                team.add(r.getString("players"));

                teams.add(team);
            }

            teams.add(busy);

            return teams;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
