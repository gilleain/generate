package scheme3;

import graph.model.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import util.GraphDuplicateChecker;
import util.GraphFileDiff;

public class DuplicateCheckTest {
	
	private List<Graph> readFile(String filepath) throws IOException {
		List<Graph> graphs = new ArrayList<Graph>();
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
		while ((line = reader.readLine()) != null) {
//			String[] bits = line.split("\\t");
//			graphs.add(new Graph(bits[1]));
		    graphs.add(new Graph(line));
		}
		reader.close();
		return graphs;
	}
	
	@Test
	public void testFives() throws IOException {
		String filepath = "output/scheme3/fives.txt";
		List<Graph> graphs = readFile(filepath);
		GraphDuplicateChecker checker = new GraphDuplicateChecker();
		checker.checkForDuplicates(graphs);
	}
	
	@Test
    public void testFivesVSymConn() throws IOException {
        String filepath = "output/scheme3/fives_v_conn_sym.txt";
        List<Graph> graphs = readFile(filepath);
        GraphDuplicateChecker checker = new GraphDuplicateChecker();
        checker.checkForDuplicates(graphs);
    }
	
	@Test
	public void testSixes() throws IOException {
		String filepath = "output/scheme3/sixes.txt";
		List<Graph> graphs = readFile(filepath);
		GraphDuplicateChecker checker = new GraphDuplicateChecker();
		checker.checkForDuplicates(graphs);
	}
	
	@Test
    public void testEights() throws IOException {
//	    String filepath = "output/scheme3/eights.txt";
        String filepath = "output/scheme3/eights_sym.txt";
        List<Graph> graphs = readFile(filepath);
        GraphDuplicateChecker checker = new GraphDuplicateChecker();
        checker.checkForDuplicates(graphs);
    }
	
	@Test
	public void diffFives() throws IOException {
		List<Graph> diff = GraphFileDiff.diff("output/scheme3/fives.txt", "output/mckay/five_x.txt");
		int count = 0;
		for (Graph graph : diff) {
			System.out.println(count + "\t" + graph);
			count++;
		}
	}

}
