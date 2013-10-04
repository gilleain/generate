package test.distance;

import graph.model.Graph;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
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
    
    private void testCent(Graph g, int... expected) {
        int[][] c = CentralityCalculator.getCentralityMatrix(g);
        int[] rowSum = new int[c.length];
        for (int i = 0; i < c.length; i++) {
            System.out.print(Arrays.toString(c[i]));
            int sum = 0;
            for (int k : c[i]) {
                sum += k;
            }
            rowSum[i] = sum;
            System.out.println(" : " + sum);
        }
        Arrays.sort(rowSum);
        Assert.assertArrayEquals(expected, rowSum);

    }
    
    @Test
    public void testCent_3MePent() {
        Graph g = new Graph("0:1,1:2,2:3,2:5,3:4");
        testCent(g, 2, 3, 3, 5, 5, 6);  // in paper, is 2, 2, 3, 5, 5, 6!
    }
    
    @Test
    public void testCent_n_Hex() {
        Graph g = new Graph("0:1,1:2,2:3,3:4,4:5");
        testCent(g, 2, 2, 2, 2, 2, 2);
    }
    
    @Test
    public void testCent_2_3_DiMeBut() {
        Graph g = new Graph("0:1,1:2,1:4,2:3,2:5");
        testCent(g, 4, 4, 6, 6, 6, 6);
    }
    
    @Test
    public void testCent_2_MePent() {
        Graph g = new Graph("0:1,1:2,1:5,2:3,3:4");
        testCent(g, 1, 1, 3, 5, 7, 7);
    }
    
    @Test
    public void testCent_2_2_DiMeBut() {
        Graph g = new Graph("0:1,1:2,1:4,1:5,2:3");
        testCent(g, 1, 1, 9, 11, 11, 11);
    }
    
    @Test
    public void testCent_n_Hept() {
        Graph g = new Graph("0:1,1:2,2:3,3:4,4:5,5:6");
        testCent(g, 2, 2, 2, 3, 3, 3, 3);
    }
    
    @Test
    public void testCent_3_Et_Pent() {
        Graph g = new Graph("0:1,1:2,2:3,2:5,3:4,5:6");
        testCent(g, 3, 6, 6, 6, 7, 7, 7);
    }
    
    @Test
    public void testCent_3_Me_Hex() {
        Graph g = new Graph("0:1,1:2,2:3,2:6,3:4,4:5");
        testCent(g, 2, 3, 3, 3, 6, 7, 8);
    }
    
    @Test
    public void testCent_2_Me_Hex() {
        Graph g = new Graph("0:1,1:2,1:6,2:3,3:4,4:5");
        testCent(g, 2, 2, 2, 3, 5, 8, 8);
    }
    
    @Test
    public void testCent_2_3_DiMe_Pent() {
        Graph g = new Graph("0:1,1:2,1:5,2:3,2:6,3:4");
        testCent(g, 5, 5, 5, 6, 7, 9, 9);
    }
    
    @Test
    public void testCent_2_4_DiMe_Pent() {
        Graph g = new Graph("0:1,1:2,1:5,2:3,3:4,3:6");
        testCent(g, 1, 1, 8, 9, 9, 9, 9);
    }
    
    @Test
    public void testCent_3_3_DiMe_Pent() {
        Graph g = new Graph("0:1,1:2,2:3,2:5,2:6,3:4");
        testCent(g, 2, 4, 4, 11, 11, 13, 13);
    }
    
    @Test
    public void testCent_2_2_3_TriMe_But() {
        Graph g = new Graph("0:1,1:2,1:4,1:5,2:6,2:3");
        testCent(g, 4, 7, 7, 9, 13, 13, 13);
    }
    
    @Test
    public void testCent_2_2_DiMe_Pent() {
        Graph g = new Graph("0:1,1:2,1:5,1:6,2:3,3:4");
        testCent(g, 1, 1, 4, 10, 14, 14, 14);
    }
    
    @Test
    public void testCent_3_Et_2_3_DiMeHex() {
        Graph g = new Graph("0:6,1:6,3:6,1:7,1:9,2:7,2:8,4:7,5:9");
        testCent(g, 5, 8, 10, 10, 11, 19, 19, 20, 21, 21);
    }
    
    @Test
    public void testCent_Cuneane() {
        Graph g = new Graph("0:1,0:3,0:5,1:2,1:7,2:3,2:7,3:4,4:5,4:6,5:6,6:7");
        testCent(g, 14, 14, 14, 14, 16, 16, 16, 16);
    }
    
}
