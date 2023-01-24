package com.isabelrosado.duckyduck.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator extends Sprite {
    private Texture animationTexture;
    private TextureRegion[] frames;
    private Animation<TextureRegion> animation;
    private int numOfFrames;
    private int xStartOfAnimation;
    private int yStartOfAnimation;
    private int width;
    private int height;

    public Animator (int numOfFrames, Texture texture, int xStartOfAnimation, int yStartOfAnimation, int width, int height){
        this.animationTexture = texture;
        this.numOfFrames = numOfFrames;
        this.xStartOfAnimation = xStartOfAnimation;
        this.yStartOfAnimation = yStartOfAnimation;
        this.width = width;
        this.height = height;
    }

    public Animation<TextureRegion> getAnimation(){
        frames = new TextureRegion[numOfFrames];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new TextureRegion(getTexture(), (xStartOfAnimation)+32*i, yStartOfAnimation, width, height);
        }
        return new Animation<TextureRegion>(0.12f, frames);
    }
}
