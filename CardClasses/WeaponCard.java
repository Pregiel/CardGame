package com.pregiel.cardgame.CardClasses;

import com.badlogic.gdx.graphics.Texture;
import com.pregiel.cardgame.CardType;
import com.pregiel.cardgame.Screens.GameScreen;

/**
 * Created by Pregiel on 17.05.2018.
 */

public class WeaponCard extends Card {
    public WeaponCard() {
        super();
//        setCardTexture(new Texture(GameScreen.WEAPONCARD_PATH));
        setCardType(CardType.WEAPON);
    }

    public WeaponCard(int maxPower) {
        this();
        randomizePower(maxPower);
    }

    @Override
    public boolean use(PlayerCard playerCard) {
        if (playerCard.getPower() > getPower()) {
            playerCard.addPower(1);
        } else {
            playerCard.setPower(getPower());
        }
        return true;
    }
}
