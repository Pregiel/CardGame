package com.pregiel.cardgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.pregiel.cardgame.CardClasses.Card;

import javax.swing.GroupLayout;


/**
 * Created by Pregiel on 17.05.2018.
 */

public class CardSlot extends Group{

    private Card card;
    private Texture backgroundTexture;

    private int slotPositionX, slotPositionY;





    public CardSlot(int slotPositionX, int slotPositionY, int x, int y, int width, int height) {
//        backgroundTexture = new Texture(com.pregiel.cardgame.Screens.GameScreen.BACKGROUNDTEXTURE_PATH);
        super();
        this.slotPositionX = slotPositionX;
        this.slotPositionY = slotPositionY;
        setPosition(x, y);
        setSize(width, height);
    }

    public int getSlotPositionX() {
        return slotPositionX;
    }

    public int getSlotPositionY() {
        return slotPositionY;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public boolean isClickable(int playerX, int playerY) {
        return (playerX + 1 == slotPositionX && playerY == slotPositionY) ||
                (playerX - 1 == slotPositionX && playerY == slotPositionY) ||
                (playerX == slotPositionX && playerY + 1 == slotPositionY) ||
                (playerX == slotPositionX && playerY - 1 == slotPositionY);
    }
}
