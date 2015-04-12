package degreeseq;

import group.Partition;

import org.junit.Test;

import combinatorics.PartitionCalculator;

import degreeseq.GraphicalChecker.Graphicality;

public class GraphicalCheckerTest {
    
    public void testV(int v) {
        int max = v * (v - 1);
        for (int e = v; e <= max; e++) {
            for (Partition p : PartitionCalculator.partition(e, v)) {
                int[] d = p.toPermutation().getValues();
                if (HakimiHavelGenerator.isGraphical(d)) {
                    Graphicality g = GraphicalChecker.check(d);
                    System.out.println(p + "\t" + g);
                } else {
//                    System.out.println(p + "\tNOT_GRAPHICAL");
                }
            }
        }
    }
    
    @Test
    public void test5s() {
        testV(5);
    }
    
    @Test
    public void test6s() {
        testV(6);
    }
    
}
