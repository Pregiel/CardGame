package com.pregiel.cardgame.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class EndGameScreen extends AbstractScreen {
    private Label label;
    private TextButton btnPlay;

    public EndGameScreen() {
        super();
    }

    @Override
    public void buildStage() {
        super.buildStage();
        label = getUiFactory().createLabel("YOU DIED", getUiFactory().getLargeFont());
        label.setPosition(getWidth() / 2, 1000, Align.center);
        addActor(label);


        btnPlay = getUiFactory().createButton("PLAY", getUiFactory().getButtonFont());
        btnPlay.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        addActor(btnPlay);

        btnPlay.addListener(getUiFactory().createListener(com.pregiel.cardgame.Utils.ScreenEnum.GAME));}
    @Override
    public void dispose() {
        super.dispose();
        label.remove();
        btnPlay.remove();
    }
}
