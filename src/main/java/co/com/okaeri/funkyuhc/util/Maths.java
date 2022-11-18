package co.com.okaeri.funkyuhc.util;

import java.util.ArrayList;
import java.util.List;

public class Maths {

    public List<List<Integer>> polygon_coords(int points, int diameter){

        double theta = 1 * Math.PI / (points / 2.0);

        List<List<Integer>> coords = new ArrayList<>();

        for (int i = 0; i < points; ++i) {
            List<Integer> coord = new ArrayList<>();

            int x = (int) Math.cos(theta * i) * diameter;
            int y = (int) Math.sin(theta * i) * diameter;

            coord.add(x);
            coord.add(y);
            coords.add(coord);
        }

        return coords;
    }

}
