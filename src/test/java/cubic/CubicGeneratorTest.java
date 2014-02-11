package test.cubic;

import generate.handler.IsomorphCountingHandler;
import graph.model.Graph;

import java.util.List;

import org.junit.Test;

import cubic.CubicGenerator;

public class CubicGeneratorTest {
    
    @Test
    public void k4ExtensionTest() {
        Graph prime = new Graph("0:1,0:2,0:3,1:2,1:3,2:3");
        CubicGenerator generator = new CubicGenerator();
        generator.generate(prime, 6);
    }
    
    @Test
    public void prismExtensionTest() {
        Graph prism = new Graph("0:3,0:4,0:5,1:2,1:3,1:4,2:3,2:5,4:5");
        IsomorphCountingHandler handler = new IsomorphCountingHandler();
        CubicGenerator generator = new CubicGenerator(handler);
        generator.generate(prism, 6);
        List<Graph> unique = handler.getNonIsomorphicGraphs();
        System.out.println(handler.getTotalGraphCount() + "\t" + unique.size());
        for (Graph g : unique) {
            System.out.println(g);
        }
    }
    
    @Test
    public void bothPrismExtensionTest() {
        Graph prism        = new Graph("0:3,0:4,0:5,1:2,1:3,1:4,2:3,2:5,4:5");
        Graph twistedPrism = new Graph("0:2,0:3,0:4,1:2,1:3,1:4,2:5,3:5,4:5");
        
        IsomorphCountingHandler handler = new IsomorphCountingHandler();
        CubicGenerator generator = new CubicGenerator(handler);
        generator.generate(prism, 6);
        generator.generate(twistedPrism, 6);
        List<Graph> unique = handler.getNonIsomorphicGraphs();
        System.out.println(handler.getTotalGraphCount() + "\t" + unique.size());
        for (Graph g : unique) {
            System.out.println(g);
        }
    }

    
}
