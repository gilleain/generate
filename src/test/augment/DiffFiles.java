package test.augment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Graph;

import org.junit.Test;

import tools.GraphFileDiff;

public class DiffFiles {
	
	@Test
	public void diffFours() throws IOException {
		List<Graph> diff = GraphFileDiff.diff("output/nauty/fours_nauty.txt", "output/mckay/four_x.txt");
		int count = 0;
		for (Graph graph : diff) {
			System.out.println(count + "\t" + graph);
			count++;
		}
	}
	
	@Test
	public void diffFives() throws IOException {
//		List<Graph> diff = GraphFileDiff.diff("output/nauty/fives_nauty.txt", "output/mckay/five_x.txt");
		List<Graph> diff = GraphFileDiff.diff("output/mckay/five_x.txt", "output/nauty/fives_nauty.txt");
		int count = 0;
		for (Graph graph : diff) {
			System.out.println(count + "\t" + graph);
			count++;
		}
	}
	
	@Test
	public void diffEights() throws IOException {
		List<Graph> diff = GraphFileDiff.diff("output/nauty/eights_nauty.txt", "output/mckay/eight_x.txt");
//		List<Graph> diff = GraphFileDiff.diff("output/mckay/eight_x.txt", "output/nauty/eights_nauty.txt");
		int count = 0;
		for (Graph graph : diff) {
			System.out.println(count + "\t" + graph);
			count++;
		}
	}
	
	@Test
	public void diffNines() throws IOException {
//		List<Graph> diff = GraphFileDiff.diff("output/nauty/nines_nauty.txt", "output/mckay/nine_4.txt");
		List<Graph> diff = GraphFileDiff.diff("output/mckay/nine_4.txt", "output/nauty/nines_nauty.txt");
		FileWriter outputFile = new FileWriter(new File("output/diff/nines_diff.txt")); 
		int count = 0;
		for (Graph graph : diff) {
			System.out.println(count + "\t" + graph);
			outputFile.write(count + "\t" + graph + "\n");
			count++;
		}
		outputFile.close();
	}
	
	@Test
	public void diffNines_bug() throws IOException {
		List<Graph> diff = GraphFileDiff.diff("output/mckay/nine_4_v2.txt", "output/mckay/nine_4.txt");
		int count = 0;
		for (Graph graph : diff) {
			System.out.println(count + "\t" + graph);
			count++;
		}
	}

}
