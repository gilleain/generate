package hybrid;

import graph.model.Graph;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import util.GraphFileDiff;

public class DiffFiles {
	
	@Test
	public void diffSixes() throws IOException {
		List<Graph> diff = GraphFileDiff.diff("output/hybrid/six_x.txt", "output/nauty/sixes_nauty.txt");
		int count = 0;
		for (Graph graph : diff) {
			System.out.println(count + "\t" + graph);
			count++;
		}
	}

}
