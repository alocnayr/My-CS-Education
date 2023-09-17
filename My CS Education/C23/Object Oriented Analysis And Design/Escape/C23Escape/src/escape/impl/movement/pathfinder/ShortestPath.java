package escape.impl.movement.pathfinder;

import escape.required.Coordinate;

import java.util.List;

// Interface for having a common definition between hex and square shortest path
public interface ShortestPath {

    /**
     *
     * @param to the starting location
     * @param from the end location
     * @return a list of coordinates representing the shortest path between two coordinates
     */
    public List<Coordinate> findShortestPath(Coordinate to, Coordinate from);
    public int getPathSize();


}
