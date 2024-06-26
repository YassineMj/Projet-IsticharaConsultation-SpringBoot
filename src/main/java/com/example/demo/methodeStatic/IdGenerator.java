package com.example.demo.methodeStatic;

import java.security.SecureRandom;
import java.util.Random;

public class IdGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
    private static final int ID_LENGTH = 15;

    public static String generateId() {
        Random random = new SecureRandom();
        StringBuilder idBuilder = new StringBuilder();

        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            idBuilder.append(randomChar);
        }

        return idBuilder.toString();
    }
}
