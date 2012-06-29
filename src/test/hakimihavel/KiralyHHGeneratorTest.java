package test.hakimihavel;

import generate.handler.IsomorphCountingHandler;
import hakimihavel.KiralyHHGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Graph;

import org.junit.Test;

public class KiralyHHGeneratorTest {
    
    public void test(int[] degSeq) {
//        KiralyHHGenerator generator = new KiralyHHGenerator(new SystemOutHandler(-1, false, true));
        IsomorphCountingHandler duplicateHandler = new IsomorphCountingHandler();
        KiralyHHGenerator generator = new KiralyHHGenerator(duplicateHandler);
        generator.generate(degSeq);
        Map<Graph, Integer> dups = duplicateHandler.getNonIsomorphicGraphCount();
        for (Graph g : dups.keySet()) {
            System.out.println(g + "\t" + dups.get(g) + "\t" + toDegreeSeq(g));
        }
    }
    
    public List<Integer> toDegreeSeq(Graph g) {
        List<Integer> degSeq = new ArrayList<Integer>();
        for (int i = 0; i < g.vsize(); i++) {
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
    public void three_To_Six_Test() {
        test(new int[] { 3, 3, 3, 3, 3, 3 });
    }
    
    @Test
    public void three_To_Eight_Test() {
        test(new int[] { 3, 3, 3, 3, 3, 3, 3, 3 });
    }
}
