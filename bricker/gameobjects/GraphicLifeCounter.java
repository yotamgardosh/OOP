package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;

/**
 * Represents a GraphicLifeCounter GameObject in the Bricker game.
 * Extends the GameObject class and manages the graphical representation of the player's remaining lives.
 */
public class GraphicLifeCounter extends GameObject {
    private final BrickerGameManager brickerGameManager;
    private final ArrayList<GameObject> hearts;
    private final Counter lifeCounter;
    private int numOfRenderedHearts;

    /**
     * Constructs a new GraphicLifeCounter instance.
     *
     * @param topLeftCorner      Position of the GraphicLifeCounter, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height of the GraphicLifeCounter in window coordinates.
     * @param renderable         The renderable representing the GraphicLifeCounter. Can be null,
     *                           in which case the GraphicLifeCounter will not be rendered.
     * @param brickerGameManager The game manager responsible for game logic.
     * @param hearts             The list of heart GameObjects representing individual lives.
     * @param lifeCounter        The Counter representing the player's remaining lives.
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                              BrickerGameManager brickerGameManager, ArrayList<GameObject> hearts,
                              Counter lifeCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.hearts = hearts;
        this.lifeCounter = lifeCounter;
        this.numOfRenderedHearts = Constants.BEGINNING_LIFE_COUNTER;
        for (int i = 0; i < numOfRenderedHearts; i++) {
            this.brickerGameManager.addGameObject(hearts.get(i), Layer.UI);
        }
    }

    /**
     * Updates the GraphicLifeCounter's state based on changes in the player's remaining lives.
     *
     * @param deltaTime The time passed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.lifeCounter.value() > this.numOfRenderedHearts &&
                this.numOfRenderedHearts < Constants.MAX_LIFE_NUMBER) {
            for (int i = this.numOfRenderedHearts; i < this.lifeCounter.value(); i++) {
                this.brickerGameManager.addGameObject(hearts.get(i), Layer.UI);
                this.numOfRenderedHearts++;
            }
        }

        if (this.lifeCounter.value() < this.numOfRenderedHearts && this.numOfRenderedHearts > 0) {
            for (int i = this.numOfRenderedHearts - 1; i >= this.lifeCounter.value(); i--) {
                this.brickerGameManager.removeGameObject(hearts.get(i), Layer.UI);
                this.numOfRenderedHearts--;
            }
        }

    }
}
