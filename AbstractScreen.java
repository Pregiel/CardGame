package com.pregiel.cardgame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

/**
 * Created by Pregiel on 27.05.2018.
 */

public abstract class AbstractScreen extends Stage implements Screen {

    protected AbstractScreen() {
        super( new StretchViewport(320.0f, 240.0f, new OrthographicCamera()) );
    }
}
