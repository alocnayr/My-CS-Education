package escape.impl.movement.movevalidation;

import escape.impl.boardinformation.board.Bounds;
import escape.required.Coordinate;

//This class is used to validate whether the input coordinates are valid based on the bounds of the board
public class ValidateCoordinateMove {
    private final Bounds bounds;

    /**
     * Constructor for ValidateCoordinateMove
     * @param bounds the bounds of the game
     */
    public ValidateCoordinateMove(Bounds bounds) {
        this.bounds = bounds;
    }

    /**
     * Determine if the input it out of bounds
     * @param from from location
     * @param to to location
     * @return true if the input is within the bounds of the game
     */
    public boolean validInput(Coordinate from, Coordinate to) {
        if(!from.equals(to)) {
            return bounds.isWithinBounds(from.getRow(), from.getColumn()) && bounds.isWithinBounds(to.getRow(), to.getColumn());
        }
        return false;
    }
}
