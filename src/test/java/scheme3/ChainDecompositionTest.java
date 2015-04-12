package scheme3;

import graph.model.GraphFileReader;
import graph.model.IntEdge;
import graph.model.IntGraph;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ChainDecompositionTest {
    
    public void test(IntGraph g, int expPaths, int expCycles, int expBridges) {
        ChainDecomposition decomposition = new ChainDecomposition(g);
        List<List<IntEdge>> cycles = decomposition.getCycleChains();
        List<List<IntEdge>> paths = decomposition.getPathChains();
        List<IntEdge> bridges = decomposition.getBridges();
        System.out.println(paths);
        System.out.println(cycles);
        System.out.println(bridges);
        Assert.assertEquals(expPaths, paths.size());
        Assert.assertEquals(expCycles, cycles.size());
        Assert.assertEquals(expBridges, bridges.size());
    }
    
    @Test
    public void fusedCycleTest() {
        test(new IntGraph("0:1,0:5,1:2,1:4,2:3,3:4,4:5"), 1, 1, 0);
    }
    
    @Test
    public void biStalkedFourCycleTest() {
        test(new IntGraph("0:1,0:2,1:3,2:3,2:4,3:5"), 0, 1, 2);
    }
    
    @Test
    public void paperTest() {
        test(new IntGraph("0:1,0:2,0:3,0:4,0:5,1:2,1:6,2:6,2:7,3:4,5:8,5:9,6:7,8:9"), 2, 3, 1);
    }
    
    public void test(String file) throws FileNotFoundException {
        GraphFileReader reader = new GraphFileReader(file);
        int count = 0;
        for (IntGraph g : reader) {
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
