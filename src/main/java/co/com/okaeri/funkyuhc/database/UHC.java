package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;

public class UHC {

    @SuppressWarnings("FieldMayBeFinal")
    private FunkyUHC plugin;

    public UHC(FunkyUHC plugin) {
        this.plugin = plugin;
    }

    public void addInfo(int round_,
                        Boolean rStarted,
                        Boolean wbBefore,
                        Boolean wbStart,
                        Double border,
                        Double new_size,
                        Long timerDuration,
                        Boolean running) {

        int round = 0;

        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT round FROM uhcRound");

            while (data.next()) {
                round++;
            }

            if (round_ > round) {

                statment.executeUpdate("INSERT INTO 'main'.'uhcRound'(" +
                        "'round','rStarted','wbBefore','wbStart','border','newSize','rRest','running') VALUES " +
                        "('" + round_ +
                        "','" + ((rStarted) ? 1 : 0) +
                        "','" + ((wbBefore) ? 1 : 0) +
                        "','" + ((wbStart) ? 1 : 0) +
                        "','" + border +
                        "','" + new_size +
                        "','" + timerDuration +
                        "','" + ((running) ? 1 : 0) +
                        "');");
            } else {
                String sql = "UPDATE uhcRound SET border = " + border + " , rRest = " + timerDuration + " ," +
                        " wbBefore = " + ((wbBefore) ? 1 : 0) + " ," +
                        " wbStart = " + ((wbStart) ? 1 : 0) + " " +
                        "WHERE round = " + round_ + "";

                //plugin.print(sql);

                statment.executeUpdate(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean getRoundStarted(int round) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM uhcRound WHERE round =\"" +
                    round + "\";").getBoolean("rStarted");

        } catch (SQLException ignored) {
        }

        return false;
    }

    public Boolean getWorldBorderReduceStarted(int round) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM uhcRound WHERE round =\"" +
                    round + "\";").getBoolean("wbStart");

        } catch (SQLException ignored) {
        }

        return false;
    }

    public Boolean getWorldBorderBeforeStarted(int round) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM uhcRound WHERE round =\"" +
                    round + "\";").getBoolean("wbBefore");

        } catch (SQLException ignored) {
        }

        return false;
    }

    public Boolean getPaused(int round) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM uhcRound WHERE round =\"" +
                    round + "\";").getBoolean("running");

        } catch (SQLException ignored) {
        }

        return false;
    }

    public int getRound() {
        try {
            Statement statment = plugin.db.statement();

            ResultSet rs = statment.executeQuery("SELECT * FROM uhcRound");
            rs.last();
            return rs.getRow();

        } catch (SQLException ignored) {
        }

        return 1;
    }

    public long getUhcTimeDuration(int round) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM uhcRound WHERE round =\"" +
                    round + "\";").getLong("rRest");

        } catch (SQLException ignored) {
        }

        return Duration.between(plugin.startTime, LocalDateTime.now()).getSeconds();
    }

    public double getNewSize(int round) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM uhcRound WHERE round =\"" +
                    round + "\";").getDouble("newSize");

        } catch (SQLException ignored) {
        }

        return 0.0;
    }

    public double getBorder(int round) {
        try {
            Statement statment = plugin.db.statement();

            return statment.executeQuery("SELECT * FROM uhcRound WHERE round =\"" +
                    round + "\";").getDouble("border");

        } catch (SQLException ignored) {
        }

        return 0.0;
    }

    public void clear() {
        try {
            String sql = "DELETE FROM uhcRound;";
            plugin.db.statement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
