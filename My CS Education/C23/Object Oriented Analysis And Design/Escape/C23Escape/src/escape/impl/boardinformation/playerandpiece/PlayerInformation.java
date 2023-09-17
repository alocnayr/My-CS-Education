package escape.impl.boardinformation.playerandpiece;

// This class is used as a data structure for storing information about the players
public class PlayerInformation {
    private final String[] players;
    private String currentPlayer;
    private int player1Score;
    private int player2Score;

    /**
     *
     * @param players an array of players in the game
     * @param currentPlayer the current player whose turn it is
     * @param player1Score player 1's score
     * @param player2Score player 2's score
     */
    public PlayerInformation(String[] players, String currentPlayer, int player1Score, int player2Score){
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }

    public String getCurrentPlayer(){
        return this.currentPlayer;
    }

    public String[] getPlayers(){
        return this.players;
    }
    public int getPlayer1Score() {
        return player1Score;
    }
    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public void addPlayer1Score(int player1Score) {
        this.player1Score += player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void addPlayer2Score(int player2Score) {
        this.player2Score += player2Score;
    }

}
