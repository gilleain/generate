package degreeseq;

import graph.model.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HakimiHavelGenerator {
    
    public static Graph generate(int[] degSeq) {
        Graph g = new Graph();
        int n = degSeq.length;
        List<Integer> currentSeq = asList(degSeq);
        int i = 0;
        while (i < n) {
            currentSeq = reduce(currentSeq, i, g);
//            System.out.println(i + " ds = " + currentSeq + " g = " + g);
            if (currentSeq.isEmpty()) {
                return g;
            } else {
                i = getNext(i, g, degSeq);
            }
        }
        return g;
    }
    
    private static int getNext(int i, Graph g, int[] degSeq) {
        for (int v = 0; v < degSeq.length; v++) {
            if (degSeq[v] - g.degree(v) > 0) {
                return v;
            }
        }
        return i;
    }
    
    private static List<Integer> asList(int[] arr) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i : arr) {
            list.add(i);
        }
        return list;
    }
    
    private static List<Integer> reduce(List<Integer> degSeq, int v, Graph g) {
        int k = degSeq.get(0);
        int n = degSeq.size();
        List<Integer> redDegSeq = new ArrayList<Integer>();
        for (int i = 1; i < n; i++) {
            int dI = degSeq.get(i);
            if (i <= k) {
                g.makeEdge(v, v + i);
                if (dI > 1) {
                    redDegSeq.add(dI - 1);
                }
            } else {
                redDegSeq.add(dI);
            }
        }
        Collections.sort(redDegSeq);
        Collections.reverse(redDegSeq);
        return redDegSeq;
    }
    
    public static boolean isGraphical(int[] degSeq) {
        int sum = 0;
        int n = degSeq.length;
        for (int k = 1; k < n + 1; k++) {
            sum += degSeq[k - 1];
            int stubCount = k * (k - 1);
            int remainingDegree = calculateRemainingDegree(degSeq, k);
//            System.out.println(k + "\t" + sum + "\t" + stubCount + "\t" + remainingDegree);
            if (sum <= stubCount + remainingDegree) {
                continue;
            } else {
                return false;
            }
        }
        return (sum % 2 == 0);
    }
    
    private static int calculateRemainingDegree(int[] degSeq, int k) {
        int n = degSeq.length;
        int sum = 0;
        for (int i = k; i < n; i++) {
            sum += Math.min(k, degSeq[i - 1]);
        }
        return sum;
    }
    
}
