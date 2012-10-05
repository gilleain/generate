package test.scheme3;

import java.io.FileReader;
import java.io.IOException;

import model.Graph;
import model.GraphFileReader;

import org.junit.Test;

import filter.MaxDegreeFilter;

public class DegreeFiltering {
    
    @Test
    public void filterIncorrectSevens() throws IOException {
        MaxDegreeFilter filter = new MaxDegreeFilter(4);
        GraphFileReader file = new GraphFileReader(new FileReader("output/scheme3/seven_four.txt"));
        for (Graph graph : file) {
            if (!filter.filter(graph)) {
                System.out.println(graph);
            }
        }
    }
    
}
