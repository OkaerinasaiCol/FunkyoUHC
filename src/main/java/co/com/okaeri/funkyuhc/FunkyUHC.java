package co.com.okaeri.funkyuhc;

import co.com.okaeri.funkyuhc.commands.Regeneration;
import co.com.okaeri.funkyuhc.commands.*;
import co.com.okaeri.funkyuhc.commands.TabCompleter.mapSizeTab;
import co.com.okaeri.funkyuhc.commands.TabCompleter.teamsTab;
import co.com.okaeri.funkyuhc.controller.GetTime;
import co.com.okaeri.funkyuhc.database.Database;
import co.com.okaeri.funkyuhc.database.Heads;
import co.com.okaeri.funkyuhc.database.SQLite;
import co.com.okaeri.funkyuhc.items.ItemManager;
import co.com.okaeri.funkyuhc.player.*;
import co.com.okaeri.funkyuhc.util.Colors;
import co.com.okaeri.funkyuhc.util.Tittle;
import fr.mrmicky.fastboard.FastBoard;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FunkyUHC extends JavaPlugin {

    PluginDescriptionFile descriptionFile = getDescription();

    public String pluginName = descriptionFile.getName();
    public String pluginVersion = descriptionFile.getVersion();
    public String apiVersion = descriptionFile.getAPIVersion();
    public String creatorWebsite = descriptionFile.getWebsite();
    public String apiDesc = descriptionFile.getDescription();
    public SQLite db;
    public BossBar timeBar = Bukkit.createBossBar("Tiempo hasta la ronda #", BarColor.BLUE, BarStyle.SEGMENTED_12);
    public WorldBorder wb;
    public int maxSize = 1500;
    public int size = maxSize;
    public List<List<String>> teams;
    public Heads heads = new Heads(this);
    public co.com.okaeri.funkyuhc.database.Teams TeamDB = new co.com.okaeri.funkyuhc.database.Teams(this);
    @SuppressWarnings("FieldMayBeFinal")
    private PluginManager pm = this.getServer().getPluginManager();
    public boolean UhcTimerStarted;
    @SuppressWarnings("unused")
    public boolean UhcTimerPaused;
    public boolean UhcStarted;
    public boolean UhcTimeRestarted;
    public Duration UhcTimerDuration;
    public Map<Player, FastBoard> boards = new HashMap<>();
    public Colors colors = new Colors();
    public ScoreManager manager;
    public ItemManager itemsManager;
    public int maxRounds = 5;
    public int timePerRound = 60 * 2; // 60 * 30 = 30 minutos
    @SuppressWarnings("unused")
    public int roundTime;
    public int round = 1;
    public Map<Integer, Boolean> roundsStarted = new HashMap<>();
    public Map<Integer, Boolean> worldBorderReduceStart = new HashMap<>();
    // public Map<Integer, Integer> bordersOrder = new HashMap<>();

    public GetTime timer = new GetTime();

    public LocalDateTime startTime;

    @Override
    public void onEnable() {

        // Imprimir informacion del plugin al inicar el servidor
        consoleInfo(colors.green + "<------------------------------------------>");
        consoleInfo(StringUtils.center(pluginName, 44));
        consoleInfo("");
        consoleInfo("Version: " + pluginVersion);
        consoleInfo("Api version: " + apiVersion);
        consoleInfo("");
        consoleInfo(apiDesc);
        consoleInfo("");
        consoleInfo("Creator Website: " + creatorWebsite);
        consoleInfo(colors.green + "<------------------------------------------>");

        for (int i = 1; i <= maxRounds; i++) {
            roundsStarted.put(i, false); // TODO: cargar de bd por si se crashea
        }

        for (int i = 1; i <= maxRounds; i++) {
            worldBorderReduceStart.put(i, false); // TODO: cargar de bd por si se crashea
        }

        /*
        for (int i = 1; i <maxRounds; i++){
            worldBorderReduceStart.put(i, false); // Esto despues se podria jalar desde un archivo
        }
        */

        // Inicializar base de datos y cargar
        this.db = new SQLite(this);
        this.db.load();

        // Registrar comandos
        RegistrarComandos();

        // Set inicial world border
        //noinspection ConstantConditions
        this.wb = Bukkit.getWorld("world").getWorldBorder();
        wb.setCenter(0, 0);
        wb.setSize(maxSize * 2);

        // agregar registro de muertes
        this.pm.registerEvents(new DeathListener(this), this);

        // agregar registro de colacion de bloques
        this.pm.registerEvents(new BlockPlaceListener(this), this);

        // agregar registro de destrucción de bloques
        this.pm.registerEvents(new BlockDestroyListener(this), this);

        // agregar registro de chat
        this.pm.registerEvents(new ChatListener(this), this);

        // Cargar manager de muertes de Ghast
        this.pm.registerEvents(new GhastDrop(this), this);

        // Cargar registro de crafteos
        // this.pm.registerEvents(new ItemCraftEvent(this), this);

        // Cargar registro de movimientos de jugador
        this.pm.registerEvents(new PlayerMove(this), this);

        // Cargar teams almacenados en la base de datos
        this.teams = db.getTeams();

        // Cargar crafteos custom
        this.itemsManager = new ItemManager(this);
        itemsManager.LoadCustomCrafts();

        // Cargar manager de scoreboards
        this.manager = new ScoreManager(this);

        // Cargar manager de titulos
        this.tittle = new Tittle(this);

        //noinspection Convert2Lambda
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (UhcTimerStarted) {
                    // TODO: guardar informacion de rondas etc en bd por si se crashea el server

                    if (!(UhcTimeRestarted)) {
                        UhcTimerDuration = Duration.between(startTime, LocalDateTime.now());
                        manager.UpdateBoard();
                    } else {
                        print("El juego ha sido despausado");
                        // Funciones para reestablecer variables de tiempo en caso de que se haya pausado
                        // y reiniciado el evento
                    }

                    switch (round) {
                        case 1:
                            long restanteRonda = (((long) timePerRound * round) - (UhcTimerDuration.getSeconds()));

                            if (restanteRonda <= 0) {
                                round += 1;
                                break;
                            }

                            if (!roundsStarted.get(round)) {
                                // este codigo Solo se va a ejecutar al inciar la ronda
                                tittle.setTittle(colors.green + "Iniciando ronda 1" + colors.reset,
                                        colors.aqua + "Teleportando a los equipos");
                                print("Se inicia ronda 1");
                                print("Teleportando a los equipos");
                                print("Equipos teleportados con éxito");

                                roundsStarted.put(round, true); // guardar que ya se ejecutó esto
                            }

                            if (restanteRonda <= ((timePerRound / 2.0) + 180) && !worldBorderBefore.get(round)){
                                tittle.setTittle(colors.red + "Reduciendo tamaño del mapa" + colors.reset,
                                        colors.green + "El mapa iniciara a reducirse en 3 minutos");
                                worldBorderBefore.put(round, true);
                            }

                            if ((restanteRonda <= timePerRound / 2.0) &&
                                    !worldBorderReduceStart.get(round)) {
                                // este codigo se ejecutará una sola vez para iniciar el despl de world border
                                wb.setSize(1500.0, (long) (((long) timePerRound * round) / 2.0));

                                worldBorderReduceStart.put(round, true);
                            }

                            timeBar.setProgress((double) restanteRonda / (timePerRound * round));
                            timeBar.setTitle("Ronda número " + colors.red + colors.bold + round + colors.reset +
                                    " Tiempo restante: " + timer.toString(restanteRonda));
                            print("round: " + round + ", restante: " + timer.toString(restanteRonda) + " world border" +
                                    wb.getSize());
                            break;

                        case 2:
                            long restanteRonda_2 = (((long) timePerRound * round) - (UhcTimerDuration.getSeconds()));

                            if (restanteRonda_2 <= 0) {
                                round += 1;
                                break;
                            }

                            if (!roundsStarted.get(round)) {
                                // este codigo Solo se va a ejecutar al inciar la ronda
                                tittle.setTittle(colors.green + "Iniciando ronda 2","");

                                roundsStarted.put(round, true); // guardar que ya se ejecutó esto
                            }

                            if (restanteRonda_2 <= ((timePerRound / 2.0) + 180) && !worldBorderBefore.get(round)){
                                tittle.setTittle(colors.red + "Reduciendo tamaño del mapa" + colors.reset,
                                        colors.green + "El mapa iniciara a reducirse en 3 minutos");
                                worldBorderBefore.put(round, true);
                            }

                            if ((restanteRonda_2 <= timePerRound / 2.0) &&
                                    !worldBorderReduceStart.get(round)) {
                                // este codigo se ejecutará una sola vez para iniciar el despl de world border
                                wb.setSize(900.0, (long) ((long) timePerRound / 2.0));

                                worldBorderReduceStart.put(round, true);
                            }

                            timeBar.setProgress((double) restanteRonda_2 / (timePerRound * round));
                            timeBar.setTitle("Ronda número " + colors.red + colors.bold + round + colors.reset +
                                    " Tiempo restante: " + timer.toString(restanteRonda_2));
                            print("round: " + round + ", restante: " + timer.toString(restanteRonda_2) +
                                    " world border" + wb.getSize());
                            break;

                        case 3:
                            long restanteRonda_3 = (((long) timePerRound * round) - (UhcTimerDuration.getSeconds()));

                            if (restanteRonda_3 <= 0) {
                                round += 1;
                                break;
                            }

                            if (!roundsStarted.get(round)) {
                                // este codigo Solo se va a ejecutar al inciar la ronda
                                tittle.setTittle(colors.green + "Se inicia ronda 3",
                                        colors.aqua + "Se habilitará el PVP");
                                // TODO: habilitar el PVP

                                roundsStarted.put(round, true); // guardar que ya se ejecutó esto
                            }

                            if (restanteRonda_3 <= ((timePerRound / 2.0) + 180) && !worldBorderBefore.get(round)){
                                tittle.setTittle(colors.red + "Reduciendo tamaño del mapa" + colors.reset,
                                        colors.green + "El mapa iniciara a reducirse en 3 minutos");
                                worldBorderBefore.put(round, true);
                            }

                            if ((restanteRonda_3 <= timePerRound / 2.0) &&
                                    !worldBorderReduceStart.get(round)) {
                                // este codigo se ejecutará una sola vez para iniciar el despl de world border
                                wb.setSize(200, (long) ((long) timePerRound / 2.0));

                                worldBorderReduceStart.put(round, true);
                            }

                            timeBar.setProgress((double) restanteRonda_3 / (timePerRound * round));
                            timeBar.setTitle("Ronda número " + colors.red + colors.bold + round + colors.reset +
                                    " Tiempo restante: " + timer.toString(restanteRonda_3));
                            print("round: " + round + ", restante: " + timer.toString(restanteRonda_3) +
                                    " world border" + wb.getSize());
                            break;

                        case 4:
                            long restanteRonda_4 = (((long) timePerRound * round) - (UhcTimerDuration.getSeconds()));

                            if (restanteRonda_4 <= 0) {
                                round += 1;
                                break;
                            }

                            if (!roundsStarted.get(round)) {
                                // este codigo Solo se va a ejecutar al inciar la ronda
                                tittle.setTittle(colors.green + "Se inicia la ronda 4", "");
                                // TODO: imprimir en pantalla información de la ronda

                                roundsStarted.put(round, true); // guardar que ya se ejecutó esto
                            }

                            if (restanteRonda_4 <= ((timePerRound / 2.0) + 180) && !worldBorderBefore.get(round)){
                                tittle.setTittle(colors.red + "Reduciendo tamaño del mapa" + colors.reset,
                                        colors.green + "El mapa iniciara a reducirse en 3 minutos");
                                worldBorderBefore.put(round, true);
                            }

                            if ((restanteRonda_4 <= timePerRound / 2.0) &&
                                    !worldBorderReduceStart.get(round)) {
                                // este codigo se ejecutará una sola vez para iniciar el despl de world border
                                wb.setSize(100, (long) ((long) timePerRound / 2.0));

                                worldBorderReduceStart.put(round, true);
                            }

                            timeBar.setProgress((double) restanteRonda_4 / (timePerRound * round));
                            timeBar.setTitle("Ronda número " + colors.red + colors.bold + round + colors.reset +
                                    " Tiempo restante: " + timer.toString(restanteRonda_4));
                            print("round: " + round + ", restante: " + timer.toString(restanteRonda_4) +
                                    " world border" + wb.getSize());
                            break;

                        case 5:
                            long restanteRonda_5 = (((long) timePerRound * round) - (UhcTimerDuration.getSeconds()));

                            if (restanteRonda_5 <= 0) {
                                // TODO: Verificar que hacer en caso de que a este momento no haya quedado un ganador
                                print("Rondas acabadas, muerte subita");
                                round += 1;
                                break;
                            }

                            if (!roundsStarted.get(round)) {
                                // este codigo Solo se va a ejecutar al inciar la ronda
                                print("Se inicia ronda 5");
                                // TODO: imprimir en pantalla información de la ronda
                                // Ultima ronda, despues de acabar esta ronda se hara ....

                                roundsStarted.put(round, true); // guardar que ya se ejecuto esto
                                break;
                            }

                            if (restanteRonda_5 <= ((timePerRound / 2.0) + 180) && !worldBorderBefore.get(round)){
                                tittle.setTittle(colors.red + "Reduciendo tamaño del mapa" + colors.reset,
                                        colors.green + "El mapa iniciara a reducirse en 3 minutos");
                                worldBorderBefore.put(round, true);
                            }

                            if ((restanteRonda_5 <= timePerRound / 2.0) &&
                                    !worldBorderReduceStart.get(round)) {
                                // este codigo se ejecutará una sola vez para iniciar el despl de world border
                                wb.setSize(50, (long) ((long) timePerRound / 2.0));

                                worldBorderReduceStart.put(round, true);
                            }

                            timeBar.setProgress((double) restanteRonda_5 / (timePerRound * round));
                            timeBar.setTitle("Ronda número " + colors.red + colors.bold + round + colors.reset +
                                    " Tiempo restante: " + timer.toString(restanteRonda_5));
                            print("round: " + round + ", restante: " + timer.toString(restanteRonda_5) +
                                    " world border" + wb.getSize());
                            break;
                    }
                }
            }
        }, 10, 20L);

        consoleInfo(colors.green + "Plugin inicializado con exito");
    }

    /*
    * Color format message
    *
    private String formatMessage(String message) {
        message = this.color ? "§e[§2SkinsRestorer§e] §r" + message : message;
        message = message + "§r";
        message = ANSIConverter.convertToAnsi(message);
        return message;
    }
    *
    * */

    @Override
    public void onDisable() {

        print(Color.GREEN + "<------------------------------------------>");
        consoleInfo("Plugin deshabilitado");
        print(Color.GREEN + "<------------------------------------------>");
        // TODO: agregar StopUHC aqui para que cuando se deshabilite el plugin
    }

    public void print(String data) {
        Bukkit.getConsoleSender().sendMessage(data);
    }

    private void consoleInfo(String data) {
        Bukkit.getConsoleSender().sendMessage(colors.green + "[" + pluginName + "]" + colors.reset + data);
    }

    @SuppressWarnings("unused")
    public Database getRDatabase() {
        return this.db;
    }

    public void changeMaxSize(int size, long time) {
        maxSize = size * 2;
        if (time != 1) {
            wb.setSize(size * 2, time);
        } else {
            wb.setSize(size * 2);
        }
    }

    public void changeSize(int size, long time) {
        this.size = size * 2;
        if (time != 1) {
            wb.setSize(size * 2, time);
        } else {
            wb.setSize(size * 2);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void RegistrarComandos() {
        this.getCommand("mapSize").setExecutor(new mapSize(this));
        this.getCommand("mapSize").setTabCompleter(new mapSizeTab(this));

        this.getCommand("teams").setExecutor(new Teams(this));
        this.getCommand("teams").setTabCompleter(new teamsTab(this));

        this.getCommand("regeneration").setExecutor(new Regeneration(this));

        this.getCommand("uhc").setExecutor(new UhcController(this));

        this.getCommand("score").setExecutor(new ScoreBoard(this));

        this.getCommand("global").setExecutor(new GlobalMessage(this));
    }

}
