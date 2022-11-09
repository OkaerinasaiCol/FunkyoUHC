package co.com.okaeri.funkyuhc;

import co.com.okaeri.funkyuhc.commands.mapSize;
import co.com.okaeri.funkyuhc.database.Database;
import co.com.okaeri.funkyuhc.database.SQLite;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

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
        maxSize = size;
    }

    public void changeSize(int size){
        this.size = size;
    }

    public void RegistrarComandos(){
        this.getCommand("mapSize").setExecutor(new mapSize(this));
        this.getCommand("timeBar").setExecutor(new roundTimeBar(this, timeBar));
    }
}
