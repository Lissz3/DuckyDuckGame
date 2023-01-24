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
import com.isabelrosado.duckyduck.Sprites.Brick;
import com.isabelrosado.duckyduck.Sprites.BrickHit;
import com.isabelrosado.duckyduck.Sprites.Fruit;
import com.isabelrosado.duckyduck.Sprites.Ground;

public class WorldCreator {

    public WorldCreator(World world, TiledMap map){
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
            body.createFixture(fdef);
            new Ground(world, map, rec);
        }

        //hittable bricks
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rec = ((RectangleMapObject) object).getRectangle();
            new BrickHit(world, map, rec);
        }

        //bricks
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rec = ((RectangleMapObject) object).getRectangle();
            new Brick(world, map, rec);
        }

        //fruits
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rec = ((RectangleMapObject) object).getRectangle();
            new Fruit(world, map, rec);
        }
    }
}
