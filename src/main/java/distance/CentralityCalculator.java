package distance;

import graph.model.IntEdge;
import graph.model.IntGraph;
import group.Partition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class CentralityCalculator {
    
    public static Partition getVertexPartition(IntGraph g) {
        int[][] cm = CentralityCalculator.getCentralityMatrix(g);
        Map<String, SortedSet<Integer>> cellMap = new HashMap<String, SortedSet<Integer>>();
        for (int i = 0; i < g.vsize(); i++) {
            int[] rowCopy = Arrays.copyOf(cm[i], cm.length);
            Arrays.sort(rowCopy);
            StringBuffer labelBuffer = new StringBuffer();
            labelBuffer.append(g.degree(i));
            for (int j = 0; j < cm.length; j++) {
                labelBuffer.append(rowCopy[j]);
            }
            // erk.. what if really large? use BigInteger?
            String label = labelBuffer.toString();
            SortedSet<Integer> cell;
            if (cellMap.containsKey(label)) {
                cell = cellMap.get(label);
            } else {
                cell = new TreeSet<Integer>();
                cellMap.put(label, cell);
            }
            cell.add(i);
        }
        
        Partition partition = new Partition();
        for (String label : cellMap.keySet()) {
            partition.addCell(cellMap.get(label));
        }
        partition.order();
        
        return partition;
    }
    
    /**
     * Floyd-Warshall - could replace with a dedicated class.
     * 
     * @param g
     * @return
     */
    public static int[][] getDistanceMatrix(IntGraph g) {
        int v = g.vsize();
        int[][] dist = new int[v][v];
        for (int i = 0; i < v; i++) {
            Arrays.fill(dist[i], v + 1);
            dist[i][i] = 0;
        }
        
        for (IntEdge e : g.edges) {
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
    
    public static List<Map<Integer, Set<Integer>>> getNeighbourhoods(IntGraph g) {
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
    
    public static int[][] getCentralityMatrix(IntGraph g) {
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
    
    public static int[] getORS(IntGraph g) {
        int[][] c = getCentralityMatrix(g);
        int[] rowSum = new int[c.length];
        for (int i = 0; i < c.length; i++) {
            int sum = 0;
            for (int k : c[i]) {
                sum += k;
            }
            rowSum[i] = sum;
        }
        Arrays.sort(rowSum);
        return rowSum;
    }
        
}
