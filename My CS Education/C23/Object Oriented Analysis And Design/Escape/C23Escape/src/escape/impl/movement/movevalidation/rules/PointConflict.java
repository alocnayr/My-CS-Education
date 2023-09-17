package escape.impl.movement.movevalidation.rules;

import escape.impl.gamemanager.EscapeGameManagerImpl;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.impl.gamemanager.GameStatusImpl;
import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.GameStatus;

import java.util.Map;

// Class for managing point conflicts
public class PointConflict {
    public PointConflict(){
    }

    /**
     * Handles piece removal and point and gamestatus updates from point conflict
     * @param pieceLocations the locations of the pieces on the board
     * @param status the gamestatus of the game
     * @param from where the piece is moving from
     * @param to where the piece is moving to
     */
    public static void handlePieceConflict(Map<Coordinate, EscapePiece> pieceLocations, GameStatusImpl status, Coordinate from, Coordinate to){

        EscapePieceImpl currPiece = (EscapePieceImpl) pieceLocations.get(from);
        EscapePieceImpl pieceConflict = (EscapePieceImpl) pieceLocations.get(to);

        if (currPiece.getValue() > pieceConflict.getValue()) {
            currPiece.changeValue(currPiece.getValue() - pieceConflict.getValue());
            pieceLocations.remove(to);
            pieceLocations.remove(from);
            pieceLocations.put(to, currPiece);
            status.setCombatResult(GameStatus.CombatResult.ATTACKER);
            EscapeGameManagerImpl.addObserverMessage(currPiece.getPlayer() + "'s " + currPiece.getName() + " attacked " + pieceConflict.getPlayer() + "'s " + pieceConflict.getName() + " at " + to + " and won.");

        } else if (currPiece.getValue() < pieceConflict.getValue()) {
            pieceConflict.changeValue(pieceConflict.getValue() - currPiece.getValue());
            pieceLocations.remove(from);
            status.setCombatResult(GameStatus.CombatResult.DEFENDER);
            EscapeGameManagerImpl.addObserverMessage(currPiece.getPlayer() + "'s " + currPiece.getName() + " attacked " + pieceConflict.getPlayer() + "'s " + pieceConflict.getName() + " at " + to + " and lost.");

        } else if (currPiece.getValue() == pieceConflict.getValue()) {
            pieceLocations.remove(from);
            pieceLocations.remove(to);
            status.setCombatResult(GameStatus.CombatResult.DRAW);
            EscapeGameManagerImpl.addObserverMessage(currPiece.getPlayer() + "'s " + currPiece.getName() + " attacked " + pieceConflict.getPlayer() + "'s " + pieceConflict.getName() + " at " + to + " and the conflict was a draw.");
        }
    }

}
