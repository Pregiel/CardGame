package com.pregiel.cardgame;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;


public class ImageLabel extends Group {
    private Label label;
    private Image image;

    public ImageLabel(Label label, Image image, int width, int height) {
        setSize(width, height);
        this.label = label;
        if (image != null) {
            this.image = image;
            this.image.setFillParent(true);
            addActor(this.image);
        }
        label.setFillParent(true);
        label.setAlignment(Align.center);
        addActor(label);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public StringBuilder getText() {
        return label.getText();
    }

    public void removeImage() {
        if (image != null) {
            image.remove();
        }
        image = null;
    }

    public void setImage(Image image) {
        if (this.image != null) {
            this.image.remove();
        }
        this.image = image;
        this.image.setFillParent(true);
        addActor(this.image);
        label.toFront();
    }

    @Override
    public boolean remove() {
        boolean b = super.remove();
        label.remove();
        if (image != null) {
            image.remove();
        }
        return b;
    }
}
