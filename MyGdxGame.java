package com.pregiel.cardgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.pregiel.cardgame.CardClasses.Card;
import com.pregiel.cardgame.CardClasses.GoldCard;
import com.pregiel.cardgame.CardClasses.MonsterCard;
import com.pregiel.cardgame.CardClasses.PlayerCard;
import com.pregiel.cardgame.CardClasses.WeaponCard;

public class MyGdxGame extends ApplicationAdapter {
    private static final double CARDSLOT_WIDTH_RATIO = 0.3;
    private static final double CARDSLOT_HEIGHT_RATIO = 0.3;
    private static final double CARDSLOT_PADDING_RATIO = 0.025;

    private int CARD_PADDING;

    public static final String BACKGROUNDTEXTURE_PATH = "cardBackground.jpg";
    public static final String DEFAULTCARD_PATH = "defaultCard.png";
    public static final String PLAYERCARD_PATH = "playerCard.png";
    public static final String GOLDCARD_PATH = "goldCard.png";
    public static final String MONSTERCARD_PATH = "monsterCard.png";
    public static final String WEAPONCARD_PATH = "weaponCard.png";

    private static final int PLAYER_DEFAULT_HEALTH = 10;
    private static final int PLAYER_DEFAULT_POWER = 6;

    private static final int MONSTER_MAX_POWER = 15;
    private static final int GOLD_MAX_POWER = 10;
    private static final int WEAPON_MAX_POWER = 12;

    private int playerPositionX, playerPositionY;

    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private CardSlot[][] cardSlots;

    //gradlew desktop:run

    @Override
    public void create() {
        int SCREEN_WIDTH = 720;
        int SCREEN_HEIGHT = 1280;

        int CARDSLOT_WIDTH = (int) (SCREEN_WIDTH * CARDSLOT_WIDTH_RATIO);  //140
        int CARDSLOT_HEIGHT = (int) (SCREEN_HEIGHT * CARDSLOT_HEIGHT_RATIO);  //250
        int CARDSLOT_PADDING = (int) (SCREEN_WIDTH * CARDSLOT_PADDING_RATIO);  //8


        CARD_PADDING = (int) (SCREEN_WIDTH * CARDSLOT_PADDING_RATIO);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);


        cardSlots = new CardSlot[3][3];
        for (int i = 0; i < 3; i++) {
            for (int ii = 0; ii < 3; ii++) {
                cardSlots[i][ii] = new CardSlot();
                cardSlots[i][ii].x = CARDSLOT_PADDING * (i + 1) + CARDSLOT_WIDTH * i;
                cardSlots[i][ii].y = CARDSLOT_PADDING * (ii + 1) + CARDSLOT_HEIGHT * ii;
                cardSlots[i][ii].width = CARDSLOT_WIDTH;
                cardSlots[i][ii].height = CARDSLOT_HEIGHT;
            }
        }

        randomizeSlots();
        spawnPlayer();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (CardSlot[] slots : cardSlots) {
            for (CardSlot slot : slots) {
                batch.draw(slot.getBackgroundTexture(), slot.x, slot.y, slot.width, slot.height);
                batch.draw(slot.getCard().getCardTexture(), slot.x, slot.y, slot.width, slot.height);

                switch (slot.getCard().getCardType()) {
                    case PLAYER:
                        font.draw(batch, "Gold: " + String.valueOf(((PlayerCard) slot.getCard()).getGold()), slot.x + CARD_PADDING, slot.y + 6 * CARD_PADDING);
                        font.draw(batch, "Health: " + String.valueOf(((PlayerCard) slot.getCard()).getHealth()), slot.x + CARD_PADDING, slot.y + 4 * CARD_PADDING);
                        font.draw(batch, "Power: " + String.valueOf(slot.getCard().getPower()), slot.x + CARD_PADDING, slot.y + 2 * CARD_PADDING);
                        break;

                    case GOLD:
                        font.draw(batch, "Amount: " + String.valueOf(slot.getCard().getPower()), slot.x + CARD_PADDING, slot.y + 2 * CARD_PADDING);
                        break;

                    case WEAPON:
                        font.draw(batch, "Power: " + String.valueOf(slot.getCard().getPower()), slot.x + CARD_PADDING, slot.y + 2 * CARD_PADDING);
                        break;

                    case MONSTER:
                        font.draw(batch, "Power: " + String.valueOf(slot.getCard().getPower()), slot.x + CARD_PADDING, slot.y + 2 * CARD_PADDING);
                        break;
                }
            }
        }
        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (getPlayerCard().isAlive()) {
                for (int i = 0; i < 3; i++) {
                    for (int ii = 0; ii < 3; ii++) {
                        if (cardSlots[i][ii].contains(touch.x, touch.y) && slotIsClickable(i, ii)) {
                            System.out.println("Move to: X: " + i + " Y: " + ii + " " + cardSlots[i][ii].getCard());
                            if (cardSlots[i][ii].getCard().use(getPlayerCard())) {
                                moveTo(i, ii);
                            }
                        }
                    }
                }
            }
        }
    }

    private void moveTo(int x, int y) {
        cardSlots[x][y].setCard(getPlayerCard());
        cardSlots[playerPositionX][playerPositionY].setCard(randomCard());
        playerPositionX = x;
        playerPositionY = y;
    }

    private PlayerCard getPlayerCard() {
        return (PlayerCard) cardSlots[playerPositionX][playerPositionY].getCard();
    }

    private boolean slotIsClickable(int x, int y) {
        return (playerPositionX + 1 == x && playerPositionY == y) ||
                (playerPositionX - 1 == x && playerPositionY == y) ||
                (playerPositionX == x && playerPositionY + 1 == y) ||
                (playerPositionX == x && playerPositionY - 1 == y);
    }

    private void spawnPlayer() {
        cardSlots[1][1].setCard(new PlayerCard(PLAYER_DEFAULT_POWER, PLAYER_DEFAULT_HEALTH));
        playerPositionX = 1;
        playerPositionY = 1;
    }

    private void randomizeSlots() {
        for (CardSlot[] slots : cardSlots) {
            for (CardSlot slot : slots) {
                slot.setCard(randomCard());
            }
        }
    }

    private Card randomCard() {
        switch (CardType.getRandomCardType()) {
            case GOLD:
                return new GoldCard(GOLD_MAX_POWER);

            case WEAPON:
                return new WeaponCard(WEAPON_MAX_POWER);

            case MONSTER:
                return new MonsterCard(MONSTER_MAX_POWER);
        }
        return new Card();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


}
