package distance;

import graph.model.Edge;
import graph.model.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CentralityCalculator {
    
    /**
     * Floyd-Warshall - could replace with a dedicated class.
     * 
     * @param g
     * @return
     */
    public static int[][] getDistanceMatrix(Graph g) {
        int v = g.vsize();
        int[][] dist = new int[v][v];
        for (int i = 0; i < v; i++) {
            Arrays.fill(dist[i], v + 1);
            dist[i][i] = 0;
        }
        
        for (Edge e : g.edges) {
            dist[e.a][e.b] = 1;
            dist[e.b][e.a] = 1;
        }
        
        for (int k = 0; k < v; k++) {
            for (int i = 0; i < v; i++) {
                for (int j = 0; j < v; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        return dist;
    }
    
    public static List<Map<Integer, Set<Integer>>> getNeighbourhoods(Graph g) {
        int[][] dist = CentralityCalculator.getDistanceMatrix(g);
        int n = g.getVertexCount();
        List<Map<Integer, Set<Integer>>> neighbourhoods = new ArrayList<Map<Integer, Set<Integer>>>();
        for (int i = 0; i < n; i++) {
            Map<Integer, Set<Integer>> neighbourhood = new HashMap<Integer, Set<Integer>>();
            neighbourhoods.add(neighbourhood);
            for (int j = 0; j < n; j++) {
                if (dist[i][j] > 0) {
                    int d = dist[i][j];
                    Set<Integer> dNeighbours;
                    if (neighbourhood.containsKey(d)) {
                        dNeighbours = neighbourhood.get(d);
                    } else {
                        dNeighbours = new TreeSet<Integer>();
                        neighbourhood.put(d, dNeighbours);
                    }
                    dNeighbours.add(j);
                }
            }
        }
        return neighbourhoods;
    }
    
    public static int[][] getCentralityMatrix(Graph g) {
        int v = g.vsize();
        int[][] cent = new int[v][v];
        List<Map<Integer, Set<Integer>>> neighbourhoods = getNeighbourhoods(g);
        for (int i = 0; i < v; i++) {
            Map<Integer, Set<Integer>> nI = neighbourhoods.get(i);
            for (int j = i + 1; j < v; j++) {
                Map<Integer, Set<Integer>> nJ = neighbourhoods.get(j);
                int maxD = Math.min(nI.size(), nJ.size());
                for (int d = 1; d <= maxD; d++) {
                    Set<Integer> intersection = new TreeSet<Integer>(nI.get(d));
                    intersection.retainAll(nJ.get(d)); 
                    int count = intersection.size();
//                    System.out.println("intersection " + i + ", " + j + " = " + intersection);
                    cent[i][j] += count;
                    cent[j][i] += count;
                }
            }
        }
        return cent;
    }
        
}
