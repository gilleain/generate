package setorbit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Wrapper class for orbits of sets.
 * 
 * @author maclean
 *
 */
public class SetOrbit implements Iterable<Set<Integer>>{
    
    private List<Set<Integer>> orbit;
    
    public SetOrbit() {
        this.orbit = new ArrayList<Set<Integer>>();
    }
    
    public boolean contains(Set<Integer> set) {
        return orbit.contains(set);
    }
    
    
    public void add(Set<Integer> set) {
        orbit.add(set);
    }

    @Override
    public Iterator<Set<Integer>> iterator() {
        return orbit.iterator();
    }

}