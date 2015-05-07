package cpa.degree;

import graph.model.IntGraph;
import cpa.Augmentation;
import cpa.handler.GenerationHandler;

public class DegreeSequenceGenerator {
    
    private final GenerationHandler handler;
    
    private int[] degreeSequence;
    
    public DegreeSequenceGenerator(GenerationHandler handler, int[] degreeSequence) {
        this.handler = handler;
        this.degreeSequence = degreeSequence;
    }
    
    public void generate() {
        
    }
    
    public void augment(Augmentation<ResidualDegreeGraphPair> parent) {
        IntGraph graph = parent.getAugmentedObject().getGraph();
        
    }

}
