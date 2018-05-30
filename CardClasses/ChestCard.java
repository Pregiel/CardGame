package com.pregiel.cardgame.CardClasses;

import com.pregiel.cardgame.CardType;

/**
 * Created by Pregiel on 30.05.2018.
 */

public class ChestCard extends Card {
    public ChestCard() {
        super();
        setCardType(CardType.CHEST);
        setPower(-1);
    }

    @Override
    public boolean use(PlayerCard playerCard) {
        return false;
    }
}
