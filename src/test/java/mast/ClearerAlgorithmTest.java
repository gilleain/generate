package mast;

import graph.model.Graph;

import java.math.BigInteger;

import mast.ClearerAlgorithm;

import org.junit.Test;

public class ClearerAlgorithmTest {
    
    public void test(Graph tree) {
        ClearerAlgorithm algorithm = new ClearerAlgorithm();
        BigInteger sym = algorithm.getSym(tree);
        System.out.println(tree + " \t" + sym);
    }
    
    @Test
    public void threeClawTest() {
        test(new Graph("0:1,0:2,0:3"));
    }
    
    @Test
    public void twoByTwoTest() {
        test(new Graph("0:1,0:2,1:3,1:4,2:5,2:6"));
    }
    
    @Test
    public void twoTwoTwoTest() {
        test(new Graph("0:1,0:2,1:3,1:4,2:5,2:6,3:7,3:8,4:9,4:10,5:11,5:12,6:13,6:14"));
    }
    
    @Test
    public void raggedTreeTest() {
    	test(new Graph("0:1,0:6,1:2,1:5,2:3,2:4,6:7,6:8,8:9,8:10"));
    }
    
    @Test
    public void symmetricTwoCentreTest() {
    	test(new Graph("0:1, 1:2, 2:3, 0:4, 4:5"));
    }
    
    @Test
    public void assymmetricTwoCentreTest() {
    	test(new Graph("0:1, 0:2, 0:3, 1:4"));
    }
    
    @Test
    public void rigidTreeTest() {
    	test(new Graph("0:1, 1:2, 2:3, 0:4, 4:5, 0:6"));
    }
    
}
