package com.pregiel.cardgame.CardClasses;

import com.badlogic.gdx.graphics.Texture;
import com.pregiel.cardgame.CardType;
import com.pregiel.cardgame.MyGdxGame;

/**
 * Created by Pregiel on 17.05.2018.
 */

public class MonsterCard extends Card {
    public MonsterCard() {
        super();
        setCardTexture(new Texture(MyGdxGame.MONSTERCARD_PATH));
        setCardType(CardType.MONSTER);
    }

    public MonsterCard(int maxPower) {
        this();
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
