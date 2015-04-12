package degreeseq;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseHHGenerator {
    
    protected boolean isGraphicalWithConstraint(int[] degSeq, int i, List<Integer> X) {
        int d = degSeq[i];
        
        // get the leftmost adjacency set
        List<Integer> L = getL(i, d, degSeq.length, X);
        
        // this could be done in one step, rather than iterating twice through
        // degseq
        return isGraphical(reduce(degSeq, i, L));
    }
    
    protected List<Integer> getL(int i, int d, int n, List<Integer> X) {
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
    
    protected int[] reduce(int[] degSeq, IntGraph g) {
        int[] reducedSeq = new int[degSeq.length];
        for (int k = 0; k < degSeq.length; k++) {
            reducedSeq[k] = Math.max(degSeq[k] - g.degree(k), 0);
        }
        return reducedSeq;
    }
    
    protected int[] reduce(int[] degSeq, int i, List<Integer> adjSet) {
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
    
    protected int[] reduce(int[] degSeq, int i, int j) {
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
    
    protected int[] revSort(int[] arr) {
        Arrays.sort(arr);
        int[] rev = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            rev[arr.length - i - 1] = arr[i];
        }
        return rev;
    }
    
    protected boolean isGraphical(int[] degSeq) {
        return HakimiHavelGenerator.isGraphical(degSeq);
    }
}
