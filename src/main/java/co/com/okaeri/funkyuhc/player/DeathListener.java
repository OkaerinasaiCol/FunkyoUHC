package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.*;

public class DeathListener implements Listener{

    private FunkyUHC plugin;

    public  DeathListener(FunkyUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void Muertes(PlayerDeathEvent e){
        Player p = e.getEntity();

        ItemStack drop = getPlayerHead(p);
        p.getWorld().dropItem(p.getLocation(), drop);

        plugin.print(e.getEntity() + " death " + e.getEntity().getLastDamageCause() + e.getEntity().getKiller());
        plugin.print("death id" + e.getEntity().getUniqueId());
    }

    public ItemStack getPlayerHead(Player p){
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);

        PlayerProfile profile = p.getPlayerProfile();
        // TODO: colocar la skin desde el skin restorer si es posible

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwnerProfile(p.getPlayerProfile());
        meta.setDisplayName("Cabeza de " + p.getName());

        List<String> lore = Arrays.asList("Cabeza de: " + p.getName(), "uuid: " + p.getUniqueId());

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }


}