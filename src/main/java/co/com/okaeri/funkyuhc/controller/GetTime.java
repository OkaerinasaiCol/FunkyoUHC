package co.com.okaeri.funkyuhc.controller;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class GetTime {

    /**
     * Pasar un lapso de tiempo del formato {@link Duration} al formato {@link String} con el formato<p>
     * /-------------Horas <p>
     * |&#160&#160&#160&#160/---------Minutos <p>
     * |&#160&#160&#160&#160|&#160&#160&#160&#160/-----Segundos <p>
     * 00:00:00
     * @param duration: Tiempo en formato {@link Duration}
     * @return Timpo en formato {@link String}
     */
    public String toString(@NotNull Duration duration) {

        long totalSecs = duration.getSeconds();

        return toString(totalSecs);
    }

    /**
     * Pasar un lapso de tiempo del formato {@link Long} al formato {@link String} con el formato<p>
     * /-------------Horas <p>
     * |&#160&#160&#160&#160/---------Minutos <p>
     * |&#160&#160&#160&#160|&#160&#160&#160&#160/-----Segundos <p>
     * 00:00:00
     * @param totalSecs: Tiempo en formato {@link Long}
     * @return Timpo en formato {@link String}
     */
    public String toString(Long totalSecs) {
        String time;

        int hours = Math.toIntExact(totalSecs / 3600);
        int minutes = Math.toIntExact((totalSecs % 3600)) / 60;
        int seconds = Math.toIntExact(totalSecs % 60);

        time = String.format("%02d", hours)
                + ":" + String.format("%02d", minutes) +
                ":" + String.format("%02d", seconds);
        return time;
    }
}
