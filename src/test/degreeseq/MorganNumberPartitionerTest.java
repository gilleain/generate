package test.degreeseq;

import group.Partition;
import model.Graph;

import org.junit.Test;

import degreeseq.MorganNumberPartitioner;

public class MorganNumberPartitionerTest {
    
    @Test
    public void bugTest() {
        int[] degSeq = new int[] { 3, 3, 3, 3, 2, 1, 1 };
        Graph g = new Graph("0:2,0:3,0:4");
//        Graph g = new Graph("0:1,0:2,0:3");
        MorganNumberPartitioner partitioner = new MorganNumberPartitioner();
        Partition p = partitioner.getOrbitPartition(g, degSeq);
        System.out.println(p);
    }
     
    
}
