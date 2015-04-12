package cubic;

import graph.model.IntEdge;
import group.Permutation;

public class EdgePair implements Comparable<EdgePair> {
    public IntEdge f;
    public IntEdge s;
    
    public EdgePair(IntEdge f, IntEdge s) {
        this.f = f;
        this.s = s;
    }
    
    public String toString() {
        return f + "|" + s;
    }

    public EdgePair permute(Permutation pi) {
        int pfA = pi.get(f.a);
        int pfB = pi.get(f.b);
        IntEdge pF = (pfA < pfB)? new IntEdge(pfA, pfB) : new IntEdge(pfB, pfA);
        int psA = pi.get(s.a);
        int psB = pi.get(s.b);
        IntEdge pS = (psA < psB)? new IntEdge(psA, psB) : new IntEdge(psB, psA);
        return new EdgePair(pF, pS);
    }

    @Override
    public int compareTo(EdgePair other) {
        int cF = f.compareTo(other.f);
        int cS = s.compareTo(other.s);
        if (cF < cS) {
            return 1;
        } else if (cF > cS) {
            return -1;
        } else {
            return 0;
        }
    }
    
    public boolean equals(Object o) {
        if (o instanceof EdgePair) {
            EdgePair other = (EdgePair) o;
            return this.f.equals(other.f) && this.s.equals(other.s);
        } else {
            return false;
        }
    }
}
