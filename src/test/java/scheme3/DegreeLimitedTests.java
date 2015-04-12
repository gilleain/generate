package scheme3;

import generate.handler.FileOutputHandler;
import generate.handler.GeneratorHandler;
import graph.model.IntGraph;

import org.junit.Test;

public class DegreeLimitedTests {
    
    public void vSymToFile(int n, int maxDegree, String filepath) {
        GeneratorHandler handler = new FileOutputHandler(filepath, n);
        GraphGenerator generator = new GraphGenerator(handler, true, false, false, maxDegree);
        generator.extend(new IntGraph("0:1"), n);
        handler.finish();
    }
    
    public void vFilToFile(int n, int maxDegree, String filepath) {
        GeneratorHandler handler = new FileOutputHandler(filepath, n);
        GraphGenerator generator = new GraphGenerator(handler, true, false, true, maxDegree);
        generator.extend(new IntGraph("0:1"), n);
        handler.finish();
    }
    
    @Test
    public void sevensVSym4() {
        vSymToFile(7, 4, "output/scheme3/seven_four_vsym.txt");
    }
    
    @Test
    public void sevensVFil4() {
        vSymToFile(7, 4, "output/scheme3/seven_four_vfil.txt");
    }
    
}
