package scheme3;

import graph.model.Graph;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import util.GraphFileDiff;

public class DiffFiles {
    
    public void diff(String fileA, String fileB) throws IOException {
        List<Graph> diff = GraphFileDiff.diff(fileA, fileB);
        int count = 0;
        for (Graph graph : diff) {
            String degSeq = Arrays.toString(graph.degreeSequence(true));
            System.out.println(count + "\t" + degSeq + "\t" + graph);
            count++;
        }
    }
	
	@Test
	public void diffSevens() throws IOException {
		diff("output/scheme3/sevens.txt", "output/mckay/seven_x.txt");
	}
	
}
