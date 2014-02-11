package mast;

import graph.model.Graph;

import java.math.BigInteger;
import java.util.TreeSet;
import java.util.Set;

public class OriginalAlgorithm extends AbstractAlgorithm {
    
    private class Vertex implements Comparable<Vertex> {
        public int treeIndex;
        public int id;
        public int degree;
        
        public Vertex(int treeIndex, int degree) {
            this.treeIndex = treeIndex;
            this.degree = degree;
        }
        
        public String toString() {
            return "" + treeIndex;
        }

        @Override
        public int compareTo(Vertex other) {
            return new Integer(treeIndex).compareTo(other.treeIndex);
        }
    }
    
    private class Edge implements Comparable<Edge> {
        private int a;
        private int b;
        
        public Edge(int a, int b) {
            this.a = a;
            this.b = b;
        }
        
        public boolean contains(Vertex v, Vertex w) {
            return (a == v.treeIndex && b == w.treeIndex) ||
                    (b == v.treeIndex && a == w.treeIndex);
        }
        
        public boolean contains(Vertex v) {
            return a == v.treeIndex || b == v.treeIndex;
        }
        
        public int other(Vertex v) {
            if (a == v.treeIndex) return b;
            else return a;
        }
        
        public int compareTo(Edge other) {
            return toString().compareTo(other.toString());
        }
        
        public String toString() {
            return a + ":" + b;
        }
        
    }
    
    private int maxID; 
    
    public OriginalAlgorithm() {
        this.maxID = 1;
    }
    
    public BigInteger getSym(Graph tree) {
        BigInteger SYM = BigInteger.valueOf(1);
        Set<Vertex> vertices = makeVertices(tree);
        Set<Edge> edges = makeEdges(tree);
        setID(1, vertices);
        while (vertices.size() > 2) {
            Set<Vertex> leaves = getLeaves(vertices);
            Set<Vertex> parents = getParents(edges, leaves, vertices);
            for (Vertex parent : parents) {
                Set<Vertex> neighbours = getNeighbours(edges, leaves, parent);
                while (neighbours.size() > 0) {
                    Set<Vertex> brethren = getBrethren(neighbours);
                    int k = brethren.size();
                    SYM = SYM.multiply(factorial(BigInteger.valueOf(k)));
                    neighbours.removeAll(brethren);
                }
            }
            
            while (parents.size() > 0) {
                Set<Vertex> tmp = getIso(parents);
                setID(uniqueID(), tmp);
                parents.removeAll(tmp);
            }
            vertices.removeAll(leaves);
            edges = prune(edges, vertices, leaves);
        }
        return SYM;
    }
    
    private int uniqueID() {
        this.maxID++;
        return maxID;
    }
    
    private Set<Edge> prune(Set<Edge> edges, Set<Vertex> all, Set<Vertex> leaves) {
        Set<Edge> pruned = new TreeSet<Edge>();
        for (Edge e : edges) {
            for (Vertex v : leaves) {
                if (e.contains(v)) {
                    int other = e.other(v);
                    for (Vertex o : all) {
                        if (o.treeIndex == other) o.degree--;
                    }
                } else {
                    pruned.add(e);
                }
            }
        }
        return pruned;
    }
    
    private Set<Vertex> getIso(Set<Vertex> vertices) {
        Vertex first = vertices.iterator().next();
        Set<Vertex> brethren = new TreeSet<Vertex>();
        for (Vertex v : vertices) {
            if (v.id == first.id) {
                brethren.add(v);
            }
        }
        return brethren;
    }
    
    private Set<Vertex> getBrethren(Set<Vertex> neighbours) {
        Vertex first = neighbours.iterator().next();
        Set<Vertex> brethren = new TreeSet<Vertex>();
        for (Vertex v : neighbours) {
            if (v.id == first.id) {
                brethren.add(v);
            }
        }
        return brethren;
    }
    
    
    private Set<Vertex> getNeighbours(Set<Edge> edges, Set<Vertex> leaves, Vertex parent) {
        Set<Vertex> neighbours = new TreeSet<Vertex>();
        for (Vertex leaf : leaves) {
            for (Edge edge : edges) {
                if (edge.contains(leaf, parent)) {
                    neighbours.add(leaf);
                }
            }
        }
        return neighbours;
    }
    
    
    private Set<Vertex> getParents(Set<Edge> edges, Set<Vertex> leaves, Set<Vertex> all) {
        Set<Vertex> parents = new TreeSet<Vertex>();
        for (Vertex leaf : leaves) {
            for (Vertex v : all) {
                for (Edge edge : edges) {
                    if (edge.contains(leaf, v)) {
                        parents.add(v);
                    }
                }
            }
        }
        return parents;
    }
    
    
    public void setID(int id, Set<Vertex> vertices) {
        for (Vertex v : vertices) {
            v.id = id;
        }
    }
    
    private Set<Vertex> makeVertices(Graph tree) {
        Set<Vertex> vertices = new TreeSet<Vertex>();
        for (int i = 0; i < tree.vsize(); i++) {
            vertices.add(new Vertex(i, tree.degree(i)));
        }
        return vertices;
    }
    
    private Set<Vertex> getLeaves(Set<Vertex> vertices) {
        Set<Vertex> leaves = new TreeSet<Vertex>();
        for (Vertex v : vertices) {
            if (v.degree == 1) {
                leaves.add(v);
            }
        }
        return leaves;
    }
    
    private Set<Edge> makeEdges(Graph tree) {
        Set<Edge> edges = new TreeSet<Edge>();
        for (graph.model.Edge graphEdge : tree.edges) {
            edges.add(new Edge(graphEdge.a, graphEdge.b));
        }
        return edges;
    }
}
