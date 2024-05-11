package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.main.Constants;

/**
 * Represents a FallingHeart GameObject in the Bricker game.
 * Extends the GameObject class and handles collision events and updates its state.
 */
public class FallingHeart extends GameObject {
    private BrickerGameManager brickerGameManager;
    private Counter lifeCounter;


    /**
     * Constructs a new FallingHeart instance.
     *
     * @param topLeftCorner      Position of the FallingHeart, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height of the FallingHeart in window coordinates.
     * @param renderable         The renderable representing the FallingHeart. Can be null,
     *                           in which case the FallingHeart will not be rendered.
     * @param lifeCounter        The Counter representing the player's remaining lives.
     * @param brickerGameManager The game manager responsible for game logic.
     */
    public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        Counter lifeCounter, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.lifeCounter = lifeCounter;
        this.brickerGameManager = brickerGameManager;
    }


    /**
     * Determines if the FallingHeart should collide with a specific GameObject.
     * In this case, it should only collide with the Paddle.
     *
     * @param other The GameObject with which collision is being checked.
     * @return True if the FallingHeart should collide with the specified GameObject, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals(Constants.PADDLE_TAG)) {
            return true;
        }
        return false;
    }

    /**
     * Updates the FallingHeart's state based on game logic and collision events.
     *
     * @param deltaTime The time passed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (this.getTopLeftCorner().y() > Constants.WINDOW_LENGTH) {
            this.brickerGameManager.removeGameObject(this, Layer.DEFAULT);
        }
    }

    /**
     * Handles actions when a collision occurs. Updates the FallingHeart's state based on collisions.
     *
     * @param other     The GameObject with which the collision occurs.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.lifeCounter.value() < Constants.MAX_LIFE_NUMBER) {
            this.lifeCounter.increment();
        }
        this.brickerGameManager.removeGameObject(this, Layer.DEFAULT);
    }
}
