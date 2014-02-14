package degreeseq;

import graph.model.Graph;
import group.Partition;

import org.junit.Test;

import degreeseq.SignatureOrbitPartitioner;

public class SignatureOrbitPartitionerTest {
    
    public void test(Graph g, int[] degSeq) {
        SignatureOrbitPartitioner partitioner = new SignatureOrbitPartitioner();
        Partition p = partitioner.getOrbitPartition(g, degSeq);
        System.out.println(p);
    }
    
    @Test
    public void test_33_22_11_A() {
        Graph g = new Graph("0:1,0:2,0:4,1:3,1:5,2:3");
        int[] degSeq = new int[] { 3, 3, 2, 2, 1, 1 };
        test(g, degSeq);
    }
    
    @Test
    public void test_33_22_11_B() {
        Graph g = new Graph("0:1,0:2,0:3,1:4,1:5,2:3");
        int[] degSeq = new int[] { 3, 3, 2, 2, 1, 1 };
        test(g, degSeq);
    }
    
    @Test
    public void test_33_22_11_C() {
        Graph g = new Graph("0:1,0:2,0:4,1:2,1:3,3:5");
        int[] degSeq = new int[] { 3, 3, 2, 2, 1, 1 };
        test(g, degSeq);
    }
}
