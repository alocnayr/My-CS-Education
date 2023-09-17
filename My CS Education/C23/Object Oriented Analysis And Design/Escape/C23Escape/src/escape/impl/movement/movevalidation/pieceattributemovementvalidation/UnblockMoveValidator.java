package escape.impl.movement.movevalidation.pieceattributemovementvalidation;

import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.required.Coordinate;
import escape.required.LocationType;

// Implementation of the MovementAttributeValidator interface
// for determining the validity of a piece moving with the unblock attribute
public class UnblockMoveValidator implements MovementAttributeValidator {

    private final BoardInformation board;
    private final Coordinate to;
    private final String currentPlayer;

    /**
     * Constructor for the unblock attribute
   */
    public UnblockMoveValidator(BoardInformation board, Coordinate to, String currentPlayer) {
        this.board = board;
        this.to = to;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Determine the validity of a move based on unblock attribute
     * @param row the row
     * @param col the column
     * @return true if the move is valid
     */
    @Override
    public boolean isValidMove(int row, int col) {
        boolean isExit = board.getBlockExitLocations().get(new CoordinateImpl(row, col)) == LocationType.EXIT;
        boolean hasPiece = board.getPieceLocations().containsKey(new CoordinateImpl(row, col));
        boolean isPointConflict = hasPiece && new CoordinateImpl(row, col).equals(to) && !board.getPieceLocations().get(new CoordinateImpl(row, col)).getPlayer().equals(currentPlayer) && board.getPointConflict();
        boolean isExitLastLocation = isExit && new CoordinateImpl(row, col).equals(this.to);

        boolean isWithinBounds = board.getBounds().isWithinBounds(row, col);
        boolean noPieceOrPointConflict = !hasPiece || isPointConflict;
        boolean noExitOrFinalLocation = isExitLastLocation || !isExit;

        return isWithinBounds && noPieceOrPointConflict && noExitOrFinalLocation;
    }

}
