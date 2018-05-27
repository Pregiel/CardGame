package com.pregiel.cardgame;

import com.badlogic.gdx.Game;
import com.pregiel.cardgame.Utils.ScreenEnum;
import com.pregiel.cardgame.Utils.ScreenManager;

public class MyGdxGame extends Game {



    //gradlew desktop:run

    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
    }



}
