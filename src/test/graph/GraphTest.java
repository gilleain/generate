package test.graph;

import generate.CanonicalChecker;
import graph.group.GraphDiscretePartitionRefiner;
import graph.group.GraphPermutor;
import graph.model.Edge;
import graph.model.Graph;

import org.junit.Assert;
import org.junit.Test;

public class GraphTest {
    
    @Test
    public void connectionTableTest() {
        Graph g =  new Graph("2:3");
        System.out.println(g.getConnectionTableCopy());
    }
    
    @Test
    public void testSort() {
        Graph g = new Graph("0:1, 1:2, 2:3, 3:4, 4:5, 0:5, 0:6, 6:7, 7:8, 8:9, 1:9, 6:10");
        String s = g.getSortedEdgeString();
        System.out.println(s);
    }
	
	@Test
	public void testDisconnected() {
		Graph g = new Graph("0:1");
		g.makeIsolatedVertex();
		g.makeEdge(3, 4);
		System.out.println(g.getConnectionTable());
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		refiner.getAutomorphismGroup(g);
	}
    
    @Test
    public void testColorsAsPartition() {
        Graph g4 = new Graph("0:1,0:3,1:2,2:3");
        g4.setColors(0, 1, 0, 1);
        System.out.println(g4.getColorsAsPartition());
        Graph g6 = new Graph("0:1,0:5,1:2,2:3,3:4,4:5");
        g6.setColors(0, 1, 2, 1, 2, 0);
        System.out.println(g6.getColorsAsPartition());
    }
    
    @Test
    public void testFragmentA() {
//        Graph frag = new Graph("0:1,0:2,0:4,1:3");
        Graph frag = new Graph("0:1,0:2,0:3,1:4");
        Assert.assertTrue("non ordered", frag.edgesInOrder());
        Assert.assertTrue("non canonical", CanonicalChecker.isCanonical3(frag));
        // don't need to check for disconnected for this example
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        refiner.getAutomorphismGroup(frag);
        System.out.println(refiner.getBest());
    }
    
    public void searchForCanonical(Graph graph) {
        GraphPermutor permutor = new GraphPermutor(graph);
        while (permutor.hasNext()) {
            Graph permutation = permutor.next();
            if (CanonicalChecker.isCanonical3(permutation)) {
                System.out.println(permutation);
            }
        }
    }
    
    @Test
    public void test4CycleWithTwoSticks() {
//        Graph frag = new Graph("0:1,0:2,0:3,1:4,1:5,2:4");
//        Graph frag = new Graph("0:1,0:2,0:4,1:3,1:5,2:3");
//        Graph frag = new Graph("0:2,0:3,0:4,1:2,1:3,1:5");
        Graph frag = new Graph("0:2,0:3,0:4,1:2,1:3");
//        canonicallyDisintigrate(frag);
        searchForCanonical(frag);
        Assert.assertTrue("non ordered", frag.edgesInOrder());
        Assert.assertTrue("non canonical", CanonicalChecker.isCanonical3(frag));
    }
    
    @Test
    public void test4CycleWithOneStick() {
        Graph cycle = new Graph("[0:1, 0:2, 0:4, 1:3, 2:3]");
        canonicallyDisintigrate(cycle);
    }
    
    public void canonicallyDisintigrate(Graph graph) {
        try {
            if (CanonicalChecker.isCanonical3(graph)) {
                System.out.println(graph);
            } else {
                System.out.println("NC : " + graph);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (Edge edge : graph.edges) {
            canonicallyDisintigrate(graph.remove(edge));
        }
    }

}
