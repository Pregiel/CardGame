package com.pregiel.cardgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.pregiel.cardgame.CardClasses.Card;
import com.pregiel.cardgame.CardClasses.PlayerCard;
import com.pregiel.cardgame.Screens.GameScreen;
import com.pregiel.cardgame.Utils.AssetsManager;
import com.pregiel.cardgame.Utils.UIFactory;


/**
 * Created by Pregiel on 17.05.2018.
 */

public class CardSlot extends Group {

    private Card card;
    private Texture backgroundTexture;

    private Image imgCard;

    private Table table;

    private ImageLabel ilblPower;

    public int tak = 0;

    private int slotPositionX, slotPositionY;

    public static final float ANIMATION_MOVETO_DURATION = 0.4f;
    public static final float ANIMATION_DESTROY_DURATION = 0.2f;
    public static final float ANIMATION_CREATE_DURATION = 0.2f;
    public static final float ANIMATION_DAMAGE_DURATION = 0.06f;


    public CardSlot(int slotPositionX, int slotPositionY, int x, int y, int width, int height) {
        super();
        this.slotPositionX = slotPositionX;
        this.slotPositionY = slotPositionY;
        setPosition(x, y);
        setSize(width, height);
        setOrigin(Align.center);
    }

    public void redraw(UIFactory uiFactory, AssetsManager assetsManager) {
        imgCard.setDrawable(new TextureRegionDrawable(new TextureRegion(card.getCardTexture())));
        if (getCard().getPower() == -1) {
            ilblPower.setText("");
        } else {
            ilblPower.setText(String.valueOf(card.getPower()));
        }


        if (getCard().getCardType() != CardType.PLAYER) {
            table.removeActor(ilblPower);
            switch (getCard().getCardType()) {

                case GOLD:
                    ilblPower.setImage(uiFactory.drawImage(assetsManager.getGold()));
                    table.top().right();
                    break;

                case WEAPON:
                    ilblPower.setImage(uiFactory.drawImage(assetsManager.getPower()));
                    table.top().right();

                    break;

                case MONSTER:
                    ilblPower.setImage(uiFactory.drawImage(assetsManager.getPower()));
                    table.bottom().right();

                    break;

                case HEALTH_POTION:
                    ilblPower.setImage(uiFactory.drawImage(assetsManager.getHeart()));
                    table.top().right();

                    break;

                case CHEST:
                    ilblPower.removeImage();
                    table.top().right();


            }
            table.add(getIlblPower());

        }
    }

    public Image getImgCard() {
        return imgCard;
    }

    public void setImgCard(Image imgCard) {
        this.imgCard = imgCard;
        this.addActor(this.imgCard);
    }

    public ImageLabel getIlblPower() {
        return ilblPower;
    }

    public void setIlblPower(ImageLabel ilblPower) {
        if (ilblPower.getText().toString().equals("-1")) {
            ilblPower.setText("");
        }
        this.ilblPower = ilblPower;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getSlotPositionX() {
        return slotPositionX;
    }

    public int getSlotPositionY() {
        return slotPositionY;
    }

    public void setSlotPosition(int x, int y) {
        this.slotPositionX = x;
        this.slotPositionY = y;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public boolean isClickable(int playerX, int playerY) {
        return (playerX + 1 == slotPositionX && playerY == slotPositionY) ||
                (playerX - 1 == slotPositionX && playerY == slotPositionY) ||
                (playerX == slotPositionX && playerY + 1 == slotPositionY) ||
                (playerX == slotPositionX && playerY - 1 == slotPositionY);
    }

    public void animate(Animation animation) {
        animate(animation, null);
    }

    public void animate(Animation animation, Direction direction) {
        Action action = null;
        switch (animation) {
            case CREATE:
                action = createAnimation();
                break;

            case DESTROY:
                action = destroyAnimation();
                break;

            case MOVE:
                action = moveToAnimation(getX(), getY(), direction);
                break;

            case DAMAGE:
                action = damageAnimation();
                break;

            case UNDAMAGE:
                action = undamageAnimation();
                break;
        }

        if (action != null) {
            this.addAction(action);
        }
    }

    private ScaleToAction damageAnimation() {
        ScaleToAction action = new ScaleToAction();
        action.setScale(0.8f);
        action.setDuration(ANIMATION_DAMAGE_DURATION);

        return action;
    }

    private ScaleToAction undamageAnimation() {
        ScaleToAction action = new ScaleToAction();
        action.setScale(1);
        action.setDuration(ANIMATION_DAMAGE_DURATION);

        return action;
    }

    private MoveToAction moveToAnimation(float x, float y, Direction direction) {
        MoveToAction action = new MoveToAction();
        action.setX(x);
        action.setY(y);
        switch (direction) {
            case NORTH:
                action.setX(x);
                action.setY(y + GameScreen.CARD_PADDING + GameScreen.CARDSLOT_HEIGHT);
                slotPositionY = slotPositionY + 1;
                break;

            case EAST:
                action.setX(x + GameScreen.CARD_PADDING + GameScreen.CARDSLOT_WIDTH);
                action.setY(y);
                slotPositionX = slotPositionX + 1;
                break;

            case SOUTH:
                action.setX(x);
                action.setY(y - GameScreen.CARD_PADDING - GameScreen.CARDSLOT_HEIGHT);
                slotPositionY = slotPositionY - 1;
                break;


            case WEST:
                action.setX(x - GameScreen.CARD_PADDING - GameScreen.CARDSLOT_WIDTH);
                action.setY(y);
                slotPositionX = slotPositionX - 1;
                break;


        }
        action.setDuration(ANIMATION_MOVETO_DURATION);
        return action;
    }

    private ScaleToAction destroyAnimation() {
        ScaleToAction action = new ScaleToAction();
        action.setScale(0);
        action.setDuration(ANIMATION_DESTROY_DURATION);


        return action;
    }

    private ScaleToAction createAnimation() {
        ScaleToAction action = new ScaleToAction();
        action.setScale(1);
        action.setDuration(ANIMATION_CREATE_DURATION);

        return action;
    }

    public enum Animation {
        DESTROY, CREATE, MOVE, DAMAGE, UNDAMAGE
    }

    @Override
    public boolean remove() {
        super.remove();
        imgCard.remove();
        table.remove();
        ilblPower.remove();

        return true;
    }
}
