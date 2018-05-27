package com.pregiel.cardgame.CardClasses;

import com.badlogic.gdx.graphics.Texture;
import com.pregiel.cardgame.CardType;
import com.pregiel.cardgame.Screens.GameScreen;

/**
 * Created by Pregiel on 17.05.2018.
 */

public class GoldCard extends Card {
    public GoldCard() {
        super();
//        setCardTexture(new Texture(GameScreen.GOLDCARD_PATH));
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
