package test.hakimihavel;

import java.util.Collections;

import hakimihavel.RandomHHGenerator;

import model.Graph;

import org.junit.Test;

public class RandomHHTest {
    
    public void test(int[] dgSq) {
        RandomHHGenerator generator = new RandomHHGenerator();
        Graph g = generator.generate(dgSq);
        if (g != null) {
            Collections.sort(g.edges);
            System.out.println(g.vsize() 
                               + "\t" + g.esize() 
                               + "\t" + g.getSortedEdgeString()
                               + "\t" + g.isConnected());
        }
    }
    
    public void test(int degreeSum, int numberOfVertices) {
        RandomHHGenerator generator = new RandomHHGenerator();
        Graph g = generator.generate(degreeSum, numberOfVertices);
        if (g != null) {
            Collections.sort(g.edges);
            System.out.println(g.vsize() 
                               + "\t" + g.esize() 
                               + "\t" + g.getSortedEdgeString()
                               + "\t" + g.isConnected());
        }
    }
    
    @Test
    public void test332211() {
        test(new int[] {3, 3, 2, 2, 1, 1});
    }
    
    @Test
    public void test44332211() {
        test(new int[] {4, 4, 3, 3, 2, 2, 1, 1});
    }
    
    @Test
    public void test333222111() {
        test(new int[] {3, 3, 3, 2, 2, 2, 1, 1, 1});
    }
    
    @Test
    public void test333333333333() {
        test(new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 });
    }
    
    @Test
    public void test_28_18() {
        test(28, 16);
    }

    
}
