package com.pregiel.cardgame;

import com.badlogic.gdx.Game;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class ScreenManager {

    private static ScreenManager instance;

    private Game game;
    private ScreenManager() {
        super();
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(Game game) {
        this.game = game;
    }

    
}
