package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Heads {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public Heads(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    public void setHead(@NotNull ItemStack head,
                        @NotNull Player p,
                        @NotNull Block block) {
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        try {
            Statement statment = plugin.db.statement();

            //noinspection ConstantConditions
            statment.executeUpdate("INSERT INTO " +
                    "\"main\".\"heads\"(\"owner\",\"uuid\",\"world\",\"coords\",\"placer\",\"lore\")" +
                    " VALUES (" +
                    "'" + meta.getOwnerProfile().getName() + "'," +
                    "'" + meta.getOwnerProfile().getUniqueId().toString() + "'," +
                    "'" + block.getWorld() + "'," +
                    "'" + block.getLocation() + "'," +
                    "'" + p.getName() + "'," +
                    "'" + meta.getLore() + "');");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getHead(@NotNull Block block) {
        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT * FROM heads WHERE coords =\"" +
                    block.getLocation() + "\";");

            String uuid = data.getString("uuid");
            String owner = data.getString("owner");
            plugin.print(uuid);

            statment.executeUpdate("DELETE FROM 'main'.'heads' WHERE owner IN ('" + owner + "');");

            List<String> player = new ArrayList<>();
            player.add(uuid);
            player.add(owner);

            return player;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clearHeads() {
        try {
            String sql = "DELETE FROM heads;";
            plugin.db.statement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
