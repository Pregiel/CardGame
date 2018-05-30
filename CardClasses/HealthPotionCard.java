package com.pregiel.cardgame.CardClasses;

import com.pregiel.cardgame.CardType;

/**
 * Created by Pregiel on 30.05.2018.
 */

public class HealthPotionCard extends Card {
    public HealthPotionCard(int maxPower) {
        super();
        setCardType(CardType.HEALTH_POTION);
        randomizePower(maxPower);
    }

    @Override
    public boolean use(PlayerCard playerCard) {
        playerCard.addHealth(getPower());
        return true;
    }
}
