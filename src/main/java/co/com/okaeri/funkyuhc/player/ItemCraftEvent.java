package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ItemCraftEvent implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public ItemCraftEvent(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemCraft(@NotNull PrepareItemCraftEvent event) {
        if ((event.getRecipe() instanceof ShapedRecipe) && event.getRecipe().getResult().getType().equals(Material.ENCHANTED_GOLDEN_APPLE)) {

            for (ItemStack s : event.getInventory().getMatrix()) {
                if (s.getType().equals(Material.PLAYER_HEAD)) {

                    ItemMeta meta = s.getItemMeta();
                    //noinspection ConstantConditions
                    String headName = meta.getLore().get(0).replace("Cabeza de: ", "");
                    meta.setLore(Arrays.asList("Manzana creada con la cabeza de", headName));
                    ItemStack itemStack = event.getInventory().getResult();
                    //noinspection ConstantConditions
                    itemStack.setItemMeta(meta);
                    event.getInventory().setResult(itemStack);
                }
            }
            plugin.print(event.getInventory().getResult() + "");

        }
    }
}
