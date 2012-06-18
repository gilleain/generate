package test.augment;

import java.io.FileNotFoundException;

import model.GraphFileReader;

import org.junit.Test;

import tools.GraphDuplicateChecker;

public class DupsTest {
	
	@Test
	public void testSix_x() throws FileNotFoundException {
		GraphDuplicateChecker checker = new GraphDuplicateChecker();
		checker.checkForDuplicates(GraphFileReader.readAll("output/mckay/six_4.txt"));
//		checker.checkForDuplicates(GraphFileReader.readAll("output/mckay/six_x.txt"));
	}

}
