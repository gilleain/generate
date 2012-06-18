package test.hakimihavel;

import hakimihavel.RandomHHGenerator;

import model.Graph;

import org.junit.Test;

public class RandomHHTest {
    
    @Test
    public void test332211() {
        int[] dgSq = new int[] {3, 3, 2, 2, 1, 1};
        RandomHHGenerator generator = new RandomHHGenerator();
        Graph g = generator.generate(dgSq);
        if (g != null) {
            System.out.println(g.getSortedEdgeString());
        }
        
    }
    
}
