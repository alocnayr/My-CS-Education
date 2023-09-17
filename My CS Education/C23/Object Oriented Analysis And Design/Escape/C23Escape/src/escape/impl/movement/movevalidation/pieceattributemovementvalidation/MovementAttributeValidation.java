package escape.impl.movement.movevalidation.pieceattributemovementvalidation;

import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.required.Coordinate;
import escape.required.EscapePiece;

import java.util.ArrayList;
import java.util.List;

//Implementation of MovementAttributeValidator interface that
// manages the validation of movement solely based off the pieces attribute (jump, fly, unblock, none)
public class MovementAttributeValidation implements MovementAttributeValidator{

    private final BoardInformation board;
    private final Coordinate to;
    private final EscapePiece piece;
    private final String currentPlayer;
    private final String direction;
    private boolean canJump;

    /**
     * Constructor for this class
     * @param board the board of the game
     * @param to the location the piece is moving to
     * @param piece the piece
     * @param currentPlayer the current player
     * @param direction the direction the piece is moving
     */
    public MovementAttributeValidation(BoardInformation board, Coordinate to,
                             EscapePiece piece, String currentPlayer, String direction) {

        this.board = board;
        this.to = to;
        this.piece = piece;
        this.currentPlayer = currentPlayer;
        this.direction = direction;
        this.canJump = false;
    }

    /**
     * Determine if the move is valid based on the pieces attributes
     * @param row the row
     * @param col the column
     * @return true if valid move
     */
    @Override
    public boolean isValidMove(int row, int col){
        List<Boolean> moveAvailability = new ArrayList<>();
        EscapePieceImpl epi = (EscapePieceImpl) piece;

        boolean fly = epi.getMovementAttributes().contains(EscapePiece.PieceAttributeID.FLY);
        boolean jump = epi.getMovementAttributes().contains(EscapePiece.PieceAttributeID.JUMP);
        boolean unblock = epi.getMovementAttributes().contains(EscapePiece.PieceAttributeID.UNBLOCK);

        NormalMoveValidator normalAttribute = new NormalMoveValidator(board, to, currentPlayer);

        //every piece can move normally, add this to available moves
        moveAvailability.add(normalAttribute.isValidMove(row, col));
        if(fly){
            FlyMoveValidator flyAttribute = new FlyMoveValidator(board.getBounds());
            moveAvailability.add(flyAttribute.isValidMove(row, col));
        }
        if(jump){
            JumpMoveValidator jumpAttribute = new JumpMoveValidator(board, to, currentPlayer, direction);
            moveAvailability.add(jumpAttribute.isValidMove(row, col));
            this.canJump = jumpAttribute.canJump();
        }
        if(unblock){
            UnblockMoveValidator unblockAttribute = new UnblockMoveValidator(board, to, currentPlayer);
            moveAvailability.add(unblockAttribute.isValidMove(row, col));
        }
        return moveAvailability.contains(Boolean.TRUE);
    }

    public boolean canJump(){
        return this.canJump;
    }

}
