package util;

import java.io.IOException;

import org.junit.Test;

public class TestDegreeSequenceSplitter {

    @Test
    public void splitSixes() throws IOException {
        String inputFilename = "output/mckay/six_x.txt";
        String outputDir = "output/degsplit/six";
        
        DegreeSequenceSplitter splitter = new DegreeSequenceSplitter();
        splitter.split(inputFilename, outputDir);
    }
    
    @Test
    public void splitSevens() throws IOException {
        String inputFilename = "output/mckay/seven_x.txt";
        String outputDir = "output/degsplit/seven";
        
        DegreeSequenceSplitter splitter = new DegreeSequenceSplitter();
        splitter.split(inputFilename, outputDir);
    }
    
}
