package generate;

import graph.model.Graph;
import graph.model.IntGraph;
import group.Partition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SignatureGeneratorTest {
    
    private SignatureGenerator gen;
    
    @Before
    public void setup() {
      gen = new SignatureGenerator();  
    }
    
    @Test
    public void testConnectedComponentsOnConnected() {
        IntGraph connected = new IntGraph("0:1,0:2,1:3,2:4");
        Assert.assertTrue(gen.connected(connected));
    }
    
    @Test
    public void testConnectedComponentsOnDisconnected() {
        IntGraph disconnected = new IntGraph("0:1,1:3,2:4");
        Assert.assertFalse(gen.connected(disconnected));
    }
    
    @Test
    public void testK5SaturatedSubIntGraph() {
        // contains K5, as maxDegree is 4 by default
        Graph ssg = new IntGraph("0:1,2:3,2:4,2:5,2:6,3:4,3:5,3:6,4:5,4:6,5:6");
        Assert.assertFalse(gen.noSaturatedSubgraphs(ssg));
    }
    
    @Test
    public void testNearK5NonSaturatedSubIntGraph() {
        // contains K5 - {5:6}
        Graph ssg = new IntGraph("0:1,2:3,2:4,2:5,2:6,3:4,3:5,3:6,4:5,4:6");
        Assert.assertTrue(gen.noSaturatedSubgraphs(ssg));
    }
    
    @Test
    public void testOrbitsAsPartition() {
        IntGraph g3x2_2x2_1x2Sigs = new IntGraph("0:4,0:5,1:4,1:5,2:4,3:5");
        Partition partition = gen.getOrbitsAsPartition(g3x2_2x2_1x2Sigs);
        System.out.println(partition);
        Assert.assertEquals(3, partition.size());
        IntGraph g3x3_2x3_1x3Sigs = new IntGraph("0:5,0:6,1:5,1:8,2:6,3:7,4:7,5:8,7:8");
        partition = gen.getOrbitsAsPartition(g3x3_2x3_1x3Sigs);
        System.out.println(partition);
        Assert.assertEquals(8, partition.size());
    }
    
    @Test
    public void generateFrom3x2_2x2_1x2() {
        List<String> signatures = new ArrayList<String>();
        signatures.add("[.]([.][.][.])");
        signatures.add("[.]([.][.][.])");
        signatures.add("[.]([.][.])");
        signatures.add("[.]([.][.])");
        signatures.add("[.]([.])");
        signatures.add("[.]([.])");
        for (IntGraph IntGraph : gen.enumerate(signatures)) {
            System.out.println(IntGraph);
        }
    }

}
