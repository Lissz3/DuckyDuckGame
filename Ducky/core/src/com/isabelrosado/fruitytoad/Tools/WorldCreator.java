package com.isabelrosado.fruitytoad.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Sprites.Enemies.FatBird;
import com.isabelrosado.fruitytoad.Sprites.TileObjects.Brick;
import com.isabelrosado.fruitytoad.Sprites.TileObjects.BrickHit;
import com.isabelrosado.fruitytoad.Sprites.TileObjects.CheckPoint;
import com.isabelrosado.fruitytoad.Sprites.TileObjects.Ground;


public class WorldCreator {
    private Array<FatBird> fatBirds;

    public Array<FatBird> getFatBirds() {
        return fatBirds;
    }

    public WorldCreator(FruityToad game, PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //ground body and fixture
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rec = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rec.getX() + rec.getWidth()/2) / FruityToad.PIXEL_PER_METER, (rec.getY() + rec.getHeight()/2) / FruityToad.PIXEL_PER_METER);

            body = world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2) / FruityToad.PIXEL_PER_METER, (rec.getHeight()/2) / FruityToad.PIXEL_PER_METER);
            fdef.shape = shape;
            fdef.filter.categoryBits = FruityToad.GROUND_BIT;
            body.createFixture(fdef);
            new Ground(game, screen, object);
        }

        //hittable bricks
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            new BrickHit(game, screen, object);
        }

        //bricks
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            new Brick(game, screen, object);
        }

        //Checkpoint
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new CheckPoint(game, screen, object);
        }

        //fatbirds
        fatBirds = new Array<FatBird>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rec = ((RectangleMapObject) object).getRectangle();
            fatBirds.add(new FatBird(game, screen, rec.getX() / FruityToad.PIXEL_PER_METER, rec.getY() / FruityToad.PIXEL_PER_METER, 0, 0));
        }
    }
}
