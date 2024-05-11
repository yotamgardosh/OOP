package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Interface representing a collision strategy for game objects in the Bricker game.
 */
public interface CollisionStrategy {

    /**
     * Defines the behavior to be executed when a collision occurs between two game objects.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    void onCollision(GameObject gameObject1, GameObject gameObject2);
}
