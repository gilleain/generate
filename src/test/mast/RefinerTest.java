package test.mast;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;
import group.Permutation;
import group.PermutationGroup;

import org.junit.Test;

public class RefinerTest {
    
    public void test(Graph tree) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup group = refiner.getAutomorphismGroup(tree);
        for (Permutation p : group.all()) {
            System.out.println(p + "\t" + tree.getSortedPermutedEdgeString(p.getValues()));
        }
//        System.out.println(tree + "\t" + group.getSize());
    }
    
    @Test
    public void threeClawTest() {
        test(new Graph("0:1,0:2,0:3"));
    }
    
    @Test
    public void twoByTwoTest() {
        test(new Graph("0:1,0:2,1:3,1:4,2:5,2:6"));
    }
    
    @Test
    public void twoTwoTwoTest() {
        test(new Graph("0:1,0:2,1:3,1:4,2:5,2:6,3:7,3:8,4:9,4:10,5:11,5:12,6:13,6:14"));
    }
    
}
