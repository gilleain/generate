package degreeseq;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;
import graph.model.Graph;

public class KiralyHHGenerator {
    
    private GeneratorHandler handler;
    
    public KiralyHHGenerator() {
        this(new SystemOutHandler());
    }
    
    public KiralyHHGenerator(GeneratorHandler handler) {
        this.handler = handler;
    }
    
    public void generate(int[] degreeSequence) {
        generate(degreeSequence, degreeSequence.length, new Graph());
        handler.finish();
    }
    
    public void generate(int[] degreeSequence, final int n, Graph g) {
//        System.out.println("recursing " + n + " " + Arrays.toString(degreeSequence) + " " + g);
        int dN = degreeSequence[n - 1];
        KiralyTree tree = new KiralyTree(n - 1, new KiralyTreeListener() {

            @Override
            public void handle(int[] degreeSequence, Graph g) {
                generate(degreeSequence, n - 1, new Graph(g));
            }
            
        });
        if (n == 3) {
            Graph h = tree.connectRemaining(degreeSequence, g);
            if (h != null) {
                handler.handle(g, h);
            }
        } else {
            tree.traverse(degreeSequence, g, n, dN);
        }
    }
    
}
