package tree;

import graph.model.IntGraph;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class WROMTreeGeneratorTest {
	
	public void generateOnN(int n, int exp) {
		int count = 0;
		for (IntGraph tree : WROMTreeGenerator.generate(n)) {
			System.out.println(count + "\t" + tree.getSortedEdgeString());
			count++;
		}
		Assert.assertEquals(exp, count);
	}
	
	@Test
    public void generateOn4() {
        generateOnN(4, 2);
    }
	
	@Test
	public void generateOn5() {
		generateOnN(5, 3);
	}
	
	@Test
	public void generateOn6() {
		generateOnN(6, 6);
	}
	
	@Test
	public void generateOn7() {
		generateOnN(7, 11);
	}
	
	@Test
    public void generateOn8() {
        generateOnN(8, 23);
    }
	
	@Test
    public void generateOn9() {
        generateOnN(9, 47);
    }
	
	@Test
    public void generateOn10() {
        generateOnN(10, 106);
    }
	
	@Test
    public void generateOn11() {
        generateOnN(11, 235);
    }
	
	@Test
    public void generateOn12() {
        generateOnN(12, 551);
    }
	
	@Test
	public void initialSeqTest() {
		System.out.println(Arrays.toString(WROMTreeGenerator.makeInitialSeq(5)));
	}

}
