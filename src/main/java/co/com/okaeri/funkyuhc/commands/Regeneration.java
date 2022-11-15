package co.com.okaeri.funkyuhc.commands;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Regeneration implements CommandExecutor {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;
    @SuppressWarnings("FieldMayBeFinal")
    private co.com.okaeri.funkyuhc.player.Regeneration regeneration;

    public Regeneration(FunkyUHC plugin) {
        this.plugin = plugin;
        regeneration = new co.com.okaeri.funkyuhc.player.Regeneration(plugin);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return command(args);
        } // TODO: agregar funcion para si se ejecuta desde el juego

        return false;
    }

    private boolean command(String[] args) { // boolean para saber si se imprime a juego o no
        if (args.length == 0) {
            get();
            return true;
        } else if (args[0].equals("change")) {
            if (regeneration.isEnabled) {
                regeneration.disableRegeneration();
                plugin.print("Regeneration change to: " + regeneration.isEnabled);
            } else {
                regeneration.enableRegeneration();
                plugin.print("Regeneration change to: " + regeneration.isEnabled);
            }
        }

        return false;
    }

    private void get() {
        plugin.print("" + regeneration.isEnabled);
    }
}
