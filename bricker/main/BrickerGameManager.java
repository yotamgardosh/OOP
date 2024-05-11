package bricker.main;

import bricker.brick_strategies.BrickStrategyFactory;
import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * The main game manager for the Bricker game. Manages game initialization, updates,
 * and interactions between game objects.
 */
public class BrickerGameManager extends GameManager {
    private int numOfBricksPerRow;
    private int nomOfBrickRows;
    private Counter lifeCounter;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Counter brickCounter;
    private UserInputListener inputListener;
    private boolean isExtraPaddle;
    private Counter cameraCounter;

    /**
     * Constructs a new BrickerGameManager instance with default brick and row values.
     *
     * @param windowTitle      Title of the game window.
     * @param windowDimensions Dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.numOfBricksPerRow = Constants.NUM_OF_BRICKS_PER_ROW;
        this.nomOfBrickRows = Constants.NUM_OF_BRICK_ROWS;
    }

    /**
     * Constructs a new BrickerGameManager instance with specified brick and row values.
     *
     * @param windowTitle       Title of the game window.
     * @param windowDimensions  Dimensions of the game window.
     * @param numOfBricksPerRow Number of bricks per row.
     * @param nomOfBrickRows    Number of brick rows.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numOfBricksPerRow,
                              int nomOfBrickRows) {
        super(windowTitle, windowDimensions);
        this.numOfBricksPerRow = numOfBricksPerRow;
        this.nomOfBrickRows = nomOfBrickRows;

    }

    /**
     * Initializes the Bricker game, including setting up game objects, counters, and other components.
     *
     * @param imageReader      The ImageReader for loading game images.
     * @param soundReader      The SoundReader for loading game sounds.
     * @param inputListener    The UserInputListener for handling user input.
     * @param windowController The WindowController for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.lifeCounter = new Counter(Constants.BEGINNING_LIFE_COUNTER);
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.cameraCounter = new Counter(0);
        this.isExtraPaddle = false;

        // create ball
        this.createBall(imageReader, soundReader);

        // create paddle
        this.createPaddle(imageReader, inputListener);

        // create walls
        this.createWalls();

        // create background
        this.createBackground(imageReader);

        // create bricks
        this.createBricks(imageReader, soundReader);

        // create GraphicLifeCounter
        this.createGraphicLifeCounter(imageReader);

        // create NumericLifeCounter
        this.createNumericLifeCounter();
    }

    /**
     * Adds a GameObject to the game with a specified layer.
     *
     * @param gameObject The GameObject to be added.
     * @param layer      The layer to which the GameObject should be added.
     */
    public void addGameObject(GameObject gameObject, int layer) {
        gameObjects().addGameObject(gameObject, layer);
    }


    /**
     * Removes a GameObject from the game with a specified layer.
     *
     * @param gameObject The GameObject to be removed.
     * @param layer      The layer from which the GameObject should be removed.
     * @return True if the GameObject was successfully removed, false otherwise.
     */
    public boolean removeGameObject(GameObject gameObject, int layer) {
        return gameObjects().removeGameObject(gameObject, layer);
    }

    /**
     * Retrieves the current state of the extra paddle flag.
     *
     * @return True if an extra paddle is active, false otherwise.
     */
    public boolean getIsExtraPaddle() {
        return this.isExtraPaddle;
    }

    /**
     * Sets the state of the extra paddle flag.
     *
     * @param bool The new value for the extra paddle flag.
     */
    public void setIsExtraPaddle(boolean bool) {
        this.isExtraPaddle = bool;
    }

    /**
     * Updates the game state.
     *
     * @param deltaTime The time passed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballHeight = ball.getCenter().y();
        if (ballHeight > this.windowDimensions.y()) {
            this.lifeCounter.decrement();
            this.setBallLocationAndSpeed();
        }
        if (this.lifeCounter.value() == 0) {
            this.endGameWindow(Constants.LOSE_MESSAGE);
        }
        if (this.brickCounter.value() <= 0 || this.inputListener.isKeyPressed(KeyEvent.VK_W)) {
            this.endGameWindow(Constants.WIN_MESSAGE);
        }
        if (this.camera() != null) {
            if (this.ball.getCollisionCounter() - this.cameraCounter.value() >=
                    Constants.CAMERA_BALL_COLLISION) {
                this.setCamera(null);
                this.cameraCounter.reset();
            }
        }
    }

    private void endGameWindow(String message) {
        if (this.windowController.openYesNoDialog(message)) {
            this.windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        Ball ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_SIZE, Constants.BALL_SIZE), ballImage
                , collisionSound,
                this);
        this.ball = ball;
        this.setBallLocationAndSpeed();
        this.ball.setTag(Constants.BALL_TAG);
        gameObjects().addGameObject(ball);
    }

    private void setBallLocationAndSpeed() {
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        this.ball.setVelocity(new Vector2(ballVelX, ballVelY));
        this.ball.setCenter(this.windowDimensions.mult((float) Constants.HALF));
    }

    private void createWalls() {
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(Constants.WALL_WIDTH,
                windowDimensions.y()),
                null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - Constants.WALL_WIDTH, 0),
                new Vector2(Constants.WALL_WIDTH, windowDimensions.y()), null);
        GameObject ceilingWall = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), Constants.WALL_WIDTH), null);
        gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(ceilingWall, Layer.STATIC_OBJECTS);
    }

    private void createBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(this.windowDimensions.x(),
                this.windowDimensions.y()), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

    }

    private void createPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT), paddleImage,
                inputListener, this.windowController);
        paddle.setCenter(new Vector2(this.windowDimensions.x() / Constants.TWO,
                (int) this.windowDimensions.y() - Constants.PADDLE_DIST_FROM_FLOUR));
        paddle.setTag(Constants.PADDLE_TAG);
        gameObjects().addGameObject(paddle);

    }

    private void createBricks(ImageReader imageReader, SoundReader soundReader) {
        this.brickCounter = new Counter(0);
        float totalBricksWidth = this.windowDimensions.x() - (Constants.WALL_WIDTH * Constants.TWO);
        float brickWidth =
                (totalBricksWidth - (numOfBricksPerRow - 1) * Constants.DIST_BETWEEN_TWO_BRICKS)
                        / numOfBricksPerRow;

        for (int row = 0; row < nomOfBrickRows; row++) {
            for (int col = 0; col < numOfBricksPerRow; col++) {
                this.brickCounter.increment();
                float brickX = Constants.WALL_WIDTH + col * (brickWidth + Constants.DIST_BETWEEN_TWO_BRICKS);
                float brickY =
                        Constants.WALL_WIDTH + row *
                                (Constants.BRICK_HEIGHT + Constants.DIST_BETWEEN_TWO_BRICKS);

                Renderable brickImage = imageReader.readImage("assets/brick.png", true);
                BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(this,
                        this.brickCounter, imageReader, soundReader, this.inputListener,
                        this.windowController, this.ball, this.cameraCounter, this.lifeCounter);
                CollisionStrategy strategy = brickStrategyFactory.getStrategy(Constants.STRATEGY_BOUND);
                GameObject brick = new Brick(new Vector2(brickX, brickY), new Vector2(brickWidth,
                        Constants.BRICK_HEIGHT), brickImage, strategy);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private void createGraphicLifeCounter(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        ArrayList<GameObject> hearts = new ArrayList<>();
        for (int i = 0; i < Constants.MAX_LIFE_NUMBER; i++) {
            float heartX =
                    Constants.HEART_X + i * (Constants.HEART_SIZE + Constants.DIST_BETWEEN_TWO_HEARTS);
            float heartY = this.windowDimensions.y() - Constants.HEART_Y;
            GameObject heart = new GameObject(new Vector2(heartX, heartY), new Vector2(Constants.HEART_SIZE,
                    Constants.HEART_SIZE), heartImage);
            hearts.add(heart);
        }
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(Vector2.ZERO, Vector2.ZERO, null,
                this, hearts, this.lifeCounter);
        this.gameObjects().addGameObject(graphicLifeCounter, Layer.UI);
    }

    private void createNumericLifeCounter() {
        String numString = Integer.toString(Constants.BEGINNING_LIFE_COUNTER);
        TextRenderable textRenderable = new TextRenderable(numString);
        textRenderable.setColor(Color.GREEN);
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(new Vector2(Constants.NUMERIC_X,
                this.windowDimensions.y() - Constants.NUMERIC_Y), new Vector2(Constants.NUMERIC_LIFE_SIZE,
                Constants.NUMERIC_LIFE_SIZE), textRenderable, lifeCounter);
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);
    }

    /**
     * The main method to launch the Bricker game.
     *
     * @param args Command line arguments. If present they use to set the num of bricks.
     */
    public static void main(String[] args) {
        if (args.length == Constants.TWO_ARGS) {
            int arg0 = Integer.parseInt(args[0]);
            int arg1 = Integer.parseInt(args[1]);
            GameManager gameManager = new BrickerGameManager(Constants.WINDOW_TITLE,
                    new Vector2(Constants.WINDOW_WIDTH,
                            Constants.WINDOW_LENGTH), arg0, arg1);
            gameManager.run();
        } else {
            GameManager gameManager = new BrickerGameManager(Constants.WINDOW_TITLE,
                    new Vector2(Constants.WINDOW_WIDTH,
                            Constants.WINDOW_LENGTH));
            gameManager.run();
        }
    }
}
