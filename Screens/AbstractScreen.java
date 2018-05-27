package com.pregiel.cardgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pregiel.cardgame.Utils.AssetsManager;
import com.pregiel.cardgame.Utils.ScreenManager;
import com.pregiel.cardgame.Utils.UIFactory;

import java.util.ArrayList;

/**
 * Created by Pregiel on 27.05.2018.
 */

public abstract class AbstractScreen extends Stage implements Screen {

//    public AssetsManager assetsManager;
//    public UIFactory uiFactory;

    protected AbstractScreen() {
        super(new StretchViewport(ScreenManager.SCREEN_WIDTH, ScreenManager.SCREEN_HEIGHT, new OrthographicCamera()));
//        assetsManager = new AssetsManager();
//        uiFactory = new UIFactory();
    }

    public abstract void buildStage();

//    public AssetsManager getAssetsManager() {
//        return assetsManager;
//    }

//    public void setAssetsManager(AssetsManager assetsManager) {
//        this.assetsManager = assetsManager;
//    }

//    public UIFactory getUiFactory() {
//        return uiFactory;
//    }

//    public void setUiFactory(UIFactory uiFactory) {
//        this.uiFactory = uiFactory;
//    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Calling to Stage methods
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
}
