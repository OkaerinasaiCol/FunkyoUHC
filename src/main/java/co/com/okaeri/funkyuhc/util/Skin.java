package co.com.okaeri.funkyuhc.util;

import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Skin {

    public String getFromName(String name){
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());

            return JsonParser.parseReader(reader_0).getAsJsonObject().get("id").getAsString();

        } catch (IOException e){
            System.err.println("Could not get skin data from session servers!");
            e.printStackTrace();
            return null;
        }
    }
}
