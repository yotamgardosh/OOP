package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.AvatarJumpListener;
import pepse.world.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A tree is a GameObject that consists of a trunk and a canopy of leaves.
 * It is a GameObject and an AvatarJumpListener
 */
public class Tree extends GameObject implements AvatarJumpListener {
    private final GameObject trunk;
    private final ArrayList<Leaf> leaves = new ArrayList<>();
    private final ArrayList<Fruit> fruits = new ArrayList<>();
    private static final Random random = new Random();
    private static final float LEAF_SIZE = 15f;
    private static final float MIN_TRUNK_HEIGHT = 80f;
    private static final float MAX_TRUNK_HEIGHT = 160f;
    private static final float TRUNK_WIDTH = 20;
    private static final float CANOPY_WIDTH = 100;
    private static final float CANOPY_HEIGHT = 120;
    private static final float LEAF_PLACEMENT_THRESHOLD = 0.4f;
    private static final float FRUIT_PLACEMENT_THRESHOLD = 0.1f;
    private static final int BASE_RED = 100;
    private static final int COLOR_VARIATION = 60;



    /**
     * Constructor for the Tree class, creating the trunk and leaves.
     * @param basePosition The base position of the tree trunk.
     */
    public Tree(Vector2 basePosition) {

        super(basePosition, Vector2.ZERO, null);

        // Adjust basePosition for the trunk's bottom to be at the specified position
        float trunkHeight = MIN_TRUNK_HEIGHT + random.nextFloat() * (MAX_TRUNK_HEIGHT - MIN_TRUNK_HEIGHT);

        Vector2 trunkPosition = basePosition.subtract(new Vector2(TRUNK_WIDTH / 2, trunkHeight));
        // Create the trunk as an immovable Block
        RectangleRenderable renderableForTrunk = new RectangleRenderable(getRandomBarkColor());
        this.trunk = new Block(trunkPosition, new Vector2(TRUNK_WIDTH, trunkHeight), renderableForTrunk);

        trunk.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        trunk.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

        // Calculate the canopy's top left position
        Vector2 canopyTopLeft = basePosition.subtract(new Vector2(CANOPY_WIDTH / 2,
                trunkHeight + (CANOPY_HEIGHT / 2)));

        // Generate leaves and fruit
        int gridRows = (int) (CANOPY_HEIGHT / LEAF_SIZE);
        int gridCols = (int) (CANOPY_WIDTH / LEAF_SIZE);

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                // Randomly decide whether to place a leaf at this grid spot
                if (random.nextFloat() < LEAF_PLACEMENT_THRESHOLD) {
                    // Calculate the actual position based on the grid spot
                    Vector2 leafPosition = new Vector2(
                            canopyTopLeft.x() + col * LEAF_SIZE,
                            canopyTopLeft.y() + row * LEAF_SIZE);
                    // Create the leaf at the calculated position
                    Leaf leaf = new Leaf(leafPosition, Vector2.ONES.mult(LEAF_SIZE));
                    leaves.add(leaf);
                }
                else if (random.nextFloat() < FRUIT_PLACEMENT_THRESHOLD) {
                    // Calculate the actual position based on the grid spot
                    Vector2 fruitPosition = new Vector2(
                            canopyTopLeft.x() + col * LEAF_SIZE,
                            canopyTopLeft.y() + row * LEAF_SIZE);
                    // Create the leaf at the calculated position
                    Fruit fruit = new Fruit(fruitPosition);
                    fruits.add(fruit);
                }
            }
        }

    }

    /**
     * Returns the trunk of the tree.
     * @return The trunk of the tree.
     */
    public GameObject getTrunk() {
        return trunk;
    }

    /**
     * Returns the leaves of the tree.
     * @return ArrayList of Leaf objects.
     */
    public ArrayList<Leaf> getLeaves() {
        return new ArrayList<>(leaves); // Return a copy to prevent external modification
    }
    /**
     * Returns the fruits of the tree.
     * @return ArrayList of Fruit objects.
     */
    public ArrayList<Fruit> getFruits() {
        return new ArrayList<>(fruits); // Return a copy to prevent external modification
    }

    /**
     * When the avatar jumps, the trunk changes color
     */
    @Override
    public void onAvatarJump() {
        this.trunk.renderer().setRenderable(new RectangleRenderable(getRandomBarkColor()));
    }


    private static Color getRandomBarkColor() {
        // Randomly adjust the red component within the variation range
        int redVariation = random.nextInt(COLOR_VARIATION + 1) - (COLOR_VARIATION / 2);
        // Ensure within valid color range
        int adjustedRed = Math.min(Math.max(BASE_RED + redVariation, 0), 255);
        return new Color(adjustedRed, 50, 20);
    }
}
