package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.GameRule;
import org.bukkit.World;

public class Regeneration {

    private FunkyUHC plugin;
    public boolean isEnabled;

    public Regeneration(FunkyUHC plugin){
        this.plugin = plugin;
        isEnabled = true; // TODO: configurar para obtener de si el uhc está en curso o no
                            // FIXME: sigue dejando que se cure con comida
    }

    public void enableRegeneration(){
        for (World world: plugin.getServer().getWorlds()){
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        }
        isEnabled = true;
    }

    public void disableRegeneration(){
        for (World world: plugin.getServer().getWorlds()){
            world.setGameRule(GameRule.NATURAL_REGENERATION, true);
        }
        isEnabled = false;
    }

    public boolean getState(){
        return isEnabled;
    }
}
