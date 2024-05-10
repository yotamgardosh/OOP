package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A block is a GameObject that is immovable and has a fixed size.
 */
public class Block extends GameObject{

/**
 * The size of the block.
 */
public static final int SIZE = 30;
    /**
     * Creates a block with the given top left corner and renderable.
     * @param topLeftCorner the top left corner of the block
     * @param renderable the renderable of the block
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
    /**
     * creates a block with given top left corner, dimensions and renderable
     * @param topLeftCorner the top left corner of the block
     * @param dimensions the dimensions of the block
     * @param renderable the renderable of the block
     */
    public Block(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

}
