package pepse.world;

import danogl.util.Vector2;
import pepse.util.NoiseGenerator;
import danogl.gui.rendering.RectangleRenderable;
import pepse.util.ColorSupplier;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The terrain of the game.
 */
public class Terrain {
    private static final double NOISE_SCALE_FACTOR = 100;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final String BLOCK_TAG = "ground";
    private static final int TERRAIN_DEPTH = 20;
    private int groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;
    private final float HEIGHT_RATIO = (float) 2/3;

    /**
     * Creates a new terrain.
     * @param windowDimensions The dimensions of the window.
     * @param seed The seed for the noise generator.
     */
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = (int) (windowDimensions.y() * HEIGHT_RATIO);
        noiseGenerator = new NoiseGenerator(seed, groundHeightAtX0);
    }

    /**
     * Returns the height of the ground at the given x coordinate.
     * @param x The x coordinate.
     * @return The height of the ground at the given x coordinate.
     */
    public float groundHeightAt(int x) {
        return (float) (groundHeightAtX0 + noiseGenerator.noise(x, NOISE_SCALE_FACTOR));
    }

    /**
     * Creates a list of blocks in the given range.
     * @param minX The minimum x value.
     * @param maxX The maximum x value.
     * @return The list of blocks.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> groundBlocks = new ArrayList<>();
        int adjustedStart = minX - minX % Block.SIZE; // Adjust start to align with block size
        int adjustedEnd = maxX + Block.SIZE - (maxX % Block.SIZE); // Adjust end to align with block size

        for (int posX = adjustedStart; posX < adjustedEnd; posX += Block.SIZE) {
            int groundLevel = (int) (groundHeightAt(posX) / Block.SIZE * Block.SIZE);
            int maxDepth = groundLevel + TERRAIN_DEPTH * Block.SIZE;
            for (int posY = groundLevel; posY < maxDepth; posY += Block.SIZE) {
                Vector2 blockPosition = new Vector2(posX, posY);
                RectangleRenderable renderable = new RectangleRenderable(
                        ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Block groundBlock = new Block(blockPosition, renderable);
                groundBlock.physics().preventIntersectionsFromDirection(Vector2.UP);
                groundBlock.setTag(BLOCK_TAG);
                groundBlocks.add(groundBlock);
            }
        }
        return groundBlocks;
    }

}
