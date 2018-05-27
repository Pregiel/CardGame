package com.pregiel.cardgame.Utils;

import com.pregiel.cardgame.Screens.AbstractScreen;
import com.pregiel.cardgame.Screens.EndGameScreen;
import com.pregiel.cardgame.Screens.GameScreen;
import com.pregiel.cardgame.Screens.MainMenuScreen;

/**
 * Created by Pregiel on 27.05.2018.
 */

public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },
    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen();
        }
    },
    END_GAME {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new EndGameScreen();
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}

