package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
import danogl.components.Transition;

/**
 * sun class
 */
public class Sun {

    private static final String SUN_TAG = "sun";
    private static final float SUN_DIAMETER = 150;
    private static final float HEIGHT_RATIO = (float) 2/3;
    private static final Float ANGLE_START = 0f;
    private static final Float ANGLE_END = 360f;

    /**
     * Creates a sun object that will transition from day to night and back again.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength The length of the day-night cycle in seconds.
     * @return The sun object.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){

        Vector2 initialSunCenter = new Vector2((windowDimensions.x() / 2),
                windowDimensions.y() * HEIGHT_RATIO / 2);

        Vector2 sunDimensions = new Vector2(SUN_DIAMETER, SUN_DIAMETER);

        GameObject sun = new GameObject(
                initialSunCenter,
                sunDimensions,
                new OvalRenderable(Color.YELLOW));

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);

        Vector2 cycleCenter = new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() * HEIGHT_RATIO);

        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter (initialSunCenter.subtract(cycleCenter)
                        .rotated(angle)
                        .add(cycleCenter)),
                ANGLE_START,
                ANGLE_END,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        return sun;
    }

}
