package pepse;

import danogl.GameManager;
import danogl.util.Vector2;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.*;
import pepse.world.daynight.*;
import pepse.world.trees.*;

import java.util.List;

/**
 * The manager class for the game.
 */
public class PepseGameManager extends GameManager {
    private static Vector2 windowDimensions;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private static final float CYCLE_LENGTH = 30;
    private static final int SEED = 1;
    private static final int START = 0;
    private final Vector2 AVATAR_START;
    private static final Vector2 THREE_FOURTH_RES = Vector2.of(800, 600);


    /**
     * Constructor for the game manager.
     * @param windowTitle The title of the window.
     * @param windowDimensions The dimensions of the window.
     */
    public PepseGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        PepseGameManager.windowDimensions = windowDimensions;
        this.AVATAR_START = Vector2.of(windowDimensions.x()/2, windowDimensions.y()/2);

    }

    /**
     * Called when the game is started. Initializes the game.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * @param soundReader Contains a single method: readSound, which reads a wav file from disk.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not.
     * @param windowController controls window
     */
    public void initializeGame (
            ImageReader imageReader,
            SoundReader soundReader,
            UserInputListener inputListener,
            WindowController windowController) {
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        initializeWorld();
    }

    /**
     * Initializes the game world.
     * Creates the environment, the ground, the avatar, the trees and the energy display.
     */
    private void initializeWorld() {
        // create environment
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);

        GameObject night = Night.create(windowDimensions, CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        GameObject sun = Sun.create(windowDimensions, CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);

        // create ground
        Terrain terrain = new Terrain(windowDimensions, SEED);
        List<Block> groundBlocks = terrain.createInRange(START, (int) windowDimensions.x());
        for (GameObject block :groundBlocks){
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }

        //createAvatar();
        Avatar avatar = new Avatar(AVATAR_START, inputListener, imageReader);
        gameObjects().addGameObject(avatar);

        //energyCallback =  avatar::getEnergy;
        GameObject energyDisplay = EnergyDisplay.create(avatar);
        gameObjects().addGameObject(energyDisplay, Layer.UI);

        // create trees
        Flora floraCreator = new Flora(terrain::groundHeightAt, AVATAR_START);
        List<Tree> trees = floraCreator.createInRange(START, (int) windowDimensions.x());
        for (Tree tree : trees){
            gameObjects().addGameObject(tree.getTrunk());
            avatar.addJumpListener(tree);
            for (Leaf leaf : tree.getLeaves()){
                gameObjects().addGameObject(leaf, Layer.STATIC_OBJECTS);
                avatar.addJumpListener(leaf);
            }
            for (Fruit fruit : tree.getFruits()){
                gameObjects().addGameObject(fruit);
                avatar.addJumpListener(fruit);
            }
        }
    }

    /**
     * Called to run the game.
     */
    public static void main(String[] args) {
        new PepseGameManager("Pepse", THREE_FOURTH_RES).run();
    }
}
