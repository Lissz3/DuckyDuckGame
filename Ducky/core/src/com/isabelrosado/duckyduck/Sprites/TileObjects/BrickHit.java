package com.isabelrosado.duckyduck.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;
import com.isabelrosado.duckyduck.Sprites.Duck;
import com.isabelrosado.duckyduck.Sprites.Items.Fruit;
import com.isabelrosado.duckyduck.Sprites.Items.ItemDef;
import com.isabelrosado.duckyduck.Tools.Animator;

public class BrickHit extends InteractiveTileObject {

    public BrickHit(DuckyDuck game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.BRICKHIT_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(DuckyDuck.DESTROYED_BIT);
        getCell().setTile(null);
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / DuckyDuck.PIXEL_PER_METER), Fruit.class));
        game.getAssetManager().get("Audio/Sounds/BreakableBox.mp3", Sound.class).play(DuckyDuck.FX_VOLUME);
    }

}