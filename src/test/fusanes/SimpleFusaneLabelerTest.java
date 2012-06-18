package test.fusanes;

import model.Graph;

import org.junit.Test;

import fusanes.FusaneInnerDual;
import fusanes.SimpleFusaneLabeler;

public class SimpleFusaneLabelerTest {
    
    @Test
    public void branchingTest() {
        Graph t = new Graph("0:1,1:2,1:4,2:3");
        for (FusaneInnerDual dual : SimpleFusaneLabeler.label(t)) {
            System.out.println(dual.getLabels());
        }
    }
    
    @Test
    public void fourLineTest() {
        Graph t = new Graph("0:1,1:2,2:3,3:4");
        for (FusaneInnerDual dual : SimpleFusaneLabeler.label(t)) {
            System.out.println(dual.getLabels());
        }
    }
    
    @Test
    public void bugTest() {
        Graph t = new Graph("0:1, 1:2, 1:3, 0:4, 4:5");
        for (FusaneInnerDual dual : SimpleFusaneLabeler.label(t)) {
            System.out.println(dual.getLabels());
        }
    }
    
}
