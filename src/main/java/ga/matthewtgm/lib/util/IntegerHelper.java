package ga.matthewtgm.lib.util;

import java.util.Random;

public class IntegerHelper {

    private static Random random = new Random();

    public static Integer getRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }

}