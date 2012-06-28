package test.hakimihavel;

import generate.handler.SystemOutHandler;
import hakimihavel.KiralyHHGenerator;

import org.junit.Test;

public class KiralyHHGeneratorTest {
    
    public void test(int[] degSeq) {
        KiralyHHGenerator generator = new KiralyHHGenerator(new SystemOutHandler(-1, false, true));
        generator.generate(degSeq);        
    }
    
    @Test
    public void two_To_Four_Test() {
        test(new int[] { 2, 2, 2, 2 });
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
        int[] degSeq = new int[] { 3, 3, 3, 3, 3, 3, 3, 3 };
        KiralyHHGenerator generator = new KiralyHHGenerator();
        generator.generate(degSeq);
    }
}
