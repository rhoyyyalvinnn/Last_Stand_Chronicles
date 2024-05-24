package com.mygdx.game.Map;

public class MapTwoFactory extends MapFactory{
    @Override
    public Map createMap() {
        return new MapTwo();
    }
}
