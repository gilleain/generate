package coloring;

import filter.Filterer;
import filter.SignatureCanonicalFilter;
import graph.model.IntGraph;

import java.util.List;

import org.junit.Test;

public class AugmentingEdgeColorerTest {
    
    public void test(IntGraph IntGraph) {
        AugmentingEdgeColorer colorer = new AugmentingEdgeColorer();
        int count = 0;
        List<IntGraph> coloredIntGraphs = colorer.color(IntGraph); 
        for (IntGraph cG : coloredIntGraphs) {
            System.out.println(count + "\t" + cG.getSortedEdgeStringWithEdgeOrder());
            count++;
        }
        
        Filterer filterer = new Filterer(new SignatureCanonicalFilter());
        List<IntGraph> filtered = filterer.filter(coloredIntGraphs);
        System.out.println(filtered.size());
    }
    
    @Test
    public void squareTest() {
        test(new IntGraph("0:1,0:3,1:2,2:3"));
    }
    
    @Test
    public void squarePairTest() {
        test(new IntGraph("0:1,0:3,1:2,1:4,2:3,2:5,4:5"));
    }
    
    @Test
    public void hexagonTest() {
        test(new IntGraph("0:1,0:5,1:2,2:3,3:4,4:5"));
    }
    
    @Test
    public void hexagonPairTest() {
        test(new IntGraph("0:1,0:5,1:2,1:6,2:3,3:4,4:5,6:7,7:8,8:9"));
    }
    
}
