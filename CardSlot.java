package com.pregiel.cardgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.pregiel.cardgame.CardClasses.Card;
import com.pregiel.cardgame.Screens.GameScreen;

import javax.swing.GroupLayout;


/**
 * Created by Pregiel on 17.05.2018.
 */

public class CardSlot extends Group {

    private Card card;
    private Texture backgroundTexture;

    private Image imgCard;
    private Label lblPower, lblPowerName;

    public int tak = 0;

    private int slotPositionX, slotPositionY;

    public static final float ANIMATION_MOVETO_DURATION = 0.4f;
    public static final float ANIMATION_DESTROY_DURATION = 0.2f;
    public static final float ANIMATION_CREATE_DURATION = 0.2f;


    public CardSlot(int slotPositionX, int slotPositionY, int x, int y, int width, int height) {
//        backgroundTexture = new Texture(com.pregiel.cardgame.Screens.GameScreen.BACKGROUNDTEXTURE_PATH);
        super();
        this.slotPositionX = slotPositionX;
        this.slotPositionY = slotPositionY;
        setPosition(x, y);
        setSize(width, height);
        setOrigin(Align.center);
    }

    public void redraw() {
        imgCard.setDrawable(new TextureRegionDrawable(new TextureRegion(card.getCardTexture())));
        lblPower.setText(String.valueOf(card.getPower()));
        switch (getCard().getCardType()) {
            case GOLD:
                lblPowerName.setText("Amount: ");
                break;

            default:
                lblPowerName.setText("Power: ");
        }
    }

    public Image getImgCard() {
        return imgCard;
    }

    public void setImgCard(Image imgCard) {
//        if (this.imgCard != null) {
//            this.imgCard.remove();
//        }
        this.imgCard = imgCard;
//        this.imgCard.setFillParent(true);
        this.addActor(this.imgCard);
    }

    public Label getLblPower() {
        return lblPower;
    }

    public void setLblPower(Label lblPower) {
        this.lblPower = lblPower;
    }

    public Label getLblPowerName() {
        return lblPowerName;
    }

    public void setLblPowerName(Label lblPowerName) {
        this.lblPowerName = lblPowerName;
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
        }

        if (action != null) {
            this.addAction(action);
        }
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
        DESTROY, CREATE, MOVE
    }
}
