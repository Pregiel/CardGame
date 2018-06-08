package com.pregiel.cardgame.CardClasses;

import com.pregiel.cardgame.CardType;
import com.pregiel.cardgame.Utils.AssetsManager;
import com.pregiel.cardgame.Utils.ScreenEnum;
import com.pregiel.cardgame.Utils.ScreenManager;

/**
 * Created by Pregiel on 17.05.2018.
 */

public class PlayerCard extends Card {
    private int health;
    private int gold;
    private int character;


    public PlayerCard(int power, int health, int character) {
        super();
        gold = 0;
        this.character = character;
        this.health = health;
        setCardType(CardType.PLAYER);
        setPower(power);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void addHealth(int value) {
        health = health + value;
        if (health <= 0) {
            ScreenManager.getInstance().showScreen(ScreenEnum.END_GAME);
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addGold(int gold) {
        this.gold = this.gold + gold;
    }

    @Override
    public String toString() {
        return "Card{" + "cardType=" + getCardType() +
                ", power=" + getPower() +
                ", health=" + health +
                '}';
    }

    @Override
    public void setCardTexture(AssetsManager assetsManager) {
        setCardTexture(assetsManager.getCardTexture(getCardType(), character));
    }
}
