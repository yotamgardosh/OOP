package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import bricker.gameobjects.Ball;

import java.util.Random;

/**
 * Factory class for creating different collision strategies for bricks in the Bricker game.
 */
public class BrickStrategyFactory {
    private final BrickerGameManager brickGameManager;
    private final Sound ballSound;
    private final Renderable puckImage;
    private final Renderable puddleImage;
    private final Renderable heartImage;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Ball ball;
    private Counter lifeCounter;
    private Counter cameraCounter;
    private Counter brickCounter;

    /**
     * Constructs a new BrickStrategyFactory instance.
     *
     * @param brickerGameManager The game manager associated with the brick strategies.
     * @param brickCounter       The counter representing the number of bricks in the game.
     * @param imageReader        The image reader for loading images.
     * @param soundReader        The sound reader for loading sounds.
     * @param inputListener      The user input listener for handling user input.
     * @param windowController   The window controller for managing the game window.
     * @param ball               The ball game object.
     * @param cameraCounter      The counter for managing camera-related events.
     * @param lifeCounter        The counter representing the player's remaining lives.
     */
    public BrickStrategyFactory(BrickerGameManager brickerGameManager, Counter brickCounter,
                                ImageReader imageReader, SoundReader soundReader,
                                UserInputListener inputListener, WindowController windowController,
                                Ball ball, Counter cameraCounter, Counter lifeCounter) {
        this.brickGameManager = brickerGameManager;
        this.brickCounter = brickCounter;
        this.ballSound = soundReader.readSound(Constants.SOUND_SOURCE);
        this.puckImage = imageReader.readImage(Constants.PUCK_IMAGE_SOURCE, true);
        this.puddleImage = imageReader.readImage(Constants.PUDDLE_IMAGE_SOURCE, true);
        this.heartImage = imageReader.readImage(Constants.HEART_IMAGE_SOURCE, true);
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.ball = ball;
        this.cameraCounter = cameraCounter;
        this.lifeCounter = lifeCounter;
    }

    /**
     * Gets a collision strategy based on a randomly chosen integer within a specified bound.
     *
     * @param bound The upper limit for choosing the collision strategy.
     * @return A collision strategy instance based on the randomly chosen integer.
     */
    public CollisionStrategy getStrategy(int bound) {
        Random rand = new Random();
        int chooseCollisionInt = rand.nextInt(bound);
        if (chooseCollisionInt <= Constants.BIG_BASIC_STRATEGY &&
                chooseCollisionInt >= Constants.SMALL_BASIC_STRATEGY) {
            return new BasicCollisionStrategy(this.brickGameManager, this.brickCounter);
        }
        if (chooseCollisionInt == Constants.DOUBLE_STRATEGY) {
            return new DoubleCollisionStrategy(this);
        }
        if (chooseCollisionInt == Constants.PUCK_STRATEGY) {
            return new PuckCollisionStrategy(this.brickGameManager, this.brickCounter, this.puckImage,
                    this.ballSound);
        }
        if (chooseCollisionInt == Constants.PADDLE_STRATEGY) {
            return new PaddleCollisionStrategy(this.brickGameManager, this.brickCounter, this.puddleImage,
                    this.inputListener, this.windowController);
        }
        if (chooseCollisionInt == Constants.CAMERA_STRATEGY) {
            return new CameraCollisionStrategy(this.brickGameManager, this.brickCounter,
                    this.windowController, this.ball, this.cameraCounter);
        } else {
            return new HeartCollisionStrategy(this.brickGameManager, this.brickCounter, this.heartImage,
                    this.lifeCounter);
        }
    }
}
