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
    private Label lblScore, lblScoreText, lblHishscore, lblHishscoreText;
    private TextButton btnPlay, btnMenu;
    private Table table;
    private int score;

    public EndGameScreen(int score) {
        super();
        this.score = score;
    }

    @Override
    public void buildStage() {
        super.buildStage();
        table = new Table();
        table.setPosition(getWidth() / 2, getHeight() / 2);

        if (score > MainMenuScreen.getHighScore()) {
            lblScoreText = getUiFactory().createLabel("NEW HIGH SCORE:", getUiFactory().getMediumFont());
            lblHishscoreText = getUiFactory().createLabel("LAST HIGH SCORE: ", getUiFactory().getEndHscoreFont());
        } else {
            lblScoreText = getUiFactory().createLabel("SCORE:", getUiFactory().getMediumFont());
            lblHishscoreText = getUiFactory().createLabel("HIGH SCORE: ", getUiFactory().getEndHscoreFont());
        }
        lblScore = getUiFactory().createLabel(String.valueOf(score), getUiFactory().getLargeFont());
        table.add(lblScoreText).colspan(2);
        table.row();
        table.add(lblScore).colspan(2);

        table.row().space(20);
        lblHishscore = getUiFactory().createLabel(String.valueOf(MainMenuScreen.getHighScore()), getUiFactory().getEndHscoreFont());

        table.add(lblHishscoreText).align(Align.right);
        table.add(lblHishscore).align(Align.left);

        table.row().space(50);
        btnPlay = getUiFactory().createButton("PLAY", getUiFactory().getButtonFont());
        table.add(btnPlay).colspan(2);
        table.row().space(30);

        btnMenu = getUiFactory().createButton("MENU", getUiFactory().getButtonFont());
        table.add(btnMenu).colspan(2);

        addActor(table);

        btnPlay.addListener(getUiFactory().createListener(com.pregiel.cardgame.Utils.ScreenEnum.GAME));
        btnMenu.addListener(getUiFactory().createListener(ScreenEnum.MAIN_MENU));

        MainMenuScreen.setHighScore(score);
    }

    @Override
    public void dispose() {
        super.dispose();
        btnPlay.remove();
        btnMenu.remove();
        table.remove();
        lblHishscore.remove();
        lblHishscoreText.remove();
        lblScore.remove();
        lblScoreText.remove();
    }
}
