package tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import filter.MaxDegreeFilter;
import graph.model.Graph;
import graph.model.GraphFileReader;

public class DegreeThreeFilter {
	
	public static final String IN_DIR = "output/trees/prufer";
	
	public void filter(String filename) throws FileNotFoundException {
		MaxDegreeFilter filter = new MaxDegreeFilter(3);
		GraphFileReader file = new GraphFileReader(new FileReader(new File(IN_DIR, filename)));
		for (Graph tree : file) {
			System.out.println(filter.filter(tree) + "\t" + tree.getSortedEdgeString());
		}
	}
	
	@Test
	public void filterFours() throws FileNotFoundException {
		filter("fours.txt");
	}
	
	@Test
	public void filterFives() throws FileNotFoundException {
		filter("fives.txt");
	}
	
	@Test
	public void filterSixes() throws FileNotFoundException {
		filter("sixes.txt");
	}
	
	@Test
	public void filterSevens() throws FileNotFoundException {
		filter("sevens.txt");
	}


}
