package cpa.degree;

import graph.model.IntGraph;
import cpa.Augmentation;
import cpa.Augmentor;
import cpa.handler.AugmentationHandler;
import cpa.handler.GenerationHandler;

public class DegreeSequenceGenerator {
    
    private final GenerationHandler handler;
    
    private AugmentationHandler<ResidualDegreeGraphPair> partialHandler;
    
    private int[] degreeSequence;
    
    public DegreeSequenceGenerator(GenerationHandler handler, int[] degreeSequence) {
        this(handler, null, degreeSequence);
    }
    
    public DegreeSequenceGenerator(
            GenerationHandler handler, 
            AugmentationHandler<ResidualDegreeGraphPair> augmentationHandler, 
            int[] degreeSequence) {
        this.handler = handler;
        this.partialHandler = augmentationHandler;
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
                    if (partialHandler != null) {
                        partialHandler.handleCanonical(augmentation);
                    }
                    augment(augmentation);
                } else {
                    if (partialHandler != null) {
                        partialHandler.handleNonCanonical(augmentation);
                    }
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
