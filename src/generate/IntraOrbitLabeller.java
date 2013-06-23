package generate;

import graph.model.Graph;
import group.Partition;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

public class IntraOrbitLabeller {
    
    public static boolean isCanonical(Graph g, Partition orbits) {
        String initialLabelling = g.getSortedEdgeString();
        PermutationGroup permGroup = colorEquivalenceClasses(orbits);
        for (Permutation p : permGroup.all()) {
            String permutedLabelling = g.getPermutedEdgeString(p.getValues());
            if (permutedLabelling.compareTo(initialLabelling) < 0) {
                return false;
            }
        }
        return true;
    }
    
    public static PermutationGroup colorEquivalenceClasses(Partition partition) {
        int n = partition.numberOfElements();
        List<Permutation> generators = new ArrayList<Permutation>();
        for (int cellIndex = 0; cellIndex < partition.size(); cellIndex++) {
            SortedSet<Integer> cell = partition.getCell(cellIndex);
            Permutation generatorA = cellToPermutationA(cell, n);
            generators.add(generatorA);
            if (cell.size() > 2) {
                Permutation generatorB = cellToPermutationB(cell, n);
                generators.add(generatorB);
            }
        }
        return new PermutationGroup(n, generators);
    }
    
    private static Permutation cellToPermutationA(SortedSet<Integer> cell, int n) {
        if (cell.size() < 2) {
            return new Permutation(n);
        } else {
            int[] p = new int[n];
            for (int i = 0; i < n; i++) { p[i] = i; }
            Iterator<Integer> itr = cell.iterator(); 
            int first = itr.next();
            int second = itr.next();
            p[second] = first;
            p[first] = second;
            return new Permutation(p);
        }
    }
    
    private static Permutation cellToPermutationB(SortedSet<Integer> cell, int n) {
        if (cell.size() < 2) {
            return new Permutation(n);
        } else {
            int[] p = new int[n];
            for (int i = 0; i < n; i++) { p[i] = i; }
            int[] c = new int[cell.size()];
            int i = 0;
            for (int item : cell) {
                c[i] = item;
                i++;
            }
            p[c[0]] = c[c.length - 1];
            for (int j = 1; j < c.length; j++) {
                p[c[j]] = c[j - 1];
            }
            return new Permutation(p);
        }
    }

}
