package com.pregiel.cardgame.Utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.pregiel.cardgame.CardType;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class AssetsManager extends AssetManager{
//    private AssetManager assetManager;

    public static final String BACKGROUNDTEXTURE_PATH = "cardBackground.jpg";
    public static final String DEFAULTCARD_PATH = "defaultCard.png";
    public static final String PLAYERCARD_PATH = "playerCard.png";
    public static final String GOLDCARD_PATH = "goldCard.png";
    public static final String MONSTERCARD_PATH = "monsterCard.png";
    public static final String WEAPONCARD_PATH = "weaponCard.png";


    public AssetsManager() {
//        this.assetManager = new AssetManager();
        super();
        load(BACKGROUNDTEXTURE_PATH, Texture.class);
        load(DEFAULTCARD_PATH, Texture.class);
        load(PLAYERCARD_PATH, Texture.class);
        load(GOLDCARD_PATH, Texture.class);
        load(MONSTERCARD_PATH, Texture.class);
        load(WEAPONCARD_PATH, Texture.class);
        finishLoading();
    }



    public Texture getCardTexture(CardType type) {
        switch (type) {
            case PLAYER:
                return get(PLAYERCARD_PATH, Texture.class);
            case MONSTER:
                return get(MONSTERCARD_PATH, Texture.class);
            case WEAPON:
                return get(WEAPONCARD_PATH, Texture.class);
            case GOLD:
                return get(GOLDCARD_PATH, Texture.class);
        }
        return get(DEFAULTCARD_PATH, Texture.class);
    }

    public Texture getBackgroundTexture() {
        return get(BACKGROUNDTEXTURE_PATH, Texture.class);
    }



}
