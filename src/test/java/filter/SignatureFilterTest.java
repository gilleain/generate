package filter;

import graph.model.Graph;
import graph.model.GraphFileReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SignatureFilterTest {
    
    public void signaturesFromY(List<String> sigStrings, int height, String filename) throws FileNotFoundException {
        SignatureFilter filter = new SignatureFilter(sigStrings, height);
        GraphFileReader graphs = new GraphFileReader(new FileReader(filename));
        for (Graph graph : graphs) {
            if (filter.filter(graph)) {
                System.out.println(graph);
            }
        }
    }
    
    @Test
    public void testA() throws FileNotFoundException {
        List<String> signatures = new ArrayList<String>();
        signatures.add("[.]([.][.][.])");
        signatures.add("[.]([.][.][.])");
        signatures.add("[.]([.][.])");
        signatures.add("[.]([.][.])");
        signatures.add("[.]([.])");
        signatures.add("[.]([.])");
        signaturesFromY(signatures, 1, "sixes.txt");
    }
    
    @Test
    public void testB() throws FileNotFoundException {
        List<String> signatures = new ArrayList<String>();
        signatures.add("[.]([.][.][.])");
        signatures.add("[.]([.][.][.])");
        signatures.add("[.]([.][.][.])");
        signatures.add("[.]([.][.])");
        signatures.add("[.]([.][.])");
        signatures.add("[.]([.][.])");
        signatures.add("[.]([.])");
        signatures.add("[.]([.])");
        signatures.add("[.]([.])");
        signaturesFromY(signatures, 1, "nines.txt");
    }


}
