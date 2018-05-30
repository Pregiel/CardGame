package com.pregiel.cardgame.CardClasses;

import com.badlogic.gdx.graphics.Texture;
import com.pregiel.cardgame.CardType;
import com.pregiel.cardgame.Utils.AssetsManager;

import java.util.Random;


/**
 * Created by Pregiel on 17.05.2018.
 */

public class Card {
    private int power;
    private CardType cardType;
    private Texture cardTexture;

    public Card() {
        cardType = CardType.NONE;
        power = 0;
    }

    public Card(int maxPower) {
        this();
        randomizePower(maxPower);
    }

    public void randomizePower(int maxPower) {
        Random r = new Random();
        power = r.nextInt(maxPower - 1) + 1;

    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void addPower(int value) {
        power = power + value;
        if (power < 0)
            power = 0;
    }

    public Texture getCardTexture() {
        return cardTexture;
    }

    public void setCardTexture(Texture cardTexture) {
        this.cardTexture = cardTexture;
    }

    public void setCardTexture(AssetsManager assetsManager) {
        setCardTexture(assetsManager.getCardTexture(getCardType()));
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "Card{" + "cardType=" + cardType +
                ", power=" + power +
                '}';
    }

    public boolean use(PlayerCard playerCard) {
        return true;
    }
}
