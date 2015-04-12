package nauty;

import graph.model.IntGraph;
import group.PermutationGroup;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class DreadnautLinkTest {
    
    @Test
    public void ioUncoloredTest() throws IOException, InterruptedException {
        IntGraph graph = new IntGraph("0:1,0:3,1:2,2:3");
        List<String> outputStrings = DreadnautLink.getOutput(graph, false);
        for (String outputString : outputStrings) {
            System.out.println(outputString);
        }
    }
    
    @Test
    public void uncolored4CycleTest() throws IOException, InterruptedException {
        IntGraph graph = new IntGraph("0:1,0:3,1:2,2:3");
        PermutationGroup group = DreadnautLink.getAutGroup(graph, false);
        System.out.println("group size = " + group.all().size());
    }
    
    @Test
    public void colored4CycleTest() throws IOException, InterruptedException {
        IntGraph graph = new IntGraph("0:1,0:3,1:2,2:3");
        graph.setColors(0, 1, 0, 1);
        PermutationGroup group = DreadnautLink.getAutGroup(graph, true);
        System.out.println("group size = " + group.all().size());
    }
    
    @Test
    public void uncolored6CycleTest() throws IOException, InterruptedException {
        IntGraph graph = new IntGraph("0:1,0:5,1:2,2:3,3:4,4:5");
        PermutationGroup group = DreadnautLink.getAutGroup(graph, false);
        System.out.println("group size = " + group.all().size());
    }
    
    @Test
    public void colored6CycleTest() throws IOException, InterruptedException {
        IntGraph graph = new IntGraph("0:1,0:5,1:2,2:3,3:4,4:5");
        graph.setColors(0, 1, 0, 1, 0, 1);
        PermutationGroup group = DreadnautLink.getAutGroup(graph, true);
        System.out.println("group size = " + group.all().size());
    }
    
    @Test
    public void ioColoredTest() throws IOException, InterruptedException {
        IntGraph graph = new IntGraph("0:1,0:3,1:2,2:3");
        graph.setColors(0, 1, 0, 1);
        List<String> outputStrings = DreadnautLink.getOutput(graph, true);
        for (String outputString : outputStrings) {
            System.out.println(outputString);
        }
    }

}
