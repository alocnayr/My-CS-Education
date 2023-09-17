package escape.impl.movement.movevalidation.pieceattributemovementvalidation;

import escape.impl.boardinformation.board.Bounds;

// Implementation of the MovementAttributeValidator interface
// for determining the validity of a piece moving with the fly attribute
public class FlyMoveValidator implements MovementAttributeValidator {
    private final Bounds bounds;

    /**
     * Constructor for the fly move
     * @param bounds the bounds of the board
     */
    public FlyMoveValidator(Bounds bounds) {
        this.bounds = bounds;
    }

    /**
     * Determine if a coordinate is in the bounds of the game
     * @param row the row
     * @param col the column
     * @return true if the move is within the game bounds
     */
    @Override
    public boolean isValidMove(int row, int col) {
        return bounds.isWithinBounds(row, col);
    }
}
