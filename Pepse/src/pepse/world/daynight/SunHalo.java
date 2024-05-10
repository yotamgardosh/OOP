package pepse.world.daynight;


import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;

import java.awt.*;

/**
 * SunHalo class
 */
public class SunHalo {

    private static final String HALO_TAG = "halo";
    private static final float SIZE_FACTOR = 1.5f;
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);

    /**
     * Creates a sun halo object that will follow the sun.
     * @param sun The sun object.
     * @return The sun halo object.
     */
    public static GameObject create(GameObject sun){
        GameObject sunHalo = new GameObject(
                sun.getCenter(),
                sun.getDimensions().mult(SIZE_FACTOR),
                new OvalRenderable(HALO_COLOR)
        );
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        sunHalo.setCoordinateSpace(sun.getCoordinateSpace());
        sunHalo.setTag(HALO_TAG);
        return sunHalo;
    }


}
