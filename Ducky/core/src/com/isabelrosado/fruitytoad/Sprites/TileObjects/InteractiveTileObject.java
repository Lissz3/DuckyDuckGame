package com.isabelrosado.fruitytoad.Sprites.TileObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
/**
 * <p>
 * Abstract class to define an interactive object.
 * </p>
 * @author Isabel Rosado
 */
public abstract class InteractiveTileObject {
    /**
     * Manager for physic entities and dynamic simulation.
     */
    protected World world;

    /**
     * Manager for the current map
     */
    protected TiledMap map;

    /**
     * Bounds (position and size) of the object
     */
    protected Rectangle bounds;

    /**
     * The rigid body of the object
     */
    protected Body body;

    /**
     * Shape, density, friction and restitution of the object body.
     */
    protected Fixture fixture;

    /**
     * Main screen of the game.
     * @see FruityToad
     */
    protected FruityToad game;

    /**
     * The interactive object itself
     */
    protected MapObject object;

    /**
     * Game screen
     * @see PlayScreen
     */
    protected PlayScreen screen;

    /**
     *  Initialize the values to the values given to the constructor.
     *  <p>Generates the body definition and creates it on the world with his fixtures with specific bounds.</p>
     * @param game main screen
     * @param screen actual screen
     * @param object the interactive object itself
     */
    public InteractiveTileObject(FruityToad game, PlayScreen screen, MapObject object){
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
        bDef.position.set((bounds.getX() + bounds.getWidth()/2) / FruityToad.PIXEL_PER_METER, (bounds.getY() + bounds.getHeight()/2) / FruityToad.PIXEL_PER_METER);

        body = world.createBody(bDef);

        shape.setAsBox((bounds.getWidth()/2) / FruityToad.PIXEL_PER_METER, (bounds.getHeight()/2) / FruityToad.PIXEL_PER_METER);
        fixDef.shape = shape;

        fixture = body.createFixture(fixDef);
    }

    /**
     * Called when the object collides with another fixture
     */
    public abstract void onHeadHit();

    /**
     * <p>Sets the unique bit for the object to hold contact filtering data.</p>
     * @param filterBit object type bit
     */
    public void setCategoryFilter(short filterBit){
        Filter f = new Filter();
        f.categoryBits = filterBit;
        fixture.setFilterData(f);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * FruityToad.PIXEL_PER_METER / 16), (int)(body.getPosition().y * FruityToad.PIXEL_PER_METER / 16));
    }
}
