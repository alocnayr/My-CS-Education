package escape.impl.movement.movevalidation.pieceattributemovementvalidation;

import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.impl.movement.pathfinder.DirectionClassifier;
import escape.required.Coordinate;
import escape.required.LocationType;

// Implementation of the MovementAttributeValidator interface for validating the movement of the jump attribute
public class JumpMoveValidator implements MovementAttributeValidator {
    private final BoardInformation board;
    private final Coordinate to;
    private final String currentPlayer;
    private final String direction;
    private boolean canJump;

    /**
     * Constructor for the jump move
     * @param board the board of the game
     * @param to to location moving to
     * @param currentPlayer the current player
     * @param direction the direction the piece is moving
     */
    public JumpMoveValidator(BoardInformation board, Coordinate to,
                             String currentPlayer, String direction) {
        this.board = board;
        this.to = to;
        this.currentPlayer = currentPlayer;
        this.direction = direction;
        this.canJump = false;
    }

    /**
     * Determine the validity of a move based on jump attribute
     * @param row the row
     * @param col the column
     * @return true if the move is valid
     */
    @Override
    public boolean isValidMove(int row, int col) {

        if (!board.getBounds().isWithinBounds(row, col)) {
            return false;
        }

        CoordinateImpl coordinate = new CoordinateImpl(row, col);
        if (board.getBlockExitLocations().get(coordinate) == LocationType.EXIT && coordinate.equals(to)) {
            return true;
        }

        if (board.getPieceLocations().containsKey(coordinate) || board.getBlockExitLocations().get(coordinate) == LocationType.EXIT) {
            return checkIfCanJump(row, col);
        }

        return board.getBlockExitLocations().get(coordinate) != LocationType.BLOCK;
    }

    /**
     * Method for verifying if the piece can make a jump over a cell to another cell
     * @param row the row
     * @param col the column
     * @return true if a valid jump can be made
     */
    private boolean checkIfCanJump(int row, int col) {
        DirectionClassifier direction = new DirectionClassifier(this.direction, row, col);
        int newRow = direction.getX();
        int newCol = direction.getY();
        CoordinateImpl newCoordinate = new CoordinateImpl(newRow, newCol);

        boolean isWithinBounds = board.getBounds().isWithinBounds(newRow, newCol);
        boolean isBlock = board.getBlockExitLocations().get(newCoordinate) == LocationType.BLOCK;
        boolean isPiece = board.getPieceLocations().containsKey(newCoordinate);
        boolean isExit = board.getBlockExitLocations().get(newCoordinate) == LocationType.EXIT;
        boolean isConflict = board.getPointConflict();
        boolean noPieceOrIsPointConflict = (!isPiece ||
                (!board.getPieceLocations().get(newCoordinate).getPlayer().equals(currentPlayer) && isConflict && newCoordinate.equals(to)));
        boolean noExitOrIsFinalLocation = !isExit || newCoordinate.equals(to);

        if (isWithinBounds && !isBlock && noPieceOrIsPointConflict && noExitOrIsFinalLocation) {
            this.canJump = true;
            return true;
        }
        return false;
    }

    public boolean canJump(){
        return this.canJump;
    }
}