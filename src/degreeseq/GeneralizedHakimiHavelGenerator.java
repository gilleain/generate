package degreeseq;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.ArrayList;
import java.util.List;

import model.Graph;

public class GeneralizedHakimiHavelGenerator extends BaseHHGenerator {
    
    private GeneratorHandler handler;
    
    public GeneralizedHakimiHavelGenerator() {
        this(new SystemOutHandler());
    }
    
    public GeneralizedHakimiHavelGenerator(GeneratorHandler handler) {
        this.handler = handler;
    }
    
    public void connectAll(int[] degSeq) {
        List<Graph> initialList = new ArrayList<Graph>();
        Graph initialGraph = new Graph(); 
        initialList.add(initialGraph);
        connectAll(degSeq, initialGraph, initialList);
        handler.finish();
    }
    
    public void connectAll(int[] degSeq, Graph parent, List<Graph> gParents) {
        for (Graph g : gParents) {
            int[] reducedDegSeq = reduce(degSeq, g);
            int i = nextStart(degSeq, g);
            if (i == -1) {
                handler.handle(parent, g);
                return;
            } else {
//                System.out.println("i " + i + "\t" + g);
                List<Graph> gChildren = new ArrayList<Graph>();
                for (int j = i; j < degSeq.length; j++) {
                    List<Integer> X = new ArrayList<Integer>();
                    List<Graph> gList = new ArrayList<Graph>();
                    connect(g, i, j, X, reducedDegSeq, gList);
                    gChildren.addAll(gList);
                }
                connectAll(degSeq, g, gChildren);
            }
        }
    }
    
    public int nextStart(int[] degSeq, Graph g) {
        for (int i = 0; i < degSeq.length; i++) {
            if (degSeq[i] - g.degree(i) > 0) {
                return i;
            }
        }
        return -1;
    }
    
    public void connect(Graph g, int i, int j, List<Integer> X, int[] degSeq, List<Graph> children) {
        if (degSeq[i] == 0) {
            children.add(g);
        } else {
            for (int j1 = j + 1; j1 < degSeq.length; j1++) {
                X.add(j1);
                int[] reducedDegSeq = reduce(degSeq, i, j1);
                if (isGraphicalWithConstraint(reducedDegSeq, i, X)) {
                    connect(g.makeNew(i, j1), i, j1, X, reducedDegSeq, children);
                }
            }
        }
    }
    
   
}
