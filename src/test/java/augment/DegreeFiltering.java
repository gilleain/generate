package augment;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import filter.DegreeSequenceFilter;
import graph.model.Graph;
import graph.model.GraphFileReader;

public class DegreeFiltering {

	@Test
	public void filterSixes() throws IOException {
		DegreeSequenceFilter filter = new DegreeSequenceFilter(3, 3, 3, 3, 3, 3);
		GraphFileReader file = new GraphFileReader(new FileReader("output/mckay/six_4.txt"));
		for (Graph graph : file) {
			if (filter.filter(graph)) {
				System.out.println(graph);
			}
		}
	}
	
	@Test
	public void filterEights() throws IOException {
		DegreeSequenceFilter filter = new DegreeSequenceFilter(3, 3, 3, 3, 3, 3, 3, 3);
		GraphFileReader file = new GraphFileReader(new FileReader("output/mckay/eight_x.txt"));
		for (Graph graph : file) {
			if (filter.filter(graph)) {
				System.out.println(graph);
			}
		}
	}
	
	@Test
	public void filterTens() throws IOException {
		DegreeSequenceFilter filter = new DegreeSequenceFilter(3, 3, 3, 3, 3, 3, 3, 3, 3, 3);
		GraphFileReader file = new GraphFileReader(new FileReader("output/mckay/ten_3.txt"));
		for (Graph graph : file) {
			if (filter.filter(graph)) {
				System.out.println(graph);
			}
		}
	}
	
	@Test
	public void filterTwelves() throws IOException {
		DegreeSequenceFilter filter = new DegreeSequenceFilter(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3);
		GraphFileReader file = new GraphFileReader(new FileReader("output/mckay/twelve_3.txt"));
		for (Graph graph : file) {
			if (filter.filter(graph)) {
				System.out.println(graph);
			}
		}
	}

}
