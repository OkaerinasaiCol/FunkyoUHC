package co.com.okaeri.funkyuhc;

import co.com.okaeri.funkyuhc.commands.*;
import co.com.okaeri.funkyuhc.commands.Regeneration;
import co.com.okaeri.funkyuhc.commands.TabCompleter.mapSizeTab;
import co.com.okaeri.funkyuhc.commands.TabCompleter.teamsTab;
import co.com.okaeri.funkyuhc.controller.GetTime;
import co.com.okaeri.funkyuhc.database.Database;
import co.com.okaeri.funkyuhc.database.Heads;
import co.com.okaeri.funkyuhc.database.SQLite;
import co.com.okaeri.funkyuhc.items.ItemManager;
import co.com.okaeri.funkyuhc.player.*;
import co.com.okaeri.funkyuhc.util.Colors;
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
    public Duration UhcTimerDuration;
    public Map<Player, FastBoard> boards = new HashMap<>();
    public Colors colors = new Colors();
    public ScoreManager manager;
    public ItemManager itemsManager;

    public GetTime timer = new GetTime();

    public LocalDateTime startTime;

    @Override
    public void onEnable() {

        // Imprimir informacion del plugin al inicar el servidor
        print(colors.green + "<------------------------------------------>");
        print(StringUtils.center(pluginName, 44));
        print("");
        print("Version: " + pluginVersion);
        print("Api version: " + apiVersion);
        print("");
        print(apiDesc);
        print("");
        print("Creator Website: " + creatorWebsite);
        print(colors.green + "<------------------------------------------>");
        consoleInfo("Plugin inicializado con exito");


        // Inicializar base de datos y cargar
        this.db = new SQLite(this);
        this.db.load();

        // Registrar comandos
        RegistrarComandos();

        // Set inicial world border
        //noinspection ConstantConditions
        this.wb = Bukkit.getWorld("world").getWorldBorder();
        wb.setCenter(0,0);
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

        //noinspection Convert2Lambda
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (UhcTimerStarted) {
                    UhcTimerDuration = Duration.between(startTime, LocalDateTime.now());
                    manager.UpdateBoard();
                }
            }
        }, 10, 20L);

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

    public void print(String data){
        Bukkit.getConsoleSender().sendMessage(data);
    }

    private void consoleInfo(String data){
        Bukkit.getConsoleSender().sendMessage(Color.GREEN + "[" + pluginName + "]" + Color.WHITE + data);
    }

    @SuppressWarnings("unused")
    public Database getRDatabase(){
        return this.db;
    }

    public void changeMaxSize(int size){
        maxSize = size * 2;
        wb.setSize(size * 2);
    }

    public void changeSize(int size){
        this.size = size * 2;
        wb.setSize(size * 2);
    }

    @SuppressWarnings("ConstantConditions")
    public void RegistrarComandos(){
        this.getCommand("mapSize").setExecutor(new mapSize(this));
        this.getCommand("mapSize").setTabCompleter(new mapSizeTab(this));

        this.getCommand("timeBar").setExecutor(new roundTimeBar(this, timeBar));

        this.getCommand("teams").setExecutor(new Teams(this));
        this.getCommand("teams").setTabCompleter(new teamsTab(this));

        this.getCommand("regeneration").setExecutor(new Regeneration(this));

        this.getCommand("uhc").setExecutor(new UhcController(this));

        this.getCommand("score").setExecutor(new ScoreBoard(this));

        this.getCommand("global").setExecutor(new GlobalMessage(this));
    }

}
