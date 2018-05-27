package com.pregiel.cardgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pregiel.cardgame.CardClasses.Card;
import com.pregiel.cardgame.CardClasses.GoldCard;
import com.pregiel.cardgame.CardClasses.MonsterCard;
import com.pregiel.cardgame.CardClasses.PlayerCard;
import com.pregiel.cardgame.CardClasses.WeaponCard;
import com.pregiel.cardgame.CardSlot;
import com.pregiel.cardgame.CardType;
import com.pregiel.cardgame.Utils.AssetsManager;
import com.pregiel.cardgame.Utils.ScreenManager;
import com.pregiel.cardgame.Utils.UIFactory;


/**
 * Created by Pregiel on 27.05.2018.
 */

public class GameScreen extends com.pregiel.cardgame.Screens.AbstractScreen {
    private static final double CARDSLOT_WIDTH_RATIO = 0.3;
    private static final double CARDSLOT_HEIGHT_RATIO = 0.3;
    private static final double CARDSLOT_PADDING_RATIO = 0.025;

    private int CARD_PADDING;


    private static final int PLAYER_DEFAULT_HEALTH = 10;
    private static final int PLAYER_DEFAULT_POWER = 6;

    private static final int MONSTER_MAX_POWER = 15;
    private static final int GOLD_MAX_POWER = 10;
    private static final int WEAPON_MAX_POWER = 12;

    private int playerPositionX, playerPositionY;

//    private SpriteBatch batch;
//    private BitmapFont font;
    private OrthographicCamera camera;
    private CardSlot[][] cardSlots;


    private UIFactory uiFactory;
    private AssetsManager assetsManager;


    public GameScreen() {
        super();

        uiFactory = new UIFactory();
        assetsManager = new AssetsManager();
    }

    @Override
    public void buildStage() {
        int SCREEN_WIDTH = ScreenManager.SCREEN_WIDTH;
        int SCREEN_HEIGHT = ScreenManager.SCREEN_HEIGHT;

        int CARDSLOT_WIDTH = (int) (SCREEN_WIDTH * CARDSLOT_WIDTH_RATIO);  //140
        int CARDSLOT_HEIGHT = (int) (SCREEN_HEIGHT * CARDSLOT_HEIGHT_RATIO);  //250
        int CARDSLOT_PADDING = (int) (SCREEN_WIDTH * CARDSLOT_PADDING_RATIO);  //8


        CARD_PADDING = (int) (SCREEN_WIDTH * CARDSLOT_PADDING_RATIO);

//        batch = new SpriteBatch();
//        font = uiFactory.generateFont(UIFactory.CARDTEXT_SIZE);//new BitmapFont();
//        font.getData().setScale(2f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);


        cardSlots = new CardSlot[3][3];
        for (int i = 0; i < 3; i++) {
            for (int ii = 0; ii < 3; ii++) {
                cardSlots[i][ii] = new CardSlot(i, ii, CARDSLOT_PADDING * (i + 1) + CARDSLOT_WIDTH * i,
                        CARDSLOT_PADDING * (ii + 1) + CARDSLOT_HEIGHT * ii,
                        CARDSLOT_WIDTH,
                        CARDSLOT_HEIGHT);

                cardSlots[i][ii].setBackgroundTexture(assetsManager.getBackgroundTexture());
            }
        }

        randomizeSlots();
        spawnPlayer();

        drawScene();

    }

    private void drawScene() {
        clear();
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        Group screen = new Group();
        for (CardSlot[] slots : cardSlots) {
            for (final CardSlot slot : slots) {
//                Group group = new Group();
//                group.setPosition(slot.x, slot.y);
//                group.setSize(slot.width, slot.height);

                Image imgSlot = uiFactory.drawImage(slot.getBackgroundTexture());
                imgSlot.setFillParent(true);
                slot.addActor(imgSlot);

                Image imgCard = uiFactory.drawImage(slot.getCard().getCardTexture());
                imgCard.setFillParent(true);
                slot.addActor(imgCard);

                Table table = new Table();
                table.setFillParent(true);
                table.left().bottom().padLeft(10).padBottom(10);
                slot.addActor(table);

                switch (slot.getCard().getCardType()) {
                    case PLAYER:
                        table.add(uiFactory.createCardDescLabel("Gold: ")).left();
                        table.add(uiFactory.createCardDescLabel(String.valueOf(((PlayerCard) slot.getCard()).getGold())));
                        table.row();
                        table.add(uiFactory.createCardDescLabel("Health: ")).left();
                        table.add(uiFactory.createCardDescLabel(String.valueOf(((PlayerCard) slot.getCard()).getHealth())));
                        table.row();
                        table.add(uiFactory.createCardDescLabel("Power: ")).left();
                        table.add(uiFactory.createCardDescLabel(String.valueOf(slot.getCard().getPower())));
                        break;

                    case GOLD:
                        table.add(uiFactory.createCardDescLabel("Amount: ")).left();
                        table.add(uiFactory.createCardDescLabel(String.valueOf(slot.getCard().getPower())));
                        break;

                    case WEAPON:
                        table.add(uiFactory.createCardDescLabel("Power: ")).left();
                        table.add(uiFactory.createCardDescLabel(String.valueOf(slot.getCard().getPower())));
                        break;

                    case MONSTER:
                        table.add(uiFactory.createCardDescLabel("Power: ")).left();
                        table.add(uiFactory.createCardDescLabel(String.valueOf(slot.getCard().getPower())));
                        break;
                }

                imgCard.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (slot.isClickable(playerPositionX, playerPositionY)) {
                            System.out.println("Move to: X: " + slot.getSlotPositionX() + " Y: " + slot.getSlotPositionY() + " " + slot.getCard());
                            if (slot.getCard().use(getPlayerCard())) {
                                moveTo(slot.getSlotPositionX(), slot.getSlotPositionY());
                                drawScene();
                            }
                        }
                        return false;
                    }
                });

                screen.addActor(slot);
            }
        }
        addActor(screen);
    }

//    @Override
//    public void render(float delta) {
//        super.render(delta);
//        if (getPlayerCard().isAlive()) {
//            batch.setProjectionMatrix(camera.combined);
//            batch.begin();
//            for (CardSlot[] slots : cardSlots) {
//                for (CardSlot slot : slots) {
//                    batch.draw(slot.getBackgroundTexture(), slot.x, slot.y, slot.width, slot.height);
//                    batch.draw(slot.getCard().getCardTexture(), slot.x, slot.y, slot.width, slot.height);
//
//                    switch (slot.getCard().getCardType()) {
//                        case PLAYER:
//                            font.draw(batch, "Gold: " + String.valueOf(((PlayerCard) slot.getCard()).getGold()), slot.x + CARD_PADDING, slot.y + 6 * CARD_PADDING);
//                            font.draw(batch, "Health: " + String.valueOf(((PlayerCard) slot.getCard()).getHealth()), slot.x + CARD_PADDING, slot.y + 4 * CARD_PADDING);
//                            font.draw(batch, "Power: " + String.valueOf(slot.getCard().getPower()), slot.x + CARD_PADDING, slot.y + 2 * CARD_PADDING);
//                            break;
//
//                        case GOLD:
//                            font.draw(batch, "Amount: " + String.valueOf(slot.getCard().getPower()), slot.x + CARD_PADDING, slot.y + 2 * CARD_PADDING);
//                            break;
//
//                        case WEAPON:
//                            font.draw(batch, "Power: " + String.valueOf(slot.getCard().getPower()), slot.x + CARD_PADDING, slot.y + 2 * CARD_PADDING);
//                            break;
//
//                        case MONSTER:
//                            font.draw(batch, "Power: " + String.valueOf(slot.getCard().getPower()), slot.x + CARD_PADDING, slot.y + 2 * CARD_PADDING);
//                            break;
//                    }
//                }
//            }
//            batch.end();
//
//            if (Gdx.input.isTouched()) {
//                Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//                camera.unproject(touch);
//                if (getPlayerCard().isAlive()) {
//                    for (int i = 0; i < 3; i++) {
//                        for (int ii = 0; ii < 3; ii++) {
//                            if (cardSlots[i][ii].contains(touch.x, touch.y) && slotIsClickable(i, ii)) {
//                                System.out.println("Move to: X: " + i + " Y: " + ii + " " + cardSlots[i][ii].getCard());
//                                if (cardSlots[i][ii].getCard().use(getPlayerCard())) {
//                                    if (getPlayerCard().isAlive()) {
//                                        moveTo(i, ii);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

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
        PlayerCard playerCard = new PlayerCard(PLAYER_DEFAULT_POWER, PLAYER_DEFAULT_HEALTH);
        playerCard.setCardTexture(assetsManager.getCardTexture(CardType.PLAYER));
        cardSlots[1][1].setCard(playerCard);
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
        CardType type = CardType.getRandomCardType();
        Card card;
        switch (type) {
//            case GOLD:
//                card = new GoldCard(GOLD_MAX_POWER);

            case WEAPON:
                card = new WeaponCard(WEAPON_MAX_POWER);
                break;

            case MONSTER:
                card = new GoldCard(GOLD_MAX_POWER);
//                card = new MonsterCard(MONSTER_MAX_POWER);
                break;

            default:
                card = new GoldCard(GOLD_MAX_POWER);
        }
        card.setCardTexture(assetsManager.getCardTexture(type));
        return card;
    }

    @Override
    public void dispose() {
        super.dispose();


    }
}
