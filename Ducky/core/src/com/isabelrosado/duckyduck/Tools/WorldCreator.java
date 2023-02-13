package com.isabelrosado.duckyduck.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;
import com.badlogic.gdx.utils.Array;
import com.isabelrosado.duckyduck.Sprites.Enemies.FatBird;
import com.isabelrosado.duckyduck.Sprites.TileObjects.Brick;
import com.isabelrosado.duckyduck.Sprites.TileObjects.BrickHit;
import com.isabelrosado.duckyduck.Sprites.TileObjects.CheckPoint;
import com.isabelrosado.duckyduck.Sprites.TileObjects.Ground;


public class WorldCreator {
    private Array<FatBird> fatBirds;

    public Array<FatBird> getFatBirds() {
        return fatBirds;
    }

    public WorldCreator(DuckyDuck game, PlayScreen screen){
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
            bdef.position.set((rec.getX() + rec.getWidth()/2) / DuckyDuck.PIXEL_PER_METER, (rec.getY() + rec.getHeight()/2) / DuckyDuck.PIXEL_PER_METER);

            body = world.createBody(bdef);

            shape.setAsBox((rec.getWidth()/2) / DuckyDuck.PIXEL_PER_METER, (rec.getHeight()/2) / DuckyDuck.PIXEL_PER_METER);
            fdef.shape = shape;
            fdef.filter.categoryBits = DuckyDuck.GROUND_BIT;
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
            fatBirds.add(new FatBird(game, screen, rec.getX() / DuckyDuck.PIXEL_PER_METER, rec.getY() / DuckyDuck.PIXEL_PER_METER, 0, 0));
        }
    }
}
