package escape.impl.movement.pathfinder;

// This class is used for getting the new row and column coordinate
// based on the direction the piece wants to move
public class DirectionClassifier {

    private final String direction;
    private final int x;
    private final int y;

    /**
     *
     * @param direction the direction the piece is moving in (north, south, east, ...)
     * @param x the row of the piece
     * @param y the column of the piece
     */
    public DirectionClassifier(String direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the row of the new coordinate based on the direction the piece wants to move
     */
    public int getX() {
        if (direction.equals("North") || direction.equals("South")) {
            return x;
        }
        if (direction.equals("East")) {
            return x + 1;
        }
        if (direction.equals("West")) {
            return x - 1;
        }
        if (direction.equals("North East")) {
            return x + 1;
        }
        if (direction.equals("North West")) {
            return x - 1;
        }
        if (direction.equals("South East")) {
            return x + 1;
        }
        //southwest
        else{
            return x - 1;
        }
    }

    /**
     *
     * @return the column of the new coordinate based on the direction the piece wants to move
     */
    public int getY() {
        if (direction.equals("East") || direction.equals("West")) {
            return y;
        }
        if (direction.equals("North") ) {
            return y + 1;
        }
        if (direction.equals("South")) {
            return y - 1;
        }
        if (direction.equals("North East")) {
            return y + 1;
        }
        if (direction.equals("North West")) {
            return y + 1;
        }
        if (direction.equals("South East")) {
            return y - 1;
        }
        //southwest
        else {
            return y - 1;
        }
    }
}
