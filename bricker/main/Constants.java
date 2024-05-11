package bricker.main;

/**
 * Class containing constants used throughout the Bricker game.
 */
public class Constants {

    /**
     * Constructor for the Constants class.
     */
    public Constants() {
    }

    /**
     * Paddle tag used for identification.
     */
    public static final String PADDLE_TAG = "paddle";

    /**
     * Ball tag used for identification.
     */
    public static final String BALL_TAG = "ball";

    /**
     * Maximum number of lives a player can have.
     */
    public static final int MAX_LIFE_NUMBER = 4;

    /**
     * Size of the heart image.
     */
    public static final int HEART_SIZE = 15;

    /**
     * Speed of the ball in the game.
     */
    public static final float BALL_SPEED = 175;

    /**
     * Width of the walls in the game window.
     */
    public static final float WALL_WIDTH = 10;

    /**
     * Size of the ball in the game.
     */
    public static final float BALL_SIZE = 20;

    /**
     * Width of the game window.
     */
    public static final float WINDOW_WIDTH = 700;

    /**
     * Length of the game window.
     */
    public static final float WINDOW_LENGTH = 500;


    /**
     * Width of the paddle.
     */
    public static final float PADDLE_WIDTH = 100;

    /**
     * Height of the paddle.
     */
    public static final float PADDLE_HEIGHT = 15;

    /**
     * Height of a brick in the game.
     */
    public static final float BRICK_HEIGHT = 15;

    /**
     * Distance of the paddle from the floor.
     */
    public static final int PADDLE_DIST_FROM_FLOUR = 30;

    /**
     * Distance between two bricks in the game.
     */
    public static final int DIST_BETWEEN_TWO_BRICKS = 5;

    /**
     * Message displayed when the player loses.
     */
    public static final String LOSE_MESSAGE = "You lose! Play again?";


    /**
     * Message displayed when the player wins.
     */
    public static final String WIN_MESSAGE = "You won! Play again?";

    /**
     * Distance between two heart images.
     */
    public static final int DIST_BETWEEN_TWO_HEARTS = 5;

    /**
     * Initial number of lives for the player.
     */
    public static final int BEGINNING_LIFE_COUNTER = 3;

    /**
     * Bound for the collision strategy selection.
     */
    public static final int STRATEGY_BOUND = 10;

    /**
     * Size of the numeric life counter.
     */
    public static final float NUMERIC_LIFE_SIZE = 20;

    /**
     * X position of the numeric life counter.
     */
    public static final float NUMERIC_X = 10;

    /**
     * Y position of the numeric life counter.
     */
    public static final float NUMERIC_Y = 27;

    /**
     * Number of arguments.
     */
    public static final int TWO_ARGS = 2;

    /**
     * Y position of the heart image.
     */
    public static final float HEART_Y = 20;

    /**
     * X position of the heart image.
     */
    public static final float HEART_X = 40;


    /**
     * Number of collisions for the camera strategy dis-activation.
     */
    public static final int CAMERA_BALL_COLLISION = 5;

    /**
     * Default number of bricks per row in the game.
     */
    public static final int NUM_OF_BRICKS_PER_ROW = 8;

    /**
     * Default number of brick rows in the game.
     */
    public static final int NUM_OF_BRICK_ROWS = 7;

    /**
     * Common mathematical constant representing half.
     */
    public static final double HALF = 0.5;

    /**
     * Common mathematical constant representing two.
     */
    public static final float TWO = 2;

    /**
     * Title of the game window.
     */
    public static final String WINDOW_TITLE = "Bricker";

    /**
     * Numeric life counter color change threshold.
     */
    public static final int YELLOW_NUMERIC_NUMBER = 2;

    /**
     * Speed of the paddle in the game.
     */
    public static final float PADDLE_SPEED = 300;

    /**
     * Source file for the sound used in the game.
     */
    public static final String SOUND_SOURCE = "assets/blop.wav";

    /**
     * Source file for the Puck image used in the game.
     */
    public static final String PUCK_IMAGE_SOURCE = "assets/mockBall.png";

    /**
     * Source file for the Puddle image used in the game.
     */
    public static final String PUDDLE_IMAGE_SOURCE = "assets/paddle.png";

    /**
     * Source file for the Heart image used in the game.
     */
    public static final String HEART_IMAGE_SOURCE = "assets/heart.png";

    /**
     * Type identifier for the Camera collision strategy.
     */
    public static final int CAMERA_STRATEGY = 1;

    /**
     * Type identifier for the Paddle collision strategy.
     */
    public static final int PADDLE_STRATEGY = 2;

    /**
     * Type identifier for the Puck collision strategy.
     */
    public static final int PUCK_STRATEGY = 3;

    /**
     * Type identifier for the Double collision strategy.
     */
    public static final int DOUBLE_STRATEGY = 4;

    /**
     * Type identifier for the lower bound of Basic collision strategy.
     */
    public static final int SMALL_BASIC_STRATEGY = 5;

    /**
     * Type identifier for the higher bound of Basic collision strategy.
     */
    public static final int BIG_BASIC_STRATEGY = 9;

    /**
     * Bound for special collision strategy.
     */
    public static final int SPECIAL_STRATEGY_BOUND = 5;

    /**
     * Min number of strategies in a double collision strategy.
     */
    public static final int NUM_OF_STRATEGIES_IN_DOUBLE = 2;

    /**
     * Max number of strategies in a double collision strategy.
     */
    public static final int EXTRA_NUM_OF_STRATEGIES_IN_DOUBLE = 3;

    /**
     * Falling heart speed.
     */
    public static final float FALLING_HEART_SPEED = 100;

    /**
     * Extra paddle initial X position.
     */
    public static final float EXTRA_PADDLE_X = 350;

    /**
     * Extra paddle initial Y position.
     */
    public static final float EXTRA_PADDLE_Y = 250;

    /**
     * Size of the Puck ball image.
     */
    public static final float PUCK_BALL_SIZE = 15;
}
