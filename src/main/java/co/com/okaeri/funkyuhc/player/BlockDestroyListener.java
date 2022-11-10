package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockDestroyListener implements Listener {

    @SuppressWarnings({"FieldCanBeLocal", "unused", "FieldMayBeFinal"})
    private FunkyUHC plugin;

    public BlockDestroyListener(FunkyUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event){
        if (event.getBlock().getType() == Material.PLAYER_WALL_HEAD){
            event.setDropItems(false);



            // PlayerProfile = plugin.getServer().createPlayerProfile();
        }
    }

}
