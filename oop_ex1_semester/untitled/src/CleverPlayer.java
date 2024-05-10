/**
 * The CleverPlayer class represents a player that intelligently places marks on the game board.
 * It starts from the top-left corner of the board and iterates through each cell,
 * placing the mark in the first available empty spot.
 * If the entire row is filled, it moves to the next row.
 *
 * @author Yotam
 */
public class CleverPlayer implements Player {

    /**
     * Plays a turn by placing a mark on the game board in an intelligent manner.
     * The player starts from the top-left corner and fills each empty spot in order.
     *
     * @param board The game board on which the player places the mark.
     * @param mark The mark to be placed on the board.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int row = 0;
        int col = 0;
        while (board.getMark(row, col) != Mark.BLANK) {
            col++;
            if (col == board.getSize()) {
                col = 0;
                row++;
            }
        }
        board.putMark(mark, row, col);
    }
}
