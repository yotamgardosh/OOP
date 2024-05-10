package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.AvatarJumpListener;

import java.awt.*;
import java.util.Random;

/**
 * A leaf in the trees of the game.
 * It is a GameObject and an AvatarJumpListener
 */
public class Leaf extends GameObject implements AvatarJumpListener {
    private static final Random random = new Random();
    private static final int BASE_GREEN = 200;
    private static final int COLOR_VARIATION = 20;
    private static final String LEAF_TAG = "leaf";
    private static final int WAIT_TIME = 2;
    private final Vector2 dimensions;


    /**
     * Construct a new Leaf instance.
     * @param coordinates Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public Leaf(Vector2 coordinates, Vector2 dimensions) {
        super(coordinates, dimensions, new RectangleRenderable(getRandomLeafColor()));
        this.dimensions = dimensions;
        setTag(LEAF_TAG);
        float randWaitTime = random.nextFloat() * WAIT_TIME;
        new ScheduledTask(
                this,
                randWaitTime,
                true,
                this::transitions
        );
    }

    /**
     * When the avatar jumps, the leaf rotates 90 degrees.
     */
    @Override
    public void onAvatarJump() {
        // Calculate the current angle and the target angle for rotation.
        float currentAngle = this.renderer().getRenderableAngle();
        float targetAngle = currentAngle + 90; // Rotate 90 degrees on jump

        // Create a transition to rotate the leaf
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle, // Method reference to set the leaf's angle
                currentAngle, // Starting angle
                targetAngle, // Target angle after rotation
                Transition.LINEAR_INTERPOLATOR_FLOAT, // Linear interpolation for smooth rotation
                0.5f, // Duration of the rotation transition, adjust as needed
                Transition.TransitionType.TRANSITION_ONCE, // Execute this rotation once per jump
                // Ensure angle stays within 0-359 degrees
                () -> this.renderer().setRenderableAngle(targetAngle % 360)
        );
    }

    /**
     * Generates a color for the leaf with slight variation in the green channel.
     * @return A Color object for the leaf.
     */
    private static Color getRandomLeafColor() {
        // Randomly adjust the green component within the variation range
        int greenVariation = random.nextInt(COLOR_VARIATION + 1) - (COLOR_VARIATION / 2);
        // Ensure within valid color range
        int adjustedGreen = Math.min(Math.max(BASE_GREEN + greenVariation, 0), 255);
        return new Color(50, adjustedGreen, 30);
    }

    private void transitions(){
        rotateLeaf();
        leafWidthOscillation();
    }

    private void rotateLeaf(){
        float initialAngle = -5;
        float finalAngle = 5;
        float rotationTime = 2;
        new Transition<>(
                this,
                (Float angle) -> this.renderer().setRenderableAngle(angle),
                initialAngle,
                finalAngle,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                rotationTime,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }

    private void leafWidthOscillation(){
        float initialScale = 1.0f;
        float finalScale = 1.5f;
        float scaleTime = 4f;
        new Transition<>(
                this,
                scale -> this.setDimensions(new Vector2(
                this.dimensions.x() * scale, this.dimensions.y())),
                initialScale,
                finalScale,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                scaleTime,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );

    }


}

