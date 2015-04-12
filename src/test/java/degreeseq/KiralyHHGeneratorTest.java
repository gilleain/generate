package degreeseq;

import generate.handler.IsomorphCountingHandler;
import graph.model.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class KiralyHHGeneratorTest {
    
    public void test(int[] degSeq) {
//        KiralyHHGenerator generator = new KiralyHHGenerator(new SystemOutHandler(-1, false, true));
        IsomorphCountingHandler duplicateHandler = new IsomorphCountingHandler();
        KiralyHHGenerator generator = new KiralyHHGenerator(duplicateHandler);
        generator.generate(degSeq);
//        Map<String, Graph> sigMap = duplicateHandler.getSignatureMap(); 
//        for (String sig : sigMap.keySet()) {
//            System.out.println(sig + " " + sigMap.get(sig));
//        }
        Map<Graph, Integer> dups = duplicateHandler.getNonIsomorphicGraphCount();
        for (Graph g : dups.keySet()) {
            System.out.println(dups.get(g) + "\t" + toDegreeSeq(g) + g + "\t" + g.getEdgeCount());
        }
    }
    
    public List<Integer> toDegreeSeq(Graph g) {
        List<Integer> degSeq = new ArrayList<Integer>();
        for (int i = 0; i < g.getVertexCount(); i++) {
            degSeq.add(g.degree(i));
        }
        return degSeq;
    }
    
    @Test
    public void two_To_Four_Test() {
        test(new int[] { 2, 2, 2, 2 });
    }
    
    @Test
    public void seq_32221_Test() {
        test(new int[] { 3, 2, 2, 2, 1 });
    }
    
    @Test
    public void two_To_Five_Test() {
        test(new int[] { 2, 2, 2, 2, 2 });
    }
    
    @Test
    public void seq_332211_Test() {
        test(new int[] { 3, 3, 2, 2, 1, 1 });
    }
    
    @Test
    public void seq_333222111_Test() {
        test(new int[] { 3, 3, 3, 2, 2, 2, 1, 1, 1 });
    }
    
    @Test
    public void three_To_Six_Test() {
        test(new int[] { 3, 3, 3, 3, 3, 3 });
    }
    
    @Test
    public void three_To_Eight_Test() {
        test(new int[] { 3, 3, 3, 3, 3, 3, 3, 3 });
    }
    
    @Test
    public void five_to_four_four_squared_Test() {
        test(new int[] { 5, 5, 5, 5, 4, 4 });
    }
    
    @Test
    public void five_to_four_three_squared_two() {
        test(new int[] { 5, 5, 5, 5, 3, 3, 2 });
    }
    
    @Test
    public void four_to_five_three_squared_two() {
        test(new int[] { 4, 4, 4, 4, 4, 3, 3, 2 });
    }
}
