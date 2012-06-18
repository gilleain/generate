package moss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ColoredGraph {
    
    public class Vertex {
        
        public int index;
        
        public int label;
        
        public boolean visited;
        
        public int color;
        
        public Vertex(int index, int color) {
            this.index = index;
            this.color = color;
        }

        public Vertex(Vertex vertex) {
            this.index = vertex.index;
            this.color = vertex.color;
        }
        
        public String toString() {
            return index + " " + label;
        }
        
    }
    
    public class Edge {
        
        public int start;
        
        public int end;
        
        public int color;
        
        public boolean visited;
        
        public Edge(int start, int end, int color) {
            this.start = start;
            this.end = end;
            this.color = color;
        }
        
        public Edge(Edge edge) {
            this.start = edge.start;
            this.end = edge.end;
            this.color = edge.color;
        }

        public boolean isBetween(int i, int j) {
            return ((i == start && j == end) || i == end && j == start);
        }

        public boolean isFrom(int i) {
            return i == start || i == end;
        }

        public int getPartner(int i) {
            return (i == start)? end : start;
        }
        
        public String toString() {
            return String.format("%s%s%s", start, color, end);
        }
    }

    public List<Vertex> vertices;
    
    public List<Edge> edges;
    
    public ColoredGraph() {
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
    }

    public ColoredGraph(ColoredGraph coloredMultigraph) {
        for (Vertex vertex : coloredMultigraph.vertices) {
            this.vertices.add(new Vertex(vertex));
        }
        for (Edge edge : coloredMultigraph.edges) {
            this.edges.add(new Edge(edge));
        }
    }
    
    public ColoredGraph(ColoredGraph coloredMultigraph, int i, int j) {
        for (Vertex vertex : coloredMultigraph.vertices) {
            this.vertices.add(new Vertex(vertex));
        }
        boolean multipliedEdge = false;
        for (Edge edge : coloredMultigraph.edges) {
            if (edge.isBetween(i, j)) {
                this.edges.add(new Edge(edge.start, edge.end, edge.color + 1));
                multipliedEdge = true;
            } else {
                this.edges.add(new Edge(edge));
            }
        }
        if (!multipliedEdge) {
            this.edges.add(new Edge(i, j, 1));
        }
    }
    
    public String toString(Edge edge) {
        if (edge.start < edge.end) {
            return String.format("%s%s%s", edge.start, eColorToString(edge.color), edge.end);
        } else {
            return String.format("%s%s%s", edge.end, eColorToString(edge.color), edge.start);
        }
    }

    private static String eColorToString(int color) {
        switch (color) {
            case 1: return "-";
            case 2: return "=";
            case 3: return "#";
            default: return "!";
        }
    }

    public boolean isCanonical() {
//        return isCanonical(getWord());
        return toString().equals(wordToString(getWord()));
//        String initialWord = wordToString(getWord());
//        for (int i = 1; i < vertices.size(); i++) {
//            String wordForI = wordToString(getWord(vertices.get(i)));
//            if (initialWord.compareTo(wordForI) > 0) {
//                return false;
//            }
//        }
//        return true;
    }
    
    public int[] getWord() {
        return getWord(vertices.get(0)); 
    }
        
    public int[] getWord(Vertex root) {
        for (Vertex vertex : vertices) { vertex.visited = false; }
        for (Edge edge : edges) { edge.visited = false; }
        int[] w = new int[2 + (edges.size() * 4)];
        w[0] = root.color;
        Queue<Vertex> queue = new LinkedList<Vertex>();
        queue.add(root);
        root.visited = true;
        int wordIndex = 1;
        int currentLabel = 0;
        int[] labels = new int[vertices.size()];
        Arrays.fill(labels, -1);
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            int rootIndex = v.index;
            for (Edge edge : edges) {
                int otherIndex;
                if (edge.start == rootIndex) {
                    otherIndex = edge.end;
                } else if (edge.end == rootIndex) {
                    otherIndex = edge.start;
                } else {
                    continue;
                }
                Vertex other = vertices.get(otherIndex);
                if (edge.visited) continue;
                if (labels[rootIndex] == -1) {
                    labels[rootIndex] = currentLabel++;
                }
                w[wordIndex++] = labels[rootIndex];
                w[wordIndex++] = edge.color;
                w[wordIndex++] = other.color;
                if (labels[otherIndex] == -1) {
                    labels[otherIndex] = currentLabel++;
                }
                w[wordIndex++] = labels[otherIndex];
                if (!other.visited) {
                    queue.add(other);
                }
                other.visited = true;
                edge.visited = true;
            }
        }
        return w;
    }
       
    public boolean isCanonical(int[] w) {
        for (Vertex v : vertices) v.visited = false;
        for (Edge e : edges) e.visited = false;
        Vertex[] x = new Vertex[vertices.size()];
        for (Vertex v : vertices) {
            if (v.color < w[0]) return false;
            if (v.color > w[0]) continue;
            
            v.visited = true;
            x[0] = v;
            if (!recurse(w, 1, x, 1, 0)) return false;
            v.visited = false;
        }
        return false;
    }
    
    private boolean recurse(int[] w, int k, Vertex[] x, int n, int i) {
        if (k >= w.length) return true;
        while (i < w[k]) {
            Vertex xi = x[i];
            for (Edge e : edges) {
                if (e.isFrom(xi.index)) {
                    if (!e.visited) return false;
                }
            }
            i++;
        }
        for (Edge e : edges) {
            if (!e.isFrom(i)) continue;
            if (e.visited)  continue;
            if (e.color < w[k + 1]) return false;
            if (e.color > w[k + 1]) return true;
            Vertex d = vertices.get(e.getPartner(i));
            if (d.color < w[k + 2]) return false;
            if (d.color > w[k + 2]) return true;
            int j = (d.visited)? d.label : n;
            if (j < w[k + 3]) return false;
            if (j == w[k + 3]) {
                e.visited = true;
                if (!d.visited) {
                    d.label = j;
                    x[n] = d;
                }
                boolean r = recurse(w, k + 4, x, n + 1, i);
                if (!d.visited) {
                    d.label = -1;
                }
                e.visited = false;
                if (!r) return false;
            }
        }
        return true;
    }

    public void addEdge(int i, int j) {
        edges.add(new Edge(i, j, 1));
    }
    
    public void addEdge(int i, int j, int c) {
        edges.add(new Edge(i, j, c));
    }

    public boolean isConnected(int i, int j) {
        for (Edge edge : edges) {
            if (edge.isBetween(i, j)) {
                return true;
            }
        }
        return false;
    }

    public boolean saturated(int i, int maxDegree) {
        int degree = 0;
        for (Edge edge : edges) {
            if (edge.isFrom(i)) {
                degree += edge.color;
            }
            if (degree > maxDegree) return false;
        }
        return true;
    }
    
    public String toString() {
        if (vertices.size() == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(vColorToString(vertices.get(0).color)).append(":");
            int edgeIndex = 0;
            for (Edge edge : edges) {
                if (edge.start < edge.end) {
                    int endVertexColor = vertices.get(edge.end).color;
                    sb.append(edge.start);
                    sb.append(eColorToString(edge.color));
                    sb.append(vColorToString(endVertexColor));
                    sb.append(edge.end);
                } else {
                    int endVertexColor = vertices.get(edge.start).color;
                    sb.append(edge.end);
                    sb.append(eColorToString(edge.color));
                    sb.append(endVertexColor);
                    sb.append(edge.start);
                }
                if (edgeIndex < edges.size() - 1) {
                    sb.append(",");
                }
                edgeIndex++;
            }
            return sb.toString();
        }
    }
    
    public static String wordToString(int[] word) {
        StringBuilder builder = new StringBuilder();
        builder.append(vColorToString(word[0])).append(':');
        for (int k = 1; k < word.length - 4; k += 4) {
            builder.append(word[k]);
            builder.append(eColorToString(word[k + 1]));
            builder.append(vColorToString(word[k + 2]));
            builder.append(word[k + 3]);
            if (k < word.length - 8) {
                builder.append(',');
            }
        }
        return builder.toString();
    }

    private static String vColorToString(int color) {
        switch (color) {
            case 1: return "C";
            case 2: return "O";
        }
        return "C";
    }

    public void addVertex(String string) {
        addVertex(stringToVColor(string));
    }
    
    public void addVertex(int color) {
        int index = vertices.size();
        vertices.add(new Vertex(index, color));
    }

    private int stringToVColor(String string) {
        if (string.equals("C")) {
            return 1;
        } else if (string.equals("O")) {
            return 2;
        } else {
            return 1;
        }
    }

}
