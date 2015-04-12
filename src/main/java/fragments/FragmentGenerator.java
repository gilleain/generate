package fragments;

import graph.model.Graph;
import graph.model.GraphFactory;
import graph.model.IntGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tree.WROMTreeGenerator;

public class FragmentGenerator {
    
    public void generateFromMetaTree(MetaTree metaTree) {
        
    }
    
    public void addTreesToCycle(DegreeSequence degSeq, Map<Integer, Integer> cyclePositionDegreeMap) {
        // make the initial cycle
        int cycleSize = cyclePositionDegreeMap.size();
        Graph graph = GraphFactory.cycle(cycleSize);
        
        // make the difference map for the attachment points
        Map<Integer, Integer> cycleAttachmentPoints = new HashMap<Integer, Integer>();
        for (int position : cyclePositionDegreeMap.keySet()) {
            int degree = cyclePositionDegreeMap.get(position);
            int diff = degSeq.difference(position, degree);
            cycleAttachmentPoints.put(position, diff);
        }
        
        // turn the remaining degree elements into forests
        int treeTotalSize = degSeq.size() - cycleSize;
        for (int treeSize = 1; treeSize <= treeTotalSize; treeSize++) {
            List<IntGraph> trees = WROMTreeGenerator.generate(treeSize);
        }
        
    }
    
}
