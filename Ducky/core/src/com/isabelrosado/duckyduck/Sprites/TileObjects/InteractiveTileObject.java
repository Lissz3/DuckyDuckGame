package com.isabelrosado.duckyduck.Sprites.TileObjects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected DuckyDuck game;
    protected MapObject object;

    PlayScreen screen;


    public InteractiveTileObject(DuckyDuck game, PlayScreen screen, MapObject object){
        this.game = game;
        this.screen = screen;
        this.object = object;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bDef = new BodyDef();
        FixtureDef fixDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set((bounds.getX() + bounds.getWidth()/2) / DuckyDuck.PIXEL_PER_METER, (bounds.getY() + bounds.getHeight()/2) / DuckyDuck.PIXEL_PER_METER);

        body = world.createBody(bDef);

        shape.setAsBox((bounds.getWidth()/2) / DuckyDuck.PIXEL_PER_METER, (bounds.getHeight()/2) / DuckyDuck.PIXEL_PER_METER);
        fixDef.shape = shape;
//        body.createFixture(fixDef);

        fixture = body.createFixture(fixDef);
    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit){
        Filter f = new Filter();
        f.categoryBits = filterBit;
        fixture.setFilterData(f);
    }
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * DuckyDuck.PIXEL_PER_METER / 16), (int)(body.getPosition().y * DuckyDuck.PIXEL_PER_METER / 16));
    }
}