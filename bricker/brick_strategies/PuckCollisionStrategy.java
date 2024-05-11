package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Collision strategy that generates Puck balls.
 */
public class PuckCollisionStrategy implements CollisionStrategy {

    private final BrickerGameManager brickerGameManager;
    private final Renderable puckImage;
    private final Sound ballSound;
    private Counter brickCounter;

    /**
     * Constructs a PuckCollisionStrategy instance with the necessary dependencies.
     *
     * @param brickerGameManager The game manager managing game objects and interactions.
     * @param brickCounter       The counter tracking the number of bricks in the game.
     * @param puckImage          The renderable representing the Puck power-up.
     * @param ballSound          The sound effect to be played on collision.
     */
    public PuckCollisionStrategy(BrickerGameManager brickerGameManager, Counter brickCounter,
                                 Renderable puckImage, Sound ballSound) {
        this.brickerGameManager = brickerGameManager;
        this.brickCounter = brickCounter;
        this.puckImage = puckImage;
        this.ballSound = ballSound;
    }

    /**
     * Handles the collision between a brick and the Puck power-up.
     * Removes the brick from the game, decrements the brick counter, and generates
     * two additional Puck instances.
     * Each Puck instance is set at the location of the collided brick with a randomized velocity.
     *
     * @param gameObject1 The first game object involved in the collision (brick).
     * @param gameObject2 The second game object involved in the collision (Puck power-up).
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        Vector2 brick_location = gameObject1.getCenter();
        if (this.brickerGameManager.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)) {
            this.brickCounter.decrement();
        }

        Puck puck1 = new Puck(Vector2.ZERO, new Vector2(Constants.PUCK_BALL_SIZE, Constants.PUCK_BALL_SIZE),
                this.puckImage,
                this.ballSound, this.brickerGameManager);
        Puck puck2 = new Puck(Vector2.ZERO, new Vector2(Constants.PUCK_BALL_SIZE, Constants.PUCK_BALL_SIZE),
                this.puckImage,
                this.ballSound, this.brickerGameManager);
        puck1.setCenter(brick_location);
        puck2.setCenter(brick_location);
        this.setPuckSpeed(puck1);
        this.setPuckSpeed(puck2);

        brickerGameManager.addGameObject(puck1, Layer.DEFAULT);
        brickerGameManager.addGameObject(puck2, Layer.DEFAULT);
    }

    private void setPuckSpeed(Puck puck) {
        Random rand = new Random();
        double angle = rand.nextDouble() * Math.PI;
        float ballVelX = (float) Math.cos(angle) * Constants.BALL_SPEED;
        float ballVelY = (float) Math.sin(angle) * Constants.BALL_SPEED;
        puck.setVelocity(new Vector2(ballVelX, ballVelY));
    }
}
