package test.hybrid;

import generate.handler.FileOutputHandler;
import generate.handler.SystemOutHandler;
import generate.handler.TreeHandler;
import hybrid.HybridGenerator;
import model.Graph;

import org.junit.Test;

public class HybridGeneratorTest {

	@Test
	public void test4FromSingleEdge() {
		Graph initial = new Graph("0:1");
//		HybridGenerator generator = new HybridGenerator(new SystemOutHandler(4, false));
		HybridGenerator generator = new HybridGenerator(new TreeHandler(4));
//		HybridGenerator generator = new HybridGenerator(new FileOutputHandler("output/hybrid/four_x.txt", 4));
		generator.generate(initial, 4);
	}
	
	@Test
	public void test5FromSingleEdge() {
		Graph initial = new Graph("0:1");
//		HybridGenerator generator = new HybridGenerator(new SystemOutHandler(5, false));
		HybridGenerator generator = new HybridGenerator(new FileOutputHandler("output/hybrid/five_x.txt", 5));
		generator.generate(initial, 5);
	}
	
	@Test
	public void test6FromSingleEdge() {
		Graph initial = new Graph("0:1");
//		HybridGenerator generator = new HybridGenerator(new SystemOutHandler(6, false));
		HybridGenerator generator = new HybridGenerator(new FileOutputHandler("output/hybrid/six_x.txt", 6));
		generator.generate(initial, 6);
	}
	
	@Test
	public void childrenOf6Line() {
		Graph initial = new Graph("0:1,1:2,2:3,3:4,4:5");
		HybridGenerator generator = new HybridGenerator(new SystemOutHandler(6, false));
		generator.generate(initial, 6);
	}
	
	@Test
	public void test7FromSingleEdge() {
		Graph initial = new Graph("0:1");
//		HybridGenerator generator = new HybridGenerator(new SystemOutHandler(7, true));
//		HybridGenerator generator = new HybridGenerator(new FileOutputHandler("output/hybrid/seven_4.txt", 7));
		HybridGenerator generator = new HybridGenerator(new FileOutputHandler("output/hybrid/seven_4.txt", 4, 7));
		generator.generate(initial, 7);
	}
	
	@Test
	public void test8FromSingleEdge() {
		Graph initial = new Graph("0:1");
//		HybridGenerator generator = new HybridGenerator(new SystemOutHandler(8, true));
//		HybridGenerator generator = new HybridGenerator(new FileOutputHandler("output/hybrid/eight_x.txt", 8));
		HybridGenerator generator = new HybridGenerator(new FileOutputHandler("output/hybrid/eight_3.txt", 3, 8));
		generator.generate(initial, 8);
	}

}
