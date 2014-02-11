package test.tree;

import graph.model.Graph;
import graph.model.GraphSignature;

import java.util.Arrays;

import org.junit.Test;

import tree.PruferGenerator;

public class PruferGeneratorTest {
	
	public void testTree(Graph tree) {
		int[] seq = PruferGenerator.treeToSequence(tree);
		System.out.println(Arrays.toString(seq) + " " + tree);
	}
	
	public void testSequence(int[] seq, int n) {
		Graph tree = PruferGenerator.sequenceToTree(seq, n);
		System.out.println(Arrays.toString(seq) + " " + tree.getSortedEdgeString());
	}
	
	@Test
	public void cages_page_93_test_seqToTree() {
		int[] seq = new int[] {0, 8, 6, 8, 4, 6, 2, 1, 0};
		testSequence(seq, 8);
	}
	
	@Test
	public void cages_page_93_test_treeToSeq() {
		Graph tree = new Graph("1:2,2:6,3:8,4:6,4:8,5:6,7:8");
		testTree(tree);
	}
	
	@Test
	public void testUnrankingOn4Vertices() {
		int n = 4;
		for (int rank = 0; rank < 16; rank++) {
			int[] seq = PruferGenerator.rankToSequence(rank, n);
			System.out.println(rank + "\t" + Arrays.toString(seq));
		}
	}
	
	@Test
	public void testRankingOn4Vertices() {
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 4; j++) {
				int[] seq = new int[] { 0, i, j, 0 };
				int rank = PruferGenerator.sequenceToRank(seq);
				System.out.println(rank + "\t" + Arrays.toString(seq));
			}
		}
	}
	
	public void testGeneratingOnNVertices(int n) {
		int max = (int) Math.pow(n, n - 2);
		for (int rank = 0; rank < max; rank++) {
			int[] seq = PruferGenerator.rankToSequence(rank, n);
			Graph tree = PruferGenerator.sequenceToTree(seq, n);
			System.out.println(rank + "\t" 
							+ Arrays.toString(seq) + "\t" 
							+ tree.getSortedEdgeString());
		}
	}
	
	public void testCanonicalVerificationOnNVertices(int n) {
		int max = (int) Math.pow(n, n - 2);
		for (int rank = 0; rank < max; rank++) {
			int[] seq = PruferGenerator.rankToSequence(rank, n);
			Graph tree = PruferGenerator.sequenceToTree(seq, n);
			if (new GraphSignature(tree).isCanonicallyLabelled()) {
				System.out.println(rank + "\t" + tree.getSortedEdgeString());
			}
		}
	}
	
	@Test
    public void testGeneratingOn3Vertices() {
        testGeneratingOnNVertices(3);
    }
	
	@Test
	public void testGeneratingOn4Vertices() {
		testGeneratingOnNVertices(4);
	}
	
	@Test
	public void testGeneratingOn5Vertices() {
		testGeneratingOnNVertices(5);
	}
	
	@Test
	public void testGeneratingOn6Vertices() {
		testGeneratingOnNVertices(6);
	}
	
	@Test
	public void testCanonicalVerificationOn4Vertices() {
		testCanonicalVerificationOnNVertices(4);
	}
	
	@Test
	public void testCanonicalVerificationOn5Vertices() {
		testCanonicalVerificationOnNVertices(5);
	}
	
	@Test
	public void testCanonicalVerificationOn6Vertices() {
		testCanonicalVerificationOnNVertices(6);
	}

}
