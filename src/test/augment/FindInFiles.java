package test.augment;

import graph.model.Graph;

import java.io.IOException;

import org.junit.Test;

import tools.GraphFileSearch;

public class FindInFiles {
	
	@Test
	public void findNines() throws IOException {
		Graph g = new Graph("0:1,0:2,0:3,1:2,3:4,4:5,5:6,6:7");
		Graph inNauty = GraphFileSearch.get(g, "output/nauty/eights_nauty.txt");
		System.out.println("in nauty? =\t" + inNauty);
		Graph inMine = GraphFileSearch.get(g, "output/mckay/eight_x.txt");
//		boolean inMine = GraphFileSearch.contains(g, "output/mckay/nine_4.txt");
		System.out.println("in mine? =\t" + inMine);
	}
	
	
	@Test
	public void findNines_bug() throws IOException {
		Graph g = new Graph("[0:1, 0:2, 0:3, 0:5, 1:2, 1:4, 1:7, 2:6, 2:8, 3:4, 3:6, 4:5, 5:8, 6:7, 7:8]");
		Graph inV1 = GraphFileSearch.get(g, "output/mckay/nine_4.txt");
		System.out.println("in v1? =\t" + inV1);
		Graph inV2 = GraphFileSearch.get(g, "output/mckay/nine_4_v2.txt");
		System.out.println("in v2? =\t" + inV2);
	}
	
	
	@Test
	public void findATwelve() throws IOException {
		Graph g = new Graph("0:1,0:2,0:7,1:2,1:9,2:3,3:4,3:5,4:5,4:6,5:8,6:7,6:10,7:10,8:9,8:11,9:11,10:11");
		for (int i = 0; i < g.getVertexCount(); i++) { assert g.degree(i) == 3; }
		Graph inD3 = GraphFileSearch.get(g, "output/nauty/twelves__three_nauty.txt");
//		Graph inD3 = GraphFileSearch.get(g, "output/mckay/twelve_3.txt");
		System.out.println("in nauty? =\t" + inD3);
	}
}
