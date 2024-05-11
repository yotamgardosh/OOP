package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Represents a basic collision strategy for handling collisions between game objects.
 * This strategy is specifically designed for collisions involving bricks in the Bricker game.
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager brickerGameManager;
    private Counter brickCounter;

    /**
     * Constructs a new BasicCollisionStrategy instance.
     *
     * @param brickerGameManager The game manager associated with the collision strategy.
     * @param brickCounter       The counter representing the number of bricks in the game.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager, Counter brickCounter) {
        this.brickerGameManager = brickerGameManager;
        this.brickCounter = brickCounter;
    }

    /**
     * Handles the collision between two game objects, specifically designed for bricks.
     * Removes the first game object from the game if it is a brick, and decrements the brick counter.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (this.brickerGameManager.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)) {
            this.brickCounter.decrement();
        }
    }
}
