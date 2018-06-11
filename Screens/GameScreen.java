package com.pregiel.cardgame.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.pregiel.cardgame.CardClasses.Card;
import com.pregiel.cardgame.CardClasses.ChestCard;
import com.pregiel.cardgame.CardClasses.GoldCard;
import com.pregiel.cardgame.CardClasses.HealthPotionCard;
import com.pregiel.cardgame.CardClasses.MonsterCard;
import com.pregiel.cardgame.CardClasses.PlayerCard;
import com.pregiel.cardgame.CardClasses.WeaponCard;
import com.pregiel.cardgame.CardSlot;
import com.pregiel.cardgame.CardType;
import com.pregiel.cardgame.Direction;
import com.pregiel.cardgame.PlayerCardSlot;
import com.pregiel.cardgame.Utils.ScreenEnum;
import com.pregiel.cardgame.Utils.ScreenManager;
import com.pregiel.cardgame.Utils.Vector2;


/**
 * Created by Pregiel on 27.05.2018.
 */

public class GameScreen extends com.pregiel.cardgame.Screens.AbstractScreen {

    public static int CARD_PADDING;
    public static int CARDSLOT_WIDTH;
    public static int CARDSLOT_HEIGHT;
    public static int CARDSLOT_PADDING;
    public static int TOPBAR_HEIGHT;


    private static final int PLAYER_DEFAULT_HEALTH = 1;
    private static final int PLAYER_DEFAULT_POWER = 6;

    private static final int MONSTER_MAX_POWER = 15;
    private static final int GOLD_MAX_POWER = 10;
    private static final int WEAPON_MAX_POWER = 12;
    private static final int HEALTH_POTION_MAX_POWER = 8;

    private static final int GAME_SIZE = 3;


    private boolean isAnimating = false;

    private int playerPositionX, playerPositionY;

    private OrthographicCamera camera;
    private CardSlot[][] cardSlots;

    private TextButton btnMenu;
    private Label lblGold;


    public GameScreen() {
        super();
    }

    @Override
    public void buildStage() {
        super.buildStage();
        int SCREEN_WIDTH = ScreenManager.SCREEN_WIDTH;
        int SCREEN_HEIGHT = ScreenManager.SCREEN_HEIGHT;

        double CARDSLOT_PADDING_RATIO = 0.025;
        CARDSLOT_PADDING = (int) (SCREEN_WIDTH * CARDSLOT_PADDING_RATIO);  //8
        CARD_PADDING = (int) (SCREEN_WIDTH * CARDSLOT_PADDING_RATIO);

        double TOPBAR_RATIO = 0.1;
        TOPBAR_HEIGHT = (int) (SCREEN_HEIGHT * TOPBAR_RATIO);

        double CARDSLOT_WIDTH_RATIO = (1 - ((GAME_SIZE + 1) * CARDSLOT_PADDING_RATIO)) / GAME_SIZE;
        CARDSLOT_WIDTH = (int) (SCREEN_WIDTH * CARDSLOT_WIDTH_RATIO);  //140

        double CARDSLOT_PADDING2_RATIO = (double) CARDSLOT_PADDING / SCREEN_HEIGHT;

        double CARDSLOT_HEIGHT_RATIO = (1 - ((GAME_SIZE + 1) * CARDSLOT_PADDING2_RATIO) - TOPBAR_RATIO) / GAME_SIZE;
        CARDSLOT_HEIGHT = (int) (SCREEN_HEIGHT * CARDSLOT_HEIGHT_RATIO);  //250


        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);


        Table topBar = new Table();
        topBar.setWidth(ScreenManager.SCREEN_WIDTH - 2 * CARDSLOT_PADDING);
        topBar.setHeight(TOPBAR_HEIGHT);
        topBar.setPosition(CARDSLOT_PADDING, ScreenManager.SCREEN_HEIGHT - TOPBAR_HEIGHT);


        Label lblGoldText = getUiFactory().createLabel("GOLD: ", getUiFactory().getTopbarFont());
        topBar.add(lblGoldText);

        lblGold = getUiFactory().createLabel("0", getUiFactory().getTopbarFont());
        topBar.add(lblGold).expand().align(Align.left);

        btnMenu = getUiFactory().createButton("Menu", getUiFactory().getTopbarFont());
        btnMenu.addListener(getUiFactory().createListener(ScreenEnum.MAIN_MENU));
        topBar.add(btnMenu);

        addActor(topBar);


        Image separator = getUiFactory().drawImage(getAssetsManager().getSeparator());
        separator.setWidth(ScreenManager.SCREEN_WIDTH);
        separator.setY(ScreenManager.SCREEN_HEIGHT - TOPBAR_HEIGHT);
        addActor(separator);


        cardSlots = new CardSlot[GAME_SIZE][GAME_SIZE];
        for (int i = 0; i < GAME_SIZE; i++) {
            for (int ii = 0; ii < GAME_SIZE; ii++) {
                if (i == 1 && ii == 1) {
                    cardSlots[i][ii] = new PlayerCardSlot(i, ii, CARDSLOT_PADDING * (i + 1) + CARDSLOT_WIDTH * i,
                            CARDSLOT_PADDING * (ii + 1) + CARDSLOT_HEIGHT * ii,
                            CARDSLOT_WIDTH,
                            CARDSLOT_HEIGHT);
                } else {
                    cardSlots[i][ii] = new CardSlot(i, ii, CARDSLOT_PADDING * (i + 1) + CARDSLOT_WIDTH * i,
                            CARDSLOT_PADDING * (ii + 1) + CARDSLOT_HEIGHT * ii,
                            CARDSLOT_WIDTH,
                            CARDSLOT_HEIGHT);
                }


                cardSlots[i][ii].setBackgroundTexture(getAssetsManager().getBackgroundTexture());
                cardSlots[i][ii].setScale(0);
                cardSlots[i][ii].animate(CardSlot.Animation.CREATE);
            }
        }

        randomizeSlots();
        spawnPlayer();

        drawScene();

    }

    private void drawScene() {
//        Group group = new Group();
//        group.setSize(ScreenManager.SCREEN_WIDTH, TOPBAR_HEIGHT);
////        group.setPosition(0, ScreenManager.SCREEN_HEIGHT - TOPBAR_HEIGHT);
//        group.setPosition(50,50);
//        group.setColor(new Color(1,1,1,0));
//        addActor(group);


        Group screen = new Group();
        for (CardSlot[] slots : cardSlots) {
            for (final CardSlot slot : slots) {

                Image imgSlot = getUiFactory().drawImage(slot.getBackgroundTexture());
                imgSlot.setFillParent(true);
                slot.addActor(imgSlot);

                slot.setImgCard(getUiFactory().drawImage(slot.getCard().getCardTexture()));
                slot.getImgCard().setFillParent(true);

                Table table = new Table();
                table.setFillParent(true);
                table.left().bottom().padLeft(10).padBottom(10);
                slot.addActor(table);

                switch (slot.getCard().getCardType()) {
                    case PLAYER:
                        ((PlayerCardSlot) slot).setLblGold(getUiFactory().createCardDescLabel(String.valueOf(((PlayerCard) slot.getCard()).getGold())));
                        table.add(getUiFactory().createCardDescLabel("Gold: ")).left();
                        table.add(((PlayerCardSlot) slot).getLblGold());
                        table.row();
                        ((PlayerCardSlot) slot).setLblHealth(getUiFactory().createCardDescLabel(String.valueOf(((PlayerCard) slot.getCard()).getHealth())));
                        table.add(getUiFactory().createCardDescLabel("Health: ")).left();
                        table.add(((PlayerCardSlot) slot).getLblHealth());
                        table.row();
                        slot.setLblPowerName(getUiFactory().createCardDescLabel("Power: "));
                        break;

                    case GOLD:
                        slot.setLblPowerName(getUiFactory().createCardDescLabel("Amount: "));
                        break;

                    case WEAPON:
                        slot.setLblPowerName(getUiFactory().createCardDescLabel("Power: "));
                        break;

                    case MONSTER:
                        slot.setLblPowerName(getUiFactory().createCardDescLabel("Power: "));
                        break;

                    case HEALTH_POTION:
                        slot.setLblPowerName(getUiFactory().createCardDescLabel("Power: "));
                        break;

                    case CHEST:
                        slot.setLblPowerName(getUiFactory().createCardDescLabel(""));
                }
                slot.setLblPower(getUiFactory().createCardDescLabel(String.valueOf(slot.getCard().getPower())));
                table.add(slot.getLblPowerName()).left();
                table.add(slot.getLblPower());

                slot.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!isAnimating) {
                            if (button == Input.Buttons.LEFT) {
                                if (slot.isClickable(playerPositionX, playerPositionY)) {
                                    System.out.println("Move to: X: " + slot.getSlotPositionX() + " Y: " + slot.getSlotPositionY() + " " + slot.getCard());
                                    if (slot.getCard().use(getPlayerCard())) {
                                        slot.redraw();
                                        cardSlots[playerPositionX][playerPositionY].redraw();
                                        moveToSequence(slot.getSlotPositionX(), slot.getSlotPositionY());
                                        if (slot.getCard().getCardType() == CardType.GOLD) {
                                            lblGold.setText(String.valueOf(getPlayerCard().getGold()));
                                        }
                                    } else {
                                        if (slot.getCard().getCardType() == CardType.CHEST) {
                                            changeCardSequence(slot, randomCard(CardType.MONSTER, CardType.CHEST));
                                        }
                                        if (slot.getCard().getCardType() == CardType.MONSTER) {
                                            getPlayerCardSlot().redraw();
                                            slot.redraw();
                                            if (slot.getCard().getPower() == 0) {
                                                GoldCard newCard = new GoldCard();
                                                newCard.setPower(((MonsterCard) slot.getCard()).getMaxPower());
                                                newCard.setCardTexture(getAssetsManager());
                                                changeCardSequence(slot, newCard);
                                            } else {
                                                damageCardSequence(getPlayerCardSlot());
                                                damageCardSequence(slot);
                                            }
                                        }
                                    }

                                }
                            } else {
                                System.out.println(slot.getSlotPositionX() + " " + slot.getSlotPositionY() + " " + slot.getCard());
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

    private void changeCardSequence(final CardSlot slot, final Card newCard) {
        isAnimating = true;

        slot.animate(CardSlot.Animation.DESTROY);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                slot.setCard(newCard);
                slot.redraw();
                slot.animate(CardSlot.Animation.CREATE);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        isAnimating = false;
                    }
                }, CardSlot.ANIMATION_CREATE_DURATION);
            }
        }, CardSlot.ANIMATION_DESTROY_DURATION);
    }

    private void damageCardSequence(final CardSlot slot) {
        isAnimating = true;

        slot.animate(CardSlot.Animation.DAMAGE);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                slot.animate(CardSlot.Animation.UNDAMAGE);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        isAnimating = false;
                    }
                }, CardSlot.ANIMATION_DAMAGE_DURATION);
            }
        }, CardSlot.ANIMATION_DAMAGE_DURATION);

    }

    private void moveToSequence(final int x, final int y) {
        isAnimating = true;
        final Direction direction = getDirection(x, y);
        final Direction oppositeDirection;


        final CardSlot oppositeSlot;
        if (getSlotByDirection(playerPositionX, playerPositionY, direction.getOpposite()) != null) {
            oppositeSlot = getSlotByDirection(playerPositionX, playerPositionY, direction.getOpposite());
            oppositeDirection = direction;
        } else if (getSlotByDirection(playerPositionX, playerPositionY, direction.getOpposite().getNext()) != null) {
            oppositeSlot = getSlotByDirection(playerPositionX, playerPositionY, direction.getOpposite().getNext());
            oppositeDirection = direction.getNext();
        } else {
            oppositeSlot = getSlotByDirection(playerPositionX, playerPositionY, direction.getNext());
            oppositeDirection = direction.getNext().getOpposite();
        }
        final Vector2 targetPosition = new Vector2(x, y);
        final Vector2 oppositePosition = new Vector2(oppositeSlot.getSlotPositionX(), oppositeSlot.getSlotPositionY());


        cardSlots[x][y].animate(CardSlot.Animation.DESTROY);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {

                cardSlots[playerPositionX][playerPositionY].animate(CardSlot.Animation.MOVE, direction);

//
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {

                        cardSlots[x][y].setPosition(oppositeSlot.getX(), oppositeSlot.getY());
                        cardSlots[x][y].setCard(randomCard());
                        cardSlots[x][y].redraw();


                        oppositeSlot.animate(CardSlot.Animation.MOVE, oppositeDirection);


                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                cardSlots[x][y].animate(CardSlot.Animation.CREATE);
                                cardSlots[x][y].setSlotPosition(oppositePosition.x, oppositePosition.y);

                                cardSlots[oppositePosition.x][oppositePosition.y] = cardSlots[targetPosition.x][targetPosition.y];

                                cardSlots[targetPosition.x][targetPosition.y] = cardSlots[playerPositionX][playerPositionY];
                                cardSlots[playerPositionX][playerPositionY] = oppositeSlot;

                                playerPositionX = x;
                                playerPositionY = y;

                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        isAnimating = false;
                                    }
                                }, CardSlot.ANIMATION_CREATE_DURATION);

                            }
                        }, CardSlot.ANIMATION_MOVETO_DURATION);
                    }
                }, CardSlot.ANIMATION_MOVETO_DURATION);
            }
        }, CardSlot.ANIMATION_DESTROY_DURATION);

    }

    private Direction getDirection(int x, int y) {
        if (x == playerPositionX && y > playerPositionY) {
            return Direction.NORTH;
        } else if (x > playerPositionX && y == playerPositionY) {
            return Direction.EAST;
        } else if (x == playerPositionX && y < playerPositionY) {
            return Direction.SOUTH;
        } else {
            return Direction.WEST;
        }
    }

    private CardSlot getSlotByDirection(int oldX, int oldY, Direction direction) {
        int x = oldX, y = oldY;
        switch (direction) {
            case NORTH:
                y = y + 1;
                break;

            case EAST:
                x = x + 1;
                break;

            case SOUTH:
                y = y - 1;
                break;

            case WEST:
                x = x - 1;
                break;
        }
        if (x >= 0 && x < GAME_SIZE && y >= 0 && y < GAME_SIZE) {
            return cardSlots[x][y];
        }
        return null;
    }


    private PlayerCard getPlayerCard() {
        drawCardArray();
        return (PlayerCard) cardSlots[playerPositionX][playerPositionY].getCard();
    }

    private PlayerCardSlot getPlayerCardSlot() {
        return (PlayerCardSlot) cardSlots[playerPositionX][playerPositionY];
    }

    private void drawCardArray() {
        String array = "CARD SLOTS\n";
        for (int i = GAME_SIZE - 1; i >= 0; i--) {
            for (int ii = 0; ii < GAME_SIZE; ii++) {
                array = array + cardSlots[ii][i].tak + " || ";
            }
            array = array + "\n";
        }
        System.out.println(array);
    }

    private void spawnPlayer() {
        PlayerCard playerCard = new PlayerCard(PLAYER_DEFAULT_POWER, PLAYER_DEFAULT_HEALTH, MainMenuScreen.getCharacter());
        playerCard.setCardTexture(getAssetsManager());
        cardSlots[1][1].setCard(playerCard);
        playerPositionX = 1;
        playerPositionY = 1;
        cardSlots[1][1].tak = 1;
    }

    private void randomizeSlots() {
        for (CardSlot[] slots : cardSlots) {
            for (CardSlot slot : slots) {
                slot.setCard(randomCard());
            }
        }
    }

    private Card randomCard(CardType... excludeCardTypes) {
        CardType type = CardType.getRandomCardType(excludeCardTypes);
        Card card;
        switch (type) {
//            case GOLD:
//                card = new GoldCard(GOLD_MAX_POWER);

            case WEAPON:
                card = new WeaponCard(WEAPON_MAX_POWER);
                break;

            case MONSTER:
//                card = new GoldCard(GOLD_MAX_POWER);
                card = new MonsterCard(MONSTER_MAX_POWER);
                break;

            case HEALTH_POTION:
                card = new HealthPotionCard(HEALTH_POTION_MAX_POWER);
                break;

            case CHEST:
                card = new ChestCard();
                break;

            default:
                card = new GoldCard(GOLD_MAX_POWER);
        }

        card.setCardTexture(getAssetsManager());
        return card;
    }

    @Override
    public void dispose() {
        super.dispose();


    }
}
