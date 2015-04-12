package degreeseq;

import graph.model.IntGraph;
import group.Partition;

import org.junit.Test;

public class MorganNumberPartitionerTest {
    
    @Test
    public void bugTest() {
        int[] degSeq = new int[] { 3, 3, 3, 3, 2, 1, 1 };
        IntGraph g = new IntGraph("0:2,0:3,0:4");
//        Graph g = new Graph("0:1,0:2,0:3");
        MorganNumberPartitioner partitioner = new MorganNumberPartitioner();
        Partition p = partitioner.getOrbitPartition(g, degSeq);
        System.out.println(p);
    }
     
    
}
