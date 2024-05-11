/**
 * The Player interface defines the contract for a player in a game.
 * Implementing classes must provide the logic for playing a turn on the game board.
 *
 * @author Yotam
 */
public interface Player {

    /**
     * Plays a turn on the game board by placing a mark.
     *
     * @param board The game board on which the player places the mark.
     * @param playerMark The mark to be placed on the board.
     */
    void playTurn(Board board, Mark playerMark);
}
