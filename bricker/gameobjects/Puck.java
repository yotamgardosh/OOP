package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a Puck GameObject in the Bricker game, extending the Ball class.
 * Handles specific behaviors related to Puck movement and removal.
 */
public class Puck extends Ball {
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param collisionSound The sound played on collisions with other GameObjects.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable, collisionSound, brickerGameManager);
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Updates the Puck's state based on its movement and removes it when reaching the bottom.
     *
     * @param deltaTime The time passed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getTopLeftCorner().y() <= 0) {
            this.brickerGameManager.removeGameObject(this, Layer.DEFAULT);
        }
    }
}
