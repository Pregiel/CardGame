package com.pregiel.cardgame;

import java.util.Random;

/**
 * Created by Pregiel on 17.05.2018.
 */

public enum CardType {
    NONE, PLAYER, MONSTER, GOLD, WEAPON;

    private static final int size = values().length - 1;

    public static CardType getRandomCardType() {
        Random r = new Random();
        int value = r.nextInt(size-1) + 1;

        return getCardType(value);
    }

    public static CardType getCardType(int value) {
        switch (value) {
            case 0:
                return PLAYER;

            case 1:
                return MONSTER;

            case 2:
                return GOLD;

            case 3:
                return WEAPON;

            default:
                return NONE;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case PLAYER:
                return "PLAYER";

            case MONSTER:
                return "MONSTER";

            case GOLD:
                return "GOLD";

            case WEAPON:
                return "WEAPON";

            default:
                return "NONE";
        }
    }
}
