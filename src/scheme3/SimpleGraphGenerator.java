package scheme3;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Edge;
import model.Graph;
import model.GraphBuilder;
import model.GraphSignature;
import model.VertexSignature;
import signature.AbstractVertexSignature;
import signature.ColoredTree;

public class SimpleGraphGenerator {
	
	private GeneratorHandler handler;
	
	private int count;
	
	public SimpleGraphGenerator() {
		this(new SystemOutHandler());
	}
	
	public SimpleGraphGenerator(GeneratorHandler handler) {
		this.handler = handler;
	}
	
	public void extend(Graph g, int n) {
		int l = g.getVertexCount(); 

		GraphSignature gSignature = new GraphSignature(g);
		String gCanonicalLabel = getCanonicalLabel(gSignature);
		Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
		int max = Math.min(l, n - 1);
		for (int start = 0; start < l; start++) {
			for (int end = start + 1; end <= max; end++) {
				if (g.isConnected(start, end)) {
					continue;
				} else {
				    Graph h = g.makeNew(start, end);
				    GraphSignature signature = new GraphSignature(h);
		            String canonicalLabel = getCanonicalLabel(signature);
		            if (children.containsKey(canonicalLabel)) {
		                continue;
		            } else {
		                children.put(canonicalLabel, signature);
		            }
				}
			}
		}
		
		for (String gPrimeCanonLabel : children.keySet()) {
		    GraphSignature gPrimeSignature = children.get(gPrimeCanonLabel);
		    Graph gPrime = gPrimeSignature.getGraph();
		    Graph canonGPrime = reconstruct(gPrimeCanonLabel);
			if (isCanonicalAugmentation(canonGPrime, gPrimeSignature, gPrime, gCanonicalLabel)) {
			    if (gPrime.getVertexCount() == n && gPrime.isConnected()) {
			        handler.handle(g, gPrime);
		            count++;
			    }
			    extend(gPrime, n);
			}
		}
		if (l < n - 2) {
		    Graph h = g.makeNew(l, l + 1);
		    extend(h, n);
		}
	}
	
	public String getCanonicalLabel(GraphSignature gSig) {
	    Graph g = gSig.getGraph();
	    List<List<Integer>> components = g.getComponents();
	    if (components.size() == 1) {
	        return gSig.toCanonicalString();
	    } else {
	        StringBuffer fullLabel = new StringBuffer();
	        for (List<Integer> component : components) {
	            String maxComponentLabel = null;
	            for (int i : component) {
	                String sigStringForI = gSig.signatureStringForVertex(i);
	                if (maxComponentLabel == null || maxComponentLabel.compareTo(sigStringForI) < 0) {
	                    maxComponentLabel = sigStringForI;
	                }
	            }
	            if (fullLabel.length() == 0) {
	                fullLabel.append(maxComponentLabel);
	            } else {
	                fullLabel.append(".").append(maxComponentLabel);
	            }
	        }
	        return fullLabel.toString();
	    }
	}
	 
	
	public int[] getLabels(GraphSignature gSig) {
	    Graph g = gSig.getGraph();
	    List<List<Integer>> components = g.getComponents();
	    if (components.size() == 1) {
	        int[] extLabels = gSig.getCanonicalLabels();
	        int[] labels = new int[g.vsize()];
	        // XXX this is effectively reversing the permutation again - don't reverse in first place? 
	        for (int i = 0; i < g.vsize(); i++) {
	            labels[extLabels[i]] = i;
	        }
	        return labels;
	    } else {
	        int[] labels = new int[g.vsize()];
	        int componentOffset = 0;
	        for (List<Integer> component : components) {
	            AbstractVertexSignature maxSigForComponent = null;
	            String maxSigStrForComponent = null;
	            for (int i : component) {
	                AbstractVertexSignature sigForI = gSig.signatureForVertex(i);
	                String sigForIStr = sigForI.toCanonicalString();
	                if (maxSigForComponent == null || maxSigStrForComponent.compareTo(sigForIStr) < 0) {
	                    maxSigForComponent = sigForI;
	                    maxSigStrForComponent = sigForIStr;
	                }
	            }
	            int[] componentLabels = maxSigForComponent.getCanonicalLabelling(g.vsize());
	            for (int i : component) {
	                int j = componentLabels[i];
	                labels[componentOffset + j] = i;
	            }
	            componentOffset += component.size();
	        }
//	        System.out.println(java.util.Arrays.toString(labels));
	        return labels;   
	    }
	}
	
	public boolean isCanonicalAugmentation(Graph parent, Graph child) {
	    GraphSignature childSig = new GraphSignature(child);
	    Graph canonChild = getCanonicalForm(child);
	    String parentLabel = new GraphSignature(parent).toCanonicalString();
	    return isCanonicalAugmentation(canonChild, childSig, child, parentLabel);
	}
	
	public boolean isCanonicalAugmentation(
	        Graph canonGPrime, GraphSignature gPrimeSig, Graph gPrime, String gCanonicalLabel) {
	    Edge lastEdge = canonGPrime.edges.get(canonGPrime.esize() - 1);
	    int[] labels = getLabels(gPrimeSig);
	    int lA = labels[lastEdge.a];
	    int lB = labels[lastEdge.b];
	    Graph gPrimeMinusE = gPrime.remove(gPrime.getEdge(lA, lB));
        String gPrimeMinusESignatureString = getCanonicalLabel(new GraphSignature(gPrimeMinusE));
        return gPrimeMinusESignatureString.equals(gCanonicalLabel);
	}
	
	public Graph getCanonicalForm(Graph g) {
	    String canString = new GraphSignature(g).toCanonicalString();
	    return reconstruct(canString);
	}
	
	public Graph reconstruct(String canonicalLabel) {
		ColoredTree tree = VertexSignature.parse(canonicalLabel);
		GraphBuilder builder = new GraphBuilder();
		builder.makeFromColoredTree(tree);
		return builder.getProduct();
	}

}
