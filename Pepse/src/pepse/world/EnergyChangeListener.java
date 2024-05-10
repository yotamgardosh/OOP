package pepse.world;

/**
 * Interface for objects that want to observe when the energy changes.
 */
public interface EnergyChangeListener {
    /**
     * Called when the energy changes.
     * @param currentEnergy the current energy
     */
    void onEnergyChanged(float currentEnergy);
}
