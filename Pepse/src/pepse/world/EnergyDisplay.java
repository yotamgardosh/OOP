package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

/**
 * A class for creating a GameObject that displays the avatar's energy.
 */
public class EnergyDisplay {

    private static final String ENERGY_COUNTER_TAG = "energyCounter";
    private static final int INIT_X = 5;
    private static final int INIT_Y = 5;
    private static final int DIAMETER = 20;

    /**
     * Creates a GameObject that displays the avatar's energy.
     * @param avatar the avatar whose energy is to be displayed
     * @return a GameObject that displays the avatar's energy
     */
    public static GameObject create(Avatar avatar) {
        TextRenderable visualEnergyCounter = new TextRenderable("Energy: " + avatar.getEnergy());
        GameObject energyCounter = new GameObject(new Vector2(INIT_X, INIT_Y),
                new Vector2(DIAMETER, DIAMETER),
                visualEnergyCounter);
        energyCounter.setTag(ENERGY_COUNTER_TAG);

        // Register an EnergyChangeListener with the avatar
        avatar.addEnergyChangeListener(currentEnergy ->
                visualEnergyCounter.setString("Energy: " + currentEnergy));

        return energyCounter;
    }
}
