package escape.impl.movement.movevalidation;


import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.impl.movement.pathfinder.ShortestPath;
import escape.impl.movement.pathfinder.ShortestPathImpl;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;
import escape.required.Rule;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Class for determining if the other player can make any valid move
public class Checkmate {


    private final BoardInformation board;
    private final Map<Rule.RuleID, Integer> rules;
    public Checkmate(BoardInformation board, Map<Rule.RuleID, Integer> rules){
        this.board = board;
        this.rules = new HashMap<>(rules);
    }


    /**
     * Determines whether the player can make any valid move
     * @param player the player
     * @return true if the player can not move to any spot on the board
     */
    public boolean isCheckmate(String player) {


        for (Map.Entry<Coordinate, EscapePiece> entry : board.getPieceLocations().entrySet()) {
            EscapePiece piece = entry.getValue();
            EscapePieceImpl epi = (EscapePieceImpl) piece;
            if (!piece.getPlayer().equals(player)) {
                continue;
            }

            ShortestPathImpl shortestPath = new ShortestPathImpl(board, piece);
            int x = entry.getKey().getRow();
            int y = entry.getKey().getColumn();

            //check the nearest 2 neighbors closest to the piece
            for (int row = x-2; row <= x+2; row++) {
                for (int col = y-2; col <= y+2; col++) {
                    Coordinate from = new CoordinateImpl(x, y);
                    Coordinate newCoordinate = new CoordinateImpl(row, col);
                    if (validInput(from, newCoordinate, player)) {
                        shortestPath.findShortestPath(from, newCoordinate);
                        if(epi.getDistance() < shortestPath.getPathSize()){
                            continue;
                        }
                        if (shortestPath.getPathSize() >= 1 && epi.getDistance() >= shortestPath.getPathSize()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Determines the validity of the input
     * @param from the from location
     * @param to the to location
     * @param currentPlayer the current player
     * @return true if the input is valid
     */
    private boolean validInput(Coordinate from, Coordinate to, String currentPlayer){
        int x = to.getRow();
        int y = to.getColumn();
        if(!board.getBounds().isWithinBounds(x, y)){
            return false;
        }
        if(board.getBlockExitLocations().get(to) == LocationType.BLOCK || from.equals(to)){
            return false;
        }


        return !board.getPieceLocations().containsKey(to) || (rules.containsKey(Rule.RuleID.POINT_CONFLICT) && !board.getPieceLocations().get(to).getPlayer().equals(currentPlayer));
    }


}
