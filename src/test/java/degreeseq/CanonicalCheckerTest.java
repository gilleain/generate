package degreeseq;

import graph.model.IntGraph;
import group.Partition;

import org.junit.Test;

public class CanonicalCheckerTest {
    
    @Test
    public void intermediateA() {
        IntGraph g = new IntGraph("0:1, 0:2, 0:3, 1:4, 1:5, 2:4, 2:5");
        Partition p = Partition.fromString("0,1|2,3,4,5");  // FIXME
        int[] degSeq = new int[] { 3, 3, 3, 3, 3, 3 };
        boolean check = degreeseq.CanonicalChecker.isPartitionCanonical(g, p, degSeq);
        System.out.println(check);
    }
    
    @Test
    public void intermediateB() {
        IntGraph g = new IntGraph("0:1, 0:2, 0:5, 1:3, 1:4");
        Partition p = new Partition();  // FIXME
        int[] degSeq = new int[] { 3, 3, 3, 3, 3, 3 };
        boolean check = degreeseq.CanonicalChecker.isPartitionCanonical(g, p, degSeq);
        System.out.println(check);
    }
    
}
