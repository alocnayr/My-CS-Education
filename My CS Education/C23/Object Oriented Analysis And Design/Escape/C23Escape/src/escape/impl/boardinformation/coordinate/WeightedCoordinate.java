package escape.impl.boardinformation.coordinate;

import escape.required.Coordinate;

// This record is used instead of the CoordinateImpl class when the weight of each coordinate is needed
// This is used in the pathfinding algorithm for computing jump weights
public record WeightedCoordinate(Coordinate coordinate, int weight) implements Comparable<WeightedCoordinate> {

    @Override
    public int compareTo(WeightedCoordinate other) {
        return Integer.compare(this.weight, other.weight);
    }
}
