package distance;

import graph.model.Graph;
import graph.model.GraphFileReader;
import graph.model.IntGraph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import util.LexIntArrComparator;

public class LargeScaleCentralityTest {
    
    public void testFile(String filename) throws FileNotFoundException {
        GraphFileReader gFile = new GraphFileReader(filename);
        Map<int[], Graph> byORS = new HashMap<int[], Graph>();
        for (IntGraph g : gFile) {
            byORS.put(CentralityCalculator.getORS(g), g);
        }
        List<int[]> keys = new ArrayList<int[]>(byORS.keySet());
        Collections.sort(keys, new LexIntArrComparator());
        for (int[] ors : keys) {
            System.out.println(Arrays.toString(ors) + "\t" + byORS.get(ors));
        }
    }

    
    @Test
    public void testFives() throws FileNotFoundException {
        testFile("output/nauty/fives_nauty.txt");
    }
    
    @Test
    public void testSixes() throws FileNotFoundException {
        testFile("output/nauty/sixes_nauty.txt");
    }
    
    @Test
    public void testSevens() throws FileNotFoundException {
        testFile("output/nauty/sevens_nauty.txt");
    }
    
}
