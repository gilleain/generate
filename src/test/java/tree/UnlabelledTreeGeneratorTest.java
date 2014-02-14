package tree;

import graph.model.Graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class UnlabelledTreeGeneratorTest {
	
	public static final String OUT_DIR = "output/trees/prufer";
	
	public void testOnNVerticesWithCert(int n) {
		Map<String, Graph> treeMap = UnlabelledTreeGenerator.generateWithCertificate(n);
		int count = 0;
		for (String cert : treeMap.keySet()) {
			Graph tree = treeMap.get(cert);
			System.out.println(count + "\t" + tree.getSortedEdgeString() + "\t" + cert);
			count++;
		}
	}
	
	public void testOnNVertices(int n) {
		List<Graph> trees = UnlabelledTreeGenerator.generate(n);
		int count = 0;
		for (Graph graph : trees) {
			System.out.println(count + "\t" + graph.getSortedEdgeString());
			count++;
		}
	}
	
	public void toFileOnNVertices(int n, String filename) throws IOException {
		File dir = new File(OUT_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir, filename)));
		List<Graph> trees = UnlabelledTreeGenerator.generate(n);
		for (Graph graph : trees) {
			writer.write(graph.getSortedEdgeString());
			writer.newLine();
		}
		writer.close();
	}
	
	@Test
	public void testFour() {
		testOnNVertices(4);
	}
	
	@Test
	public void testFive() {
		testOnNVertices(5);
	}
	
	@Test
	public void testSix() {
		testOnNVertices(6);
	}
	
	@Test
	public void testFourWithCert() {
		testOnNVerticesWithCert(4);
	}
	
	@Test
	public void testFiveWithCert() {
		testOnNVerticesWithCert(5);
	}
	
	@Test
	public void testSixWithCert() {
		testOnNVerticesWithCert(6);
	}
	
	@Test
	public void testSevenWithCert() {
		testOnNVerticesWithCert(7);
	}
	
	@Test
	public void testEightWithCert() {
		testOnNVerticesWithCert(8);
	}
	
	@Test
	public void testNineWithCert() {
		testOnNVerticesWithCert(9);
	}
	
	@Test
	public void toFileFour() throws IOException {
		toFileOnNVertices(4, "fours.txt");
	}
	
	@Test
	public void toFileFive() throws IOException {
		toFileOnNVertices(5, "fives.txt");
	}
	
	@Test
	public void toFileSix() throws IOException {
		toFileOnNVertices(6, "sixes.txt");
	}
	
	@Test
	public void toFileSeven() throws IOException {
		toFileOnNVertices(7, "sevens.txt");
	}
	
	@Test
	public void toFileEight() throws IOException {
		toFileOnNVertices(8, "eights.txt");
	}

}
