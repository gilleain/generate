package moss;

import java.util.ArrayList;
import java.util.List;

public class ColoredGraphGenerator {
    
    public List<ColoredGraph> graphs;
    
    public int maxLength;
    
    public int maxDegree;
    
    public ColoredGraphGenerator(int maxLength, int maxDegree) {
        this.maxLength = maxLength;
        this.graphs = new ArrayList<ColoredGraph>();
        this.maxDegree = maxDegree;
    }
    
    public void generate() {
        ColoredGraph graph = new ColoredGraph();
        generate(graph);
    }

    public void generate(ColoredGraph graph) {
        int vertexCount = graph.vertices.size();
        if (vertexCount >= maxLength) return;
        
        if (graph.isCanonical()) {
            System.out.println("CANON\t" + graph);
            if (vertexCount == maxLength - 1) {
                graphs.add(graph);
            }
        } else {
            return;
        }
        
        int n = Math.max(2, maxLength);
        for (int i = 0; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                if (canAddBond(i, j, graph)) {
                    generate(new ColoredGraph(graph, i, j));
                }
            }
        }
    }
    
    public boolean canAddBond(int i, int j, ColoredGraph g) {
        return i != j && !g.saturated(i, maxDegree) && !g.saturated(j, maxDegree);
    }

}
