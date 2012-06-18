package test.nauty;

import group.SSPermutationGroup;

import java.io.IOException;
import java.util.List;

import model.Graph;
import nauty.DreadnautLink;

import org.junit.Test;

public class DreadnautLinkTest {
    
    @Test
    public void ioUncoloredTest() throws IOException, InterruptedException {
        Graph graph = new Graph("0:1,0:3,1:2,2:3");
        List<String> outputStrings = DreadnautLink.getOutput(graph, false);
        for (String outputString : outputStrings) {
            System.out.println(outputString);
        }
    }
    
    @Test
    public void uncolored4CycleTest() throws IOException, InterruptedException {
        Graph graph = new Graph("0:1,0:3,1:2,2:3");
        SSPermutationGroup group = DreadnautLink.getAutGroup(graph, false);
        System.out.println("group size = " + group.all().size());
    }
    
    @Test
    public void colored4CycleTest() throws IOException, InterruptedException {
        Graph graph = new Graph("0:1,0:3,1:2,2:3");
        graph.setColors(0, 1, 0, 1);
        SSPermutationGroup group = DreadnautLink.getAutGroup(graph, true);
        System.out.println("group size = " + group.all().size());
    }
    
    @Test
    public void uncolored6CycleTest() throws IOException, InterruptedException {
        Graph graph = new Graph("0:1,0:5,1:2,2:3,3:4,4:5");
        SSPermutationGroup group = DreadnautLink.getAutGroup(graph, false);
        System.out.println("group size = " + group.all().size());
    }
    
    @Test
    public void colored6CycleTest() throws IOException, InterruptedException {
        Graph graph = new Graph("0:1,0:5,1:2,2:3,3:4,4:5");
        graph.setColors(0, 1, 0, 1, 0, 1);
        SSPermutationGroup group = DreadnautLink.getAutGroup(graph, true);
        System.out.println("group size = " + group.all().size());
    }
    
    @Test
    public void ioColoredTest() throws IOException, InterruptedException {
        Graph graph = new Graph("0:1,0:3,1:2,2:3");
        graph.setColors(0, 1, 0, 1);
        List<String> outputStrings = DreadnautLink.getOutput(graph, true);
        for (String outputString : outputStrings) {
            System.out.println(outputString);
        }
    }

}
