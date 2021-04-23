package ga.matthewtgm.lib.util;

import java.util.Random;

public class IntegerHelper {

    private static final Random random = new Random();

    /**
     * @param min The minimum number that can be returned.
     * @param max The maximum number that can be returned.
     * @return A number within the given range.
     * @author MatthewTGM
     */
    public static Integer getRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }

}