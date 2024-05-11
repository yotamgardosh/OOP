package bricker.brick_strategies;

import bricker.main.Constants;
import danogl.GameObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Collision strategy that combines multiple collision strategies for a double impact effect.
 */
public class DoubleCollisionStrategy implements CollisionStrategy {
    private ArrayList<CollisionStrategy> strategiesArray;

    /**
     * Constructs a DoubleCollisionStrategy instance using the provided BrickStrategyFactory.
     *
     * @param brickStrategyFactory The factory for obtaining collision strategies.
     */
    public DoubleCollisionStrategy(BrickStrategyFactory brickStrategyFactory) {

        int num_of_strategies = Constants.NUM_OF_STRATEGIES_IN_DOUBLE;
        Random rand = new Random();
        int randomizedPecialStrategy = rand.nextInt(Constants.SPECIAL_STRATEGY_BOUND);
        if (randomizedPecialStrategy != Constants.DOUBLE_STRATEGY) {
            randomizedPecialStrategy = rand.nextInt(Constants.SPECIAL_STRATEGY_BOUND);
        }
        if (randomizedPecialStrategy == Constants.DOUBLE_STRATEGY) {
            num_of_strategies = Constants.EXTRA_NUM_OF_STRATEGIES_IN_DOUBLE;
        }

        this.strategiesArray = new ArrayList<>();
        for (int i = 0; i < num_of_strategies; i++) {
            CollisionStrategy strategy = brickStrategyFactory.getStrategy(Constants.DOUBLE_STRATEGY);
            this.strategiesArray.add(strategy);
        }
    }

    /**
     * Handles the collision between two game objects by applying each individual strategy
     * for the double collision effect.
     *
     * @param gameObject1 The first game object involved in the collision.
     * @param gameObject2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        for (CollisionStrategy strategy : strategiesArray) {
            strategy.onCollision(gameObject1, gameObject2);
        }
    }
}
