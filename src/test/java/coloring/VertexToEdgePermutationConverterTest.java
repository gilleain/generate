package coloring;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

import org.junit.Test;

public class VertexToEdgePermutationConverterTest {
    
    public void test(IntGraph g) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup vGroup = refiner.getAutomorphismGroup(g);
        PermutationGroup eGroup = VertexToEdgePermutationConverter.convert(g, vGroup);
        int index = 0;
        for (Permutation p : eGroup.all()) {
            System.out.println(index + "\t" + p.toCycleString());
            index++;
        }
    }
    
    @Test
    public void fourCycle() {
        test(new IntGraph("0:1,0:3,1:2,2:3"));
    }
    
    @Test
    public void twoFusedSixCycles() {
        test(new IntGraph("0:1,0:9,1:2,1:6,2:3,3:4,4:5,5:6,6:7,7:8,8:9"));
    }
}
