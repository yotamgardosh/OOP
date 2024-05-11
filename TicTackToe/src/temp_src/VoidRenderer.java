/**
 * The VoidRenderer class implements the Renderer interface and represents a renderer that
 * does not display any output. It is suitable for scenarios where rendering is not required.
 * Used in the Tournament class to suppress board rendering when the renderer type is "none".
 *
 * @author Yotam
 */
public class VoidRenderer implements Renderer {

    /**
     * Renders the game board. In this implementation, it does nothing since it's a void renderer.
     *
     * @param board The game board to be rendered.
     */
    @Override
    public void renderBoard(Board board) {
        // No rendering is performed for a void renderer.
        return;
    }
}
