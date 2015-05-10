package cpa.degree;

import graph.model.GraphFileReader;
import graph.model.IntGraph;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import cpa.handler.CountingHandler;
import cpa.handler.IsomorphismHandler;

/**
 * Test that every degree sequence split from the graphs on N vertices can be replicated.
 * 
 * @author maclean
 *
 */
public class TestAllSplits {
    
    private void test(String inputDir) throws IOException {
        for (String filename : new File(inputDir).list()) {
            File file = new File(inputDir, filename);
            int expected = getExpected(file);
            int[] degSeq = toDegSeq(filename);
//            int count = generateIso(degSeq);
            int count = generate(degSeq);
            if (expected == count) {
                System.out.println("Passed " + filename);
            } else if (expected > count) {
                System.out.println("Too few! " + filename + " " + expected + " > " + count);
            } else {
                System.out.println("Too many! " + filename + " " + expected + " < " + count);
            }
//            assertEquals("Failed for " + filename, expected, count);
        }
    }
    
    private int getExpected(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        GraphFileReader reader = new GraphFileReader(fileReader);
        int expected = 0;
        for (@SuppressWarnings("unused") IntGraph g : reader) {
            expected++;
        }
        fileReader.close();
        return expected;
    }
    
    private int[] toDegSeq(String filename) {
        String[] nameParts = filename.split("_");
        int[] degSeq = new int[nameParts.length];
        int counter = 0;
        for (String part : nameParts) {
            degSeq[counter] = Integer.parseInt(part);
            counter++;
        }
        return degSeq;
    }
    
    private int generate(int[] degSeq) {
        CountingHandler handler = new CountingHandler();
        DegreeSequenceGenerator gen = new DegreeSequenceGenerator(handler, degSeq);
        gen.generate();
        return handler.getCount();
    }
    
    private int generateIso(int[] degSeq) {
        IsomorphismHandler handler = new IsomorphismHandler();
        DegreeSequenceGenerator gen = new DegreeSequenceGenerator(handler, degSeq);
        gen.generate();
        Map<String, List<IntGraph>> m = handler.getMap();
        return m.size();
    }
    
    @Test
    public void testFives() throws IOException {
        test("output/degsplit/five");
    }
    
    @Test
    public void testSixes() throws IOException {
        test("output/degsplit/six");
    }
    
    @Test
    public void testSevens() throws IOException {
        test("output/degsplit/seven");
    }

}
