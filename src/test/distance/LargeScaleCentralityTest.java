package test.distance;

import graph.model.Graph;
import graph.model.GraphFileReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import distance.CentralityCalculator;

public class LargeScaleCentralityTest {
    
    public class LexIntArrComparator implements Comparator<int[]> {

        @Override
        public int compare(int[] arrA, int[] arrB) {
            if (arrA.length != arrB.length) {
                return new Integer(arrA.length).compareTo(new Integer(arrB.length));
            } else {
                for (int i = 0 ; i < arrA.length; i++) {
                    if (arrA[i] < arrB[i]) {
                        return -1;
                    } else if (arrA[i] > arrB[i]) {
                        return 1;
                    }
                }
            }
            return 0;
        }
        
    }
    
    public void testFile(String filename) throws FileNotFoundException {
        GraphFileReader gFile = new GraphFileReader(filename);
        Map<int[], Graph> byORS = new HashMap<int[], Graph>();
        for (Graph g : gFile) {
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
    
}
