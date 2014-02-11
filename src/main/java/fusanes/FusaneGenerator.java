package fusanes;

import filter.MaxDegreeFilter;
import graph.model.Graph;
import graph.tree.TreeCertificateMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tree.WROMTreeGenerator;

public class FusaneGenerator {
    
    public static List<Graph> generate(int n) {
        MaxDegreeFilter filter = new MaxDegreeFilter(3);
        List<String> certs = new ArrayList<String>();
        Map<String, Graph> fusanes = new HashMap<String, Graph>();
        for (Graph tree : WROMTreeGenerator.generate(n)) {
            // certificate destroys graph, so we need to clone it
            String cert = TreeCertificateMaker.treeToCertificate(new Graph(tree));  
            if (certs.contains(cert)) {
                continue;
            } else {
                certs.add(cert);
            }
            if (filter.filter(tree)) {
                for (FusaneInnerDual dual : SimpleFusaneLabeler.label(tree)) {
                    Graph g = FusaneDualExpander.expand(dual);
                    String es = g.getSortedEdgeString();
                    fusanes.put(es, g);
                }
            }
        }
        return new ArrayList<Graph>(fusanes.values());
    }
    
}
