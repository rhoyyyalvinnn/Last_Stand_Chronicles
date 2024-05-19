package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Button2 {
    private TextButton button;

    public Button2(String text, float x, float y, Skin skin) {
        button = new TextButton(text, skin);
        button.setPosition(x, y);
    }

    public TextButton getButton2() {
        return button;
    }
}