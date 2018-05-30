package com.pregiel.cardgame;

import com.pregiel.cardgame.CardClasses.Card;

import java.util.Random;

/**
 * Created by Pregiel on 17.05.2018.
 */

public enum CardType {
    NONE, PLAYER, MONSTER, GOLD, WEAPON, HEALTH_POTION, CHEST;

    private static final int size = values().length - 1;

    public static CardType getRandomCardType(CardType... excludeCardTypes) {
        Random r = new Random();
        int value = r.nextInt(size-1) + 1;
        CardType randomCardType = getCardType(value);
        for (CardType type: excludeCardTypes) {
            if (type == randomCardType) {
                randomCardType = getRandomCardType(excludeCardTypes);
                break;
            }
        }
        return randomCardType;
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

            case 4:
                return HEALTH_POTION;

            case 5:
                return CHEST;

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

            case HEALTH_POTION:
                return "HEALTH_POTION";

            case CHEST:
                return "CHEST";

            default:
                return "NONE";
        }
    }
}
