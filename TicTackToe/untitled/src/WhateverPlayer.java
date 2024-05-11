import java.util.Random;

/**
 * The WhateverPlayer class implements the Player interface and represents a player that makes
 * random moves on the game board. It selects a valid empty cell randomly to place its mark.
 *
 * @author Yotam
 */
public class WhateverPlayer implements Player {

    private Random rand = new Random();

    /**
     * Plays a turn by randomly selecting an empty cell on the board and placing the player's mark.
     *
     * @param board      The game board on which the turn is played.
     * @param playerMark The mark (X or O) of the player.
     */
    @Override
    public void playTurn(Board board, Mark playerMark) {
        int[][] availableMoves = validMoves(board);
        int upperBound = availableMoves.length;
        int randomMoveInd = rand.nextInt(upperBound);
        int row = availableMoves[randomMoveInd][0];
        int col = availableMoves[randomMoveInd][1];
        board.putMark(playerMark, row, col);
    }

    /**
     * Retrieves the array of valid moves (empty cells) on the given board.
     *
     * @param board The game board to check for valid moves.
     * @return An array containing the coordinates of valid moves.
     */
    private int[][] validMoves(Board board) {
        int size = board.getSize();
        int count = 0;
        int[][] validMovesArr = new int[size * size][2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getMark(i, j) == Mark.BLANK) {
                    validMovesArr[count][0] = i;
                    validMovesArr[count][1] = j;
                    count++;
                }
            }
        }
        int[][] result = new int[count][2];
        System.arraycopy(validMovesArr, 0, result, 0, count);
        return result;
    }
}
