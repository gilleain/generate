package tree;

import graph.model.IntGraph;
import graph.tree.TreeCertificateMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class CompareGenerators {
    
    public void compareWROMAndFiltering(int n) {
        List<IntGraph> wromTrees = WROMTreeGenerator.generate(n);
        List<IntGraph> filteredTrees = UnlabelledTreeGenerator.generate(n);
        if (wromTrees.size() < filteredTrees.size()) {
            System.out.println("WROM smaller");
            diff(wromTrees, filteredTrees);
        } else if (wromTrees.size() > filteredTrees.size()) {
            System.out.println("filtered smaller");
            diff(filteredTrees, wromTrees);
        } else {
            System.out.println("Same number");
        }
    }
    
    public void diff(List<IntGraph> smallerList, List<IntGraph> largerList) {
        Map<String, IntGraph> largerCerts = new HashMap<String, IntGraph>();
        for (IntGraph lTree : largerList) {
            // some bug here where the cert can't be generated on the same graph twice!
            String cert = TreeCertificateMaker.treeToCertificate(new IntGraph(lTree));
            largerCerts.put(cert, lTree);
        }
        List<String> remainingCerts = new ArrayList<String>(largerCerts.keySet());
        for (IntGraph sTree : smallerList) {
            String cert = TreeCertificateMaker.treeToCertificate(sTree);
            if (largerCerts.containsKey(cert)) {
                remainingCerts.remove(cert);
            } 
        }
        for (String remainingCert : remainingCerts) {
            System.out.println(largerCerts.get(remainingCert));
        }
    }
    
    @Test
    public void wrom_vs_FilteringOn4() {
        compareWROMAndFiltering(4);
    }
    
    @Test
    public void wrom_vs_FilteringOn5() {
        compareWROMAndFiltering(5);
    }
    
    @Test
    public void wrom_vs_FilteringOn6() {
        compareWROMAndFiltering(6);
    }
    
    @Test
    public void wrom_vs_FilteringOn7() {
        compareWROMAndFiltering(7);
    }
    
}
