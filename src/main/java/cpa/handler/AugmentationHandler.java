package cpa.handler;

import cpa.Augmentation;

/**
 * Handle partial results.
 *  
 * @author maclean
 *
 * @param <T>
 */
public interface AugmentationHandler<T> {
    
    public void handleCanonical(Augmentation<T> augmentation);
    
    public void handleNonCanonical(Augmentation<T> augmentation);

}
