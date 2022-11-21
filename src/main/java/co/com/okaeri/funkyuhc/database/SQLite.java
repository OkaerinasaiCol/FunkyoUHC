package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class SQLite extends Database {
    String dbname;

    /**
     * Clase encargada de todo_ el manejo sobre la base de datos Sql
     *
     * @param main: Clase principal del plugin {@link FunkyUHC}
     */
    public SQLite(FunkyUHC main) {
        super(main);
        dbname = plugin.getName(); // Set the table name here e.g player_kills
    }

    /**
     * Sql query para crear la tabla de jugadores en la base de datos
     */
    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS players (" + // make sure to put your table name in here too.
            "`player` TEXT NOT NULL UNIQUE," + // This creates the different colums you will save data too. varchar(32) Is a string, int = integer
            "`uuid` TEXT NOT NULL UNIQUE," +
            "`kills` INTEGER NOT NULL," +
            "`death` INTEGER NOT NULL," +
            "`team` TEXT," +
            "PRIMARY KEY (`player`)" +  // This is creating 3 colums Player, Kills, Total. Primary key is what you are going to use as your indexer. Here we want to use player so
            ");"; // we can search by player, and get kills and total. If you some how were searching kills it would provide total and player.

    /**
     * Sql Query para crear la tabla de los equipos
     */
    public String CreateEquips = "CREATE TABLE IF NOT EXISTS equips (" +
            "'id' INTEGER NOT NULL UNIQUE," +
            "'name' TEXT NOT NULL UNIQUE," +
            "'color' TEXT NOT NULL UNIQUE," +
            "'capitan' TEXT NOT NULL UNIQUE," +
            "'kills' INTEGER NOT NULL," +
            "'players' TEXT," +
            "PRIMARY KEY('id')" +
            ");";

    /**
     * Sql Query para crear la tabla de los tamaños del mapa
     */
    public String CreateMapSizes = "CREATE TABLE IF NOT EXISTS mapSizes(" +
            "'id' TEXT NOT NULL UNIQUE," +
            "'size' INTEGER NOT NULL," +
            "PRIMARY KEY('id'));";

    /**
     * Sql Query para crear la tabla de las cabezas
     */
    public String CreateHeads = "CREATE TABLE IF NOT EXISTS heads(" +
            "'owner' TEXT NOT NULL UNIQUE," +
            "'uuid' TEXT NOT NULL UNIQUE," +
            "'world' TEXT NOT NULL," +
            "'coords' TEXT NOT NULL," +
            "'placer' TEXT NOT NULL," +
            " 'lore' TEXT NOT NULL UNIQUE," +
            "PRIMARY KEY('owner'));";

    /**
     * Sql Query para crear la tabla que almacenará el estado del uhc
     */
    public String UhcRoundTry = "CREATE TABLE IF NOT EXISTS uhcRound(" +
            "'round' INTEGER NOT NULL UNIQUE," +
            "'rStarted' INTEGER NOT NULL," +
            "'wbBefore' INTEGER NOT NULL," +
            "'wbStart' INTEGER NOT NULL," +
            "'border' REAL NOT NULL," +
            "'newSize' REAL NOT NULL," +
            "'rRest' REAL NOT NULL," +
            "'running' INTEGER NOT NULL," +
            "PRIMARY KEY('round'));";

    /**
     * Funcion para actualizar el tamaño del mapa en la base de datos con base en el argumento proporcionado
     *
     * @param size: Valor a establecer
     */
    @SuppressWarnings("unused")
    public void updateSize(int size) {
        try {
            Statement s = connection.createStatement();
            String sql = "UPDATE 'main'.'mapSizes' SET 'size'=" + size + " WHERE id = 'size'";
            s.executeUpdate(sql);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Función para obtener la conexion a la base de datos
     *
     * @return Conexión de tipo {@link Connection}
     */
    public Connection getSQLConnection() {
        File dataFolder = new File(plugin.getDataFolder(), dbname + ".db");
        if (!dataFolder.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: " + dbname + ".db");
            }
        }
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
            // TODO: Arreglar error de que la subcarpeta FunkyoUHC no existe dentro de la carpeta plugins

        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    /**
     * Cargar base de datos
     */
    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTokensTable);
            s.executeUpdate(CreateEquips);
            s.executeUpdate(CreateMapSizes);
            s.executeUpdate(CreateHeads);
            s.executeUpdate(UhcRoundTry);
            s.executeUpdate("insert or ignore into mapSizes values('size'," + plugin.size + ");");
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }

    /**
     * Obtener el statment para hacer operaciones sobre la base de datos
     *
     * @return - {@link Statement} object si la conexión fue un exito <p>
     * - {@code null} en caso de que haya algún error
     */
    public Statement statement() {

        try {
            connection = getSQLConnection();
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return {@link List}[{@link List}[{@link String}]]
     * @deprecated Obtener una lista de los equipos creados con el siguiente formato:
     * {@link List} que contiene una {@link List} de {@link String} <p>
     * List< List < String > >
     * donde la última lista hace referencia a los ids de equipos ya usados.
     * <p></p>
     * Esta función ya está desactualizada y se recomienda el uso de la función getTeams de la clase
     * {@link Teams} mientras sea posible.
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    public List<List<String>> getTeams() {
        connection = getSQLConnection();
        List<List<String>> teams = new ArrayList<>();
        List<String> busy = new ArrayList<>();

        try {
            Statement s = connection.createStatement();

            ResultSet len = s.executeQuery("SELECT COUNT(*) AS total FROM \"main\".\"equips\"");
            len.next();
            if (len.getInt("total") == 0) {
                return null;
            }

            ResultSet r = s.executeQuery("SELECT * FROM \"main\".\"equips\"");
            while (r.next()) {
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
