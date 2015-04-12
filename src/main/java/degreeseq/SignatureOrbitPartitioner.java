package degreeseq;

import graph.model.GraphSignature;
import graph.model.IntGraph;
import group.Partition;

import java.util.List;
import java.util.SortedSet;

import signature.SymmetryClass;

public class SignatureOrbitPartitioner implements OrbitPartitioner {
    
    @Override
    public Partition getOrbitPartition(IntGraph g, int[] degreeSequence) {
        GraphSignature graphSignature = new GraphSignature(g);
        List<SymmetryClass> symCl = graphSignature.getSymmetryClasses();
        Partition p = new Partition();
        int prevClassIndex = findClass(0, symCl);
        p.addSingletonCell(0);
        SortedSet<Integer> currentCell = p.getCell(0);
        for (int i = 1; i < degreeSequence.length; i++) {
            int classIndex = findClass(i, symCl); 
            if (degreeSequence[i] == degreeSequence[i - 1]) {
                if (classIndex == prevClassIndex) {
                    currentCell.add(i);
                } else {
                    // new cell
                    p.addSingletonCell(i);
                    currentCell = p.getCell(p.size() - 1);
                }
            } else {
                // new cell
                p.addSingletonCell(i);
                currentCell = p.getCell(p.size() - 1);
            }
            prevClassIndex = classIndex;
        }
        return p;
    }
    
    private int findClass(int i, List<SymmetryClass> symCls) {
        for (int index = 0; index < symCls.size(); index++) {
            SymmetryClass symCl = symCls.get(index);
            for (int j : symCl) {
                if (i == j) {
                    return index;
                }
            }
        }
        return -1;
    }
    
}
