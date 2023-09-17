package escape.impl.movement.movevalidation.pieceattributemovementvalidation;

// Interface for defining the validation of special moves (jump, unblock, fly, or none)
public interface MovementAttributeValidator {
    boolean isValidMove(int x, int y);
}
