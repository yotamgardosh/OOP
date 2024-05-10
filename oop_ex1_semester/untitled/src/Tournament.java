/**
 * The Tournament class represents a tic-tac-toe tournament between two players.
 * It manages multiple rounds, keeps track of scores, and provides the final results.
 *
 * @author Yotam
 */
public class Tournament {

    /*
     * Fields
     */

    public int numOfRounds = 1;
    public int curRound = 0;
    private final Player player1;
    private final Player player2;
    private final Renderer renderer;
    private final int[] tournamentScore = {0, 0, 0}; // represents player1 wins - player2 wins - ties

    /*
     * Public Methods
     */

    /**
     * Constructs a tournament with the specified number of rounds, renderer, and players.
     *
     * @param rounds The number of rounds in the tournament.
     * @param renderer The renderer to display the game board.
     * @param player1 The first player participating in the tournament.
     * @param player2 The second player participating in the tournament.
     */
    Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.numOfRounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Plays the tournament with the given board size and win streak, displaying the final results.
     *
     * @param size The size of the game board.
     * @param winStreak The number of marks in a row needed to win.
     * @param playerName1 The name of player 1.
     * @param playerName2 The name of player 2.
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        System.out.println("#######################################################");
        System.out.println("Let the games begin! Who will win? How will lose? Let's find out!");
        while (this.moreRoundsToPlay()) {
            Game currentGame = setupGame(this.renderer, this.player1, this.player2, size, winStreak);
            Mark winner = currentGame.run();
            updateScore(winner);
            System.out.println();
            this.curRound++;
        }
        finalStats(playerName1, playerName2);
    }

    /*
     * Private Methods
     */

    private boolean moreRoundsToPlay() {
        return !(this.curRound == this.numOfRounds);
    }

    private Game setupGame(Renderer renderer, Player player1, Player player2, int size, int winStreak) {
        if (this.curRound % 2 == 0) {
            return new Game(player1, player2, size, winStreak, renderer);  // player 1 is X player 2 is O
        }
        return new Game(player2, player1, size, winStreak, renderer); //  player 1 is O player 2 is X
    }

    private void updateScore(Mark winner) {
        if (winner == Mark.BLANK) {
            tournamentScore[2]++;
        } else if (winner == Mark.X) {
            tournamentScore[this.curRound % 2]++;
        } else {
            tournamentScore[(1 + this.curRound) % 2]++;
        }
    }

    private void finalStats(String playerName1, String playerName2) {
        System.out.println("######### Results #########");
        System.out.printf("Player 1, %s won: %d rounds%n", playerName1, this.tournamentScore[0]);
        System.out.printf("Player 2, %s won: %d rounds%n", playerName2, this.tournamentScore[1]);
        System.out.printf("Ties: %d%n", this.tournamentScore[2]);
        System.out.println("#######################################################\n\n");
    }

    /*
     * Main Method
     */

    public static void main(String[] args) {
        String[] lowerArgs = checkArgsValidity(args);
        if (lowerArgs == null) {
            return;
        }

        int roundsInTournament = Integer.parseInt(lowerArgs[0]);
        int boardSize = Integer.parseInt(lowerArgs[1]);
        int winStreak = Integer.parseInt(lowerArgs[2]);
        String[] playerStrings = new String[]{lowerArgs[4], lowerArgs[5]};

        PlayerFactory playerFactory = new PlayerFactory();

        Player[] players = new Player[2];
        for (int i = 0; i < 2; i++) {
            players[i] = playerFactory.buildPlayer(playerStrings[i]);
            if (players[i] == null) {
                System.out.printf("Failed to create player %s%n", playerStrings[i]);
            }
        }

        RendererFactory rendererFactory = new RendererFactory();

        Renderer renderer = rendererFactory.buildRenderer(lowerArgs[3], boardSize);

        if (renderer == null) {
            System.out.printf("Failed to renderer %s%n", lowerArgs[2]);
        }

        Tournament tournament = new Tournament(roundsInTournament, renderer, players[0], players[1]);

        tournament.playTournament(boardSize, winStreak, playerStrings[0], playerStrings[1]);
    }

    /**
     * Checks the validity of command-line arguments and returns a modified array if valid.
     * Prints error messages and returns null if arguments are invalid.
     *
     * @param args The command-line arguments.
     * @return Modified and validated arguments or null if invalid.
     */
    private static String[] checkArgsValidity(String[] args) {
        if (!("console".equalsIgnoreCase(args[3]) || "none".equalsIgnoreCase(args[3]))) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return null;
        }

        for (int i = 4; i < 6; i++) {
            if (!("human".equalsIgnoreCase(args[i]) || "clever".equalsIgnoreCase(args[i]) ||
                    "whatever".equalsIgnoreCase(args[i]) || "genius".equalsIgnoreCase(args[i]))) {
                System.out.println(Constants.UNKNOWN_PLAYER_NAME);
                return null;
            }
        }

        return new String[]{args[0], args[1], args[2], args[3].toLowerCase(), args[4].toLowerCase(),
                args[5].toLowerCase()};
    }
}
