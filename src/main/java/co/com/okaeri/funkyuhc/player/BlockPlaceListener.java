package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.database.Heads;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockPlaceListener implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public BlockPlaceListener(FunkyUHC plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();
        Block block = event.getBlockPlaced();
        // plugin.print("Placed: " + item.getType());

        if (item.getType() == Material.PLAYER_HEAD){

            //noinspection unused
            ItemMeta itemmeta = event.getItemInHand().getItemMeta();
            // TODO: guardar lore en la base de datos y borrar en caso de que se quite
            plugin.heads.setHead(item, player, block);

        } else if (item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FIRE_CHARGE) {

                Block start = event.getBlock();

                if (start.getRelative(0, -1, 0).getType().equals(Material.SOUL_SAND)){

                    List<Block> structure = new ArrayList<>();
                    structure.add(start.getRelative(0, -1, 0));

                    if (start.getRelative(1,-1,0).getType().equals(Material.GOLD_BLOCK)){
                        structure.add(start.getRelative(1,-1,0));
                        rightOrient(start, structure);
                    } else if (start.getRelative(-1, -1,0).getType().equals(Material.GOLD_BLOCK)) {
                        structure.add(start.getRelative(-1, -1,0));
                        leftOrient(start, structure);
                    } else if (start.getRelative(0,-1, 1).getType().equals(Material.GOLD_BLOCK)){
                        structure.add(start.getRelative(0,-1, 1));
                        frontOrient(start, structure);
                    } else if (start.getRelative(0,-1, -1).getType().equals(Material.GOLD_BLOCK)) {
                        structure.add(start.getRelative(0,-1, -1));
                        backOrient(start, structure);
                    }
                }
        }
    }

    @SuppressWarnings("ReassignedVariable")
    private void rightOrient(Block block, List<Block> structure){

        // Verificar primero si del lado opuesto ya hay fuego prendio para evitar procesos innecesarios

        Block c_block = block.getRelative(2, 0, 0);

        plugin.print("" + c_block.getType());

        if (c_block.getType().equals(Material.SOUL_FIRE)){
            structure.add(block.getRelative(2, 1, 0));

            plugin.print("derecha SOUL_FIRE");

            c_block = block.getRelative(1, 0, 0);

            if (c_block.getType().equals(Material.DIAMOND_BLOCK)){
                structure.add(c_block);

                plugin.print("derecha DIAMOND_BLOCK");

                c_block = block.getRelative(1,1,0);

                if (c_block.getType().equals(Material.DIAMOND_BLOCK)){
                    structure.add(c_block);

                    plugin.print("derecha DIAMOND_BLOCK");

                    c_block = block.getRelative(1, 2,0);

                    if (c_block.getType().equals(Material.PLAYER_HEAD)){
                        structure.add(c_block);

                        plugin.print("derecha PLAYER_HEAD");

                        Heads h = new Heads(plugin);

                        List<String> head =  h.getHead(c_block);

                        UUID uuid = UUID.fromString(head.get(0));

                        String name = head.get(1);

                        plugin.print("uuid: " + uuid);
                        plugin.print("name: " + name);

                    }
                }

            }
        }

        plugin.print("Revivir derecha");
    }

    @SuppressWarnings("ReassignedVariable")
    private void leftOrient(Block block, List<Block> structure){

        // Verificar primero si del lado opuesto ya hay fuego prendio para evitar procesos innecesarios

        Block c_block = block.getRelative(2, 0, 0);

        plugin.print("" + c_block.getType());

        if (c_block.getType().equals(Material.SOUL_FIRE)){
            structure.add(block.getRelative(2, 1, 0));

            plugin.print("derecha SOUL_FIRE");

            c_block = block.getRelative(1, 0, 0);

            if (c_block.getType().equals(Material.DIAMOND_BLOCK)){
                structure.add(c_block);

                plugin.print("derecha DIAMOND_BLOCK");

                c_block = block.getRelative(1,1,0);

                if (c_block.getType().equals(Material.DIAMOND_BLOCK)){
                    structure.add(c_block);

                    plugin.print("derecha DIAMOND_BLOCK");

                    c_block = block.getRelative(1, 2,0);

                    if (c_block.getType().equals(Material.PLAYER_HEAD)){
                        structure.add(c_block);

                        plugin.print("derecha PLAYER_HEAD");

                        Heads h = new Heads(plugin);

                        List<String> head =  h.getHead(c_block);

                        UUID uuid = UUID.fromString(head.get(0));

                        String name = head.get(1);

                        plugin.print("uuid: " + uuid);
                        plugin.print("name: " + name);

                    }
                }

            }
        }

        plugin.print("Revivir derecha");
    }

    @SuppressWarnings("ReassignedVariable")
    private void backOrient(Block block, List<Block> structure){

        // Verificar primero si del lado opuesto ya hay fuego prendio para evitar procesos innecesarios

        Block c_block = block.getRelative(2, 0, 0);

        plugin.print("" + c_block.getType());

        if (c_block.getType().equals(Material.SOUL_FIRE)){
            structure.add(block.getRelative(2, 1, 0));

            plugin.print("derecha SOUL_FIRE");

            c_block = block.getRelative(1, 0, 0);

            if (c_block.getType().equals(Material.DIAMOND_BLOCK)){
                structure.add(c_block);

                plugin.print("derecha DIAMOND_BLOCK");

                c_block = block.getRelative(1,1,0);

                if (c_block.getType().equals(Material.DIAMOND_BLOCK)){
                    structure.add(c_block);

                    plugin.print("derecha DIAMOND_BLOCK");

                    c_block = block.getRelative(1, 2,0);

                    if (c_block.getType().equals(Material.PLAYER_HEAD)){
                        structure.add(c_block);

                        plugin.print("derecha PLAYER_HEAD");

                        Heads h = new Heads(plugin);

                        List<String> head =  h.getHead(c_block);

                        UUID uuid = UUID.fromString(head.get(0));

                        String name = head.get(1);

                        plugin.print("uuid: " + uuid);
                        plugin.print("name: " + name);

                    }
                }

            }
        }

        plugin.print("Revivir derecha");
    }

    @SuppressWarnings("ReassignedVariable")
    private void frontOrient(Block block, List<Block> structure){
        plugin.print("Revivir adelante");
    }
}
