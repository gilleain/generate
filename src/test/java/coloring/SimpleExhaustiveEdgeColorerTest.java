package coloring;

import filter.Filterer;
import filter.SignatureCanonicalFilter;
import graph.model.IntGraph;

import java.util.List;

import org.junit.Test;

public class SimpleExhaustiveEdgeColorerTest {
    
    public void color(IntGraph g, boolean filterByNeighbour, boolean filterUnique) {
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
        List<IntGraph> colored = filterer.filter(colorer.color(g));
        for (IntGraph h : colored) {
            System.out.println(index + "\t" + h.getSortedEdgeStringWithEdgeOrder());
            index++;
        }
    }
    
    @Test
    public void square() {
        color(new IntGraph("0:1,0:3,1:2,2:3"), false, false);
    }
    
    @Test
    public void squareFiltered() {
        color(new IntGraph("0:1,0:3,1:2,2:3"), true, false);
    }
    
    @Test
    public void squareUnique() {
        color(new IntGraph("0:1,0:3,1:2,2:3"), false, true);
    }
    
    @Test
    public void squareFilteredUnique() {
        color(new IntGraph("0:1,0:3,1:2,2:3"), true, true);
    }
    
    @Test
    public void hexagonFilteredUnique() {
        color(new IntGraph("0:1,0:5,1:2,2:3,3:4,4:5"), true, true);
    }
    
    @Test
    public void squarePairFilteredUnique() {
        color(new IntGraph("0:1,0:3,1:2,1:4,2:3,2:5,4:5"), true, true);
    }
    
}
