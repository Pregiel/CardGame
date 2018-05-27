package com.pregiel.cardgame.Screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.pregiel.cardgame.Utils.AssetsManager;
import com.pregiel.cardgame.Utils.UIFactory;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class MainMenuScreen extends com.pregiel.cardgame.Screens.AbstractScreen {

    //    private Texture playTexture;
    private Label label;
    private TextButton btnPlay;
    private UIFactory uiFactory;
    private AssetsManager assetsManager;

    public MainMenuScreen() {
        super();
        uiFactory = new UIFactory();
        assetsManager = new AssetsManager();
//        playTexture = new Texture(Gdx.files.internal("img/button.jpg"));
    }

    @Override
    public void buildStage() {
        label = uiFactory.createLabel("CARD GAME", uiFactory.getLargeFont());
        label.setPosition(getWidth() / 2, 1000, Align.center);
        addActor(label);


        btnPlay = uiFactory.createButton("PLAY", uiFactory.getButtonFont());
        btnPlay.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        addActor(btnPlay);

        btnPlay.addListener(uiFactory.createListener(com.pregiel.cardgame.Utils.ScreenEnum.GAME));
    }

    @Override
    public void dispose() {
        super.dispose();
        label.remove();
        btnPlay.remove();
//        playTexture.dispose();
    }
}
