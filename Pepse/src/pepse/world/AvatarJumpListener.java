package pepse.world;

/**
 * Interface for objects that want to observe when the avatar jumps.
 */
@FunctionalInterface
public interface AvatarJumpListener {
    /**
     * Called when the a jump is ditected.
     */
    void onAvatarJump();
}