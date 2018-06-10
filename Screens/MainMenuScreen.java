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
import com.pregiel.cardgame.Utils.ScreenManager;
import com.pregiel.cardgame.Utils.UIFactory;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class MainMenuScreen extends com.pregiel.cardgame.Screens.AbstractScreen {

    private Label label;
    private TextButton btnPlay, btnNextCharacter, btnPrevCharacter;
    private Image imgCharacter;

    public MainMenuScreen() {
        super();
    }

    @Override
    public void buildStage() {
        super.buildStage();
        label = getUiFactory().createLabel("CARD GAME", getUiFactory().getLargeFont());
        label.setPosition(getWidth() / 2, 1000, Align.center);
        addActor(label);

        Table table = new Table();

        btnPrevCharacter = getUiFactory().createButton("<", getUiFactory().getButtonFont());
        btnPrevCharacter.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainMenuScreen.prevCharacter();
                imgCharacter.setDrawable(new TextureRegionDrawable(new TextureRegion(getAssetsManager().getCardTexture(CardType.PLAYER, character))));

                return false;
            }
        });
        table.add(btnPrevCharacter);
        imgCharacter = getUiFactory().drawImage(getAssetsManager().getCardTexture(CardType.PLAYER, character));
        table.add(imgCharacter).width(getWidth() / 3).height(getHeight() / 3);
        btnNextCharacter = getUiFactory().createButton(">", getUiFactory().getButtonFont());
        btnNextCharacter.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainMenuScreen.nextCharacter();
                imgCharacter.setDrawable(new TextureRegionDrawable(new TextureRegion(getAssetsManager().getCardTexture(CardType.PLAYER, character))));

                return false;
            }
        });
        table.add(btnNextCharacter);

        table.row();


        btnPlay = getUiFactory().createButton("PLAY", getUiFactory().getButtonFont());
        table.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        table.add(btnPlay).colspan(3).fillX();

        addActor(table);
        btnPlay.addListener(getUiFactory().createListener(com.pregiel.cardgame.Utils.ScreenEnum.GAME));
    }

    @Override
    public void dispose() {
        super.dispose();
        label.remove();
        btnPlay.remove();
        btnPrevCharacter.remove();
        btnNextCharacter.remove();
        imgCharacter.remove();
    }


    private static int character = 0;

    public static int getCharacter() {
        return character;
    }

    private static void nextCharacter() {
        if (character < AssetsManager.playerAmount() - 1) {
            character = character + 1;
        } else {
            character = 0;
        }
    }

    private static void prevCharacter() {
        if (character > 0) {
            character = character - 1;
        } else {
            character = AssetsManager.playerAmount() - 1;
        }
    }
}
