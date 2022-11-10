package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Heads {

    private FunkyUHC plugin;

    public Heads(FunkyUHC plugin){
        this.plugin = plugin;
    }

    public void setHead(ItemStack head, Player p, Block block){
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        //noinspection ConstantConditions
        plugin.print("Cabeza de: " + meta.getOwnerProfile().getName() +
                "\nUUID: " + meta.getOwnerProfile().getUniqueId().toString() +
                "\nBlock_word: " + block.getWorld() +
                "\nBlock_coords: " + block.getLocation() +
                "\nBlock_placer: " + p.getName() +
                "\nLore: " + meta.getLore());

        try {
            Statement statment = plugin.db.statement();

            statment.executeUpdate("INSERT INTO " +
                    "\"main\".\"heads\"(\"owner\",\"uuid\",\"world\",\"coords\",\"placer\",\"lore\")" +
                    " VALUES (" +
                    "'" + meta.getOwnerProfile().getName() + "'," +
                    "'" + meta.getOwnerProfile().getUniqueId().toString() + "'," +
                    "'" + block.getWorld() + "'," +
                    "'" + block.getLocation() + "'," +
                    "'" + p.getName() + "'," +
                    "'" + meta.getLore() + "');");

        } catch (SQLException e){
            e.printStackTrace();
        }
        // Todo: hacer el metodo para consultar en la base de datos por los datos de la cabeza
        // SELECT * FROM mapSizes WHERE id = "size_";
    }

}
