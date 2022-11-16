package co.com.okaeri.funkyuhc.util;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.entity.Player;

public class Tittle {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public Tittle(FunkyUHC plugin){
        this.plugin = plugin;
    }

    public void setTittle(String tittle, String sub, int fadeIn, int stay, int fadeOut){
        for (Player p: plugin.getServer().getOnlinePlayers()){
            p.sendTitle(tittle, sub, fadeIn, stay, fadeOut);
        }
    }

    public void setTittle(String tittle, String sub){
        setTittle(tittle, sub, 10, 140, 20);
    }
}
