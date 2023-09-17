package escape.impl.movement.movevalidation;

import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.impl.boardinformation.playerandpiece.PlayerInformation;
import escape.impl.gamemanager.EscapeGameManagerImpl;
import escape.impl.gamemanager.GameStatusImpl;
import escape.impl.movement.pathfinder.ShortestPathImpl;
import escape.required.*;

import java.util.List;
import java.util.Map;

//This class is for validating movement when trying to move from one coordinate to another
public class MoveValidator {

    private final BoardInformation board;
    private final PlayerInformation playerInformation;
    private final Map<Rule.RuleID, Integer> rules;
    private final int turnNumber;
    private final GameStatus gameStatus;
    private final ValidateCoordinateMove coordinateValidator;
    private int pathSize;

    /**
     * Constructor for MoveValidator class
     * @param board the board of the game
     * @param playerInformation the player information
     * @param rules the rules
     * @param turnNumber the current turn number
     * @param gameStatus the gamestatus
     */
    public MoveValidator(BoardInformation board, PlayerInformation playerInformation, Map<Rule.RuleID, Integer> rules, int turnNumber, GameStatus gameStatus){

        this.board = board;
        this.playerInformation = playerInformation;
        this.turnNumber = turnNumber;
        this.rules = rules;
        this.gameStatus = gameStatus;
        this.coordinateValidator = new ValidateCoordinateMove(board.getBounds());
        this.pathSize = 0;
    }

    /**
     * Verifying the validity of the move based on input and distance
     * @param from from location of the move
     * @param to to location of the move
     * @return true if found a path with distance no more than piece distance
     */
    public boolean isValidMove(Coordinate from, Coordinate to) {

        GameStatusImpl gameStatusImpl = (GameStatusImpl) gameStatus;
        String currentPlayer = playerInformation.getCurrentPlayer();
        gameStatusImpl.setIsValidMove(false);
        EscapePiece piece = null;

        if(from.equals(to)){
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because they tried to move to the same location");
            return false;
        }

        if(rules.containsKey(Rule.RuleID.TURN_LIMIT)){
            if(turnNumber > rules.get(Rule.RuleID.TURN_LIMIT)){
                EscapeGameManagerImpl.addObserverMessage("Invalid move from " + currentPlayer + ", the game is already over from the turn limit being reached.");
                return false;
            }
        }

        if(rules.containsKey(Rule.RuleID.SCORE)){
            int score = rules.get(Rule.RuleID.SCORE);
            if(playerInformation.getPlayer1Score() >= score || playerInformation.getPlayer2Score() >= score){
                EscapeGameManagerImpl.addObserverMessage("Invalid move from "+ currentPlayer + ", the game is already over and " + playerInformation.getPlayers()[0] + " already won.");
                return false;
            }
        }

        if(board.getBlockExitLocations().get(to) == LocationType.BLOCK){
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because they tried to move to a block location");
            return false;
        }

        if(!coordinateValidator.validInput(from, to)){
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because they tried to move to a location off the board");
            return false;
        }

        piece = board.getPieceLocations().get(from);

        if(piece == null){
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because there is no piece located at " + from);
            return false;
        }

        if(!isPlayersTurn(piece)){
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because it is not their turn");
            return false;
        }

        if (board.getPieceLocations().containsKey(to) && !rules.containsKey(Rule.RuleID.POINT_CONFLICT)) {
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because there already exists a piece at " + to + " and there is no point conflict allowed.");
            return false;
        }

        if (board.getPieceLocations().containsKey(to) && rules.containsKey(Rule.RuleID.POINT_CONFLICT) && board.getPieceLocations().get(to).getPlayer().equals(currentPlayer)) {
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because there already exists a piece at " + to + " and you can't have a point conflict with your own player.");
            return false;
        }

        List<Coordinate> path = computeShortestPath(from, to, piece);

        if(this.pathSize < 1){
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because a valid path to " + to + " could not be found.");
            return false;
        }

        EscapePieceImpl epi = (EscapePieceImpl) piece;
        if(!validDistance(epi, this.pathSize)){
            EscapeGameManagerImpl.addObserverMessage("Invalid move: " + currentPlayer + " loses because their piece did not have enough distance to make it to " + to);
            return false;
        }

        gameStatusImpl.setIsValidMove(true);
        gameStatusImpl.setPath(path);
        gameStatusImpl.setPathSize(this.pathSize);
        return true;
    }

    // return true if the piece has enough distance to travel along the shortest path

    /**
     * Determines the validity of the move based on distance
     * @param piece the current piece
     * @param pathSize the path size
     * @return true if the piece has distance <= path size
     */
    private boolean validDistance(EscapePieceImpl piece, int pathSize){

        for(PieceAttribute attribute: piece.getAttribute()){
            if(attribute.getId() == EscapePiece.PieceAttributeID.DISTANCE){
                if(pathSize > attribute.getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    //return true if the player the piece that is trying to be moved is owned by the current player

    /**
     * Determine if it's the players turn
     * @param piece the piece
     * @return true if it is the players turn
     */
    private boolean isPlayersTurn(EscapePiece piece){
        return piece.getPlayer().equals(playerInformation.getCurrentPlayer());
    }

    //returns a list (path) of coordinates that represents the shortest path to the destination

    /**
     * Computes the shortest path from the start to final destination
     * @param from from location
     * @param to to location
     * @param piece the piece
     * @return the path from the start to final destination as a list of coordinates
     */
    private List<Coordinate> computeShortestPath(Coordinate from, Coordinate to, EscapePiece piece){
        List<Coordinate> path = null;
        ShortestPathImpl shortestPath = new ShortestPathImpl(board, piece);
        shortestPath.setPointConflict(rules.containsKey(Rule.RuleID.POINT_CONFLICT));
        shortestPath.setCurrentPlayer(playerInformation.getCurrentPlayer());
        path = shortestPath.findShortestPath(from, to);
        this.pathSize = shortestPath.getPathSize();

        return path;
    }
}
