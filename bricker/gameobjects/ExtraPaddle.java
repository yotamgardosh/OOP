package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents an ExtraPaddle GameObject in the Bricker game, extending the Paddle class.
 * Handles collision events and updates its state based on collisions and game logic.
 */
public class ExtraPaddle extends Paddle {
    private int extraPaddleCounter;
    private final BrickerGameManager brickerGameManeger;

    /**
     * Constructs a new ExtraPaddle instance.
     *
     * @param topLeftCorner       Position of the ExtraPaddle, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height of the ExtraPaddle in window coordinates.
     * @param renderable          The renderable representing the ExtraPaddle. Can be null,
     *                            in which case the ExtraPaddle will not be rendered.
     * @param inputListener       The UserInputListener for handling user input.
     * @param windowController    The WindowController for managing the game window.
     * @param brickerGameManager  The game manager responsible for game logic.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, WindowController windowController,
                       BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowController);
        this.brickerGameManeger = brickerGameManager;
        this.extraPaddleCounter = 0;
    }

    /**
     * Updates the ExtraPaddle's state based on game logic and collision events.
     *
     * @param deltaTime The time passed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.extraPaddleCounter >= 4) {
            this.brickerGameManeger.removeGameObject(this, Layer.DEFAULT);
            this.brickerGameManeger.setIsExtraPaddle(false);
        }
    }

    /**
     * Handles actions when a collision occurs. Updates the ExtraPaddle's state based on collisions.
     *
     * @param other     The GameObject with which the collision occurs.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.extraPaddleCounter++;
    }
}
