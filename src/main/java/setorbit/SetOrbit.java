package setorbit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Wrapper class for orbits of sets.
 * 
 * @author maclean
 *
 */
public class SetOrbit implements Iterable<List<Integer>>{
    
    private List<List<Integer>> orbit;
    
    public SetOrbit() {
        this.orbit = new ArrayList<List<Integer>>();
    }
    
    public boolean contains(List<Integer> set) {
        return orbit.contains(set);
    }
    
    public boolean contains(Set<Integer> set) {
        // UGH this should not be necessary...
        List<Integer> setAsList = new ArrayList<Integer>();
        setAsList.addAll(set);
        return orbit.contains(setAsList);
    }
    
    public void add(List<Integer> set) {
        orbit.add(set);
    }

    @Override
    public Iterator<List<Integer>> iterator() {
        return orbit.iterator();
    }

}