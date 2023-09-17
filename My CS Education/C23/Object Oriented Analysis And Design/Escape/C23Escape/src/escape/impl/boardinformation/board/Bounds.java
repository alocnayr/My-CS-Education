package escape.impl.boardinformation.board;

// This class is used to store xMax and yMax info about the game
// and to determine the validity of a coordinate based on the bounds
public class Bounds {

    private final int xMax;
    private final int yMax;

    /**
     *
     * @param xMax the maximum row
     * @param yMax the maximum column
     */
    public Bounds(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     *
     * @param x the row
     * @param y the column
     * @return true if the coordinate is within the bounds of the game
     */
    public boolean isWithinBounds(int x, int y) {
        if (xMax == 0 && yMax != 0) {
            return y >= 0 && y <= yMax;
        } else if (xMax != 0 && yMax == 0) {
            return x >= 0 && x <= xMax;
        } else if (xMax == 0 && yMax == 0) {
            return true;
        } else {
            return x > 0 && x <= xMax && y > 0 && y <= yMax;
        }
    }


    public int getxMax() {
        return xMax;
    }

    public int getyMax() {
        return yMax;
    }


}
