package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import co.com.okaeri.funkyuhc.database.Heads;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BlockPlaceListener implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public BlockPlaceListener(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(@NotNull BlockPlaceEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItemInHand();
        Block block = event.getBlockPlaced();

        if (item.getType() == Material.PLAYER_HEAD) {

            //noinspection unused
            ItemMeta itemmeta = event.getItemInHand().getItemMeta();
            plugin.heads.setHead(item, player, block);

        } else if (item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FIRE_CHARGE) {

            Block start = event.getBlock();

            if (start.getRelative(0, -1, 0).getType().equals(Material.SOUL_SAND)) {

                List<Block> structure = new ArrayList<>();
                structure.add(start.getRelative(0, -1, 0));

                if (start.getRelative(1, -1, 0).getType().equals(Material.GOLD_BLOCK)) {
                    structure.add(start.getRelative(1, -1, 0));
                    rightOrient(start, structure, player);
                } else if (start.getRelative(-1, -1, 0).getType().equals(Material.GOLD_BLOCK)) {
                    structure.add(start.getRelative(-1, -1, 0));
                    leftOrient(start, structure, player);
                } else if (start.getRelative(0, -1, 1).getType().equals(Material.GOLD_BLOCK)) {
                    structure.add(start.getRelative(0, -1, 1));
                    frontOrient(start, structure, player);
                } else if (start.getRelative(0, -1, -1).getType().equals(Material.GOLD_BLOCK)) {
                    structure.add(start.getRelative(0, -1, -1));
                    backOrient(start, structure, player);
                }
            }
        }
    }

    @SuppressWarnings("ReassignedVariable")
    private void rightOrient(@NotNull Block block, List<Block> structure, Player placer) {

        // Verificar primero si del lado opuesto ya hay fuego prendio para evitar procesos innecesarios

        Block c_block = block.getRelative(2, 0, 0);

        if (c_block.getType().equals(Material.SOUL_FIRE)) {
            structure.add(block.getRelative(2, -1, 0));

            c_block = block.getRelative(1, 0, 0);

            if (c_block.getType().equals(Material.DIAMOND_BLOCK)) {
                structure.add(c_block);

                c_block = block.getRelative(1, 1, 0);

                if (c_block.getType().equals(Material.DIAMOND_BLOCK)) {
                    structure.add(c_block);

                    c_block = block.getRelative(1, 2, 0);

                    if (c_block.getType().equals(Material.PLAYER_HEAD)) {
                        structure.add(c_block);

                        Heads h = new Heads(plugin);

                        List<String> head = h.getHead(c_block);

                        UUID uuid = UUID.fromString(head.get(0));

                        String name = head.get(1);

                        plugin.print("uuid: " + uuid);
                        plugin.print("name: " + name);

                        revive(uuid, c_block, structure, placer);

                    }
                }

            }
        }
    }

    @SuppressWarnings("ReassignedVariable")
    private void leftOrient(@NotNull Block block, List<Block> structure, Player placer) {

        // Verificar primero si del lado opuesto ya hay fuego prendio para evitar procesos innecesarios

        Block c_block = block.getRelative(-2, 0, 0);

        if (c_block.getType().equals(Material.SOUL_FIRE)) {
            structure.add(block.getRelative(-2, -1, 0));

            c_block = block.getRelative(-1, 0, 0);

            if (c_block.getType().equals(Material.DIAMOND_BLOCK)) {
                structure.add(c_block);

                c_block = block.getRelative(-1, 1, 0);

                if (c_block.getType().equals(Material.DIAMOND_BLOCK)) {
                    structure.add(c_block);

                    c_block = block.getRelative(-1, 2, 0);

                    if (c_block.getType().equals(Material.PLAYER_HEAD)) {
                        structure.add(c_block);

                        Heads h = new Heads(plugin);

                        List<String> head = h.getHead(c_block);

                        UUID uuid = UUID.fromString(head.get(0));

                        String name = head.get(1);

                        plugin.print("uuid: " + uuid);
                        plugin.print("name: " + name);

                        revive(uuid, c_block, structure, placer);

                    }
                }

            }
        }
    }

    @SuppressWarnings("ReassignedVariable")
    private void backOrient(@NotNull Block block, List<Block> structure, Player placer) {

        // Verificar primero si del lado opuesto ya hay fuego prendio para evitar procesos innecesarios

        Block c_block = block.getRelative(0, 0, -2);

        if (c_block.getType().equals(Material.SOUL_FIRE)) {
            structure.add(block.getRelative(0, -1, -2));

            c_block = block.getRelative(0, 0, -1);

            if (c_block.getType().equals(Material.DIAMOND_BLOCK)) {
                structure.add(c_block);

                c_block = block.getRelative(0, 1, -1);

                if (c_block.getType().equals(Material.DIAMOND_BLOCK)) {
                    structure.add(c_block);

                    c_block = block.getRelative(0, 2, -1);

                    if (c_block.getType().equals(Material.PLAYER_HEAD)) {
                        structure.add(c_block);

                        Heads h = new Heads(plugin);

                        List<String> head = h.getHead(c_block);

                        UUID uuid = UUID.fromString(head.get(0));

                        String name = head.get(1);

                        plugin.print("uuid: " + uuid);
                        plugin.print("name: " + name);

                        revive(uuid, c_block, structure, placer);

                    }
                }

            }
        }
    }

    @SuppressWarnings("ReassignedVariable")
    private void frontOrient(@NotNull Block block, List<Block> structure, Player placer) {

        // Verificar primero si del lado opuesto ya hay fuego prendio para evitar procesos innecesarios

        Block c_block = block.getRelative(0, 0, 2);

        if (c_block.getType().equals(Material.SOUL_FIRE)) {
            structure.add(block.getRelative(0, -1, 2));

            c_block = block.getRelative(0, 0, 1);

            if (c_block.getType().equals(Material.DIAMOND_BLOCK)) {
                structure.add(c_block);

                c_block = block.getRelative(0, 1, 1);

                if (c_block.getType().equals(Material.DIAMOND_BLOCK)) {
                    structure.add(c_block);

                    c_block = block.getRelative(0, 2, 1);

                    if (c_block.getType().equals(Material.PLAYER_HEAD)) {
                        structure.add(c_block);

                        Heads h = new Heads(plugin);

                        List<String> head = h.getHead(c_block);

                        UUID uuid = UUID.fromString(head.get(0));

                        String name = head.get(1);

                        plugin.print("uuid: " + uuid);
                        plugin.print("name: " + name);

                        revive(uuid, c_block, structure, placer);

                    }
                }

            }
        }
    }

    public void revive(UUID player, Block head, List<Block> structure, @NotNull Player placer) {

        Player p = plugin.getServer().getPlayer(player);

        assert p != null;
        if (Objects.equals(plugin.TeamDB.getTeam(p.getName()), plugin.TeamDB.getTeam(placer.getName()))) {
            plugin.TeamDB.setDeath(p.getName(), false);
            p.teleport(head.getLocation());
            // TODO: generar aviso de que se ha revivido a unjugador a todos los jugadores y animación de revivido

            for (Block block : structure) {
                block.setType(Material.AIR);
            }

            p.setGameMode(GameMode.SURVIVAL);
        } // TODO agregar aviso de que no se puede revivir a alguien de otro equipo

    }
}
