package test.scheme3;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import filter.MaxDegreeFilter;
import graph.model.Graph;
import graph.model.GraphFileReader;

public class DegreeFiltering {
    
    @Test
    public void filterIncorrectSevensInVSym() throws IOException {
        MaxDegreeFilter filter = new MaxDegreeFilter(4);
        GraphFileReader file = new GraphFileReader(new FileReader("output/scheme3/seven_four_vsym.txt"));
        for (Graph graph : file) {
            if (!filter.filter(graph)) {
                System.out.println(graph);
            }
        }
    }
    
    @Test
    public void filterIncorrectSevensInVFil() throws IOException {
        MaxDegreeFilter filter = new MaxDegreeFilter(4);
        GraphFileReader file = new GraphFileReader(new FileReader("output/scheme3/seven_four_vfil.txt"));
        for (Graph graph : file) {
            if (!filter.filter(graph)) {
                System.out.println(graph);
            }
        }
    }
    
}
