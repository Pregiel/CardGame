package com.pregiel.cardgame.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Pregiel on 27.05.2018.
 */

public class UIFactory {
    private static final String FONT_PATH = "fonts/impact.ttf";
    private static final String SKIN_PATH = "skins/default/skin/uiskin.json";

    private static final int DEFAULT_FONT_SIZE = 48;
    private static final int CARDTEXT_SIZE = 36;
    private static final int BUTTONTEXT_SIZE = 130;
    private static final int LARGETEXT_SIZE = 120;
    private static final int TOPBATTEXT_SIZE = 56;

    private static FreeTypeFontGenerator generator;

    private Skin skin;
    private BitmapFont cardDescFont, buttonFont, largeFont, topbarFont;

    public UIFactory() {
        cardDescFont = generateFont(CARDTEXT_SIZE);
        buttonFont = generateFont(BUTTONTEXT_SIZE);
        largeFont = generateFont(LARGETEXT_SIZE);
        topbarFont = generateFont(TOPBATTEXT_SIZE);
        skin = new Skin(Gdx.files.internal(SKIN_PATH));
    }

    public BitmapFont getCardDescFont() {
        return cardDescFont;
    }

    public BitmapFont getButtonFont() {
        return buttonFont;
    }

    public BitmapFont getLargeFont() {
        return largeFont;
    }

    public BitmapFont getTopbarFont() {
        return topbarFont;
    }

    public void dispose() {
        cardDescFont.dispose();
        buttonFont.dispose();
        largeFont.dispose();
        topbarFont.dispose();
        skin.dispose();
    }

    public Skin getSkin() {
        return new Skin(Gdx.files.internal(SKIN_PATH));
    }

    public FreeTypeFontGenerator getGenerator() {
        if (generator == null) {
            generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
        }
        return generator;
    }

    public BitmapFont generateFont(int size) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;

        return getGenerator().generateFont(parameter);
    }

    public ImageButton createButton(Texture texture) {
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public InputListener createListener(final ScreenEnum dstScreen, final Object... params) {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager.getInstance().showScreen(dstScreen);
                return false;
            }
        };
    }

    public TextButton createButton(String text, BitmapFont font) {
        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
        textButtonStyle.font = font;

        return new TextButton(text, textButtonStyle);
    }

    public Label createLabel(String text, int size) {
        Label.LabelStyle labelStyle = getSkin().get(Label.LabelStyle.class);
        labelStyle.font = generateFont(size);

        return new Label(text, labelStyle);
    }

    public Label createLabel(String text, BitmapFont font) {
        Label.LabelStyle labelStyle = getSkin().get(Label.LabelStyle.class);
        labelStyle.font = font;

        return new Label(text, labelStyle);
    }

    public Label createCardDescLabel(String text) {
        return createLabel(text, getCardDescFont());
    }

    public Image drawImage(Texture texture) {
        return new Image(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public Image drawBackground(AssetsManager assetsManager) {
        Texture texture = assetsManager.getBackground();
        TextureRegion textureRegion = new TextureRegion(texture);
        textureRegion.setRegion(0, 0,
                texture.getWidth()*2, texture.getHeight()*3);

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        Image image = new Image();
        image.setDrawable(textureRegionDrawable);
        image.setSize(ScreenManager.SCREEN_WIDTH, ScreenManager.SCREEN_HEIGHT);

        return image;
    }

}
