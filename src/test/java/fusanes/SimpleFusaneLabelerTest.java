package fusanes;

import graph.model.IntGraph;

import org.junit.Test;

public class SimpleFusaneLabelerTest {
    
    @Test
    public void branchingTest() {
        IntGraph t = new IntGraph("0:1,1:2,1:4,2:3");
        for (FusaneInnerDual dual : SimpleFusaneLabeler.label(t)) {
            System.out.println(dual.getLabels());
        }
    }
    
    @Test
    public void fourLineTest() {
        IntGraph t = new IntGraph("0:1,1:2,2:3,3:4");
        for (FusaneInnerDual dual : SimpleFusaneLabeler.label(t)) {
            System.out.println(dual.getLabels());
        }
    }
    
    @Test
    public void bugTest() {
        IntGraph t = new IntGraph("0:1, 1:2, 1:3, 0:4, 4:5");
        for (FusaneInnerDual dual : SimpleFusaneLabeler.label(t)) {
            System.out.println(dual.getLabels());
        }
    }
    
}
