package pepse.world.daynight;


import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.components.CoordinateSpace;
import danogl.util.Vector2;

import java.awt.*;

/**
 * night class
 */
public class Night {

    private static final float MIDNIGHT_OPACITY = 0.5f;
    private static final Float INITIAL_TRANSITION_VALUE = 0f;
    private static final String NIGHT_TAG = "night";

    /**
     * Creates a night object that will transition from day to night and back again.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength The length of the day-night cycle in seconds.
     * @return The night object.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength ) {
        Renderable blackRec = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                blackRec);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);
        float transitionTime = cycleLength / 2;
        new Transition<>(
                night,
                night.renderer()::setOpaqueness,
                INITIAL_TRANSITION_VALUE,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                transitionTime,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        return night;
    }

}
