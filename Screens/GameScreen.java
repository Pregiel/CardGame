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
import com.pregiel.cardgame.ImageLabel;
import com.pregiel.cardgame.PlayerCardSlot;
import com.pregiel.cardgame.Utils.ScreenEnum;
import com.pregiel.cardgame.Utils.ScreenManager;
import com.pregiel.cardgame.Utils.Vector2;


/**
 * Created by Pregiel on 27.05.2018.
 */

public class GameScreen extends com.pregiel.cardgame.Screens.AbstractScreen {

    private static final double CARDSLOT_PADDING_RATIO = 0.025;
    private static final double TOPBAR_RATIO = 0.1;
    private static final double ICON_RATIO = 0.085;

    public static int CARD_PADDING;
    public static int CARDSLOT_WIDTH;
    public static int CARDSLOT_HEIGHT;
    public static int CARDSLOT_PADDING;
    public static int TOPBAR_HEIGHT;

    private static int ICON_SIZE;


    private static final int PLAYER_DEFAULT_HEALTH = 10;
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


        CARDSLOT_PADDING = (int) (SCREEN_WIDTH * CARDSLOT_PADDING_RATIO);  //8
        CARD_PADDING = (int) (SCREEN_WIDTH * CARDSLOT_PADDING_RATIO);

        TOPBAR_HEIGHT = (int) (SCREEN_HEIGHT * TOPBAR_RATIO);

        double CARDSLOT_WIDTH_RATIO = (1 - ((GAME_SIZE + 1) * CARDSLOT_PADDING_RATIO)) / GAME_SIZE;
        CARDSLOT_WIDTH = (int) (SCREEN_WIDTH * CARDSLOT_WIDTH_RATIO);  //140

        double CARDSLOT_PADDING2_RATIO = (double) CARDSLOT_PADDING / SCREEN_HEIGHT;

        double CARDSLOT_HEIGHT_RATIO = (1 - ((GAME_SIZE + 1) * CARDSLOT_PADDING2_RATIO) - TOPBAR_RATIO) / GAME_SIZE;
        CARDSLOT_HEIGHT = (int) (SCREEN_HEIGHT * CARDSLOT_HEIGHT_RATIO);  //250

        ICON_SIZE = (int) (SCREEN_WIDTH * ICON_RATIO);


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
        Group screen = new Group();
        for (CardSlot[] slots : cardSlots) {
            for (final CardSlot slot : slots) {

                Image imgSlot = getUiFactory().drawImage(slot.getBackgroundTexture());
                imgSlot.setFillParent(true);
                slot.addActor(imgSlot);

                slot.setImgCard(getUiFactory().drawImage(slot.getCard().getCardTexture()));
                slot.getImgCard().setFillParent(true);

                Table table = new Table();
                slot.setTable(table);
                table.setFillParent(true);
                table.pad(10);

                switch (slot.getCard().getCardType()) {
                    case PLAYER:
                        table.bottom().right();
                        ((PlayerCardSlot) slot).setIlblHealth(new ImageLabel(getUiFactory().createCardDescLabel(String.valueOf(((PlayerCard) slot.getCard()).getHealth())),
                                getUiFactory().drawImage(getAssetsManager().getHeart()), ICON_SIZE, ICON_SIZE));
                        table.add(((PlayerCardSlot) slot).getIlblHealth()).expandX().align(Align.left);

                        table.bottom().left();
                        slot.setIlblPower(new ImageLabel(getUiFactory().createCardDescLabel(String.valueOf(slot.getCard().getPower())),
                                getUiFactory().drawImage(getAssetsManager().getPower()), ICON_SIZE, ICON_SIZE));
                        table.add(slot.getIlblPower());
                        break;

                    case GOLD:

                        table.top().right();
                        slot.setIlblPower(new ImageLabel(getUiFactory().createCardDescLabel(String.valueOf(slot.getCard().getPower())),
                                getUiFactory().drawImage(getAssetsManager().getGold()), ICON_SIZE, ICON_SIZE));
                        table.add(slot.getIlblPower());
                        break;

                    case WEAPON:
                        table.top().right();
                        slot.setIlblPower(new ImageLabel(getUiFactory().createCardDescLabel(String.valueOf(slot.getCard().getPower())),
                                getUiFactory().drawImage(getAssetsManager().getPower()), ICON_SIZE, ICON_SIZE));
                        table.add(slot.getIlblPower());
                        break;

                    case MONSTER:
                        table.bottom().right();
                        slot.setIlblPower(new ImageLabel(getUiFactory().createCardDescLabel(String.valueOf(slot.getCard().getPower())),
                                getUiFactory().drawImage(getAssetsManager().getPower()), ICON_SIZE, ICON_SIZE));
                        table.add(slot.getIlblPower());
                        break;

                    case HEALTH_POTION:
                        table.top().right();
                        slot.setIlblPower(new ImageLabel(getUiFactory().createCardDescLabel(String.valueOf(slot.getCard().getPower())),
                                getUiFactory().drawImage(getAssetsManager().getHeart()), ICON_SIZE, ICON_SIZE));
                        table.add(slot.getIlblPower());
                        break;

                    case CHEST:
                        table.top().right();
                        slot.setIlblPower(new ImageLabel(getUiFactory().createCardDescLabel(""),
                                null, ICON_SIZE, ICON_SIZE));
                        table.add(slot.getIlblPower());

                }
                slot.addActor(table);

                slot.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (!isAnimating) {
                            if (button == Input.Buttons.LEFT) {
                                if (slot.isClickable(playerPositionX, playerPositionY)) {
                                    System.out.println("Move to: X: " + slot.getSlotPositionX() + " Y: " + slot.getSlotPositionY() + " " + slot.getCard());
                                    if (slot.getCard().use(getPlayerCard())) {
                                        slot.redraw(getUiFactory(), getAssetsManager());
                                        cardSlots[playerPositionX][playerPositionY].redraw(getUiFactory(), getAssetsManager());
                                        moveToSequence(slot.getSlotPositionX(), slot.getSlotPositionY());
                                    } else {
                                        if (slot.getCard().getCardType() == CardType.CHEST) {
                                            changeCardSequence(slot, randomCard(CardType.MONSTER, CardType.CHEST));
                                        }
                                        if (slot.getCard().getCardType() == CardType.MONSTER) {
                                            getPlayerCardSlot().redraw(getUiFactory(), getAssetsManager());
                                            slot.redraw(getUiFactory(), getAssetsManager());
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
        getPlayerCardSlot().setLblGold(lblGold);
        addActor(screen);
    }

    private void changeCardSequence(final CardSlot slot, final Card newCard) {
        isAnimating = true;

        slot.animate(CardSlot.Animation.DESTROY);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                slot.setCard(newCard);
                slot.redraw(getUiFactory(), getAssetsManager());
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
                        cardSlots[x][y].redraw(getUiFactory(), getAssetsManager());


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
        CardType type;

        do {
            type = CardType.getRandomCardType(excludeCardTypes);
        } while (getCardAmount(type) >= 3);

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

    private int getCardAmount(CardType cardType) {

        int cardTypeInstance = 0;

        for (CardSlot[] cardSlot : cardSlots) {
            for (CardSlot slot : cardSlot) {
                if (slot.getCard() != null) {
                    if (slot.getCard().getCardType() == cardType) {
                        cardTypeInstance = cardTypeInstance + 1;
                    }
                }
            }
        }
        return cardTypeInstance;
    }

    @Override
    public void dispose() {
        super.dispose();


    }
}
