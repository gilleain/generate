package scheme3.lister;

import graph.model.IntGraph;

import java.util.List;

import org.junit.Test;

import util.GraphDuplicateChecker;

public class ConnVertSymListTest {
    
    @Test
    public void fourLineChildren() {
        IntGraph parent = new IntGraph("0:1,1:2,2:3");
        ConnectedVertexSymmetryChildLister lister = new ConnectedVertexSymmetryChildLister();
        List<IntGraph> children = lister.list(parent, 5);
        GraphDuplicateChecker.checkForDuplicates(children);
    }
    
}
