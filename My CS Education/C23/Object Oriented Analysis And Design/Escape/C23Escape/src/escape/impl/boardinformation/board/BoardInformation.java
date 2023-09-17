package escape.impl.boardinformation.board;

import escape.required.Coordinate;
import escape.required.EscapePiece;
import escape.required.LocationType;

import java.util.Map;

// This class is for storing information about the Escape board for other classes to use
// Holds info about xMax & yMax (Bounds), piece locations, block and exit locations ...
public class BoardInformation{

    private final Bounds bounds;
    private final Map<Coordinate, EscapePiece> pieceLocations;
    private final Map<Coordinate, LocationType> blockExitLocations;
    private boolean pointConflict;
    private final Coordinate.CoordinateType coordinateType;

    /**
     *
     * @param bounds the bounds of the game (xMax, yMax)
     * @param pieceLocations a map of the location of each piece on the board
     * @param blockExitLocations a map of the location of each block & exit location
     * @param coordinateType the type of board (square, hex)
     * @param pointConflict whether there exists the point conflict rule
     */
    public BoardInformation(Bounds bounds, Map<Coordinate, EscapePiece> pieceLocations,
                            Map<Coordinate, LocationType> blockExitLocations, Coordinate.CoordinateType coordinateType, boolean pointConflict) {
        this.bounds = bounds;
        this.pieceLocations = pieceLocations;
        this.coordinateType = coordinateType;
        this.blockExitLocations = blockExitLocations;
        this.pointConflict = pointConflict;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public Map<Coordinate, EscapePiece> getPieceLocations() {
        return pieceLocations;
    }

    public Map<Coordinate, LocationType> getBlockExitLocations() {
        return blockExitLocations;
    }

    public boolean getPointConflict() {
        return pointConflict;
    }
    public Coordinate.CoordinateType getCoordinateType() {
        return coordinateType;
    }

    public void setPointConflict(boolean bool){
        this.pointConflict = bool;
    }

}
