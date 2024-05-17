package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class LevelManager {
    private final MyGdxGame context;
    Texture[] test_map_textures;
    private String level;


    public LevelManager(MyGdxGame context,String level) {
        this.context = context;
        this.level=level;
    }

    public String getLevel(){
        return level;
    }
    public String setLevel(String level){
        switch (level){
            case "1":
                return "map.tmx";
            case "2":
                return "dungeonmap.tmx";
        }
        return "map.tmx";
    }


}