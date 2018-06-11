package com.pregiel.cardgame;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.pregiel.cardgame.CardClasses.PlayerCard;
import com.pregiel.cardgame.Utils.AssetsManager;
import com.pregiel.cardgame.Utils.UIFactory;

/**
 * Created by Pregiel on 30.05.2018.
 */

public class PlayerCardSlot extends CardSlot {
    private Label lblGold;
    private ImageLabel ilblHealth;

    public PlayerCardSlot(int slotPositionX, int slotPositionY, int x, int y, int width, int height) {
        super(slotPositionX, slotPositionY, x, y, width, height);
    }

    @Override
    public void redraw(UIFactory uiFactory, AssetsManager assetsManager) {
        super.redraw(uiFactory, assetsManager);
        lblGold.setText(String.valueOf(((PlayerCard) getCard()).getGold()));
        ilblHealth.setText(String.valueOf(((PlayerCard) getCard()).getHealth()));
    }

    public Label getLblGold() {
        return lblGold;
    }

    public void setLblGold(Label lblGold) {
        this.lblGold = lblGold;
    }

    public ImageLabel getIlblHealth() {
        return ilblHealth;
    }

    public void setIlblHealth(ImageLabel ilblHealth) {
        this.ilblHealth = ilblHealth;
    }


}
