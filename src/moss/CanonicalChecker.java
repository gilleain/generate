package moss;

import java.util.Arrays;

import moss.ColoredGraph.Edge;
import moss.ColoredGraph.Vertex;

public class CanonicalChecker {

    public boolean isCanonical(ColoredGraph graph) {
        return isCanonical(graph.getWord(), graph);
    }
    
    public boolean isCanonical(int[] w, ColoredGraph graph) {
        for (Vertex v : graph.vertices) v.visited = false;
        for (Edge e : graph.edges) e.visited = false;
        Vertex[] x = new Vertex[graph.vertices.size()];
        for (Vertex v : graph.vertices) {
            if (v.color < w[0]) {
                System.out.println("NC : 1 " + v.color);
                return false;
            }
            if (v.color > w[0]) {
                continue;
            }

            v.visited = true;
            x[0] = v;
            if (!recurse(w, graph, 1, x, 1, 0)) {
                return false;
            }
            v.visited = false;
        }
        return false;
    }

    private boolean recurse(int[] w, ColoredGraph graph, int k, Vertex[] x, int n, int i) {
        if (k >= w.length) return true;
        while (i < w[k]) {
            Vertex xi = x[i];
            for (Edge e : graph.edges) {
                if (e.isFrom(xi.index)) {
                    if (!e.visited) {
                        return false;
                    }
                }
            }
            i++;
        }
        for (Edge e : graph.edges) {
            if (!e.isFrom(i)) continue;
            if (e.visited)  continue;
            if (e.color < w[k + 1]) {
                return false;
            }
            if (e.color > w[k + 1]) {
                System.out.println("NC : 2 " + Arrays.toString(x));
                return true;
            }
            Vertex d = graph.vertices.get(e.getPartner(i));
            if (d.color < w[k + 2]) {
                return false;
            }
            if (d.color > w[k + 2]) {
                System.out.println("NC : 3 " + Arrays.toString(x));
                return true;
            }
            int j = (d.visited)? d.label : n;
            if (j < w[k + 3]) return false;
            if (j == w[k + 3]) {
                e.visited = true;
                if (!d.visited) {
                    d.label = j;
                    x[n] = d;
                }
                boolean r = recurse(w, graph, k + 4, x, n + 1, i);
                if (!d.visited) {
                    d.label = -1;
                }
                e.visited = false;
                if (!r) return false;
            }
        }
        System.out.println("NC : 4 " + Arrays.toString(x));
        return true;
    }
}