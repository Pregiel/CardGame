package com.pregiel.cardgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pregiel.cardgame.Utils.AssetsManager;
import com.pregiel.cardgame.Utils.ScreenManager;
import com.pregiel.cardgame.Utils.UIFactory;


/**
 * Created by Pregiel on 27.05.2018.
 */

public abstract class AbstractScreen extends Stage implements Screen {

    protected AbstractScreen() {
        super(new StretchViewport(ScreenManager.SCREEN_WIDTH, ScreenManager.SCREEN_HEIGHT, new OrthographicCamera()));

        uiFactory = new UIFactory();
        assetsManager = new AssetsManager();
    }

    private Image background;
    private UIFactory uiFactory;
    private AssetsManager assetsManager;

    public UIFactory getUiFactory() {
        return uiFactory;
    }

    public AssetsManager getAssetsManager() {
        return assetsManager;
    }

    public void buildStage() {
        background = uiFactory.drawBackground(assetsManager);
        addActor(background);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.act(delta);
        super.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        super.dispose();
        background.remove();
    }
}
