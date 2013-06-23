package test.scheme3.lister;

import graph.model.Graph;

import java.util.List;

import org.junit.Test;

import scheme3.lister.ConnectedVertexSymmetryChildLister;
import tools.GraphDuplicateChecker;

public class ConnVertSymListTest {
    
    @Test
    public void fourLineChildren() {
        Graph parent = new Graph("0:1,1:2,2:3");
        ConnectedVertexSymmetryChildLister lister = new ConnectedVertexSymmetryChildLister();
        List<Graph> children = lister.list(parent, 5);
        GraphDuplicateChecker checker = new GraphDuplicateChecker();
        checker.checkForDuplicates(children);
    }
    
}
