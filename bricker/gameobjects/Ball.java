package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents the ball GameObject in the Bricker game. Handles collisions, sound effects,
 * and collision counter tracking.
 */
public class Ball extends GameObject {

    private final Sound collisionSound;
    private final BrickerGameManager brickerGameManager;
    private int collisionCounter;

    /**
     * Constructs a new Ball instance.
     *
     * @param topLeftCorner      Position of the ball, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height of the ball in window coordinates.
     * @param renderable         The renderable representing the ball. Can be null,
     *                           in which case the ball will not be rendered.
     * @param collisionSound     The sound played on collisions with other GameObjects.
     * @param brickerGameManager The game manager associated with the Bricker game.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Retrieves the collision counter value.
     *
     * @return The current collision counter value.
     */
    public int getCollisionCounter() {
        return this.collisionCounter;
    }

    /**
     * Handles actions when a collision occurs. Increments the collision counter,
     * adjusts the velocity based on collision normal, and plays the collision sound.
     *
     * @param other     The GameObject with which the collision occurs.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionCounter++;
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        this.collisionSound.play();
    }
}
