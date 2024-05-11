/**
 * The Renderer interface defines the contract for rendering a game board.
 * Implementing classes must provide the logic for displaying the current state of the board.
 *
 * @author Yotam
 */
public interface Renderer {

    /**
     * Renders the current state of the game board.
     *
     * @param board The game board to be rendered.
     */
    void renderBoard(Board board);
}
