package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.AvatarJumpListener;

import java.awt.Color;
import java.util.Random;

/**
 * A fruit that can be eaten by the avatar
 * It is a GameObject and an AvatarJumpListener
 */
public class Fruit extends GameObject implements AvatarJumpListener {

    private boolean isEaten = false;
    private static final Random random = new Random();

    private static final Color INIT_FRUIT_COLOR = new Color(255, 0, 0); // Example color
    private static final float FRUIT_SIZE = 15f; // Example size, similar to leaves
    private static final int ENERGY_GAIN = 10; // Energy points gained
    private static final float REAPPEAR_CYCLE_TIME = 30; // Time in seconds for reappear
    private static final Color[] FRUIT_COLORS = {
            new Color(255, 0, 0),
            new Color(255, 255, 0),
            new Color(255, 100, 0),
            new Color(200, 0, 255),
            new Color(255, 100, 100),
            new Color(255, 0, 100)
    };

    /**
     * Construct a new Fruit instance.
     * @param topLeftCorner Position of the object, in window coordinates.
     */
    public Fruit(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(FRUIT_SIZE), new OvalRenderable(INIT_FRUIT_COLOR));
        setTag("Fruit");
    }
    /**
     * Check if the fruit should collide with another object.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Avatar;
    }
    /**
     * When the avatar collides with the fruit, the fruit is removed and the avatar's energy is increased
     * also a scheduled task is created to add the fruit back after life cycle length
     * @param other the other game object
     * @param collision the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (isEaten) return;
        if (other instanceof Avatar) {
            ((Avatar) other).setEnergy(ENERGY_GAIN);
            fruitCollisionAction();
        }
    }
    /**
     * When the avatar jumps, the fruit changes color
     */
    @Override
    public void onAvatarJump() {
        this.renderer().setRenderable(
                new OvalRenderable(FRUIT_COLORS[random.nextInt(0,FRUIT_COLORS.length - 1)]));
    }

    private void fruitCollisionAction(){
        isEaten = true;
        this.renderer().setOpaqueness(0);
        new ScheduledTask(
                this,
                REAPPEAR_CYCLE_TIME,
                false,
                this::reappearFruit
        );
    }

    private void reappearFruit(){
        isEaten = false;
        this.renderer().setOpaqueness(1);
    }

}
