/**
 * The GeniusPlayer class represents an intelligent player that strategically places marks on the game board.
 * It iterates through the cells in a diagonal manner, attempting to fill empty spots.
 * If the entire diagonal is filled, it moves to the next diagonal.
 *
 * @author Yotam
 */
public class GeniusPlayer implements Player {

    /**
     * Plays a turn by placing a mark on the game board in a strategic manner.
     * The player iterates through the cells in a diagonal manner, attempting to fill empty spots.
     * If the entire diagonal is filled, it moves to the next diagonal.
     *
     * @param board The game board on which the player places the mark.
     * @param mark The mark to be placed on the board.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int row = 0;
        int col = 1;
        while (board.getMark(row, col) != Mark.BLANK) {
            row++;
            if (row == board.getSize()) {
                row = 0;
                col++;
            }
            if (col == board.getSize()) {
                row = 0;
                col = 0;
            }
        }
        board.putMark(mark, row, col);
    }
}
