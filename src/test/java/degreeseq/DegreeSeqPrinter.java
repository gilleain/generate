package degreeseq;

import graph.model.GraphFileReader;
import graph.model.IntGraph;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class DegreeSeqPrinter {
    
    private void print(String filename) throws IOException {
        GraphFileReader reader = new GraphFileReader(filename);
        for (IntGraph graph : reader) {
            System.out.println(graph + " " + Arrays.toString(graph.degreeSequence(true)));
        }
        reader.close();
    }
    
    @Test
    public void printNines() throws IOException {
        print("nines_nauty.txt");
    }

}
