package bricker.brick_strategies;

import bricker.gameobjects.FallingHeart;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Collision strategy that generates a FallingHeart.
 */
public class HeartCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final Renderable heartImage;
    private Counter lifeCounter;
    private Counter brickCounter;

    /**
     * Constructs a HeartCollisionStrategy instance with the necessary dependencies.
     *
     * @param brickerGameManager The game manager managing game objects and interactions.
     * @param brickCounter       The counter tracking the number of bricks in the game.
     * @param heartImage         The renderable representing the heart power-up.
     * @param lifeCounter        The counter tracking the player's remaining lives.
     */
    public HeartCollisionStrategy(BrickerGameManager brickerGameManager, Counter brickCounter,
                                  Renderable heartImage, Counter lifeCounter) {
        this.brickerGameManager = brickerGameManager;
        this.brickCounter = brickCounter;
        this.heartImage = heartImage;
        this.lifeCounter = lifeCounter;
    }

    /**
     * Handles the collision between a brick and the player paddle by generating a FallingHeart power-up.
     * Removes the brick from the game and decrements the brick counter. Creates a FallingHeart instance
     * at the location of the removed brick, setting its velocity for a downward fall,
     * and adds it to the game.
     *
     * @param gameObject1 The first game object involved in the collision (brick).
     * @param gameObject2 The second game object involved in the collision (player paddle).
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        Vector2 brick_location = gameObject1.getCenter();
        if (this.brickerGameManager.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)) {
            this.brickCounter.decrement();
        }

        FallingHeart fallingHeart = new FallingHeart(Vector2.ZERO, new Vector2(Constants.HEART_SIZE,
                Constants.HEART_SIZE),
                this.heartImage, this.lifeCounter, this.brickerGameManager);
        fallingHeart.setCenter(brick_location);
        fallingHeart.setVelocity(new Vector2(0, Constants.FALLING_HEART_SPEED));
        this.brickerGameManager.addGameObject(fallingHeart, Layer.DEFAULT);
    }
}
