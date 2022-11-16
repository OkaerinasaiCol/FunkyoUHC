package co.com.okaeri.funkyuhc.items;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ItemManager {

    @SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
    private FunkyUHC plugin;

    /**
     * Clase encargada de manejar los crafteos customizados del plugin
     * @param plugin: {@link FunkyUHC} Clase principal del plugin
     */
    public ItemManager(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    /**
     * Cargar los crafteos custom
     */
    public void LoadCustomCrafts() {
        ghastTearRecipe();
        enchantedGapple();
        enemyCompass();
    }

    /**
     * Receta para craftear lágrimas de ghast con el siguiente formato
     * <p></p>
     * S = Melon reluciente (Melón rodeado de 8 pepitas de oro)<p>
     * G = Bloque de oro
     * <p></p>
     * - S -<p>
     * S G S<p>
     * - S -<p>
     */
    private void ghastTearRecipe() {
        ShapedRecipe sr = new ShapedRecipe(Material.GHAST_TEAR.getKey(), new ItemStack(Material.GHAST_TEAR));
        sr.shape(" S ",
                "SGS",
                " S ");
        sr.setIngredient('S', Material.GLISTERING_MELON_SLICE);
        sr.setIngredient('G', Material.GOLD_BLOCK);
        this.plugin.getServer().addRecipe(sr);
    }

    /**
     * Receta para craftear manzanas doradas encantadas con el siguiente formato
     * <p></p>
     * H = Cabeza de jugador<p>
     * G = Bloque de oro
     * <p></p>
     * G G G<p>
     * G H G<p>
     * G G G<p>
     */
    private void enchantedGapple() {
        ShapedRecipe sr = new ShapedRecipe(Material.ENCHANTED_GOLDEN_APPLE.getKey(),
                new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));

        sr.shape("GGG",
                "GHG",
                "GGG");

        sr.setIngredient('G', Material.GOLD_BLOCK);
        sr.setIngredient('H', Material.PLAYER_HEAD);

        this.plugin.getServer().addRecipe(sr);

    }

    /**
     * Receta para craftear brujula para buscar enemigos con el siguiente formato
     * <p></p>
     * H = Cabeza de jugador<p>
     * D = Diamante<p>
     * C = Cabeza de jugador<p>
     * G = Bloque de oro
     * <p></p>
     * - H -<p>
     * D C D<p>
     * - G -<p>
     */
    private void enemyCompass() {

        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(plugin.colors.bold + plugin.colors.yellow + "Brújula de enemigos" + plugin.colors.reset);
        meta.setLore(Collections.singletonList("Brújula para buscar jugadores cercanos"));

        item.setItemMeta(meta);

        //noinspection ConstantConditions
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.fromString("uhc:compass"), item);

        sr.shape(" H ",
                "DCD",
                " G ");

        sr.setIngredient('H', Material.PLAYER_HEAD);
        sr.setIngredient('D', Material.DIAMOND);
        sr.setIngredient('C', Material.COMPASS);
        sr.setIngredient('G', Material.GOLD_BLOCK);

        this.plugin.getServer().addRecipe(sr);
    }

}
