package test.scheme3;

import java.io.FileNotFoundException;
import java.util.List;

import model.Edge;
import model.Graph;
import model.GraphFileReader;

import org.junit.Test;

import scheme3.ChainDecomposition;

public class ChainDecompositionTest {
    
    public void test(Graph g) {
        ChainDecomposition decomposition = new ChainDecomposition(g);
        List<List<Edge>> cycles = decomposition.getCycleChains();
        List<List<Edge>> paths = decomposition.getPathChains();
        List<Edge> bridges = decomposition.getBridges();
        System.out.println(cycles);
        System.out.println(paths);
        System.out.println(bridges);
    }
    
    @Test
    public void fusedCycleTest() {
        test(new Graph("0:1,0:5,1:2,1:4,2:3,3:4,4:5"));
    }
    
    @Test
    public void biStalkedFourCycleTest() {
        test(new Graph("0:1,0:2,1:3,2:3,2:4,3:5"));
    }
    
    public void test(String file) throws FileNotFoundException {
        GraphFileReader reader = new GraphFileReader(file);
        int count = 0;
        for (Graph g : reader) {
            ChainDecomposition cd = new ChainDecomposition(g);
            int pathCount = cd.getPathChains().size();
            int cycleCount = cd.getCycleChains().size();
            int bridgeCount = cd.getBridges().size();
            System.out.println(count + "\t" + pathCount + "\t" + cycleCount + "\t" + bridgeCount + "\t" + g);
            count++;
        }
    }
    
    @Test
    public void testSixes() throws FileNotFoundException {
        test("output/scheme3/sixes.txt");
    }
    
    @Test
    public void testSevens() throws FileNotFoundException {
        test("output/scheme3/sevens.txt");
    }
    
}
