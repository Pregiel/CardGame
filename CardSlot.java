package com.pregiel.cardgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.pregiel.cardgame.CardClasses.Card;


/**
 * Created by Pregiel on 17.05.2018.
 */

public class CardSlot extends Rectangle{

    private Card card;
    private Texture backgroundTexture;


    public CardSlot(int x, int y, int width, int height) {
//        backgroundTexture = new Texture(com.pregiel.cardgame.Screens.GameScreen.BACKGROUNDTEXTURE_PATH);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
}
