package escape.impl.boardinformation.coordinate;

import escape.required.Coordinate;

import java.util.Objects;

//Implementation of the Coordinate interface
public class CoordinateImpl implements Coordinate {

    private final int x;
    private final int y;

    /**
     *
     * @param x the row
     * @param y the column
     */
    public CoordinateImpl(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getRow() {
        return this.x;
    }

    /**
     * @return the row component of the Coordinate
     */
    public int getColumn() {
        return this.y;
    }

    /**
     *
     * @param obj an object
     * @return true if the object is equal to this
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if (!(obj instanceof CoordinateImpl)) {
            return false;
        }
        CoordinateImpl other = (CoordinateImpl) obj;
        return this.getRow() == other.getRow() && this.getColumn() == other.getColumn();
    }

    @Override
    public String toString(){
        return "(" + this.getRow() + ", " + this.getColumn() + ")";
    }

    // used for hashmaps
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
