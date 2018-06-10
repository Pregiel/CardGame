package com.pregiel.cardgame.Utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.pregiel.cardgame.CardType;

import java.util.Random;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class AssetsManager extends AssetManager {

    private static final String BACKGROUNDTEXTURE = "cardBackground.jpg";
    private static final String BACKGROUND = "img/background.png";
    private static final String DEFAULTCARD = "defaultCard.png";
    private static final String[] PLAYERCARD = {"cards/player1.png", "cards/player2.png",
                "cards/player3.png", "cards/player4.png", "cards/player5.png", "cards/player6.png"};
    private static final String GOLDCARD = "cards/coin.png";
    private static final String[] MONSTERCARD = {"cards/monster1.png", "cards/monster2.png", "cards/monster3.png"};
    private static final String[] WEAPONCARD = {"cards/weapon1.png", "cards/weapon2.png", "cards/weapon3.png"};
    private static final String LIFEPOTIONCARD = "cards/lifePotion.png";
    private static final String CHESTCARD = "cards/chest.png";


    public AssetsManager() {
        super();
        load(BACKGROUNDTEXTURE, Texture.class);
        load(BACKGROUND, Texture.class);
        load(DEFAULTCARD, Texture.class);
        for (String s : PLAYERCARD) {
            load(s, Texture.class);
        }
        load(GOLDCARD, Texture.class);
        for (String s : MONSTERCARD) {
            load(s, Texture.class);
        }
        for (String s : WEAPONCARD) {
            load(s, Texture.class);
        }
        load(LIFEPOTIONCARD, Texture.class);
        load(CHESTCARD, Texture.class);
        finishLoading();
    }


    public Texture getCardTexture(CardType type) {
        Random r = new Random();
        switch (type) {
            case PLAYER:
                return get(PLAYERCARD[0], Texture.class);
            case MONSTER:
                return get(MONSTERCARD[r.nextInt(MONSTERCARD.length)], Texture.class);
            case WEAPON:
                return get(WEAPONCARD[r.nextInt(WEAPONCARD.length)], Texture.class);
            case GOLD:
                return get(GOLDCARD, Texture.class);
            case HEALTH_POTION:
                return get(LIFEPOTIONCARD, Texture.class);
            case CHEST:
                return get(CHESTCARD, Texture.class);

        }
        return get(DEFAULTCARD, Texture.class);
    }

    public Texture getCardTexture(CardType type, int i) {
        switch (type) {
            case PLAYER:
                return get(PLAYERCARD[i], Texture.class);

            case MONSTER:
                return get(MONSTERCARD[i], Texture.class);

            case WEAPON:
                return get(WEAPONCARD[i], Texture.class);

        }
        return getCardTexture(type);
    }

    public Texture getBackgroundTexture() {
        return get(BACKGROUNDTEXTURE, Texture.class);
    }

    public static int playerAmount() {
        return PLAYERCARD.length;
    }

    public Texture getBackground() {
        Texture texture = get(BACKGROUND, Texture.class);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        return texture;
    }

}
