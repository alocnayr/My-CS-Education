package escape.impl.movement.movevalidation.pieceattributemovementvalidation;

import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

// Implementation of the SpecialMoveValidator for validating the movement of a piece without jump, unblock, or fly
public class NormalMoveValidator implements MovementAttributeValidator {

    private final BoardInformation board;
    private final Coordinate to;
    private final String currentPlayer;

    /**
     * Constructor for this class
     * @param board the board for the game
     * @param to the location the piece is moving to
     * @param currentPlayer the current player
     */
    public NormalMoveValidator(BoardInformation board, Coordinate to,
                               String currentPlayer) {
        this.board = board;
        this.to = to;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Determine the validity of a normal move
     * @param row the row
     * @param col the column
     * @return true if the move is valid
     */
    @Override
    public boolean isValidMove(int row, int col) {
        boolean validCoordinate = board.getBounds().isWithinBounds(row, col);
        boolean containsPiece = board.getPieceLocations().containsKey(new CoordinateImpl(row, col));
        boolean finalLocation = new CoordinateImpl(row, col).equals(to);
        EscapePiece piece = board.getPieceLocations().get(new CoordinateImpl(row, col));
        boolean differentPlayer = piece != null && !piece.getPlayer().equals(currentPlayer);
        boolean hasPointConflict = board.getPointConflict();
        boolean pointConflict = containsPiece && finalLocation && differentPlayer && hasPointConflict;
        boolean notABlock = board.getBlockExitLocations().get(new CoordinateImpl(row, col)) != LocationType.BLOCK;
        boolean notAnExit = board.getBlockExitLocations().get(new CoordinateImpl(row, col)) != LocationType.EXIT;

        return validCoordinate && (!containsPiece || pointConflict) && notABlock && (notAnExit || finalLocation);
    }
}
