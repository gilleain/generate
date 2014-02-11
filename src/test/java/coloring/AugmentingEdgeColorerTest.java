package test.coloring;

import java.util.List;

import org.junit.Test;

import coloring.AugmentingEdgeColorer;
import filter.Filterer;
import filter.SignatureCanonicalFilter;
import graph.model.Graph;

public class AugmentingEdgeColorerTest {
    
    public void test(Graph graph) {
        AugmentingEdgeColorer colorer = new AugmentingEdgeColorer();
        int count = 0;
        List<Graph> coloredGraphs = colorer.color(graph); 
        for (Graph cG : coloredGraphs) {
            System.out.println(count + "\t" + cG.getSortedEdgeStringWithEdgeOrder());
            count++;
        }
        
        Filterer filterer = new Filterer(new SignatureCanonicalFilter());
        List<Graph> filtered = filterer.filter(coloredGraphs);
        System.out.println(filtered.size());
    }
    
    @Test
    public void squareTest() {
        test(new Graph("0:1,0:3,1:2,2:3"));
    }
    
    @Test
    public void squarePairTest() {
        test(new Graph("0:1,0:3,1:2,1:4,2:3,2:5,4:5"));
    }
    
    @Test
    public void hexagonTest() {
        test(new Graph("0:1,0:5,1:2,2:3,3:4,4:5"));
    }
    
    @Test
    public void hexagonPairTest() {
        test(new Graph("0:1,0:5,1:2,1:6,2:3,3:4,4:5,6:7,7:8,8:9"));
    }
    
}
