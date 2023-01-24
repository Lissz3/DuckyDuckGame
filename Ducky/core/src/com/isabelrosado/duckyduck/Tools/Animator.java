package com.isabelrosado.duckyduck.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator extends Sprite {
    private Texture animationTexture;
    private TextureRegion[] frames;
    private int width;
    private int height;

    public Animator (Texture texture, int width, int height){
        this.animationTexture = texture;
        this.width = width;
        this.height = height;
    }

    public Animation<TextureRegion> getAnimation(int numOfFrames, int xStartOfAnimation, int yStartOfAnimation){
        frames = new TextureRegion[numOfFrames];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new TextureRegion(animationTexture, (xStartOfAnimation)+width*i, yStartOfAnimation, width, height);
        }
        return new Animation<TextureRegion>(0.12f, frames);
    }
}
