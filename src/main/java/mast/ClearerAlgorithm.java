 package mast;

import graph.model.Graph;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClearerAlgorithm extends AbstractAlgorithm {
    
    public BigInteger getSym(Graph tree) {
        BigInteger sym = BigInteger.valueOf(1);
        int[] labels = new int[tree.getVertexCount()];
        List<Integer> leaves = getLeaves(tree, labels);
        BitSet seen = new BitSet(tree.getVertexCount());
        List<Integer> parents = getParents(leaves, seen, tree);
        Map<Integer, Map<Integer, List<Integer>>> parentLeafMap = 
                new HashMap<Integer, Map<Integer, List<Integer>>>();
        int maxLabel = 2;
        System.out.println(maxLabel + "\t" + parents + "\t" + leaves + "\t" + Arrays.toString(labels));
        while (seen.cardinality() < tree.getVertexCount() - 2) {
            for (int parent : parents) {
                Map<Integer, List<Integer>> leafSets = getLeafSets(parent, leaves, tree, labels);
                for (int setLabel : leafSets.keySet()) {
                    List<Integer> leafSet = leafSets.get(setLabel);
                    BigInteger k = BigInteger.valueOf(leafSet.size());
                    sym = sym.multiply(factorial(k));
                }
                parentLeafMap.put(parent, leafSets);
            }
            Map<String, List<Integer>> parentLabelMap = getParentLabels(parentLeafMap);
            for (String parentLabelString : parentLabelMap.keySet()) {
                List<Integer> parentList = parentLabelMap.get(parentLabelString);
                for (int parent : parentList) {
                    labels[parent] = maxLabel;
                }
                maxLabel++;
            }
            setSeen(seen, leaves);
            leaves = parents;
            parents = getParents(leaves, seen, tree);
            System.out.println(maxLabel + "\t" + parents + "\t" + leaves + "\t" + Arrays.toString(labels));
        }
        if (parents.size() == 2 && (labels[parents.get(0)] == labels[parents.get(1)])) {
        	return sym.multiply(BigInteger.valueOf(2));
        } else {
        	return sym;
        }
    }
    
    private void setSeen(BitSet seen, List<Integer> values) {
        for (int value : values) {
            seen.set(value);
        }
    }

    private Map<String, List<Integer>> getParentLabels(
            Map<Integer, Map<Integer, List<Integer>>> parentLeafMap) {
        Map<String, List<Integer>> compositeLabelMap = new HashMap<String, List<Integer>>();
        for (int parent : parentLeafMap.keySet()) {
            List<Integer> compositeLabel = new ArrayList<Integer>();
            Map<Integer, List<Integer>> leafMap = parentLeafMap.get(parent);
            for (int label : leafMap.keySet()) {
                List<Integer> leaves = leafMap.get(label);
                for (int i = 0; i < leaves.size(); i++) {
                    compositeLabel.add(label);
                }
            }
            Collections.sort(compositeLabel);
            String compositeLabelString = "";
            for (int label : compositeLabel) {
                compositeLabelString += String.valueOf(label);
            }
            List<Integer> parentList;
            if (compositeLabelMap.containsKey(compositeLabelString)) {
                parentList = compositeLabelMap.get(compositeLabelString);
            } else {
                parentList = new ArrayList<Integer>();
                compositeLabelMap.put(compositeLabelString, parentList);
            }
            parentList.add(parent);
        }
       
        return compositeLabelMap;
    }

    private Map<Integer, List<Integer>> getLeafSets(
            int parent, List<Integer> leaves, Graph tree, int[] labels) {
        Map<Integer, List<Integer>> leafSets = new HashMap<Integer, List<Integer>>();
        for (int leaf : leaves) {
            if (tree.hasEdge(parent, leaf)) {
                int label = labels[leaf];
                List<Integer> leafSet;
                if (leafSets.containsKey(label)) {
                    leafSet = leafSets.get(label);
                } else {
                    leafSet = new ArrayList<Integer>();
                    leafSets.put(label, leafSet);
                }
                leafSet.add(leaf);
            }
        }
        return leafSets;
    }
    
    private List<Integer> getParents(List<Integer> leaves, BitSet seen, Graph tree) {
        List<Integer> parents = new ArrayList<Integer>();
        Map<Integer, List<Integer>> conn = tree.getConnectionTable();
        for (int leaf : leaves) {
            for (int neighbour : conn.get(leaf)) {
                if (!seen.get(neighbour) && !parents.contains(neighbour)) { // could be a set...
                    parents.add(neighbour);
                }
            }
        }
        return parents;
    }

    private List<Integer> getLeaves(Graph tree, int[] labels) {
        List<Integer> leaves = new ArrayList<Integer>();
        for (int i = 0; i < tree.getVertexCount(); i++) {
            if (tree.degree(i) == 1) {
                leaves.add(i);
                labels[i] = 1;
            }
        }
        return leaves;
    }
    
}
