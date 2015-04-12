package fusanes;

import filter.MaxDegreeFilter;
import graph.model.IntGraph;
import graph.tree.TreeCertificateMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tree.WROMTreeGenerator;

public class FusaneGenerator {
    
    public static List<IntGraph> generate(int n) {
        MaxDegreeFilter filter = new MaxDegreeFilter(3);
        List<String> certs = new ArrayList<String>();
        Map<String, IntGraph> fusanes = new HashMap<String, IntGraph>();
        for (IntGraph tree : WROMTreeGenerator.generate(n)) {
            // certificate destroys graph, so we need to clone it
            String cert = TreeCertificateMaker.treeToCertificate(new IntGraph(tree));  
            if (certs.contains(cert)) {
                continue;
            } else {
                certs.add(cert);
            }
            if (filter.filter(tree)) {
                for (FusaneInnerDual dual : SimpleFusaneLabeler.label(tree)) {
                    IntGraph g = FusaneDualExpander.expand(dual);
                    String es = g.getSortedEdgeString();
                    fusanes.put(es, g);
                }
            }
        }
        return new ArrayList<IntGraph>(fusanes.values());
    }
    
}
