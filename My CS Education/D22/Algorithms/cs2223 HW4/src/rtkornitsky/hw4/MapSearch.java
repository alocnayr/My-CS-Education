package rtkornitsky.hw4;

import org.junit.Test;

import algs.hw4.map.GPS;
import algs.hw4.map.HighwayMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.DepthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SeparateChainingHashST;

/**
 * Copy this class into USERID.hw4 and make changes.
 */
public class MapSearch {

	/**
	 * This method must create a copy of the graph, which you can do by creating a
	 * new graph with the same number of vertices as the original one, BUT you only
	 * add an edge to the copy if the edge in the original graph IS NOT involved in
	 * the M25.
	 * 
	 * For example, in the data set you will see two nodes:
	 * 
	 * E13@6a(M1)&E30@21(M25)&M1@6a&M25@21 51.716288 -0.385208 E30/M25@+M25(X14)
	 * 51.713257 -0.421343
	 * 
	 * These lines correspond to vertex #114 (the first one) and vertex #1196 (the
	 * second one). Because the label for both of these vertices includes "M25" this
	 * edge must not appear in the copied graph, since it is a highway segment
	 * involving the M25.
	 * 
	 * Note that the edge is eliminated even when only one of the nodes involves
	 * M25.
	 */
	static Information remove_M25_segments(Information info) {

		edu.princeton.cs.algs4.Graph g = info.graph;
		edu.princeton.cs.algs4.Graph copy = new edu.princeton.cs.algs4.Graph(g.V());

		for (int i = 1; i < g.V(); i++) {
			String vLabel = info.labels.get(i);
			for (int j : g.adj(i)) {
				String adjLabel = info.labels.get(j);
				if (i < j && !vLabel.contains("M25") && !adjLabel.contains("M25")) {
					copy.addEdge(i, j);
				}
			}
		}

		Information newInfo = new Information(copy, info.positions, info.labels);
		return newInfo;
	}

	/**
	 * This helper method returns the western-most data point in the Information,
	 * given its latitude and longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */

	public static int westernMostVertex(Information info) {
		
		Graph givenGraph = info.graph;
		int westernMostPoint = 0;
		GPS west = info.positions.get(0);
		for(int v=1; v < givenGraph.V(); v++) {
			GPS currentLocation = info.positions.get(v);
			if(currentLocation.longitude < west.longitude) {
				west = currentLocation;
				westernMostPoint = v;
			}
		}
		return westernMostPoint;
		
	}

	/**
	 * This helper method returns the western-most data point in the Information,
	 * given its latitude and longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int easternMostVertex(Information info) {
		
		Graph givenGraph = info.graph;
		int easternMostPoint = 0;
		GPS east = info.positions.get(0);
		for(int v=1; v < givenGraph.V(); v++) {
			GPS currentLocation = info.positions.get(v);
			if(currentLocation.longitude > east.longitude) {
				east = currentLocation;
				easternMostPoint = v;
			}
		}
		return easternMostPoint;
		
	}

	/**
	 * This helper method returns the southern-most data point in the Information,
	 * given its latitude and longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int southernMostVertex(Information info) {
		
		Graph givenGraph = info.graph;
		int southernMostVertex = 0;
		GPS south = info.positions.get(0);
		for(int v=1; v < givenGraph.V(); v++) {
			GPS currentLocation = info.positions.get(v);
			if(currentLocation.latitude < south.latitude) {
				south = currentLocation;
				southernMostVertex = v;
			}
		}
		return southernMostVertex;
		
	}

	/**
	 * This helper method returns the northern-most data point in the Information,
	 * given its latitude and longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int northernMostVertex(Information info) {
		
		Graph givenGraph = info.graph;
		int northernMostVertex = 0;
		GPS north = info.positions.get(0);
		for(int v=1; v < givenGraph.V(); v++) {
			GPS currentLocation = info.positions.get(v);
			if(currentLocation.latitude > north.latitude) {
				north = currentLocation;
				northernMostVertex = v;
			}
		}
		return northernMostVertex;
		
	}

	public static void main(String[] args) {
		Information info = HighwayMap.undirectedGraph();
		int west = westernMostVertex(info);
		int east = easternMostVertex(info);

		int south = southernMostVertex(info);
		int north = northernMostVertex(info);

		String nameOfWest = info.labels.get(west);
		String nameOfEast = info.labels.get(east);
		String nameOfSouth = info.labels.get(south);
		String nameOfNorth = info.labels.get(north);

		int bfsWestToEastCount = -1;
		int bfsSouthToNorthCount = -1;
		int dfsWestToEastCount = -1;
		int dfsSouthToNorthCount = -1;

		// west to east bfs
		BreadthFirstPaths bfsWestToEast = new BreadthFirstPaths(info.graph, west);
		Iterable<Integer> bfsPathWestToEast = bfsWestToEast.pathTo(east);
		for (int i : bfsPathWestToEast) {
			bfsWestToEastCount++;
		}

		// south to north bfs
		BreadthFirstPaths bfsSouthToNorth = new BreadthFirstPaths(info.graph, south);
		Iterable<Integer> bfsPathSouthToNorth = bfsSouthToNorth.pathTo(north);
		for (int i : bfsPathSouthToNorth) {
			bfsSouthToNorthCount++;
		}

		// west to east dfs
		DepthFirstPaths dfsWestToEast = new DepthFirstPaths(info.graph, west);
		Iterable<Integer> dfsPathWestToEast = dfsWestToEast.pathTo(east);
		for (int i : dfsPathWestToEast) {
			dfsWestToEastCount++;
		}

		// south to north dfs
		DepthFirstPaths dfsSouthToNorth = new DepthFirstPaths(info.graph, south);
		Iterable<Integer> dfsPathSouthToNorth = dfsSouthToNorth.pathTo(north);
		for (int i : dfsPathSouthToNorth) {
			dfsSouthToNorthCount++;
		}

		System.out.println("BreadthFirst Search from West to East:");

		System.out.println(
				"BFS: " + nameOfWest + "(" + west + ") to " + nameOfEast + "(" + east + ") has " + bfsWestToEastCount + " edges.");

		System.out.println("\nBreadthFirst Search from South to North:");

		System.out.println("BFS: " + nameOfSouth + "(" + south + ") to " + nameOfNorth + "(" + north + ") has " + bfsSouthToNorthCount
				+ " edges.");

		System.out.println("\nDepthFirst Search from West to East:");

		System.out.println(
				"DFS: " + nameOfWest + "(" + west + ") to " + nameOfEast + "(" + east + ") has " + dfsWestToEastCount + " edges.");

		System.out.println("\nDepthFirst Search from South to North:");

		System.out.println("DFS: " + nameOfSouth + "(" + south + ") to " + nameOfNorth + "(" + north + ") has " + dfsSouthToNorthCount
				+ " edges.");

		System.out.println("\nNow without M25 edges...\n");
		System.out.println("WEST to EAST");
		Information info_no_m25 = remove_M25_segments(info);


		int west_no_m25 = westernMostVertex(info_no_m25);
		int east_no_m25 = easternMostVertex(info_no_m25);
		int south_no_m25 = southernMostVertex(info_no_m25);
		int north_no_m25 = northernMostVertex(info_no_m25);

		String westName_noM25 = info.labels.get(west_no_m25);
		String eastName_noM25 = info.labels.get(east_no_m25);
		String southName_noM25 = info.labels.get(south_no_m25);
		String northName_noM25 = info.labels.get(north_no_m25);

		int no_M25_WE_count = -1;
		int no_M25_SN_count = -1;

		BreadthFirstPaths WE_no_M25 = new BreadthFirstPaths(info_no_m25.graph, west_no_m25);
		Iterable<Integer> path_noM25_WE = WE_no_M25.pathTo(east_no_m25);
		for (int i : path_noM25_WE) {
			no_M25_WE_count++;
		}

		BreadthFirstPaths SN_no_M25 = new BreadthFirstPaths(info_no_m25.graph, south_no_m25);
		Iterable<Integer> path_noM25_SN = SN_no_M25.pathTo(north_no_m25);
		for (int i : path_noM25_SN) {
			no_M25_SN_count++;
		}

		
		System.out.println("BFS: " + westName_noM25 + "(" + west_no_m25 + ") to " + eastName_noM25 + "(" + east_no_m25 + ") has "
				+ no_M25_WE_count + " edges.");

		System.out.println("\nNORTH to SOUTH");
		
		
		System.out.println("BFS: " + southName_noM25 + "(" + south_no_m25 + ") to " + northName_noM25 + "(" + north_no_m25 + ") has "
				+ no_M25_SN_count + " edges.");
	}

}
