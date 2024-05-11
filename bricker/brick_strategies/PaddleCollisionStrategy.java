package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Collision strategy that generates an extra paddle.
 */
public class PaddleCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final Counter brickCounter;
    private final Renderable paddleImage;
    private final UserInputListener inputListener;
    private final WindowController windowController;

    /**
     * Constructs a PaddleCollisionStrategy instance with the necessary dependencies.
     *
     * @param brickerGameManager The game manager managing game objects and interactions.
     * @param brickCounter       The counter tracking the number of bricks in the game.
     * @param paddleImage        The renderable representing the paddle power-up.
     * @param inputListener      The user input listener for handling player input.
     * @param windowController   The window controller for managing the game window.
     */
    public PaddleCollisionStrategy(BrickerGameManager brickerGameManager, Counter brickCounter,
                                   Renderable paddleImage, UserInputListener inputListener,
                                   WindowController windowController) {
        this.brickerGameManager = brickerGameManager;
        this.brickCounter = brickCounter;
        this.paddleImage = paddleImage;
        this.inputListener = inputListener;
        this.windowController = windowController;
    }

    /**
     * Handles the collision between a brick and the player paddle by generating an ExtraPaddle power-up.
     * Removes the brick from the game and decrements the brick counter. Creates an ExtraPaddle instance
     * at a fixed location, and adds it to the game if an ExtraPaddle is not already present.
     *
     * @param gameObject1 The first game object involved in the collision (brick).
     * @param gameObject2 The second game object involved in the collision (player paddle).
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (this.brickerGameManager.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)) {
            this.brickCounter.decrement();
        }
        ExtraPaddle extraPaddle = new ExtraPaddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT), this.paddleImage, this.inputListener, this.windowController,
                this.brickerGameManager);
        if (!this.brickerGameManager.getIsExtraPaddle()) {
            extraPaddle.setCenter(new Vector2(Constants.EXTRA_PADDLE_X, Constants.EXTRA_PADDLE_Y));
            this.brickerGameManager.addGameObject(extraPaddle, Layer.DEFAULT);
            this.brickerGameManager.setIsExtraPaddle(true);
        }
    }
}
