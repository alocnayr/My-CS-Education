package escape.impl.movement.pathfinder;

import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.impl.boardinformation.coordinate.WeightedCoordinate;
import escape.impl.movement.movevalidation.pieceattributemovementvalidation.MovementAttributeValidation;
import escape.required.Coordinate;
import escape.required.EscapePiece;

import java.util.*;

//Used for finding the shortest path between two coordinates in a square board
public class ShortestPathImpl implements ShortestPath {

    private final EscapePiece escapePiece;
    private final BoardInformation board;
    private boolean canJump;
    private int totalPathSize;
    private String currentPlayer;
    private Coordinate to;

    /**
     *
     * @param board The Escape board
     * @param escapePiece The current piece trying to move
     */
    public ShortestPathImpl(BoardInformation board, EscapePiece escapePiece){
        this.board = board;
        this.escapePiece = escapePiece;
        this.canJump = false;
        this.totalPathSize = 0;
    }

    /**
     * This method uses BFS to find the shortest path between two coordinates
     * @param from the starting location
     * @param to the end location
     * @return The shortest path between two coordinates
     */
    @Override
    public List<Coordinate> findShortestPath(Coordinate from, Coordinate to) {

        //keep track of need to visit, path back to parent, and visited coordinates
        PriorityQueue<WeightedCoordinate> queue = new PriorityQueue<>();
        Map<Coordinate, Coordinate> parent = new HashMap<>();
        LinkedList<Coordinate> visited = new LinkedList<>();
        EscapePieceImpl newPiece = (EscapePieceImpl) this.escapePiece;
        this.to = to;

        queue.offer(new WeightedCoordinate(from, 0));
        visited.add(from);

        //Find the next coordinate to visit
        while (!queue.isEmpty()) {
            WeightedCoordinate current = queue.poll();
            Coordinate currentCoordinate = current.coordinate();
            if (currentCoordinate.equals(to)) {
                break;
            }

            //handle different movement patterns
            List<WeightedCoordinate> validMoves = switch (newPiece.getMovementPattern()) {
                case ORTHOGONAL -> getValidMovesOrthogonal(currentCoordinate);
                case DIAGONAL -> getValidMovesDiagonal(currentCoordinate);
                case OMNI -> getValidMovesOmni(currentCoordinate);
                case LINEAR -> getValidMovesLinear(currentCoordinate, to);
            };

            //See all unvisited neighbors and put them on the need to visit queue
            for (WeightedCoordinate neighbor : validMoves) {
                if (!visited.contains(neighbor.coordinate())) {
                    visited.add(neighbor.coordinate());
                    parent.put(neighbor.coordinate(), currentCoordinate);
                    queue.offer(new WeightedCoordinate(neighbor.coordinate(), current.weight() + neighbor.weight()));
                }
            }
        }

        //Create the path based on the parent map and reverse the list to get them in order (from-to)
        LinkedList<Coordinate> path = new LinkedList<>();
        Coordinate currentCoordinate = to;

        while (currentCoordinate != null) {
            path.add(currentCoordinate);
            currentCoordinate = parent.get(currentCoordinate);
        }
        Collections.reverse(path);

        // When a jump is made, the block it jumped over is not in the path, so add back the distance of 2
        int extraJumpDistance = 0;
        for(int i=0; i<path.size()-1; i++) {
            if (i + 1 <= path.size()) {
                int deltaX = path.get(i + 1).getRow() - path.get(i).getRow();
                int deltaY = path.get(i + 1).getColumn() - path.get(i).getColumn();
                if (Math.abs(deltaX) == 2 || Math.abs(deltaY) == 2) {
                    extraJumpDistance++;
                }
            }
        }

        totalPathSize = path.size()-1+extraJumpDistance;

        return path;
    }

    /**
     * Compute the valid move to neighboring coordinates for orthogonal movement pattern
     * @param curr current coordinate that the piece is on
     * @return a list of weighted coordinates representing the possible moves to neighbors
     */
    private List<WeightedCoordinate> getValidMovesOrthogonal(Coordinate curr) {
        List<WeightedCoordinate> validMoves = new ArrayList<>();
        validMoves.addAll(getValidMoves(curr, 0, 1, "North"));
        validMoves.addAll(getValidMoves(curr, 0, -1, "South"));
        validMoves.addAll(getValidMoves(curr, 1, 0, "East"));
        validMoves.addAll(getValidMoves(curr, -1, 0, "West"));
        return validMoves;
    }

    /**
     * Compute the valid move to neighboring coordinates for diagonal movement pattern
     * @param curr current coordinate that the piece is on
     * @return a list of weighted coordinates representing the possible moves to neighbors
     */
    private List<WeightedCoordinate> getValidMovesDiagonal(Coordinate curr) {
        List<WeightedCoordinate> validMoves = new ArrayList<>();
        if(this.board.getCoordinateType() != Coordinate.CoordinateType.HEX){
            validMoves.addAll(getValidMoves(curr, -1, -1, "South West"));
            validMoves.addAll(getValidMoves(curr, 1, 1, "North East"));
        }
        validMoves.addAll(getValidMoves(curr, -1, 1, "North West"));
        validMoves.addAll(getValidMoves(curr, 1, -1, "South East"));

        return validMoves;
    }

    /**
     * Compute the valid move to neighboring coordinates for omni movement pattern
     * @param curr current coordinate that the piece is on
     * @return a list of weighted coordinates representing the possible moves to neighbors
     */
    private List<WeightedCoordinate> getValidMovesOmni(Coordinate curr) {
        List<WeightedCoordinate> validMoves = new ArrayList<>();
        validMoves.addAll(getValidMovesDiagonal(curr));
        validMoves.addAll(getValidMovesOrthogonal(curr));
        return validMoves;
    }

    /**
     * Compute the valid move to neighboring coordinates for linear movement pattern
     * @param curr current coordinate that the piece is on
     * @return a list of weighted coordinates representing the possible moves to neighbors
     */
    private List<WeightedCoordinate> getValidMovesLinear(Coordinate curr, Coordinate to) {
        List<WeightedCoordinate> validMoves = new ArrayList<>();
        int deltaRow = to.getRow() - curr.getRow();
        int deltaCol = to.getColumn() - curr.getColumn();

        conditionalAddMoves(deltaRow == 0 && deltaCol > 0, validMoves, curr, 0, 1, "North");
        conditionalAddMoves(deltaRow == 0 && deltaCol < 0, validMoves, curr, 0, -1, "South");
        conditionalAddMoves(deltaRow > 0 && deltaCol == 0, validMoves, curr, 1, 0, "East");
        conditionalAddMoves(deltaRow < 0 && deltaCol == 0, validMoves, curr, -1, 0, "West");
        if(Math.abs(deltaRow) == Math.abs(deltaCol)) {
            if(this.board.getCoordinateType() == Coordinate.CoordinateType.SQUARE) {
                conditionalAddMoves(deltaRow < 0 && deltaCol < 0,
                        validMoves, curr, -1, -1, "South West");
                conditionalAddMoves(deltaRow > 0 && deltaCol > 0,
                        validMoves, curr, 1, 1, "North East");
            }
            conditionalAddMoves(deltaRow < 0 && deltaCol > 0,
                    validMoves, curr, -1, 1, "North West");
            conditionalAddMoves(deltaRow > 0 && deltaCol < 0,
                    validMoves, curr, 1, -1, "South East");
        }
        return validMoves;
    }

    /**
     * Add moves
     * @param condition the condition that needs to be satisfied in order to add the move
     * @param validMoves list of weighted coordinates indicating the valid moves to neighboring coordinates
     * @param curr current coordinate
     * @param deltaRow the change in row
     * @param deltaCol the change in col
     * @param direction the direction the piece moves
     */
    private void conditionalAddMoves(boolean condition, List<WeightedCoordinate> validMoves, Coordinate curr, int deltaRow, int deltaCol, String direction) {
        if (condition) {
            validMoves.addAll(getValidMoves(curr, deltaRow, deltaCol, direction));
        }
    }

    /**
     *
     * @param curr current coordinate
     * @param rowDelta the change in row
     * @param colDelta the change in column
     * @param direction the direction the piece is moving
     * @return a list of weighted coordinates representing the possible moves to neighbors
     */
    private List<WeightedCoordinate> getValidMoves(Coordinate curr, int rowDelta, int colDelta, String direction) {
        List<WeightedCoordinate> validMoves = new ArrayList<>();
        int x = curr.getRow();
        int y = curr.getColumn();
        int newRow = x + rowDelta;
        int newCol = y + colDelta;

        if (isValidMove(newRow, newCol, direction)) {
            if (this.canJump) {
                validMoves.add(new WeightedCoordinate(new CoordinateImpl(newRow + rowDelta, newCol + colDelta), 2));
                this.canJump = false;
            } else {
                validMoves.add(new WeightedCoordinate(new CoordinateImpl(newRow, newCol), 1));
            }
        }

        return validMoves;
    }

    /**
     *
     * @param row the row
     * @param col the col
     * @param direction the direction the piece is moving in
     * @return whether the piece can move to that location based on its attributes (jump, unblock, fly, or none)
     */
    private boolean isValidMove(int row, int col, String direction) {
        boolean validMove;
        MovementAttributeValidation smv = new MovementAttributeValidation(board, to, escapePiece, currentPlayer, direction);
        validMove = smv.isValidMove(row, col);
        this.canJump = smv.canJump();
        return validMove;
    }

    @Override
    public int getPathSize(){
        return this.totalPathSize;
    }
    public void setPointConflict(boolean bool){
        this.board.setPointConflict(bool);
    }
    public void setCurrentPlayer(String player){
        this.currentPlayer = player;
    }

}
