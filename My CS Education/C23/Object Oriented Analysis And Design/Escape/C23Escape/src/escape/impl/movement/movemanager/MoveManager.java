package escape.impl.movement.movemanager;


import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.impl.boardinformation.playerandpiece.PlayerInformation;
import escape.impl.gamemanager.EscapeGameManagerImpl;
import escape.impl.gamemanager.GameStatusImpl;
import escape.impl.movement.movevalidation.rules.PointConflict;
import escape.impl.movement.movevalidation.rules.RuleHandler;
import escape.impl.movement.movevalidation.Checkmate;
import escape.required.*;


import java.util.HashMap;
import java.util.Map;

// This class is for performing the move of a piece after the validity of the move is verified
public class MoveManager {


    private final BoardInformation board;
    private final PlayerInformation playerInformation;
    private final Map<Rule.RuleID, Integer> rules;
    private final GameStatusImpl gameStatus;
    private int turnNumber;
    private boolean gameWon;

    /**
     * Constructor for the MoveManager class
     * @param board the board
     * @param playerInformation information about the players
     * @param rules the rules of the game
     * @param turnNumber the current turn number
     * @param gameStatus the status of the game
     */
    public MoveManager(BoardInformation board, PlayerInformation playerInformation, Map<Rule.RuleID, Integer> rules, int turnNumber, GameStatusImpl gameStatus){
        this.board = board;
        this.playerInformation = playerInformation;
        this.rules = new HashMap<>(rules);
        this.turnNumber = turnNumber;
        this.gameStatus = gameStatus;
    }

    /**
     * Performs the move of the piece
     * @param from location the piece is moving from
     * @param to location the piece is moving to
     */
    public void move(Coordinate from, Coordinate to) {
        handleMovement(from, to);
        handlePoints(to);
        handleRules();
        handleCheckmate();
        handleTurnNumber();
    }


    /**
     * Moves the piece to the "to" location or deals with a point conflict
     * @param from the from location of the movement
     * @param to the to location of the movement
     */
    private void handleMovement(Coordinate from, Coordinate to) {
        Map<Coordinate, EscapePiece> pieceLocations = board.getPieceLocations();
        EscapePieceImpl currPiece = (EscapePieceImpl) pieceLocations.get(from);

        if (board.getPieceLocations().containsKey(to) && rules.containsKey(Rule.RuleID.POINT_CONFLICT)) {
            PointConflict.handlePieceConflict(pieceLocations, gameStatus, from, to);
        }
        else {
            pieceLocations.remove(from);
            pieceLocations.put(to, currPiece);
        }
    }


    /**
     * Partition the points to the player if they exited and remove the piece
     * @param to the to location of the movement
     */
    private void handlePoints(Coordinate to) {
        LocationType locationType = board.getBlockExitLocations().get(to);
        if (locationType == LocationType.EXIT) {
            EscapePieceImpl piece = (EscapePieceImpl) board.getPieceLocations().get(to);
            String currentPlayer = playerInformation.getCurrentPlayer();
            if (currentPlayer.equals(playerInformation.getPlayers()[0])) {
                playerInformation.addPlayer1Score(piece.getValue());
            } else {
                playerInformation.addPlayer2Score(piece.getValue());
            }
            board.getPieceLocations().remove(to);
        }
    }

    /**
     * Handles the rules and determine if the game is won based on the rules
     */
    private void handleRules(){
        RuleHandler ruleHandler = new RuleHandler(playerInformation, rules, turnNumber, gameStatus);
        ruleHandler.handleRules();
        this.gameWon = ruleHandler.getGetWon();
    }

    /**
     * Handles if the game has been won based on whether the other player can move
     * or if the current player has no more pieces
     */
    private void handleCheckmate() {
        if (playerInformation.getCurrentPlayer().equals(playerInformation.getPlayers()[0])) {
            Checkmate checkmate = new Checkmate(board, rules);
            if (checkmate.isCheckmate(playerInformation.getPlayers()[1])) {
                gameStatus.setMoveResult(GameStatus.MoveResult.WIN);
                gameWon = true;
                EscapeGameManagerImpl.addObserverMessage(playerInformation.getPlayers()[1] + " can't move, " + playerInformation.getCurrentPlayer() + " wins!");
                return;
            }
        }
        if (playerInformation.getCurrentPlayer().equals(playerInformation.getPlayers()[1])) {
            Checkmate checkmate = new Checkmate(board, rules);
            if (checkmate.isCheckmate(playerInformation.getPlayers()[0])) {
                gameStatus.setMoveResult(GameStatus.MoveResult.WIN);
                gameWon = true;
                EscapeGameManagerImpl.addObserverMessage(playerInformation.getPlayers()[0] + " can't move, " + playerInformation.getCurrentPlayer() + " wins!");
                return;
            }
        }
        if (!currentPlayerHasPieces()) {
            gameStatus.setMoveResult(GameStatus.MoveResult.WIN);
            gameWon = true;
            EscapeGameManagerImpl.addObserverMessage(playerInformation.getCurrentPlayer() + " has no pieces left. They win!");
            return;
        }
    }


    /**
     * @return true if the current player has any remaining pieces
     */
    private boolean currentPlayerHasPieces(){
        for(EscapePiece piece : board.getPieceLocations().values()){
            if(piece.getPlayer().equals(playerInformation.getCurrentPlayer())){
                return true;
            }
        }
        return false;
    }

    /**
     * Increment the turn number everytime after player 2 moves
     */
    private void handleTurnNumber() {
        String[] players = playerInformation.getPlayers();
        if (playerInformation.getCurrentPlayer().equals(players[1])) {
            turnNumber++;
        }
    }


    public boolean getGameWon(){
        return this.gameWon;
    }

    public int getTurnNumber(){
        return this.turnNumber;
    }

}
