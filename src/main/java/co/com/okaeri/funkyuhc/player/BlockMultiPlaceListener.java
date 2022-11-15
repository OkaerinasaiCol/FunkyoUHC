package co.com.okaeri.funkyuhc.player;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class BlockMultiPlaceListener implements Listener {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public BlockMultiPlaceListener(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFinalBlockPlaced(@NotNull BlockPlaceEvent event) {

        // plugin.print("Multi: " + event.getBlockPlaced().getType());

        World _world = event.getPlayer().getWorld();
        Location _ball1 = event.getBlock().getLocation();
        Location _ball11 = event.getBlockPlaced().getLocation();
        _ball1.add(0.0, -1.0, 0.0);
        _ball11.add(0.0, -1.0, 0.0);

        plugin.print("down 1: " + _world.getBlockAt(_ball1));
        plugin.print("down 11: " + _world.getBlockAt(_ball11));

        if (event.getBlockPlaced().getType().equals(Material.FLINT_AND_STEEL) ||
                event.getBlockPlaced().getType().equals(Material.FIRE_CHARGE)) {

            World world = event.getPlayer().getWorld();

            Location ball1 = event.getBlockPlaced().getLocation();

            ball1.add(0.0, 0.0, 0.0);
            plugin.print("Final " + world.getBlockAt(ball1).getType() + " loc " + ball1);

            ball1.add(-2.0, 0.0, 0.0);
            plugin.print("Izquierda " + world.getBlockAt(ball1).getType() + " loc " + ball1);
            ball1.add(2.0, 0.0, 0.0);

            ball1.add(2.0, 0.0, 0.0);
            plugin.print("Derecha " + world.getBlockAt(ball1).getType() + " loc " + ball1);
            ball1.add(-2.0, 0.0, 0.0);

            ball1.add(0.0, 0.0, 2.0);
            plugin.print("Adelante " + world.getBlockAt(ball1).getType() + " loc " + ball1);
            ball1.add(0.0, 0.0, -2.0);

            ball1.add(0.0, 0.0, -2.0);
            plugin.print("Detrás " + world.getBlockAt(ball1).getType() + " loc " + ball1);
            ball1.add(0.0, 0.0, 2.0);


            // intetar con el bloque de dos a la izquierda
            if (world.getBlockAt(ball1.add(-2.0, 0.0, 0.0)).getType().equals(Material.FIRE) ||
                    world.getBlockAt(ball1.add(-2.0, 0.0, 0.0)).getType().equals(Material.SOUL_FIRE)) {
                plugin.print("izquierda");
            } else if (world.getBlockAt(ball1.add(2.0, 0.0, 0.0)).getType().equals(Material.FIRE) ||
                    world.getBlockAt(ball1.add(2.0, 0.0, 0.0)).getType().equals(Material.SOUL_FIRE)) {
                plugin.print("derecha");
            } else if (world.getBlockAt(ball1.add(0.0, 0.0, -2.0)).getType().equals(Material.FIRE) ||
                    world.getBlockAt(ball1.add(0.0, 0.0, -2.0)).getType().equals(Material.SOUL_FIRE)) {
                plugin.print("detrás");
            } else if (world.getBlockAt(ball1.add(0.0, 0.0, 2.0)).getType().equals(Material.FIRE) ||
                    world.getBlockAt(ball1.add(0.0, 0.0, 2.0)).getType().equals(Material.SOUL_FIRE)) {
                plugin.print("delante");
            }

        }
    }

}
