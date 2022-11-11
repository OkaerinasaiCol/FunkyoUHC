package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    }

    public List<String> getHead(Block block){
        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT * FROM heads WHERE coords =\"" +
                    block.getLocation() + "\";");

            String uuid = data.getString("uuid");
            String owner = data.getString("owner");
            plugin.print(uuid);

            //FIXME: error al boorar
            statment.executeUpdate("DELETE * FROM heads WHERE owner = \"" + owner + "\";");

            List<String> player = new ArrayList<>();
            player.add(uuid);
            player.add(owner);

            return player;


        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
