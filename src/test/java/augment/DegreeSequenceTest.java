package augment;

import graph.model.Graph;
import graph.model.GraphFileReader;
import graph.model.IntGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class DegreeSequenceTest {
    
    public static final String IN_FILE = "output/mckay";
    
    public int sum(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }
    
    public List<Integer> getDegreeSequence(IntGraph g) {
        List<Integer> seq = new ArrayList<Integer>();
        for (int i = 0; i < g.vsize(); i++) {
            seq.add(g.degree(i));
        }
        Collections.sort(seq);
        return seq;
    }
    
    public void testFile(String filename) throws FileNotFoundException {
        GraphFileReader reader = new GraphFileReader(new FileReader(new File(IN_FILE, filename)));
        Map<List<Integer>, List<Graph>> degSeqMap = new HashMap<List<Integer>, List<Graph>>();
        for (IntGraph g : reader) {
            List<Integer> degSeq = getDegreeSequence(g);
            List<Graph> values;
            if (degSeqMap.containsKey(degSeq)) {
                values = degSeqMap.get(degSeq);
            } else {
                values = new ArrayList<Graph>();
                degSeqMap.put(degSeq, values);
            }
            values.add(g);
        }
        int counter = 0;
        List<List<Integer>> keys = new ArrayList<List<Integer>>(degSeqMap.keySet());
        Collections.sort(keys, new Comparator<List<Integer>>() {

            public int compare(List<Integer> l0, List<Integer> l1) {
                Integer s0 = sum(l0);
                Integer s1 = sum(l1);
                return s0.compareTo(s1);
            }
            
        });
        for (List<Integer> key : keys) {
            List<Graph> set = degSeqMap.get(key);
            int size = set.size();
            System.out.println(counter + "\t" + size + "\t" + sum(key) + "\t" + key + "\t" +  set);
//            System.out.println(counter + "\t" + size + "\t" + sum(key) + "\t" + key);
            counter++;
        }
    }
    
    @Test
    public void testEights() throws FileNotFoundException {
        testFile("eight_x.txt");
    }
    
    @Test
    public void testSixes() throws FileNotFoundException {
        testFile("six_x.txt");
    }
    
    @Test
    public void testFives() throws FileNotFoundException {
        testFile("five_x.txt");
    }
    
    @Test
    public void testFours() throws FileNotFoundException {
        testFile("four_x.txt");
    }
}
