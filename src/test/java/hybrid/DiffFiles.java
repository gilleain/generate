package hybrid;

import graph.model.IntGraph;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import util.GraphFileDiff;

public class DiffFiles {
	
	@Test
	public void diffSixes() throws IOException {
		List<IntGraph> diff = GraphFileDiff.diff("output/hybrid/six_x.txt", "output/nauty/sixes_nauty.txt");
		int count = 0;
		for (IntGraph graph : diff) {
			System.out.println(count + "\t" + graph);
			count++;
		}
	}

}
