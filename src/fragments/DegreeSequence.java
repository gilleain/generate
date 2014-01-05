package fragments;

public class DegreeSequence {
    
    private int[] degrees;
    
    public int difference(int position, int value) {
        return degrees[position] - value;
    }

    public int size() {
        return degrees.length;
    }
    
}
