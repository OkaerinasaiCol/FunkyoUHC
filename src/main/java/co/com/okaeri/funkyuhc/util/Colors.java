package co.com.okaeri.funkyuhc.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Colors {
    public Map<String, String> colors = new HashMap<>();

    public String black;
    public String dark_blue;
    public String dark_green;
    public String dark_aqua;
    public String dark_red;
    public String dark_purple;
    public String gold;
    public String gray;
    public String dark_gray;
    public String blue;
    public String green;
    public String aqua;
    public String red;
    public String light_purple;
    public String yellow;
    public String white;
    public String obfuscated = "§k";
    public String bold = "§l";
    public String strike = "§m";
    public String underline = "§n";
    public String italic = "§o";
    public String reset = "§r";

    public Colors(){
        black = "§0";
        colors.put("black", black);

        dark_blue = "§1";
        colors.put("dark_blue", dark_blue);

        dark_green = "§2";
        colors.put("dark_green", dark_green);

        dark_aqua = "§3";
        colors.put("dark_aqua", dark_aqua);

        dark_red = "§4";
        colors.put("dark_red", dark_red);

        dark_purple = "§5";
        colors.put("dark_purple", dark_purple);

        gold = "§6";
        colors.put("gold", gold);

        gray = "§7";
        colors.put("gray", gray);

        dark_gray = "§8";
        colors.put("dark_gray", dark_gray);

        blue = "§9";
        colors.put("blue", blue);

        green = "§a";
        colors.put("green", green);

        aqua = "§b";
        colors.put("aqua", aqua);

        red = "§c";
        colors.put("red", red);

        light_purple = "§d";
        colors.put("light_purple", light_purple);

        yellow = "§e";
        colors.put("yellow", yellow);

        white = "§f";
        colors.put("white", white);
    }

}
