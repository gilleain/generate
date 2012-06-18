package filter;

import java.util.ArrayList;
import java.util.List;

import model.Graph;
import model.GraphSignature;

public class SignatureCanonicalFilter implements Filter {
    
    private List<String> certificates;
    
    public SignatureCanonicalFilter() {
        this.certificates = new ArrayList<String>();
    }
    
    public boolean filter(Graph graph) {
        GraphSignature signature = new GraphSignature(graph);
        String cert = signature.toCanonicalString();
//        System.out.println(graph.getSortedEdgeStringWithEdgeOrder() + "\t" + cert);
        if (certificates.contains(cert)) {
            return false;
        } else {
            certificates.add(cert);
            return true;
        }
    }
    
}
