package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Represents the flora in the game world.
 */
public class Flora {
    private final Random random = new Random();
    private final Function<Integer, Float> groundHeightAt;
    private static final float TREE_PLACEMENT_THRESHOLD = 0.3f;
    private final Vector2 avatarStart;

    /**
     * Creates a new flora.
     * @param groundHeightAt The function to get the ground height at a given x value.
     * @param avatarStart The starting position of the avatar.
     */
    public Flora(Function<Integer, Float> groundHeightAt, Vector2 avatarStart) {
        this.groundHeightAt = groundHeightAt;
        this.avatarStart = avatarStart;
    }

    /**
     * Creates a list of trees in the given range.
     * @param minX The minimum x value.
     * @param maxX The maximum x value.
     * @return The list of trees.
     */
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        int adjustedStart = minX - minX % Block.SIZE; // Adjust start to align with block size
        int adjustedEnd = maxX + Block.SIZE - (maxX % Block.SIZE); // Adjust end to align with block size
        int xStart = (int) avatarStart.x();
        for (int x = adjustedStart; x < adjustedEnd; x += Block.SIZE) {
            boolean isNearAvatar = x < xStart + Block.SIZE && x > xStart - Block.SIZE;
            if (random.nextFloat() < TREE_PLACEMENT_THRESHOLD && !isNearAvatar){
                float groundHeight = groundHeightAt.apply(x);
                Vector2 treeBase = new Vector2(x, groundHeight);
                Tree tree = new Tree(treeBase);
                trees.add(tree);
            }
        }
        return trees;
    }
}
