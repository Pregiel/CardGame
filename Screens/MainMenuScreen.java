package com.pregiel.cardgame.Screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.pregiel.cardgame.CardType;
import com.pregiel.cardgame.Utils.AssetsManager;
import com.pregiel.cardgame.Utils.UIFactory;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class MainMenuScreen extends com.pregiel.cardgame.Screens.AbstractScreen {

    private Label label;
    private TextButton btnPlay, btnNextCharacter, btnPrevCharacter;
    private Image imgCharacter;
    private UIFactory uiFactory;
    private AssetsManager assetsManager;

    public MainMenuScreen() {
        super();
        uiFactory = new UIFactory();
        assetsManager = new AssetsManager();
    }

    @Override
    public void buildStage() {
        label = uiFactory.createLabel("CARD GAME", uiFactory.getLargeFont());
        label.setPosition(getWidth() / 2, 1000, Align.center);
        addActor(label);

        Table table = new Table();

        btnPrevCharacter = uiFactory.createButton("<", uiFactory.getButtonFont());
        btnPrevCharacter.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainMenuScreen.prevCharacter();
                imgCharacter.setDrawable(new TextureRegionDrawable(new TextureRegion(assetsManager.getCardTexture(CardType.PLAYER, character))));

                return false;
            }
        });
        table.add(btnPrevCharacter);
        imgCharacter = uiFactory.drawImage(assetsManager.getCardTexture(CardType.PLAYER, character));
        table.add(imgCharacter).width(getWidth() / 3).height(getHeight() / 3);
        btnNextCharacter = uiFactory.createButton(">", uiFactory.getButtonFont());
        btnNextCharacter.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainMenuScreen.nextCharacter();
                imgCharacter.setDrawable(new TextureRegionDrawable(new TextureRegion(assetsManager.getCardTexture(CardType.PLAYER, character))));

                return false;
            }
        });
        table.add(btnNextCharacter);

        table.row();


        btnPlay = uiFactory.createButton("PLAY", uiFactory.getButtonFont());
        table.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        table.add(btnPlay).colspan(3).fillX();

        addActor(table);
        btnPlay.addListener(uiFactory.createListener(com.pregiel.cardgame.Utils.ScreenEnum.GAME));
    }

    @Override
    public void dispose() {
        super.dispose();
        label.remove();
        btnPlay.remove();
        btnPrevCharacter.remove();
        btnNextCharacter.remove();
    }


    private static int character = 0;

    public static int getCharacter() {
        return character;
    }

    public static void nextCharacter() {
        if (character < AssetsManager.playerAmount()) {
            character = character + 1;
        } else {
            character = 0;
        }
    }

    public static void prevCharacter() {
        if (character > 0) {
            character = character - 1;
        } else {
            character = AssetsManager.playerAmount() - 1;
        }
    }
}
