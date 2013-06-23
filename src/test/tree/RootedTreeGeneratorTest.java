package test.tree;

import graph.model.Graph;
import graph.tree.TreeCertificateMaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tree.RootedTreeGenerator;

public class RootedTreeGeneratorTest {
	
	@Test
	public void treeFromSeqSingleTest() {
		int [] seq = new int[] { 0, 1, 2, 3, 3};
		Graph tree = RootedTreeGenerator.treeFromSeq(seq);
		System.out.println(Arrays.toString(seq) + "\t" + tree.getSortedEdgeString());
	}
	
	@Test
	public void treeFromSeqAllFivesTest() {
		int[][] seqs = new int[9][5];
		seqs[0] = new int[] { 0, 1, 2, 3, 4};
		seqs[1] = new int[] { 0, 1, 2, 3, 3};
		seqs[2] = new int[] { 0, 1, 2, 3, 2};
		seqs[3] = new int[] { 0, 1, 2, 3, 1};
		seqs[4] = new int[] { 0, 1, 2, 2, 2};
		seqs[5] = new int[] { 0, 1, 2, 2, 1};
		seqs[6] = new int[] { 0, 1, 2, 1, 2};
		seqs[7] = new int[] { 0, 1, 2, 1, 1};
		seqs[8] = new int[] { 0, 1, 1, 1, 1};
		for (int i = 0; i < seqs.length; i++) {
			int[] seq = seqs[i];
			Graph tree = RootedTreeGenerator.treeFromSeq(seq);
			System.out.println(i + "\t" + Arrays.toString(seq) + "\t" + tree.getSortedEdgeString());
		}
	}
	
	public void generateOnN(int n) {
		int count = 0;
		for (Graph tree : RootedTreeGenerator.generate(n)) {
			System.out.println(count + "\t" + tree);
			count++;
		}
		
	}
	
	public void generatePlusFilterOnN(int n) {
		int count = 0;
		List<String> certs = new ArrayList<String>();
		for (Graph tree : RootedTreeGenerator.generate(n)) {
			String cert = TreeCertificateMaker.treeToCertificate(tree);
			if (certs.contains(cert)) {
				continue;
			} else {
				certs.add(cert);
				System.out.println(count + "\t" + tree);
				count++;
			}
		}
		
	}
	
	@Test
	public void generateOn5() {
		generateOnN(5);
	}
	
	@Test
	public void generateOn6() {
		generateOnN(6);
	}
	
	@Test
	public void generateOn7() {
		generateOnN(7);
	}
	
	@Test
	public void generateOn8() {
		generateOnN(8);
	}
	
	@Test
	public void generateOn9() {
		generateOnN(9);
	}
	
	@Test
	public void generateOn10() {
		generateOnN(10);
	}
	
	@Test
	public void generatePlusFilterOn12() {
		generatePlusFilterOnN(12);
	}

}
