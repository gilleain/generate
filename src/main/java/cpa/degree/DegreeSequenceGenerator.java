package cpa.degree;

import graph.model.IntGraph;
import cpa.Augmentation;
import cpa.Augmentor;
import cpa.handler.GenerationHandler;

public class DegreeSequenceGenerator {
    
    private final GenerationHandler handler;
    
    private int[] degreeSequence;
    
    public DegreeSequenceGenerator(GenerationHandler handler, int[] degreeSequence) {
        this.handler = handler;
        this.degreeSequence = degreeSequence;
    }
    
    public void generate() {
        augment(new DegreeSequenceAugmentation(degreeSequence));
    }
    
    public void augment(Augmentation<ResidualDegreeGraphPair> parent) {
        IntGraph graph = parent.getAugmentedObject().getGraph();
        int[] residuals = parent.getAugmentedObject().getResiduals();
//        System.out.println("at " + graph + " " + java.util.Arrays.toString(residuals));
        if (isFinished(residuals)) {
            if (graph.isConnected()) {
                handler.handle(graph);
            }
        } else {
            Augmentor<ResidualDegreeGraphPair> augmentor = new DegreeSequenceAugmentor(residuals);
            for (Augmentation<ResidualDegreeGraphPair> augmentation : augmentor.augment(parent)) {
                if (augmentation.isCanonical()) {
                    augment(augmentation);
                }
            }
        }
    }
    
    private boolean isFinished(int[] residuals) {
        for (int r : residuals) {
            if (r != 0) return false;
        }
        return true;
    }

}
