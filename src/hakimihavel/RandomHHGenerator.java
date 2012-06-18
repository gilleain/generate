package hakimihavel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Graph;

/**
 * Generates a random graph with a particular degree sequence.
 * 
 * @author maclean
 *
 */
public class RandomHHGenerator extends BaseHHGenerator {

	private Random random;
	
	public RandomHHGenerator() {
		this.random = new Random();
	}
	
	public Graph generate(int[] degreeSequence) {
		if (HakimiHavelGenerator.isGraphical(degreeSequence)) {
			Graph g = new Graph();
			generate(degreeSequence, g);
			return g;
		} else {
		    return null;
		}
	}
	
	private void generate(int[] degreeSequence, Graph g) {
//	    System.out.println(g + "\t" + HakimiHavelGenerator.isGraphical(reduce(degreeSequence, g)));
	    if (isComplete(degreeSequence, g)) return;
	    
		int i = getRandomVertex(degreeSequence, g, 0);
		int j = getRandomVertex(degreeSequence, g, 0);
		if (i == j || i == -1 || j == -1) {
			generate(degreeSequence, g);
		} else {
		    List<Integer> X = g.getConnected(i);
		    if (X == null) {
		        X = new ArrayList<Integer>();
		    }
			if (isGraphicalWithConstraint(reduce(reduce(degreeSequence, g), i, j), i, X)) {
			    g.makeEdge(i, j);
			    generate(degreeSequence, g);
			} else {
			    generate(degreeSequence, g);
			}
		}
	}
	
	private boolean isComplete(int[] degreeSequence, Graph g) {
	    int n = degreeSequence.length;
	    for (int i = 0; i < n; i++) {
            if (g.degree(i) < degreeSequence[i]) {
                return false;
            }
        }
	    return true;
	}

	private int getRandomVertex(int[] degreeSequence, Graph g, int from) {
	    int n = degreeSequence.length;
	    List<Integer> candidates = new ArrayList<Integer>();
	    for (int i = from; i < n; i++) {
	        if (g.degree(i) < degreeSequence[i]) {
	            candidates.add(i);
	        }
	    }
	    
	    if (candidates.size() == 0) {
	        return -1;
	    } else {
	        return candidates.get(random.nextInt(candidates.size()));
	    }
	}

}
