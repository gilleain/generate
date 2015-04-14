package cpa;

/**
 * An augmentation of an object.
 * 
 * @author maclean
 *
 * @param <T> the object to augment
 */
public interface Augmentation<T> {
    
    /**
     * @return the resulting augmented object
     */
    public T getAugmentedObject();
    
    /**
     * @return true if the augmentation is canonical
     */
    public boolean isCanonical();

}
