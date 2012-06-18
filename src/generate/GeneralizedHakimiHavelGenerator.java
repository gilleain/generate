package generate;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Graph;

public class GeneralizedHakimiHavelGenerator {
    
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
    
    private boolean isGraphicalWithConstraint(int[] degSeq, int i, List<Integer> X) {
        int d = degSeq[i];
        
        // get the leftmost adjacency set
        List<Integer> L = getL(i, d, degSeq.length, X);
        
        // this could be done in one step, rather than iterating twice through degseq
        return isGraphical(reduce(degSeq, i, L));
    }
    
    private List<Integer> getL(int i, int d, int n, List<Integer> X) {
        int count = 0;
        List<Integer> L = new ArrayList<Integer>();
        for (int index = 0; index < n; index++) {
            if (count == d) {
                break;
            } else if (index == i || X.contains(index)) {
                continue;
            } else {
                L.add(index);
                count++;
            }
        }
        return L;
    }
    
    private int[] reduce(int[] degSeq, Graph g) {
        int[] reducedSeq = new int[degSeq.length];
        for (int k = 0; k < degSeq.length; k++) {
            reducedSeq[k] = Math.max(degSeq[k] - g.degree(k), 0);
        }
        return reducedSeq;
    }
    
    private int[] reduce(int[] degSeq, int i, List<Integer> adjSet) {
        int[] reducedSeq = new int[degSeq.length];
        for (int k = 0; k < degSeq.length; k++) {
            if (adjSet.contains(k)) {
                reducedSeq[k] = degSeq[k] - 1;
            } else if (k == i) {
                reducedSeq[k] = 0;
            } else {
                reducedSeq[k] = degSeq[k];
            }
        }
        return revSort(reducedSeq);
    }
    
    private int[] reduce(int[] degSeq, int i, int j) {
        int[] reducedSeq = new int[degSeq.length];
        for (int x = 0; x < degSeq.length; x++) {
            if (x == i || x == j) {
                reducedSeq[x] = degSeq[x] - 1;
            } else {
                reducedSeq[x] = degSeq[x];
            }
        }
        return reducedSeq;
    }
    
    private int[] revSort(int[] arr) {
        Arrays.sort(arr);
        int[] rev = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            rev[arr.length - i - 1] = arr[i];
        }
        return rev;
    }
    
    private boolean isGraphical(int[] degSeq) {
        return HakimiHavelGenerator.isGraphical(degSeq);
    }
}
