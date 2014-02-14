package moss;

import java.util.Arrays;

import moss.CanonicalChecker;
import moss.ColoredGraph;
import moss.ColoredGraphPermutor;

import org.junit.Test;

public class MossTest {
    
    public ColoredGraph makeGraph() {
        ColoredGraph graph = new ColoredGraph();
        for (int i = 0; i < 4; i++) {
            graph.addVertex("C");
        }
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 2);
        graph.addEdge(0, 3, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        return graph;
    }
    
    public ColoredGraph make4SquareGraph() {
        ColoredGraph graph = new ColoredGraph();
        for (int i = 0; i < 4; i++) {
            graph.addVertex("C");
        }
        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 1);
        return graph;
    }
    
    @Test
    public void writeTest() {
        System.out.println(makeGraph());
    }
    
    @Test
    public void lexTest() {
        ColoredGraph graph = new ColoredGraph();
        for (int i = 0; i < 4; i++) {
            graph.addVertex("C");
        }
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 1);
        graph.addEdge(0, 3, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 1);
        
        ColoredGraph graph2 = new ColoredGraph();
        for (int i = 0; i < 4; i++) {
            graph2.addVertex("C");
        }
        graph2.addEdge(0, 1, 1);
        graph2.addEdge(0, 2, 2);
        graph2.addEdge(0, 3, 1);
        graph2.addEdge(1, 2, 1);
        graph2.addEdge(2, 3, 1);
        
        String graphString = graph.toString();
        String graph2String = graph2.toString();
        
        System.out.println(graphString + "\n" + graph2String);
        System.out.println(graphString.compareTo(graph2String) > 0);
    }
    
    @Test
    public void wordTest() {
        System.out.println(Arrays.toString(makeGraph().getWord()));
        System.out.println(ColoredGraph.wordToString((makeGraph().getWord())));
    }
    
    @Test
    public void isCanonical() {
        ColoredGraph graph = makeGraph();
//        ColoredGraph graph = make4SquareGraph();
        
        CanonicalChecker checker = new CanonicalChecker();
        boolean isCanon = checker.isCanonical(graph);
        String initial = ColoredGraph.wordToString(graph.getWord());
        System.out.println(graph + "\t" + initial + "\t" + isCanon);
        ColoredGraphPermutor permutor = new ColoredGraphPermutor(graph);
        while (permutor.hasNext()) {
            ColoredGraph nextGraph = permutor.next();
            isCanon = checker.isCanonical(nextGraph); 
            String wordString = ColoredGraph.wordToString(nextGraph.getWord());
            System.out.println(nextGraph + "\t" + wordString + "\t" + isCanon);
            if (initial.compareTo(wordString) < 0) {
                initial = wordString;
            }
        }
        System.out.println(initial);
    }

}
