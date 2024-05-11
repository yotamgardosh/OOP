package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents a Paddle GameObject in the Bricker game.
 * Extends the GameObject class and handles user input for paddle movement.
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    private final WindowController windowController;

    /**
     * Constructs a new Paddle instance.
     *
     * @param topLeftCorner      Position of the Paddle, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height of the Paddle in window coordinates.
     * @param renderable         The renderable representing the Paddle. Can be null,
     *                           in which case the Paddle will not be rendered.
     * @param inputListener      The UserInputListener for handling user input.
     * @param windowController   The WindowController for managing the game window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, WindowController windowController) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowController = windowController;
    }

    /**
     * Updates the Paddle's state based on user input and enforces movement boundaries.
     *
     * @param deltaTime The time passed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(Constants.PADDLE_SPEED));

        float windowWidth = windowController.getWindowDimensions().x();

        if (this.getTopLeftCorner().x() <= Constants.WALL_WIDTH) {
            this.setTopLeftCorner(new Vector2(Constants.WALL_WIDTH, getTopLeftCorner().y()));
        }

        Vector2 paddleDims = this.getDimensions();
        if (this.getTopLeftCorner().x() + paddleDims.x() >= windowWidth - Constants.WALL_WIDTH) {
            this.setTopLeftCorner(new Vector2(windowWidth - Constants.WALL_WIDTH - paddleDims.x(),
                    getTopLeftCorner().y()));
        }
    }
}
