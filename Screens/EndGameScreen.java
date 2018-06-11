package com.pregiel.cardgame.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.pregiel.cardgame.Utils.ScreenEnum;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class EndGameScreen extends AbstractScreen {
    private Label label;
    private TextButton btnPlay, btnMenu;
    private Table table;

    public EndGameScreen() {
        super();
    }

    @Override
    public void buildStage() {
        super.buildStage();
        label = getUiFactory().createLabel("YOU DIED", getUiFactory().getLargeFont());
        label.setPosition(getWidth() / 2, 1000, Align.center);
        addActor(label);

        table = new Table();
        table.setPosition(getWidth() / 2, getHeight() / 2);

        btnPlay = getUiFactory().createButton("PLAY", getUiFactory().getButtonFont());
//        btnPlay.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        table.add(btnPlay);
        table.row().space(30);

        btnMenu = getUiFactory().createButton("MENU", getUiFactory().getButtonFont());
//        btnMenu.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        table.add(btnMenu);

        addActor(table);

        btnPlay.addListener(getUiFactory().createListener(com.pregiel.cardgame.Utils.ScreenEnum.GAME));
        btnMenu.addListener(getUiFactory().createListener(ScreenEnum.MAIN_MENU));
    }

    @Override
    public void dispose() {
        super.dispose();
        label.remove();
        btnPlay.remove();
        table.remove();
    }
}
