package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mapSize implements CommandExecutor {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    /**
     * Funcion para controlar el tamaño del woldborder, para cambiar el comando con el que se ejecuta esta
     * funcion se debe de cambiar en {@link FunkyUHC} donde se inicializan los comandos y dentro del apartado
     * comandos del archivo de configuración plugin.yml
     * <p></p>
     * Comandos disponibles: <p>
     * - setMax <p>
     * - set
     *
     * @param main la clase principal del plugin
     */
    public mapSize(FunkyUHC main) {
        this.plugin = main;
    }

    @SuppressWarnings("NullableProblems")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                size();
                return true;
            } else if (args.length == 2) { // Todo: verificar que el argumento [1] sea un entero
                switch (args[0].toLowerCase()) {
                    case "setmax":
                        setMax(Integer.parseInt(args[1]));
                        return true;
                    case "set":
                        setSize(Integer.parseInt(args[1]));
                        return true;
                    default:
                        return false;
                }

            } else {
                return false;
            }
        } else {
            if (sender.isOp()) {
                if (args.length == 0) {
                    size();
                    return true;
                } else if (args.length == 2) { // Todo: verificar que el argumento [1] sea un entero
                    switch (args[0].toLowerCase()) {
                        case "setmax":
                            setMax(Integer.parseInt(args[1]));
                            return true;
                        case "set":
                            setSize(Integer.parseInt(args[1]));
                            return true;
                        default:
                            return false;
                    }

                } else {
                    return false;
                }
            }
            return false;
        }
    }

    private void setMax(int value) {
        plugin.changeMaxSize(value, 1);
        plugin.print("Tamaño maximo del mapa cambiado a: " + value);
    }

    public void setSize(int value) {
        plugin.changeSize(value, 1);
        plugin.db.updateSize(value);
        plugin.print("Tamaño del mapa cambiado a: " + value);
    }

    @SuppressWarnings("UnusedReturnValue")
    private int size() {
        plugin.print(String.valueOf(plugin.size));
        return plugin.size;
    }
}
