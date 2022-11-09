package co.com.okaeri.funkyuhc;

import co.com.okaeri.funkyuhc.commands.Teams;
import co.com.okaeri.funkyuhc.commands.mapSize;
import co.com.okaeri.funkyuhc.commands.roundTimeBar;
import co.com.okaeri.funkyuhc.database.Database;
import co.com.okaeri.funkyuhc.database.SQLite;
import co.com.okaeri.funkyuhc.player.DeathListener;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class FunkyUHC extends JavaPlugin {
    
    PluginDescriptionFile descriptionFile = getDescription();

    public String pluginName = descriptionFile.getName();
    public String pluginVersion = descriptionFile.getVersion();
    public String apiVersion = descriptionFile.getAPIVersion();
    public String creatorWebsite = descriptionFile.getWebsite();
    public String apiDesc = descriptionFile.getDescription();
    public SQLite db;
    public BossBar timeBar;
    public WorldBorder wb;
    public int maxSize = 1500;
    public int size = maxSize;
    public List<List<String>> teams;
    private PluginManager pm = this.getServer().getPluginManager();

    @Override
    public void onEnable() {

        // Imprimir informacion del plugin al inicar el servidor
        print(Color.GREEN + "<------------------------------------------>");
        print(StringUtils.center(pluginName, 44));
        print("");
        print("Version: " + pluginVersion);
        print("Api version: " + apiVersion);
        print("");
        print(apiDesc);
        print("");
        print("Creator Website: " + creatorWebsite);
        print(Color.GREEN + "<------------------------------------------>");
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

        // inicailizar lo que se ejecuta al morir
        new DeathListener(this);

        // agregar registro de muertes
        this.pm.registerEvents(new DeathListener(this), this);
        this.timeBar = Bukkit.createBossBar("Tiempo hasta la ronda #", BarColor.BLUE, BarStyle.SEGMENTED_12);

        // Cargar teams almacenados en la base de datos
        this.teams = db.getTeams();

    }

    @Override
    public void onDisable() {
        print(Color.GREEN + "<------------------------------------------>");
        consoleInfo("Plugin deshabilitado");
        print(Color.GREEN + "<------------------------------------------>");
    }

    public void print(String data){
        Bukkit.getConsoleSender().sendMessage(data);
    }

    private void consoleInfo(String data){
        Bukkit.getConsoleSender().sendMessage(Color.GREEN + "[" + pluginName + "]" + Color.WHITE + data);
    }

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
        this.getCommand("timeBar").setExecutor(new roundTimeBar(this, timeBar));
        this.getCommand("teams").setExecutor(new Teams(this));
    }
}
