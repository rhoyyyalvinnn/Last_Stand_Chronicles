package com.mygdx.game.ui;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LoadingUI extends Table{
    private final TextButton pressAnyButtonInfo;

    private TextureAtlas atlas;
    private TextureRegion[] progressBarUi;



    public LoadingUI(TextButton pressAnyButtonInfo, ProgressBar progressBar) {

        this.atlas = new TextureAtlas(Gdx.files.internal("assets/loading/loadingatlas.atlas"));

        this.pressAnyButtonInfo = pressAnyButtonInfo;
        progressBarUi = new TextureRegion[4];



    }
}
