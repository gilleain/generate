package test.arrangement;

import model.Edge;
import model.Graph;

import org.junit.Test;

import arrangement.ExhaustiveExtender;

public class ExhaustiveExtenderTest {
	
	public boolean verticesOK(Graph graph, int expected) {
		for (int i = 0; i < expected; i++) {
			if (graph.degree(i) == 0) {
				return false;
			}
		}
		return true;
	}
	
	public boolean edgesOK(Graph graph) {
		for (int i = 0; i < graph.getVertexCount(); i++) {
			if (graph.degree(i) != 1) {
				return false;
			}
		}
		return true;
	}
	
	public boolean noncrossing(Graph graph) {
		for (int i = 0; i < graph.edges.size(); i++) {
			Edge eI = graph.edges.get(i);
			for (int j = i + 1; j < graph.edges.size(); j++) {
				Edge eJ = graph.edges.get(j);
//				System.out.println("testing " + eI + " " + eJ);
				if (eI.a < eJ.a && eI.b < eJ.a) {
//					System.out.println("abAB " + eI + " " + eJ);
					continue;			// abAB
				} else if (eI.a < eJ.a && eI.b > eJ.b) {
//					System.out.println("aABb " + eI + " " + eJ);
					continue;		// aABb
				} else if (eI.a < eJ.a && eI.b > eJ.a && eI.b < eJ.b) {
//					System.out.println("fail " + eI + " " + eJ);
					return false;	// aAbB
				}
			}
		}
		return true;
	}
	
	@Test
	public void hexagonTest() {
		Graph parent = new Graph("0:1,2:5,3:4");
		for (Graph child : ExhaustiveExtender.extend(parent)) {
			System.out.println(verticesOK(child, 6) + "\t" 
							 + edgesOK(child) + "\t"
							 + noncrossing(child) + "\t"
							 + child);
		}
	}
	
	@Test
	public void noncrossingTest() {
		Graph crossing = new Graph("0:1, 2:6, 3:7, 4:5");
		noncrossing(crossing);
	}
	

}
