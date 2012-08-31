package cubic;

import group.Permutation;
import model.Edge;

public class EdgePair implements Comparable<EdgePair> {
    public Edge f;
    public Edge s;
    
    public EdgePair(Edge f, Edge s) {
        this.f = f;
        this.s = s;
    }
    
    public String toString() {
        return f + "|" + s;
    }

    public EdgePair permute(Permutation pi) {
        int pfA = pi.get(f.a);
        int pfB = pi.get(f.b);
        Edge pF = (pfA < pfB)? new Edge(pfA, pfB) : new Edge(pfB, pfA);
        int psA = pi.get(s.a);
        int psB = pi.get(s.b);
        Edge pS = (psA < psB)? new Edge(psA, psB) : new Edge(psB, psA);
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
