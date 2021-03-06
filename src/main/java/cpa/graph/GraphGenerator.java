package cpa.graph;

import cpa.Augmentation;
import cpa.handler.GenerationHandler;
import graph.model.IntGraph;

public class GraphGenerator {
    
    private final GraphVertexAugmentor graphVertexAugmentor;
    
    private final int max;
    
    private final GenerationHandler handler;
    
    public GraphGenerator(GenerationHandler handler, int max) {
        this.max = max;
        this.handler = handler;
        this.graphVertexAugmentor = new GraphVertexAugmentor();
    }
    
    public void generate() {
        augment(graphVertexAugmentor.getInitial());
    }
    
    public void generateFrom(IntGraph start) {
        augment(new GraphVertexAugmentation(start));
    }
    
    public void augment(Augmentation<IntGraph> parent) {
        IntGraph graph = parent.getAugmentedObject(); 
        if (graph.getVertexCount() == max) {
            handler.handle(graph);
        } else {
            for (Augmentation<IntGraph> augmentation : graphVertexAugmentor.augment(parent)) {
                if (augmentation.isCanonical()) {
                    augment(augmentation);
                }
            }
        }
    }
}
