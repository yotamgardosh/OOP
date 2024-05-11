package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Collision strategy for handling interactions with bricks that trigger a camera event in the Bricker game.
 */
public class CameraCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager brickerGameManager;
    private final Ball ball;
    private Counter cameraCounter;
    private Counter brickCounter;
    private final WindowController windowController;

    /**
     * Constructs a new CameraCollisionStrategy instance.
     *
     * @param brickerGameManager The game manager associated with the brick strategies.
     * @param brickCounter       The counter representing the number of bricks in the game.
     * @param windowController   The window controller for managing the game window.
     * @param ball               The ball game object.
     * @param cameraCounter      The counter for managing camera-related events.
     */
    public CameraCollisionStrategy(BrickerGameManager brickerGameManager, Counter brickCounter,
                                   WindowController windowController, Ball ball, Counter cameraCounter) {
        this.brickerGameManager = brickerGameManager;
        this.brickCounter = brickCounter;
        this.windowController = windowController;
        this.ball = ball;
        this.cameraCounter = cameraCounter;

    }

    /**
     * Handles the collision between two game objects, removing the brick and triggering a camera
     * event if applicable.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (this.brickerGameManager.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)) {
            this.brickCounter.decrement();
        }
        if (gameObject2.getTag().equals(Constants.BALL_TAG)) {
            if (brickerGameManager.camera() == null) {
                this.brickerGameManager.setCamera(new Camera(this.ball,
                        Vector2.ZERO,
                        this.windowController.getWindowDimensions().mult(1.2f),
                        windowController.getWindowDimensions()));
                this.cameraCounter.increaseBy(this.ball.getCollisionCounter());
            }
        }
    }
}
