package mast;

import graph.model.IntGraph;

import java.math.BigInteger;

import org.junit.Test;

public class OriginalAlgorithmTest {
    
    @Test
    public void factest() {
        System.out.println(new OriginalAlgorithm().factorial(BigInteger.valueOf(5)));
        System.out.println(new OriginalAlgorithm().factorial(BigInteger.valueOf(20)));
    }
    
    public void test(IntGraph tree) {
        OriginalAlgorithm algorithm = new OriginalAlgorithm();
        BigInteger sym = algorithm.getSym(tree);
        System.out.println(tree + " \t" + sym);
    }
    
    @Test
    public void threeClawTest() {
        test(new IntGraph("0:1,0:2,0:3"));
    }
    
    @Test
    public void twoByTwoTest() {
        test(new IntGraph("0:1,0:2,1:3,1:4,2:5,2:6"));
    }
    
    @Test
    public void twoTwoTwoTest() {
        test(new IntGraph("0:1,0:2,1:3,1:4,2:5,2:6,3:7,3:8,4:9,4:10,5:11,5:12,6:13,6:14"));
    }
    
}
