package co.com.okaeri.funkyuhc;

import co.com.okaeri.funkyuhc.database.Database;
import co.com.okaeri.funkyuhc.database.SQLite;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class FunkyUHC extends JavaPlugin {
    
    PluginDescriptionFile descriptionFile = getDescription();

    public String pluginName = descriptionFile.getName();
    public String pluginVersion = descriptionFile.getVersion();
    public String apiVersion = descriptionFile.getAPIVersion();
    public String creatorWebsite = descriptionFile.getWebsite();
    public String apiDesc = descriptionFile.getDescription();
    private Database db;

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
    }

    @Override
    public void onDisable() {
        print(Color.GREEN + "<------------------------------------------>");
        consoleInfo("Plugin deshabilitado");
        print(Color.GREEN + "<------------------------------------------>");
    }

    private void print(String data){
        Bukkit.getConsoleSender().sendMessage(data);
    }

    private void consoleInfo(String data){
        Bukkit.getConsoleSender().sendMessage(Color.GREEN + "[" + pluginName + "]" + Color.WHITE + data);
    }

    public Database getRDatabase(){
        return this.db;
    }
}
