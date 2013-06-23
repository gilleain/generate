package test.coloring;

import java.util.List;

import org.junit.Test;

import coloring.NeighbourFilter;
import coloring.SimpleExhaustiveEdgeColorer;
import filter.Filterer;
import filter.SignatureCanonicalFilter;
import graph.model.Graph;

public class SimpleExhaustiveEdgeColorerTest {
    
    public void color(Graph g, boolean filterByNeighbour, boolean filterUnique) {
        SimpleExhaustiveEdgeColorer colorer = new SimpleExhaustiveEdgeColorer();
        int index = 0;
        Filterer filterer;
        if (filterByNeighbour) {
            if (filterUnique) {
                filterer = new Filterer(new NeighbourFilter(), new SignatureCanonicalFilter());
            } else {
                filterer = new Filterer(new NeighbourFilter());
            }
        } else {
            if (filterUnique) {
                filterer = new Filterer(new SignatureCanonicalFilter());
            } else {
                filterer = new Filterer();
            }
        }
        List<Graph> colored = filterer.filter(colorer.color(g));
        for (Graph h : colored) {
            System.out.println(index + "\t" + h.getSortedEdgeStringWithEdgeOrder());
            index++;
        }
    }
    
    @Test
    public void square() {
        color(new Graph("0:1,0:3,1:2,2:3"), false, false);
    }
    
    @Test
    public void squareFiltered() {
        color(new Graph("0:1,0:3,1:2,2:3"), true, false);
    }
    
    @Test
    public void squareUnique() {
        color(new Graph("0:1,0:3,1:2,2:3"), false, true);
    }
    
    @Test
    public void squareFilteredUnique() {
        color(new Graph("0:1,0:3,1:2,2:3"), true, true);
    }
    
    @Test
    public void hexagonFilteredUnique() {
        color(new Graph("0:1,0:5,1:2,2:3,3:4,4:5"), true, true);
    }
    
    @Test
    public void squarePairFilteredUnique() {
        color(new Graph("0:1,0:3,1:2,1:4,2:3,2:5,4:5"), true, true);
    }
    
}
