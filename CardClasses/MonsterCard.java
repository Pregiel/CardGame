package com.pregiel.cardgame.CardClasses;

import com.pregiel.cardgame.CardType;

/**
 * Created by Pregiel on 17.05.2018.
 */

public class MonsterCard extends Card {

    public MonsterCard(int maxPower) {
        super();
        setCardType(CardType.MONSTER);
        randomizePower(maxPower);
    }

    public boolean isAlive() {
        return getPower() > 0;
    }

    @Override
    public boolean use(PlayerCard playerCard) {
        int currentPower = getPower();
        addPower(-playerCard.getPower());
        playerCard.addPower(-currentPower);
        playerCard.addHealth(-getPower());
        return true;
//        return !isAlive();
    }
}
