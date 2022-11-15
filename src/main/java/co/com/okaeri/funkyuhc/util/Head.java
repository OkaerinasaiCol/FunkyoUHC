package co.com.okaeri.funkyuhc.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.util.Arrays;
import java.util.List;

public class Head {

    public ItemStack getPlayerHead(Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);

        //noinspection unused
        PlayerProfile profile = p.getPlayerProfile();
        // TODO: colocar la skin desde el skin restorer si es posible

        PlayerProfile p_profile = p.getPlayerProfile();
        //noinspection unused
        PlayerTextures p_textures = p_profile.getTextures();

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        //noinspection ConstantConditions
        meta.setOwnerProfile(p_profile);
        meta.setDisplayName("Cabeza de " + p.getName());

        List<String> lore = Arrays.asList("Cabeza de: " + p.getName(), "uuid: " + p.getUniqueId());

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getPlayerHead(PlayerProfile profile) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);

        // TODO: colocar la skin desde el skin restorer si es posible

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        //noinspection ConstantConditions
        meta.setOwnerProfile(profile);
        meta.setDisplayName("Cabeza de " + profile.getName());

        List<String> lore = Arrays.asList("Cabeza de: " + profile.getName(), "uuid: " + profile.getUniqueId());

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

}
