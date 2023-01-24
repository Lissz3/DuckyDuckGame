package com.isabelrosado.duckyduck.Sprites;

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

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

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
