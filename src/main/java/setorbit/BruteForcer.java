package setorbit;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import combinatorics.KSubsetLister;

/**
 * Simplistic brute-force set orbit lister, just lists all k-subsets and 
 * checks if they are in the orbit of the supplied subset. 
 * 
 * @author maclean
 *
 */
public class BruteForcer {
    
    public SetOrbit getInOrbit(List<Integer> s, PermutationGroup g) {
        SetOrbit orbits = new SetOrbit();
        KSubsetLister<Integer> lister = KSubsetLister.getIndexLister(s.size(), g.getSize());
        List<Permutation> all = g.all();
        for (Set<Integer> subset : lister) {
            for (Permutation p : all) {
                List<Integer> permuted = permute(p, subset);
                if (permuted.equals(s) && !orbits.contains(subset)) {
                    orbits.add(subset);
                }
            }
        }
        return orbits;
    }
    
    private List<Integer> permute(Permutation p, Set<Integer> subset) {
        List<Integer> permuted = new ArrayList<Integer>();
        for (int i : subset) {
            permuted.add(p.get(i));
        }
        Collections.sort(permuted);
        return permuted;
    }

}