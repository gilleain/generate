package test.hakimihavel;

import java.util.Arrays;

import group.Partition;
import junit.framework.Assert;
import model.Graph;

import org.junit.Test;

import combinatorics.PartitionCalculator;
import degreeseq.HakimiHavelGenerator;

public class HakimiHavelGeneratorTest {
    
    @Test
    public void oddSeqRejectTest() {
        int[] ds = new int[] { 3, 2, 1, 1 };
        Assert.assertFalse(HakimiHavelGenerator.isGraphical(ds));
    }
    
    @Test
    public void evenSeqAcceptTest() {
        int[] ds = new int[] { 3, 2, 2, 1 };
        Assert.assertTrue(HakimiHavelGenerator.isGraphical(ds));
    }
    
    @Test
    public void graphicalSeqAcceptTest() {
//        int[] ds = new int[] { 3, 3, 2, 2, 2, 2, 2, 2 };
        int[] ds = new int[] { 2, 2, 2, 2, 1, 1 };
        Assert.assertTrue(HakimiHavelGenerator.isGraphical(ds));
    }
    
    @Test
    public void graphicalSeqFailTest() {
        int[] ds = new int[] { 3, 3, 3, 1 };
        Assert.assertFalse(HakimiHavelGenerator.isGraphical(ds));
    }
    
    @Test
    public void graphicalSeqFailTest2() {
        int[] ds = new int[] { 3, 2, 1 };
        Assert.assertFalse(HakimiHavelGenerator.isGraphical(ds));
    }
    
    @Test
    public void partitionTest() {
        int m = 14;
        for (int parts = 1; parts < m; parts++) {
            for (Partition p : PartitionCalculator.partition(m, parts)) {
                boolean isGraphical =
                    HakimiHavelGenerator.isGraphical(p.toPermutation().getValues());    // urgh
                System.out.println(isGraphical + "\t" + p);
            }
        }
    }
    
    @Test
    public void generateGraphTest() {
        int[] ds = new int[] { 4, 3, 3, 3, 1 };
        Graph g = HakimiHavelGenerator.generate(ds);
        System.out.println(g + "\t" + Arrays.toString(ds));
        Assert.assertEquals(7, g.esize());
    }
    
    @Test
    public void generate_3_2_2_2_2_1_Test() {
        int[] ds = new int[] { 3, 2, 2, 2, 2, 1 };
        Graph g = HakimiHavelGenerator.generate(ds);
        System.out.println(g + "\t" + Arrays.toString(ds));
        Assert.assertEquals(6, g.esize());
    }
    
    @Test
    public void generate_3_3_2_2_2_Test() {
        int[] ds = new int[] { 3, 3, 2, 2, 2 };
        Graph g = HakimiHavelGenerator.generate(ds);
        System.out.println(g + "\t" + Arrays.toString(ds));
        Assert.assertEquals(5, g.esize());
    }
    
    @Test
    public void generateMissing6Test() {
        int[] ds = new int[] { 2, 2, 2, 1, 1 };
        Graph g = HakimiHavelGenerator.generate(ds);
        System.out.println(g + "\t" + Arrays.toString(ds));
        Assert.assertEquals(4, g.esize());
    }
    
    @Test
    public void generateDiconnectedGraphTest() {
        int[] ds = new int[] { 5, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        Graph g = HakimiHavelGenerator.generate(ds);
        System.out.println(g + "\t" + Arrays.toString(ds));
        Assert.assertEquals(7, g.esize());
        Assert.assertFalse(g.isConnected());
    }
    
    @Test
    public void partitionConstantEsizeGenerateGraphTest() {
        int e = 12;
        int counter = 0;
        for (int parts = 1; parts < e; parts++) {
            for (Partition p : PartitionCalculator.partition(e, parts)) {
                int[] arr = p.toPermutation().getValues(); // urgh
                if (HakimiHavelGenerator.isGraphical(arr)) {
                    Graph g = HakimiHavelGenerator.generate(arr);
                    if (g.isConnected()) {
//                        System.out.println(g + "\t" + g.isConnected() + "\t" + p);
                        System.out.println(counter + "\t" + g.esize() + "\t" + g + "\t" + p);
//                        Assert.assertEquals(e / 2, g.esize());
                        counter++;
                    }
                }
            }
        }
    }
    
    @Test
    public void partitionConstantVsizeGenerateGraphTest() {
        int v = 5;
        int counter = 0;
        int max = v * (v - 1);
        for (int e = v; e <= max; e++) {
            for (Partition p : PartitionCalculator.partition(e, v)) {
                int[] arr = p.toPermutation().getValues(); // urgh
                if (HakimiHavelGenerator.isGraphical(arr)) {
                    Graph g = HakimiHavelGenerator.generate(arr);
                    if (g.isConnected()) {
//                        System.out.println(g + "\t" + g.isConnected() + "\t" + p);
                        System.out.println(counter + "\t" + e + "\t" + g + "\t" + p);
//                        Assert.assertEquals(v, g.vsize());
                        counter++;
                    }
                }
            }
        }
    }
}
