package com.pregiel.cardgame.CardClasses;

import com.pregiel.cardgame.CardType;

/**
 * Created by Pregiel on 17.05.2018.
 */

public class MonsterCard extends Card {

    private final int maxPower;

    public MonsterCard(int maxPower) {
        super();
        setCardType(CardType.MONSTER);
        randomizePower(maxPower);
        this.maxPower = getPower();
    }

    public boolean isAlive() {
        return getPower() > 0;
    }

    @Override
    public boolean use(PlayerCard playerCard) {
        int currentPower = getPower();
        if (playerCard.getPower() > 0) {
            addPower(-playerCard.getPower());
            playerCard.addPower(-currentPower);
        } else {
            addPower(-playerCard.getHealth());
            playerCard.addHealth(-currentPower);
//            return true;
        }
        return false;
//        return !isAlive();
    }

    public int getMaxPower() {
        return maxPower;
    }
}
