package test.degreeseq;

import org.junit.Test;

import degreeseq.OrbitSaturatingGenerator;

public class OrbitSaturatingGeneratorTest {
    
    @Test
    public void test_3ToThe8() {
        int[] degSeq = new int[] { 3, 3, 3, 3, 3, 3, 3, 3 };
        OrbitSaturatingGenerator generator = new OrbitSaturatingGenerator();
        generator.generate(degSeq);
    }
    
    @Test
    public void test_3Squared_2ToThe6() {
        int[] degSeq = new int[] { 3, 3, 2, 2, 2, 2, 2, 2 };
        OrbitSaturatingGenerator generator = new OrbitSaturatingGenerator();
        generator.generate(degSeq);
    }
    
    @Test
    public void test_3ToThe6() {
        int[] degSeq = new int[] { 3, 3, 3, 3, 3, 3 };
        OrbitSaturatingGenerator generator = new OrbitSaturatingGenerator();
        generator.generate(degSeq);
    }
    
    @Test
    public void test_33_22_11() {
        int[] degSeq = new int[] { 3, 3, 2, 2, 1, 1 };
        OrbitSaturatingGenerator generator = new OrbitSaturatingGenerator();
        generator.generate(degSeq);
    }
    
    @Test
    public void test_3_22_1() {
        int[] degSeq = new int[] { 3, 2, 2, 1 };
        OrbitSaturatingGenerator generator = new OrbitSaturatingGenerator();
        generator.generate(degSeq);
    }
    
    @Test
    public void test_2222() {
        int[] degSeq = new int[] { 2, 2, 2, 2 };
        OrbitSaturatingGenerator generator = new OrbitSaturatingGenerator();
        generator.generate(degSeq);
    }
    
}
