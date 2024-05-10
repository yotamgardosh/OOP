package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The Avatar class represents the player's character in the game.
 */
public class Avatar extends GameObject {
    private int currentKey = KEY_INIT;
    private float energy = MAX_ENERGY;
    private final UserInputListener inputListener;
    private final ImageReader imageReader;
    private static final int KEY_INIT = -1;
    private static final int KEY_IDLE = 0;
    private static final int KEY_RUN = 1;
    private static final int KEY_JUMP = 2;
    private static final float TIME_BETWEEN_CLIPS = 0.1f;
    private static final float ENERGY_RECOVERY = 1f;
    private static final float COST_TO_MOVE = 0.5f;
    private static final float COST_TO_JUMP = 10f;
    private static final float MAX_ENERGY = 100;
    private static final float MIN_ENERGY = 0;
    private static final String AVATAR_TAG = "avatar";
    private static final String INIT_IMAGE_PATH = "src/assets/idle_0.png";
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -350;
    private static final float GRAVITY = 400;
    private static final int AVATAR_SIZE = 30;
    private static final String[][] ANIMATION_IMAGES = {
            {"src/assets/idle_0.png", "src/assets/idle_1.png", "src/assets/idle_2.png",
                    "src/assets/idle_3.png"},
            {"src/assets/run_0.png", "src/assets/run_1.png", "src/assets/run_2.png",
                    "src/assets/run_3.png", "src/assets/run_4.png", "src/assets/run_5.png"},
            {"src/assets/jump_0.png", "src/assets/jump_1.png", "src/assets/jump_2.png",
                    "src/assets/jump_3.png"}
    };
    private List<EnergyChangeListener> energyChangeListeners = new ArrayList<>();
    private List<AvatarJumpListener> jumpListeners = new ArrayList<>();

    /**
     * Constructs an Avatar object with the specified position, input listener, and image reader.
     *
     * @param pos           The position of the avatar.
     * @param inputListener The input listener for user input.
     * @param imageReader   The image reader for avatar graphics.
     */
    public Avatar(Vector2 pos,
                  UserInputListener inputListener,
                  ImageReader imageReader){
        super(pos, Vector2.ONES.mult(AVATAR_SIZE), imageReader.readImage(INIT_IMAGE_PATH,
                true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;

        this.imageReader = imageReader;
        this.setTag(AVATAR_TAG);
        animate(KEY_IDLE, ANIMATION_IMAGES[KEY_IDLE]);
    }

    /**
     * Updates the avatar's position and energy level based on user input.
     * @param deltaTime The time since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if (energy >= COST_TO_MOVE) {
            if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
                xVel -= VELOCITY_X;
                setEnergy(-COST_TO_MOVE);
                animate(KEY_RUN, ANIMATION_IMAGES[KEY_RUN]);
                renderer().setIsFlippedHorizontally(true);
            }
            if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
                xVel += VELOCITY_X;
                setEnergy(-COST_TO_MOVE);
                animate(KEY_RUN, ANIMATION_IMAGES[KEY_RUN]);
                renderer().setIsFlippedHorizontally(false);
            }
        }
        transform().setVelocityX(xVel);
        float yVel = getVelocity().y();
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && yVel == 0 && getEnergy() >= COST_TO_JUMP) {
            transform().setVelocityY(VELOCITY_Y);
            setEnergy(-COST_TO_JUMP);
            animate(KEY_JUMP, ANIMATION_IMAGES[KEY_JUMP]);
            notifyJumpListeners();

        }
        if (xVel == 0 && yVel == 0) {
            setEnergy(ENERGY_RECOVERY);
            animate(KEY_IDLE, ANIMATION_IMAGES[KEY_IDLE]);
        }
    }

    /**
     * Sets the energy level of the avatar.
     * @param energy The energy level to set.
     */
    public void setEnergy(float energy) {
        if (this.energy + energy < MIN_ENERGY) {
            this.energy = MIN_ENERGY;
        } else if (this.energy + energy > MAX_ENERGY) {
            this.energy = MAX_ENERGY;
        } else {
            this.energy += energy;
        }
        notifyEnergyChangeListeners();
    }

    /**
     * Returns the energy level of the avatar.
     * @return Float of the energy level of the avatar.
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * Adds an energy change listener to the avatar.
     * @param listener The listener to add.
     */
    public void addEnergyChangeListener(EnergyChangeListener listener) {
        energyChangeListeners.add(listener);
    }

    /**
     * Removes an energy change listener from the avatar.
     * @param listener The listener to remove.
     */
    public void removeEnergyChangeListener(EnergyChangeListener listener) {
        energyChangeListeners.remove(listener);
    }

    /**
     * Adds a jump listener to the avatar.
     * @param listener The listener to add.
     */
    public void addJumpListener(AvatarJumpListener listener) {
        jumpListeners.add(listener);
    }

    /**
     * Removes a jump listener from the avatar.
     * @param listener The listener to remove.
     */
    public void removeJumpListener(AvatarJumpListener listener) {
        jumpListeners.remove(listener);
    }

    private void notifyJumpListeners() {
        for (AvatarJumpListener listener : jumpListeners) {
            listener.onAvatarJump();
        }
    }

    private void notifyEnergyChangeListeners() {
        for (EnergyChangeListener listener : energyChangeListeners) {
            listener.onEnergyChanged(getEnergy());
        }
    }

    private void animate(int nextKey, String[] animationPaths) {
        if (currentKey != nextKey) {
            AnimationRenderable animation = new AnimationRenderable(
                    animationPaths,
                    imageReader,
                    true,
                    TIME_BETWEEN_CLIPS);
            renderer().setRenderable(animation);
            currentKey = nextKey;
        }
    }

}
