package cpa;

import org.junit.Test;

public class TestGraphGenerator {
    
    private void testN(int n) {
        GraphGenerator generator = 
                new GraphGenerator(
                        new PrintstreamHandler(System.out, true), n);
        generator.generate();
    }
    
    @Test
    public void testFours() {
        testN(4);
    }
    
    @Test
    public void testFives() {
        testN(5);
    }
    
    @Test
    public void testSizes() {
        testN(6);
    }
    
    @Test
    public void testSevens() {
        testN(7);
    }
    
    @Test
    public void testEights() {
        testN(8);
    }

}
