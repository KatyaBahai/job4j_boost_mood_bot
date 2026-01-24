package ru.job4j.bmb.content;

import java.util.List;
import java.util.Random;

public class Randomizer {
    private static final Random RND = new Random(System.currentTimeMillis());

    public static int getRandomNumber(int numberOfThings) {
        return RND.nextInt(0, numberOfThings);
    }
}
