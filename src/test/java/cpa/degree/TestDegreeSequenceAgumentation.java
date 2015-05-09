package cpa.degree;

import graph.model.IntGraph;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class TestDegreeSequenceAgumentation {
    
    @Test
    public void test5_4_4_3_2_2_A() {
        IntGraph parent = new IntGraph("0:1,0:2,0:3,0:4,0:5");
        int[] parentResiduals = new int[] { 0, 3, 3, 2, 1, 1 };
        DegreeSequenceAugmentation a = 
                new DegreeSequenceAugmentation(parent, parentResiduals, 1, toSet(2, 3, 4));
        DegreeSequenceAugmentation b = 
                new DegreeSequenceAugmentation(parent, parentResiduals, 1, toSet(2, 3, 5));
        System.out.println(a.getAugmentedObject().getGraph() + " is canon? " + a.isCanonical());
        System.out.println(b.getAugmentedObject().getGraph() + " is canon? " + b.isCanonical());
    }
    
    private Set<Integer> toSet(int... elements) {
        Set<Integer> s = new HashSet<Integer>();
        for (int e : elements) {
            s.add(e);
        }
        return s;
    }

}
