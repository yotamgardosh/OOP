package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents a NumericLifeCounter GameObject in the Bricker game.
 * Extends the GameObject class and manages the numeric representation of the player's remaining lives.
 */
public class NumericLifeCounter extends GameObject {
    private final TextRenderable textRenderable;
    private Counter lifeCounter;

    /**
     * Constructs a new NumericLifeCounter instance.
     *
     * @param topLeftCorner  Position of the NumericLifeCounter, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height of the NumericLifeCounter in window coordinates.
     * @param textRenderable The renderable representing the NumericLifeCounter. Can be null,
     *                       in which case the NumericLifeCounter will not be rendered.
     * @param lifeCounter    The Counter representing the player's remaining lives.
     */
    public NumericLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable textRenderable,
                              Counter lifeCounter) {
        super(topLeftCorner, dimensions, textRenderable);
        this.textRenderable = textRenderable;
        this.lifeCounter = lifeCounter;
    }

    /**
     * Updates the NumericLifeCounter's state based on changes in the player's remaining lives.
     *
     * @param deltaTime The time passed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.lifeCounter.value() >= Constants.BEGINNING_LIFE_COUNTER) {
            this.textRenderable.setColor(Color.GREEN);
        } else if (this.lifeCounter.value() == Constants.YELLOW_NUMERIC_NUMBER) {
            this.textRenderable.setColor(Color.YELLOW);
        } else {
            this.textRenderable.setColor(Color.RED);
        }
        this.textRenderable.setString(Integer.toString(this.lifeCounter.value()));
    }
}
