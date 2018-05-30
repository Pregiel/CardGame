package com.pregiel.cardgame.CardClasses;

import com.pregiel.cardgame.CardType;

/**
 * Created by Pregiel on 17.05.2018.
 */

public class GoldCard extends Card {
    public GoldCard() {
        super();
        setCardType(CardType.GOLD);
    }

    public GoldCard(int maxPower) {
        this();
        randomizePower(maxPower);
    }

    @Override
    public boolean use(PlayerCard playerCard) {
        playerCard.addGold(getPower());
        return true;
    }
}
