package cpa;

import java.util.List;

/**
 * Augments an object to produce a list of augmentations.
 * 
 * @author maclean
 *
 * @param <T>
 */
public interface Augmentor<T> {
    
    public List<Augmentation<T>> augment(Augmentation<T> parent);

}
