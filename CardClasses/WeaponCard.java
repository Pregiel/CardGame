package com.pregiel.cardgame.CardClasses;

import com.pregiel.cardgame.CardType;

/**
 * Created by Pregiel on 17.05.2018.
 */

public class WeaponCard extends Card {


    public WeaponCard(int maxPower) {
        super();
        setCardType(CardType.WEAPON);
        randomizePower(maxPower);
    }

    @Override
    public boolean use(PlayerCard playerCard) {
        if (playerCard.getPower() >= getPower()) {
            playerCard.addPower(1);
        } else {
            playerCard.setPower(getPower());
        }
        return true;
    }
}
