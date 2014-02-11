package test.filter;

import java.io.FileNotFoundException;
import java.io.FileReader;

import junit.framework.Assert;

import org.junit.Test;

import filter.DegreeSequenceFilter;
import generate.CanonicalChecker;
import graph.model.Graph;
import graph.model.GraphFileReader;

public class DegreeFilterTest {
    
    public void degreeXFromY(
            String filename, int... degreeSeq) throws FileNotFoundException {
        DegreeSequenceFilter filter = new DegreeSequenceFilter(degreeSeq);
        GraphFileReader graphs = new GraphFileReader(new FileReader(filename));
        for (Graph graph : graphs) {
            if (filter.filter(graph)) {
                System.out.println(graph);
            }
        }
    }
    
    @Test
    public void degree3FromSixesTest() throws FileNotFoundException {
        degreeXFromY("sixes.txt", 3, 3, 3, 3, 3, 3);
    }
    
    @Test
    public void degree332211FromSixesTest() throws FileNotFoundException {
        degreeXFromY("sixes_filtered.txt", 3, 3, 2, 2, 1, 1);
    }
    
    @Test
    public void degree333222111FromNinesTest() throws FileNotFoundException {
        degreeXFromY("nines.txt", 3, 3, 3, 2, 2, 2, 1, 1, 1);
    }
    
    @Test
    public void filterGraph() {
        Graph graph = new Graph("0:1,0:2,0:4,1:3,1:5,2:3");
        Assert.assertTrue(graph.edgesInOrder());
        Assert.assertTrue(CanonicalChecker.isCanonical3(graph));
        Assert.assertTrue(new DegreeSequenceFilter(3, 3, 2, 2, 1, 1).filter(graph));
    }

}
