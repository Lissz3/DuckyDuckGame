package com.isabelrosado.fruitytoad.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * <p>
 * Class to create animations, extends {@link Sprite}.
 * </p>
 * @author Isabel Rosado
 */
public class Animator extends Sprite {
    /**
     * Main texture
     */
    private Texture animationTexture;

    /**
     * Number of animation frames
     */
    private TextureRegion[] frames;

    /**
     * Width of the animation
     */
    private int width;

    /**
     * Height of the animation
     */
    private int height;

    /**
     * Initialize the values to the values given to the constructor.
     * @param texture main texture
     * @param width width of the animation
     * @param height of the animation
     */
    public Animator (Texture texture, int width, int height){
        this.animationTexture = texture;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates an animation
     * @param numOfFrames number of animation frames
     * @param xStartOfAnimation position of the first frame in the texture at <b>x-axis</b>
     * @param yStartOfAnimation position of the first frame in the texture at <b>y-axis</b>
     * @return an {@link Animation<TextureRegion>} with the given information
     */
    public Animation<TextureRegion> getAnimation(int numOfFrames, int xStartOfAnimation, int yStartOfAnimation){
        frames = new TextureRegion[numOfFrames];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new TextureRegion(animationTexture, (xStartOfAnimation)+width*i, yStartOfAnimation, width, height);
        }
        return new Animation<TextureRegion>(0.12f, frames);
    }
}
