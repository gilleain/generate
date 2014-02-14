package degreeseq;

import filter.DegreeSequenceFilter;
import generate.handler.IsomorphCountingHandler;
import graph.model.Graph;

import java.util.Map;

import org.junit.Test;

import degreeseq.GeneralizedHakimiHavelGenerator;

public class GeneralizedHHGeneratorTest {
    
    public void testSeq(int[] seq) {
        IsomorphCountingHandler handler = new IsomorphCountingHandler();
        GeneralizedHakimiHavelGenerator generator = new GeneralizedHakimiHavelGenerator(handler);
        generator.connectAll(seq);
        Map<Graph, Integer> nonIsomorphCounts = handler.getNonIsomorphicGraphCount();
        DegreeSequenceFilter filter = new DegreeSequenceFilter(seq);
        int index = 0;
        for (Graph g : nonIsomorphCounts.keySet()) {
            int count = nonIsomorphCounts.get(g);
            int eSize = g.esize();
            boolean isRightDegree = filter.filter(g);
            System.out.println(index + "\t" + count + "\t" + eSize + "\t" + isRightDegree + "\t" + g);
            index++;
        }
    }
    
    @Test
    public void test_33333333_Seq() {
        testSeq(new int[] { 3, 3, 3, 3, 3, 3, 3, 3 });
    }
    
    @Test
    public void test_443322_Seq() {
        testSeq(new int[] { 4, 4, 3, 3, 2, 2 });
    }
    
    @Test
    public void test_433321_Seq() {
        testSeq(new int[] { 4, 3, 3, 3, 2, 1 });
    }
    
    @Test
    public void test_32221_Seq() {
        testSeq(new int[] { 3, 2, 2, 2, 1 });
    }
    
    @Test
    public void test_332211_Seq() {
        testSeq(new int[] { 3, 3, 2, 2, 1, 1 });
    }
    
    @Test
    public void test_41111_Seq() {
        testSeq(new int[] { 4, 1, 1, 1, 1 });
    }
    
}
