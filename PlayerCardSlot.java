package com.pregiel.cardgame;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.pregiel.cardgame.CardClasses.PlayerCard;

/**
 * Created by Pregiel on 30.05.2018.
 */

public class PlayerCardSlot extends CardSlot {
    private Label lblGold, lblHealth;

    public PlayerCardSlot(int slotPositionX, int slotPositionY, int x, int y, int width, int height) {
        super(slotPositionX, slotPositionY, x, y, width, height);
    }

    @Override
    public void redraw() {
        super.redraw();
        lblGold.setText(String.valueOf(((PlayerCard) getCard()).getGold()));
        lblHealth.setText(String.valueOf(((PlayerCard) getCard()).getHealth()));
    }

    public Label getLblGold() {
        return lblGold;
    }

    public void setLblGold(Label lblGold) {
        this.lblGold = lblGold;
    }

    public Label getLblHealth() {
        return lblHealth;
    }

    public void setLblHealth(Label lblHealth) {
        this.lblHealth = lblHealth;
    }
}
