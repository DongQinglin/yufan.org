package me.dongqinglin.flina.rest.helper;

import java.util.Random;

public class RandomHelper {
    public static String getNumber(int numberOfDigits) {
        if(numberOfDigits < 0) {
            throw new IllegalArgumentException("param numberOfDigits can not less than 0");
        }
        Random random = new Random();
        String result = "";
        for (int i = 0; i < numberOfDigits; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
}
