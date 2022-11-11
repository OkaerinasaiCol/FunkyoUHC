package co.com.okaeri.funkyuhc.controller;

import co.com.okaeri.funkyuhc.FunkyUHC;

import java.time.Duration;

public class GetTime {

    public String toString(Duration duration){
        String time = " ";

        long totalSecs = duration.getSeconds();

        int hours = Math.toIntExact(totalSecs / 3600);
        int minutes = Math.toIntExact((totalSecs % 3600)) / 60;
        int seconds = Math.toIntExact(totalSecs % 60);

        time = String.format("%02d", hours)
                + ":" + String.format("%02d", minutes) +
                ":" + String.format("%02d", seconds);

        return time;
    }
}
