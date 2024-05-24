package com.mygdx.game.Map;

public class MapOneFactory extends MapFactory {
    @Override
    public Map createMap() {
        MapOne mapOne = new MapOne();
        mapOne.loadMap();
        if (mapOne.getTiledMap() == null) {
            System.out.println("MapOneFactory: TiledMap is null after loading.");
        }

        return new MapOne();
    }
}
