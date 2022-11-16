package co.com.okaeri.funkyuhc.database;

import co.com.okaeri.funkyuhc.FunkyUHC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UHC {

    private FunkyUHC plugin;

    public UHC(FunkyUHC plugin){
        this.plugin = plugin;
    }

    public void addInfo(int round_,
                        Boolean rStarted,
                        Boolean wbBefore,
                        Boolean wbStart,
                        Double border,
                        Double new_size,
                        Long timerDuration,
                        Boolean running){

        int round = 0;

        try {
            Statement statment = plugin.db.statement();

            ResultSet data = statment.executeQuery("SELECT round FROM uhcRound");

            while (data.next()){
                round ++;
            }

            if (round_ > round){

                statment.executeUpdate("INSERT INTO 'main'.'uhcRound'(" +
                        "'round','rStarted','wbBefore','wbStart','border','newSize','rRest','running') VALUES " +
                        "('" + round_ +
                        "','" + ( (rStarted) ? 1 : 0 ) +
                        "','" + ( (wbBefore) ? 1 : 0 ) +
                        "','" + ( (wbStart) ? 1 : 0 ) +
                        "','" + border +
                        "','" + new_size +
                        "','" + timerDuration +
                        "','" + running +
                        "');");
            } else {
                String sql = "UPDATE 'main'.'uhcRound' SET 'border'=" + ( (rStarted) ? 1 : 0 ) + " ," +
                        " 'rRest' = '" + timerDuration + "' ," +
                        " 'wbBefore' = '" + ( (wbBefore) ? 1 : 0 ) + "' ," +
                        " 'wbStart' = '" + ( (wbStart) ? 1 : 0 ) + "' " +
                        "WHERE 'round' = '" + round_ + "'";

                statment.executeUpdate(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
