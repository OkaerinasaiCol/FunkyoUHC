package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.database.Heads;
import co.com.okaeri.funkyuhc.util.Head;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;

import java.util.List;
import java.util.UUID;

public class BlockDestroyListener implements Listener {

    @SuppressWarnings({"FieldCanBeLocal", "unused", "FieldMayBeFinal"})
    private FunkyUHC plugin;

    public BlockDestroyListener(FunkyUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event){
        // plugin.print("Placed: " + event.getBlock().getType());

        if (event.getBlock().getType() == Material.PLAYER_WALL_HEAD){
            event.setDropItems(false);

            Heads head = new Heads(plugin);
            Head h = new Head();
            List<String> p_info = head.getHead(event.getBlock());

            UUID uuid = UUID.fromString(p_info.get(0));

            PlayerProfile playerProfile = plugin.getServer().createPlayerProfile(uuid, p_info.get(1));

            ItemStack drop = h.getPlayerHead(playerProfile);

            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), drop);
        }
    }

}
