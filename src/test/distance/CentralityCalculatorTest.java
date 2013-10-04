package test.distance;

import graph.model.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import distance.CentralityCalculator;

public class CentralityCalculatorTest {
    
    private void prettyPrint(List<Map<Integer, Set<Integer>>> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.print(i + " : ");
            Map<Integer, Set<Integer>> map = n.get(i);
            for (int j = 1; j < map.size() + 1; j++) {
                Set<Integer> cJ = map.get(j);  
                System.out.print(cJ);
                if (j < map.size()) {
                    System.out.print(",");    
                }
            }
            System.out.println();
        }
    }
    
    @Test
    public void testDistance() {
        Graph g = new Graph("0:1,0:2,1:3,2:3");
        int[][] dist = CentralityCalculator.getDistanceMatrix(g);
        System.out.println(Arrays.deepToString(dist));
    }
    
    @Test
    public void testNeighbours() {
        Graph g = new Graph("0:1,1:2,2:3,2:5,3:4");
        List<Map<Integer, Set<Integer>>> neighbours = CentralityCalculator.getNeighbourhoods(g);
        prettyPrint(neighbours);
    }
    
    @Test
    public void testCent() {
        Graph g = new Graph("0:1,1:2,2:3,2:5,3:4");
        int[][] c = CentralityCalculator.getCentralityMatrix(g);
        for (int i = 0; i < c.length; i++) {
            System.out.print(Arrays.toString(c[i]));
            int sum = 0;
            for (int k : c[i]) {
                sum += k;
            }
            System.out.println(" : " + sum);
        }
    }
    
}
